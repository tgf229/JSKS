'use strict';
import React,{Component} from 'react';
import {
  AppRegistry,
  StyleSheet,
  View,
  WebView,
  Text} from 'react-native';
import App_Title_Wish from '../common/App_Title_Wish';

var NativeBridge = require('react-native').NativeModules.NativeBridge;
 
 export default class Web_wish extends Component{
  constructor(props){
    super(props);
    this.state={
      backButtonEnabled: false,
      url:'',
      title:'',
    }
  }

  onLeftNavCilck(){
    if (this.state.backButtonEnabled) {
      this.refs.webview.goBack();
    }else{
      this.props.navigator.pop();
    }
  }

  onLeftCloseNavCilck(){
     this.props.navigator.pop();
  }

  onRightNavCilck(){
    NativeBridge.NATIVE_shareSDK(1,this.state.title,this.state.url);
  }

  onNavigationStateChange(navState) {
    this.setState({
      backButtonEnabled: navState.canGoBack,
      url: navState.url,
      title: navState.title,
      // forwardButtonEnabled: navState.canGoForward,
      // loading: navState.loading,
      // scalesPageToFit: true
    });
  }

  render(){
      return (
        <View style={{flex:1,backgroundColor:'white',}}>
          <App_Title_Wish title={this.state.title} navigator={this.props.navigator} obj={this} rightBtnHide={this.props.rightBtnHide}/>
          <WebView style={styles.webview_style} 
            ref='webview'
            url={this.props.url}
            onNavigationStateChange={this.onNavigationStateChange.bind(this)}
            automaticallyAdjustContentInsets={true}
            startInLoadingState={true}
            domStorageEnabled={true}
            javaScriptEnabled={true}
            scalesPageToFit={true}
            >
          </WebView>
        </View>
      )
    }
  }

const styles = StyleSheet.create({
 webview_style:{  

    }
});