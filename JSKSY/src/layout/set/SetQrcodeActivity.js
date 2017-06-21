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
  Dimensions,
  Image,
  Text,
  View
} from 'react-native';

import App_Title_Share from '../common/App_Title_Share';
import {URL_APP_SHARE} from '../../util/Global';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

export default class SetQrcodeActivity extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

  onRightNavCilck(){
    NativeBridge.NATIVE_shareSDK(1,"江苏招考APP上线啦!",URL_APP_SHARE);
  }

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
				<App_Title_Share title={'分享'} navigator={this.props.navigator} obj={this}/>
				<Image
					style={{alignSelf:'center',marginTop:80}}
					source={require('image!set_qrcode')}/>
				<Text style={{fontSize:16,color:'#444444',textAlign:'center',marginTop:35}}>请使用手机扫描上方二维码</Text>
				<Text style={{fontSize:16,color:'#444444',textAlign:'center',marginTop:10}}>即可安装「江苏招考」</Text>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


