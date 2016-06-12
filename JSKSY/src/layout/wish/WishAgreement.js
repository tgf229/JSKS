/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  TouchableHighlight,
  Dimensions,
  ScrollView,
  ActivityIndicatorIOS,
  AlertIOS,
  Text,
  Image,
  View
} from 'react-native';

// import WishPayment from './WishPayment';
import App_Title from '../common/App_Title';
import WishSearch from './WishSearch';
import PointWait from '../point/component/PointWait'
import { netClientPost,BUS_200101} from '../../util/NetUtil';

var cuTime;
var exTime;
export default class WishAgreement extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isChoise:false,
			isLoaded:false,	//是否调用时间基准接口完毕
			isPointSearchOpen:false, //查分是否已开始
		}
	}

	onSubmit(){
		if (!this.state.isChoise) {
			AlertIOS.alert(
				'温馨提示',
				'请阅读并勾选志愿辅助服务规则与条款',
			);
			return;
		}
		this.props.navigator.replace({
			component:WishSearch
		});
	}

	onChoise(){
		this.setState({isChoise:!this.state.isChoise});
	}

	//时间基准接口回调
	BUS_200101_CB(object,json){
		console.log('BUS_200101_CB = '+json)
		if (json.retcode === '000000') {
			var isOpen = false;
			cuTime = json.cuTime;
			exTime = json.exTime;
			//若当前时间大于等于目标时间，则设置isOpen为true 打开查询流程
			if (json.exTime <= json.cuTime) {
				isOpen = true;
			}

			object.setState({
				isLoaded:true,
				isPointSearchOpen:isOpen,
			});
			
		}else{

		}
	}

	//时间基准接口请求
	BUS_200101_REQ(){
		var params = {
			encrypt:'none',
		};
		netClientPost(this,BUS_200101,this.BUS_200101_CB,params);
	}

	componentDidMount() {
		this.BUS_200101_REQ();
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'录取资料'} navigator={this.props.navigator}/>
			{
				this.state.isLoaded?
					(this.state.isPointSearchOpen?
						<ScrollView
						  contentContainerStyle={styles.contentContainer}>

						 	<Image
						 		style={{alignSelf:'center'}}
						    	source={require('image!will_agreement_tips')} />
							<Text style={{marginTop:12,fontSize:15,color:'#666666',lineHeight:22}}>本服务会结合考生本年高考分数及全省位次，
								往年高考录取数据，本年度各院校招生计划，并按照如院校省份，专业，推荐策略等附加条件推荐出适合您的院校及专业。
							</Text>
							<Text style={{marginTop:20,fontSize:13,color:'#d0021b'}}>PS：本服务只针对进入第一阶段位次的文科及理科考生开放</Text>
							<View style={{marginTop:22,backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width-20}}></View>

							<View style={{flexDirection:'row',alignItems:'center'}}>
								<TouchableHighlight
								onPress={()=>this.onChoise()}
								underlayColor='#ffffff'>
									<View style={{paddingTop:24,paddingLeft:10,paddingBottom:24,flexDirection:'row',alignItems:'center'}}>
										<Image
										  source={this.state.isChoise? require('image!will_checkbox_press'):require('image!will_checkbox_none')} />
										<Text style={{marginLeft:10,fontSize:14,color:'#888888'}}>我同意</Text>
									</View>
								</TouchableHighlight>
								<View style={{paddingTop:24,paddingBottom:24}}>
									<Text style={{marginLeft:10,fontSize:14,color:'#4a90e2'}}>志愿辅助服务规则与条款</Text>
								</View>
							</View>

							<TouchableHighlight
								style={{justifyContent:'center',alignItems:'center',
									backgroundColor:'#ff902d',height:45,borderRadius:3}}
								onPress={()=>this.onSubmit()}
								underlayColor='#fcfcfc'>
								<Text style={{fontSize:16,color:'white'}}>下一步</Text>
							</TouchableHighlight>

						</ScrollView>
						:
						<PointWait cuTime={cuTime} exTime={exTime} content={'距离录取资料服务开放还有'} searchObj={this}/>)
					:
					<ActivityIndicatorIOS style={{marginTop:Dimensions.get('window').height/2-65}}/>
			}
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:45,
		paddingLeft:20,
		paddingRight:20,
	},
});


