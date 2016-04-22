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
			  <Navigator
		          initialRoute={{ component: Home }}
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
