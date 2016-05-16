'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
  WebView,
} from 'react-native';
import App_Title from '../common/App_Title';

// var DEFAULT_URL = 'http://mp.weixin.qq.com/s?__biz=MjM5NjAzNDgxNg==&mid=2650351268&idx=1&sn=92e58825af08c83f2a1bb98be2131519&scene=0#wechat_redirect';
 
 export default class Web extends React.Component{

  render(){
      return (
        <View style={{flex:1,backgroundColor:'white',}}>
          <App_Title title={'详情'} navigator={this.props.navigator}/>
          <WebView style={styles.webview_style} 
            url={this.props.url}
            automaticallyAdjustContentInsets={true}
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