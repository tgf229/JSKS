/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  NavigatorIOS,
  StyleSheet,
  StatusBarIOS,
  View
} from 'react-native';
import Home from './src/layout/home/Home';
import codePush from "react-native-code-push";

class MyProject extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	componentDidMount(){
	    codePush.sync();
	    StatusBarIOS.setStyle('light-content');
	}

	render(){
		return(
			<NavigatorIOS
				style={{flex:1}}
				barTintColor='#67aef7'
			    titleTextColor='#ffffff'
			  	initialRoute={{
			    title: '江苏省教育考试院101-1',
			    component: Home
			  }} />
		)
	}

}

const styles = StyleSheet.create({

});

AppRegistry.registerComponent('MyProject', () => MyProject);
