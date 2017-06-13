/**
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Image,
  Dimensions,
  ScrollView,
  TouchableOpacity,
  Text,
  View
} from 'react-native';
import App_Title from '../../common/App_Title';
import { BUS_200301,URL_ADDR} from '../../../util/NetUtil';
var NativeBridge = require('react-native').NativeModules.NativeBridge;

export default class OfferResult_Success_Result extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	onShareClick(channel){
		var dict = {
			a : "2",
			b : this.props.sNum,
		};
		NativeBridge.NATIVE_getEncryptData(dict,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				events.encrypt='simple';
				var data = Object.keys(events).map(key=> key+'='+encodeURIComponent(events[key])).join('&');
				var url = URL_ADDR+BUS_200301+"?"+data;
				NativeBridge.NATIVE_shareSDKWithOutUI(1,channel,"江苏招考-2017高考录取结果分享",url);
			}
		}) 
	}

	onRightNavCilck(){
		var dict = {
			a : "2",
			b : this.props.sNum,
		};
		NativeBridge.NATIVE_getEncryptData(dict,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				events.encrypt='simple';
				var data = Object.keys(events).map(key=> key+'='+encodeURIComponent(events[key])).join('&');
				var url = URL_ADDR+BUS_200301+"?"+data;
				NativeBridge.NATIVE_shareSDK(1,"江苏招考-2017高考录取结果分享",url);
			}
		})  
	}

	render(){
		return(
			<View style={{flex:1,}}>
				<App_Title title={'录取结果'} navigator={this.props.navigator} obj={this}/>
				<ScrollView
					contentContainerStyle={styles.contentContainer}>
				<Image
				  style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*1.06,alignItems:'center'}}
				  source={require('image!offer_result_top')} >
				  <View style={{alignItems:'center',justifyContent:'center',backgroundColor:'#a31c26',width:288,height:28,marginTop:20,borderRadius:20}}>
				  	<Text style={{fontSize:12,color:'white'}}>姓名：{this.props.data.sName}      考生号：{this.props.sNum}</Text>
				  </View>
				</Image>
				<Image
				  style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*0.55}}
				  source={require('image!offer_result_bottom')} >
				  	<View style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}>
				  		<Text style={{fontSize:17,color:'white',fontWeight:'bold'}}>恭喜您！</Text>
				  		{
				  			this.props.data.major
				  				?
				  				<Text style={{fontSize:13,color:'white',fontWeight:'bold',marginTop:6}}>已被{this.props.data.school}－{this.props.data.major}录取</Text>
				  				:
				  				<Text style={{fontSize:13,color:'white',fontWeight:'bold',marginTop:6}}>已被{this.props.data.school}预录取</Text>
				  		}
				  		
				 	</View>
				</Image>
				<View style={{height:50,flexDirection:'row',justifyContent:'center',alignItems:'center',paddingLeft:30,paddingRight:30}}>
					<View style={{backgroundColor:'#d5d5d5',flex:1,height:1}}/>
					<Text style={{marginLeft:11,marginRight:11,fontSize:12,color:'#444444'}}>分享</Text>
					<View style={{backgroundColor:'#d5d5d5',flex:1,height:1}}/>
				</View>
				<Text style={{fontSize:11,color:'#aeaeae',textAlign:'center'}}>分享内容会隐去个人信息以保护考生个人隐私</Text>
				<View style={{flexDirection:'row',justifyContent:'space-around',marginTop:20,marginBottom:20}}>
					<TouchableOpacity
						onPress={()=>this.onShareClick(1)}>
						<Image source={require('image!share_icon_wx')}/>
						<Text style={{fontSize:13,color:'#444444',marginTop:10,alignSelf:'center'}}>微信好友</Text>
					</TouchableOpacity>
					<TouchableOpacity
						onPress={()=>this.onShareClick(2)}>
						<Image source={require('image!share_icon_wx_timeline')}/>
						<Text style={{fontSize:13,color:'#444444',marginTop:10,alignSelf:'center'}}>朋友圈</Text>
					</TouchableOpacity>
					<TouchableOpacity
						onPress={()=>this.onShareClick(3)}>
						<Image source={require('image!share_icon_qq')}/>
						<Text style={{fontSize:13,color:'#444444',marginTop:10,alignSelf:'center'}}>QQ好友</Text>
					</TouchableOpacity>
					<TouchableOpacity
						onPress={()=>this.onShareClick(4)}>
						<Image source={require('image!share_icon_qq_timeline')}/>
						<Text style={{fontSize:13,color:'#444444',marginTop:10,alignSelf:'center'}}>QQ空间</Text>
					</TouchableOpacity>
				</View>
		    </ScrollView>
		    </View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		backgroundColor:'white',
	},
});


