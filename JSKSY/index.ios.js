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
  View
} from 'react-native';
import Home from './src/layout/home/Home';
import Welcome from './src/layout/Welcome';
import codePush from "react-native-code-push";

var device_uuid='';
var device_model='';
class MyProject extends React.Component{
	constructor(props){
		super(props);
		this.state={
			loadAD:true,
			adUrl:'',
		}
	}

	componentDidMount(){
	    codePush.sync();
	    StatusBarIOS.setStyle('light-content');
	}

	render(){
		if (!this.state.loadAD) {
			return(
				<Navigator
					initialRoute={{ component: Home, }}
			        configureScene={(route) => {
			            return Navigator.SceneConfigs.HorizontalSwipeJump;
			        }}
			        renderScene={(route, navigator) => {
			            let Component = route.component;
			            return <Component {...route.params} adUrl={this.state.adUrl} navigator={navigator} />
			        }} />
			)
		}else{
			return(
				<Welcome homeObj={this}/>
			)
		}
	}
}

const styles = StyleSheet.create({

});

AppRegistry.registerComponent('MyProject', () => MyProject);
