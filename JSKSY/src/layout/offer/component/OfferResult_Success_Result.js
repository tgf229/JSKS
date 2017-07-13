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
  TouchableHighlight,
  View
} from 'react-native';
import App_Title from '../../common/App_Title';
import { BUS_200301,URL_ADDR,BUS_100501,netClientPost,BUS_100601} from '../../../util/NetUtil';

import {URL_SCHEMA_SCHOOL_DETAIL,URL_SCHEMA_SCHOOL_LIST} from '../../../util/Global';
import Web from '../../webview/Web';
import University_Detail from '../../university/University_Detail';
import University_List from '../../university/University_List';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

export default class OfferResult_Success_Result extends React.Component{
	constructor(props){
		super(props);
		this.state={
			adDoc:[] //广告列表
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

	//广告日志接口回调
	BUS_100601_CB(object,json){
	}

	//广告日志接口
	BUS_100601_REQ(aId){
		var params = {
			encrypt:'none',
			imei:global.uuid,
			aType:'8',
			aId:aId
		}
		netClientPost(this,BUS_100601,this.BUS_100601_CB,params);
	}

		//行点击
	adPressed(item){
		if (item.type == '1') {
			this.BUS_100601_REQ(item.aId);
		}
		if (item.aUrl.indexOf(URL_SCHEMA_SCHOOL_DETAIL)!= -1) {
			const dId = item.aUrl.substring(item.aUrl.lastIndexOf("/")+1);
			this.props.navigator.push({
				component:University_Detail,
				params:{
					uCode:dId,
				},
			});
		}else if (item.aUrl.indexOf(URL_SCHEMA_SCHOOL_LIST)!= -1) {
			const keyword = item.aUrl.substring(item.aUrl.lastIndexOf("/")+1);
			this.props.navigator.push({
				component:University_List,
				params:{
					uName:keyword,
				},
			});
		}else{
			this.props.navigator.push({
				component:Web,
				params:{
					url:item.aUrl,
				},
			});
		}
	}

	//广告列表接口回调
	BUS_100501_CB(object,json){
		if (json.retcode === '000000') {
			if (json.doc.length > 0) {
				object.setState({
					adDoc:json.doc
				});
			}
		}else{
			// console.log('Home BUS_100501_CB 失败');
		}
	}

	//广告列表接口请求
	BUS_100501_REQ(){
		var params = {
			encrypt:'none',
			type:'3'
		}
		netClientPost(this,BUS_100501,this.BUS_100501_CB,params);
	}

	componentDidMount() {
		this.BUS_100501_REQ();
	}

	render(){
		var that = this;
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

				<View 
								style={{backgroundColor:'#eeeeee'}}>
								{
									this.state.adDoc.length > 0
									?
									<View style={{paddingTop:17,paddingBottom:17,paddingLeft:12,
										paddingRight:12,flexDirection:'row',justifyContent:'center',alignItems:'center'}}>
										<View style={{backgroundColor:'#d5d5d5',flex:1,height:0.5}}></View>
										<Text style={{marginLeft:20,marginRight:20,fontSize:12,color:'#444444'}}>推广</Text>
										<View style={{backgroundColor:'#d5d5d5',flex:1,height:0.5}}></View>
									</View>
									:
									null
								}
								{
									
									this.state.adDoc.map(function(item){
										if (item.type === '1') {
											return(
												<TouchableHighlight
													onPress={()=>that.adPressed(item)}
												    underlayColor='#fcfcfc'>
												  	<View>
												  		<View style={{padding:14}}>
												 			<Image
													  			 style={{width:Dimensions.get('window').width-28, height:(Dimensions.get('window').width-28)*2/7}}
													 			 source={{uri: item.imageUrl}} />
											  			</View>
												  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
												  	</View>
												</TouchableHighlight>
												)
											}else{
											return(
												<TouchableHighlight
													onPress={()=>that.adPressed(item)}
												    underlayColor='#fcfcfc'>
												  	<View>
													  	<View style={{height:84,paddingTop:12,paddingBottom:12,paddingLeft:15,paddingRight:15,justifyContent:'center'}}>
													  		<Text style={styles.title} numberOfLines={2}>{item.name}</Text>
													  		<Text style={styles.time}>发布于{item.time}</Text>
													  	</View>
												  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
												  	</View>
												</TouchableHighlight>
												)
											}
									})
								}
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


