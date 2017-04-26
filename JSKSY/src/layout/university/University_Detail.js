'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	View,
	PixelRatio,
	Animated,
	ScrollView,
	TouchableOpacity,
	Text,
	Image} from 'react-native';

import GiftedListView from 'react-native-gifted-listview';
import {BUS_700201,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
import University_Detail_One from './University_Detail_One';
import University_Detail_Two from './University_Detail_Two';
import University_Detail_Three from './University_Detail_Three';

//按钮同步锁
var syncClick = true;
//当前tab
var currentTab = 0;
export default class University_Detail extends Component{

	constructor(props) {
	  	super(props);
	  	//TAB栏宽度
	  	this.tabWidth = global.windowWidth/3;
	  	//Tab栏下的滑块宽度
	  	this.tabLineWidth = global.windowWidth/5;
	  	//每个滑块距离左侧的距离
	  	this.tabLeftPadding = (this.tabWidth - this.tabLineWidth)/2;
	  	//数组 tab滑块的三个x轴位置
	  	this.tabLineScroll = [this.tabLeftPadding,this.tabWidth+this.tabLeftPadding,this.tabWidth*2+this.tabLeftPadding];
	  	this.state = {
	  		tabLineAnimate: new Animated.Value(-this.tabLineWidth),
	  		contentHeight:1000,   //Fragemnt1的内容体高度，需要根据数据动态计算
	  		detail:{}
	  	};
	}

   _tabLineAnimateListener = (x)=>{
	   	Animated.timing(    
	       this.state.tabLineAnimate, 
	       {toValue: x},
	     ).start(); 
   };

   	//详情请求回调
	_BUS_700201_CB(object,json){
		console.log(json);
		if (json.retcode === '000000') {
			object.setState({
				detail:json.detail
			})
		}else{

		}
	}

	//详情请求
	_BUS_700201_REQ(){
		var params={
			encrypt:'none'
		}
		netClientTest(this,BUS_700201,this._BUS_700201_CB,params);
	}

   componentDidMount() {
   		this._tabLineAnimateListener(this.tabLineScroll[0]);
   		this._BUS_700201_REQ();
   }

	_onClick(flag){
		if(syncClick){
			switch(flag){
			case 0:
				if (currentTab !== 0) {
					syncClick = false;
					this.refs.scroll.scrollTo({x: 0, y: 0, animated: true});
				}
				break;
			case 1:
				if (currentTab !== 1) {
					syncClick = false;
					this.refs.scroll.scrollTo({x: global.windowWidth, y: 0, animated: true});
				}
				break;
			case 2:
			if (currentTab !== 2) {
					syncClick = false;
					this.refs.scroll.scrollTo({x: global.windowWidth*2, y: 0, animated: true});
				}
				break;
			}
		}
	}

	//Scroll回调的滑动函数
	_onMomentumScrollEnd(e, state, context) {
		//此处的e.nativeEvent.contentOffset.x会得到一个非整小数，如不Math则操作过快的情况下报错
		currentTab =  Math.round(e.nativeEvent.contentOffset.x/global.windowWidth);
		syncClick = true;
		this._tabLineAnimateListener(this.tabLineScroll[currentTab]);
	  }

	_onBackPressed(){
		this.props.navigator.pop();
	}

	render(){
		return(
			<ScrollView style={{backgroundColor:'white'}}>
				<Image 
					style={{width:global.windowWidth, height:global.windowWidth*3/5,backgroundColor:'#d5d5d5'}}
					source={{uri:this.state.detail.pic}}
					>
					<TouchableOpacity
						style={{position:'absolute',left:20,top:40}}
						onPress={()=>this._onBackPressed()}>
						<Image
							style={{width:35,height:35}}
							source={require('image!school_btn_back')}/>
					</TouchableOpacity>
					<View style={{position:'absolute',bottom:0,backgroundColor:'rgba(0, 0, 0, 0.4)',width:global.windowWidth,height:80,flexDirection:'row',alignItems:'center'}}>
						<View
							style={{width:60,height:60,borderRadius:30,backgroundColor:'white',marginLeft:11,marginRight:13,alignItems:'center',justifyContent:'center'}}>
							<Image 
								style={{width:40,height:40,backgroundColor:'#d5d5d5',}}
								source={{uri:this.state.detail.logo}}
								/>
						</View>
						<View>
							<Text style={{fontSize:15,color:'white'}}>{this.state.detail.name}</Text>
							<View style={{flexDirection:'row',marginTop:10}}>
								{/*<Text style={styles.tipsText}>排名123</Text>*/}
								{
									this.state.detail.isEyy == '1'
									?
									<Text style={styles.tipsText}>211</Text>
									:
									null
								}
								{
									this.state.detail.isJbw == '1'
									?
									<Text style={styles.tipsText}>985</Text>
									:
									null
								}
						</View>
						</View>
					</View>
				</Image>
				<View style={{flexDirection:'row',height:44}}>
					<TouchableOpacity  
						style={{flex:1,alignItems:'center',justifyContent:'center'}} 
						onPress={()=>this._onClick(0)}>
						<Text style={{fontSize:15,color:'#4990e2'}}>院校介绍</Text>
					</TouchableOpacity>
					<TouchableOpacity  
						style={{flex:1,alignItems:'center',justifyContent:'center'}} 
						onPress={()=>this._onClick(1)}>
						<Text style={{fontSize:15,color:'#4990e2'}}>录取分数线</Text>
					</TouchableOpacity>
					<TouchableOpacity  
						style={{flex:1,alignItems:'center',justifyContent:'center'}} 
						onPress={()=>this._onClick(2)}>
						<Text style={{fontSize:15,color:'#4990e2'}}>招生计划</Text>
					</TouchableOpacity>
					<Animated.View 
						ref="tabLine"
						style={{position:'absolute',left:0,bottom:0,backgroundColor:'#67aef7',height:2,width:this.tabLineWidth,
						transform: [{
							translateX:this.state.tabLineAnimate
						   }]
					}}/>
				</View>
				<View style={{height:10,backgroundColor:'#d5d5d5'}}/>
				<ScrollView 
					ref="scroll"
					onMomentumScrollEnd ={this._onMomentumScrollEnd.bind(this)}
					style={{flex:1}}
					horizontal={true}
					bounces={false}
					pagingEnabled={true}
					showsHorizontalScrollIndicator={false}
					>
					<University_Detail_One detail={this.state.detail}/>
					<University_Detail_Two navigator={this.props.navigator} detail={this.state.detail}/>
					<University_Detail_Three navigator={this.props.navigator}/>

				</ScrollView>
			</ScrollView>
		);
	}
}

const styles = StyleSheet.create({
	tipsText:{
		marginRight:8,
		paddingTop:3,
		paddingBottom:3,
		paddingLeft:6,
		paddingRight:6,
		backgroundColor:'rgba(255, 255, 255, 0.3)',
		color:'white',
		fontSize:10
	}
})