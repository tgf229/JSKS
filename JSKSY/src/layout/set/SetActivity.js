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
  ScrollView,
  Dimensions,
  Text,
  View
} from 'react-native';

import App_Title from '../common/App_Title';

export default class SetActivity extends React.Component{
	constructor(props){
		super(props);
		this.state={
		}
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'#eeeeee'}}>
				<App_Title title={'设置'} navigator={this.props.navigator}/>
				<ScrollView
				  contentContainerStyle={styles.contentContainer}>
					<Text style={{alignSelf:'center',fontSize:18,color:'#4a4a4a',marginTop:28}}>江苏省教育考试院</Text>
					<Text style={{alignSelf:'center',fontSize:16,color:'#4a4a4a',marginTop:6}}>当前版本x.x.x</Text>
					<View style={{marginTop:48,backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width}}></View>
					<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20}}>
						<Text style={{fontSize:15,color:'#666666'}}>消息推送</Text>
						<Text style={{fontSize:15,color:'#666666',position:'absolute',top:20,right:20}}>已关闭</Text>
					</View>
					<View style={{backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width,marginLeft:10}}></View>
					<View style={{height:68,justifyContent:'center',padding:20}}>
						<Text style={{fontSize:14,color:'#999999'}}>
							如果要开启或者关“江苏考试院”接收消息通知，请在iPhone的”设置－通知“中找到“江苏考试院”进行更改
						</Text>
					</View>
				</ScrollView>
				<Text style={{fontSize:12,color:'#999999',alignSelf:'center',marginBottom:20}}>江苏省教育院版权所有</Text>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:38,
		backgroundColor:'white'
	},
});

