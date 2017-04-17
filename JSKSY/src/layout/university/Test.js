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

import Swiper from 'react-native-swiper'
import GiftedListView from 'react-native-gifted-listview';
import {BUS_700101,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
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
	  	//赋值当前activity
	  	global.currentActivity = this;
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
	  	};
	}

   _tabLineAnimateListener = (x)=>{
	   	Animated.timing(    
	       this.state.tabLineAnimate, 
	       {toValue: x},
	     ).start(); 
   };

   componentDidMount() {
   		this._tabLineAnimateListener(this.tabLineScroll[0]);
   }

   componentWillUpdate(nextProps, nextState) {
   		this.refs.swiper.scrollTo(1);
   }

	_onClick(flag){
		console.log('onClick= '+syncClick)
		if(syncClick){
			switch(flag){
			case 0:
				if (currentTab == 1) {
					syncClick = false;
					this.refs.swiper.scrollTo(-1);
				}else if(currentTab == 2){
					syncClick = false;
					this.refs.swiper.scrollTo(1);
				}
				break;
			case 1:
				if (currentTab == 0) {
					syncClick = false;
					this.refs.swiper.scrollTo(1);
				}else if(currentTab == 2){
					syncClick = false;
					this.refs.swiper.scrollTo(-1);
				}
				break;
			case 2:
			if (currentTab == 0) {
					syncClick = false;
					this.refs.swiper.scrollTo(-1);
				}else if(currentTab == 1){
					syncClick = false;
					this.refs.swiper.scrollTo(1);
				}
				break;
			}
		}
	}

	//Swiper回调的滑动函数
	_onMomentumScrollEnd(e, state, context) {
		currentTab = state.index;
		syncClick = true;
		global.currentActivity._tabLineAnimateListener(global.currentActivity.tabLineScroll[currentTab]);
	  }

	_onBackPressed(){
		this.props.navigator.pop();
	}

	render(){
		return(
			<ScrollView>
				<Image 
					style={{width:global.windowWidth, height:global.windowWidth*3/5,backgroundColor:'#d5d5d5'}}
					source={{uri:'http://192.168.0.107/jszk/fdpic.png'}}
					>
					<TouchableOpacity
						onPress={()=>this._onBackPressed()}>
						<Image
							style={{position:'absolute',left:10,top:30}}
							source={require('image!school_btn_back')}/>
					</TouchableOpacity>
					<View style={{position:'absolute',bottom:0,backgroundColor:'rgba(0, 0, 0, 0.4)',width:global.windowWidth,height:70,flexDirection:'row',alignItems:'center'}}>
						<Image 
							style={{width:50,height:50,borderRadius:25,backgroundColor:'#d5d5d5',marginLeft:11,marginRight:13}}
							source={{uri:'http://192.168.0.107/jszk/fdlogo.png'}}
							/>
						<View>
							<Text style={{fontSize:15,color:'white'}}>南京大学</Text>
							<View style={{flexDirection:'row',marginTop:10}}>
								<Text style={styles.tipsText}>排名123</Text>
								<Text style={styles.tipsText}>211</Text>
								<Text style={styles.tipsText}>985</Text>
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
				<Swiper
					ref="swiper"
					showsPagination={false}
					height={this.state.contentHeight}
					onMomentumScrollEnd ={this._onMomentumScrollEnd}
					>
					<University_Detail_One/>
					<University_Detail_Two navigator={this.props.navigator}/>
					<University_Detail_Three/>
				</Swiper>
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





'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	View,
	ScrollView,
	TouchableOpacity,
	PixelRatio,
	ProgressViewIOS,
	Text,
	Image} from 'react-native';

import University_Detail_One from './University_Detail_One';
import University_Detail_Two from './University_Detail_Two';
import University_Detail_Three from './University_Detail_Three';
export default class Test extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {
	  	};
	}

	componentDidMount() {
		this.refs.scroll.scrollTo({x: global.windowWidth*2, y: 0, animated: true})
	}

	_onMomentumScrollEnd(e, state, context) {
		console.log(e.nativeEvent.contentOffset.x);
		console.log(global.windowWidth)

	  }


	render(){
		return(
			<ScrollView 
				ref="scroll"
				onMomentumScrollEnd ={this._onMomentumScrollEnd}
				style={{flex:1}}
				horizontal={true}
				bounces={false}
				pagingEnabled={true}
				showsHorizontalScrollIndicator={false}
				>
				<University_Detail_One/>
					<University_Detail_Two navigator={this.props.navigator}/>
					<University_Detail_Three/>

			</ScrollView>
		);
	}
}

const styles = StyleSheet.create({

})