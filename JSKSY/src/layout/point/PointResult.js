/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  ScrollView,
  ActivityIndicatorIOS,
  Image,
  Text,
  View
} from 'react-native';
import PointResult_Success from './component/PointResult_Success';
import App_Title from '../common/App_Title';
import { netClientPostEncrypt,BUS_200201,BUS_200301,URL_ADDR} from '../../util/NetUtil';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

var data;
export default class PointResult extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isLoaded:false,
			errorLoading:true,
			errorTips:'正在拼命查询中，请稍候...',
		}
		data = {};
	}

	onRightNavCilck(){
		var dict = {
			a : "1",
			b : this.props.sNum,
		};
		NativeBridge.NATIVE_getEncryptData(dict,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				events.encrypt='simple';
				var data = Object.keys(events).map(key=> key+'='+encodeURIComponent(events[key])).join('&');
				var url = URL_ADDR+BUS_200301+"?"+data;
				NativeBridge.NATIVE_shareSDK(1,"江苏招考-2017高考成绩分享",url);
			}
		})  
	}

	BUS_200201_CB(object,response){
		//加解密参数
		NativeBridge.NATIVE_getDecryptData(response._bodyText,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				console.log('BUS_200201_CB = '+events);
				var json = JSON.parse(events);
				if (json.retcode === '000000') {
					data = json;
					object.setState({
						isLoaded:true,
						errorLoading:false,
					});
				}else{
					var error='请求失败，请稍后再试';
					if (json.retinfo) {
						error = json.retinfo;
					}
					object.setState({
						errorLoading:false,
						errorTips:error,
					});
				}
			}	
		})

//===========
		// console.log('BUS_200201_CB = '+response);
		// 		var json = JSON.parse(response._bodyText);
		// 		if (json.retcode === '000000') {
		// 			data = json;
		// 			object.setState({
		// 				isLoaded:true,
		// 				errorLoading:false,
		// 			});
		// 		}else{
		// 			var error='请求失败，请稍后再试';
		// 			if (json.retinfo) {
		// 				error = json.retinfo;
		// 			}
		// 			object.setState({
		// 				errorLoading:false,
		// 				errorTips:error,
		// 			});
		// 		}
	}

	BUS_200201_REQ(){
		var dict = {
			sNum : this.props.sNum,
			sCheck : this.props.sTicket,
			sCheckKeyA : this.props.sCheckA,
			sCheckKeyB : this.props.sCheckB,
		};
		NativeBridge.NATIVE_getEncryptData(dict,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				events.encrypt='simple';
				netClientPostEncrypt(this,BUS_200201,this.BUS_200201_CB,events);
			}
		})  
//===========
		// dict.encrypt='none';
		// 		netClientPostEncrypt(this,BUS_200201,this.BUS_200201_CB,dict);
	}

	componentDidMount() {
		this.BUS_200201_REQ();
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'高考查分'} navigator={this.props.navigator} obj={this}/>
			<ScrollView
			  	contentContainerStyle={styles.contentContainer}>
			
				{
					this.state.isLoaded? <PointResult_Success data={data} navigator={this.props.navigator} sNum={this.props.sNum}/>
						:
						<View style={{flex:1,alignItems:'center',marginTop:90}}>
							<Image
					  			source={require('image!load_pic')} />
					  		{
					  			this.state.errorLoading
					  				?
					  				<ActivityIndicatorIOS
										style={{marginTop:10}}
									  	animating={true}
									  	color={'#808080'}
									  	size={'small'} />
									:
									null
					  		}
							
							<Text style={{fontSize:20,color:'#888888',marginTop:10}}>{this.state.errorTips}</Text>
						</View>
				}
			</ScrollView>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{

	},
});


