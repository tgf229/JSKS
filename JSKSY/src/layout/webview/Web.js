'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
  WebView,
} from 'react-native';
var DEFAULT_URL = 'http://mp.weixin.qq.com/s?__biz=MjM5NjAzNDgxNg==&mid=2650351268&idx=1&sn=92e58825af08c83f2a1bb98be2131519&scene=0#wechat_redirect';
 
 export default class Web extends React.Component{
  render(){
      return (
        <View style={{flex:1}}>
          <WebView style={styles.webview_style} 
            url={DEFAULT_URL}
            startInLoadingState={true}
            domStorageEnabled={true}
            javaScriptEnabled={true}
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