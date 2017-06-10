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
  AsyncStorage,
  View
} from 'react-native';

import { STORAGE_KEY_ALIAS} from '../../util/Global';
import App_Title from '../common/App_Title';

export default class SetActivity extends React.Component{
	constructor(props){
		super(props);
		this.state={
			aliasVal:''
		}
	}

	 async _loadInitialState(){
	       try{
	           var val = await AsyncStorage.getItem(STORAGE_KEY_ALIAS);
	           this.setState({
	           		aliasVal:val
	           })
	       }catch(error){

	       }
	  }

	componentDidMount() {
		this._loadInitialState().done();
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
					<Text style={{alignSelf:'center',fontSize:18,color:'#4a4a4a',marginTop:28}}>江苏招考</Text>
					<Text style={{alignSelf:'center',fontSize:16,color:'#4a4a4a',marginTop:6}}>当前版本P2.2.1-{this.state.aliasVal}</Text>
					<View style={{marginTop:48,backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width}}></View>
					<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20}}>
						<Text style={{fontSize:15,color:'#666666'}}>消息推送</Text>
						{/* <Text style={{fontSize:15,color:'#666666',position:'absolute',top:20,right:20}}>已关闭</Text> */}
					</View>
					<View style={{backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width,marginLeft:10}}></View>
					<View style={{height:68,justifyContent:'center',padding:20}}>
						<Text style={{fontSize:14,color:'#999999'}}>
							如果要开启或者关闭“江苏招考”接收消息通知，请在iPhone的”设置－通知“中找到“江苏招考”进行更改
						</Text>
					</View>
					<View style={{height:10,backgroundColor:'#eeeeee'}}/>

					<View style={{flexDirection:'row',height:40,alignItems:'center',justifyContent:'space-between',paddingLeft:20,paddingRight:20}}>
						<Text style={{fontSize:15,color:'#666666'}}>技术服务热线</Text>
						<Text style={{fontSize:15,color:'#666666'}}>025-83235849</Text>
					</View>
					<View style={{height:1,backgroundColor:'#eeeeee'}}/>
					<View style={{flexDirection:'row',height:40,alignItems:'center',justifyContent:'space-between',paddingLeft:20,paddingRight:20}}>
						<Text style={{fontSize:15,color:'#666666'}}>工作时间</Text>
						<Text style={{fontSize:15,color:'#666666'}}>9:00~17:00</Text>
					</View>
				</ScrollView>
				<Text style={{fontSize:12,color:'#999999',alignSelf:'center'}}>江苏省教育考试院权威发布</Text>
				<Text style={{fontSize:12,color:'#999999',alignSelf:'center',marginBottom:20}}>江苏华胜天成教育科技有限公司技术支持</Text>
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


