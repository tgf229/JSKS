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
  TouchableOpacity,
  View
} from 'react-native';

import { STORAGE_KEY_ALIAS} from '../../util/Global';
import App_Title from '../common/App_Title';
import SetQrcodeActivity from './SetQrcodeActivity';

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

	goToQrcodeShare(){
		this.props.navigator.push({
			component:SetQrcodeActivity
		})
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'#f3f3f3'}}>
				<App_Title title={'设置'} navigator={this.props.navigator}/>
				<ScrollView
				  contentContainerStyle={styles.contentContainer}>
				  	<Image
				  		style={{alignSelf:'center'}}
				  		source={require('image!set_icon')}
				  		/>
					<Text style={{alignSelf:'center',fontSize:16,color:'#444444',marginTop:14}}>江苏招考</Text>
					<Text style={{alignSelf:'center',fontSize:12,color:'#999999',marginTop:8}}>当前版本P2.3.2-{this.state.aliasVal}</Text>
					<View style={{marginTop:30,backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width}}></View>
					<TouchableOpacity 
						onPress={()=>this.goToQrcodeShare()}
						style={{height:44,flexDirection:'row',alignItems:'center',paddingLeft:15}}>
						<Image 
							source={require('image!set_icon_share')}/>
						<Text style={{fontSize:14,color:'#444444',marginLeft:10}}>分享「江苏招考」给好友</Text>
						<Image
							style={{position:'absolute',top:15,right:20}}
							source={require('image!arrow_grey')}/>
					</TouchableOpacity>
					<View style={{backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width}}></View>
					<View style={{height:44,flexDirection:'row',alignItems:'center',paddingLeft:15}}>
						<Image 
							source={require('image!set_icon_tuisong')}/>
						<Text style={{fontSize:14,color:'#444444',marginLeft:10}}>消息推送</Text>
						{/* <Text style={{fontSize:15,color:'#666666',position:'absolute',top:20,right:20}}>已关闭</Text> */}
					</View>
					<View style={{backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width}}></View>
					<View style={{height:50,justifyContent:'center',padding:15,backgroundColor:'#f3f3f3'}}>
						<Text style={{fontSize:10,color:'#aeaeae',lineHeight:14}}>
							如果要开启或者关闭“江苏招考”接收消息通知，请在iPhone的”设置－通知“中找到“江苏招考”进行更改
						</Text>
					</View>
					

					<View style={{flexDirection:'row',height:44,alignItems:'center',justifyContent:'space-between',paddingLeft:15,paddingRight:15}}>
						<View style={{flexDirection:'row'}}>
							<Image 
								source={require('image!set_icon_phone')}/>
							<Text style={{fontSize:14,color:'#444444',marginLeft:10}}>技术服务热线</Text>
						</View>
						<Text style={{fontSize:14,color:'#444444'}}>025-83235849</Text>
					</View>
					<View style={{height:1,backgroundColor:'#eeeeee'}}/>
					<View style={{flexDirection:'row',height:44,alignItems:'center',justifyContent:'space-between',paddingLeft:15,paddingRight:15}}>
						<View style={{flexDirection:'row'}}>
							<Image 
								source={require('image!set_icon_time')}/>
							<Text style={{fontSize:14,color:'#444444',marginLeft:10}}>服务时间</Text>
						</View>
						<Text style={{fontSize:14,color:'#444444'}}>9:00~18:00</Text>
					</View>
				</ScrollView>
				<Text style={{fontSize:10,color:'#aeaeae',alignSelf:'center'}}>江苏省教育考试院权威发布</Text>
				<Text style={{fontSize:10,color:'#aeaeae',alignSelf:'center',marginTop:4,marginBottom:20}}>江苏华胜天成教育科技有限公司技术支持</Text>
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


