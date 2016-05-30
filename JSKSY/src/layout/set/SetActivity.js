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
  Image,
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
				  	<Image
				  		style={{alignSelf:'center'}}
				  		source={require('image!set_icon')}
				  		/>
					<Text style={{alignSelf:'center',fontSize:18,color:'#4a4a4a',marginTop:28}}>高考君</Text>
					<Text style={{alignSelf:'center',fontSize:16,color:'#4a4a4a',marginTop:6}}>当前版本S1.0.2-V1</Text>
					{/* <Text style={{alignSelf:'center',fontSize:16,color:'#4a4a4a',marginTop:6}}>当前版本P1.0.1-V1</Text> */}
					<View style={{marginTop:48,backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width}}></View>
					<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20}}>
						<Text style={{fontSize:15,color:'#666666'}}>消息推送</Text>
						{/* <Text style={{fontSize:15,color:'#666666',position:'absolute',top:20,right:20}}>已关闭</Text> */}
					</View>
					<View style={{backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width,marginLeft:10}}></View>
					<View style={{height:68,justifyContent:'center',padding:20}}>
						<Text style={{fontSize:14,color:'#999999'}}>
							如果要开启或者关闭“高考君”接收消息通知，请在iPhone的”设置－通知“中找到“高考君”进行更改
						</Text>
					</View>
				</ScrollView>
				<Text style={{fontSize:12,color:'#999999',alignSelf:'center',marginBottom:20}}>江苏华胜天成教育科技有限公司版权所有</Text>
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


