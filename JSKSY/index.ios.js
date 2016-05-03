/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  NavigatorIOS,
  Navigator,
  StyleSheet,
  StatusBarIOS,
  AsyncStorage,
  View
} from 'react-native';
import Home from './src/layout/home/Home';
import codePush from "react-native-code-push";
import {STORAGE_KEY_ALIAS} from './src/util/Global';

var device_uuid='';
var device_model='';
class MyProject extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	  async _loadInitialState(){
	       try{
	          var alias = await AsyncStorage.getItem(STORAGE_KEY_ALIAS);
	          console.log('别名＝'+alias);
	          	if (alias == null) {
			    	alias = new Date().getTime()+"";
				    await AsyncStorage.setItem(STORAGE_KEY_ALIAS,alias);   
	          	}else{
	           		console.log('存储中无数据,初始化为空数据');
	          	}
	       }catch(error){
	            console.log('AsyncStorage错误'+error.message);
	       }
	  }

	componentDidMount(){
	    codePush.sync();
	    StatusBarIOS.setStyle('light-content');
	    this._loadInitialState().done();
	}

	render(){
		return(
			  <Navigator
		          initialRoute={{ component: Home, }}
		          configureScene={(route) => {
		            return Navigator.SceneConfigs.PushFromRight;
		          }}
		          renderScene={(route, navigator) => {
		            let Component = route.component;
		            return <Component {...route.params} navigator={navigator} />
		          }} />
		)
	}

}

const styles = StyleSheet.create({

});

AppRegistry.registerComponent('MyProject', () => MyProject);
