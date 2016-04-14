/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  TouchableHighlight,
  Dimensions,
  ScrollView,
  AlertIOS,
  Text,
  Image,
  View
} from 'react-native';

import WishPayment from './WishPayment';

export default class WishAgreement extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isChoise:false,
		}
	}

	onSubmit(){
		if (!this.state.isChoise) {
			AlertIOS.alert(
				'温馨提示',
				'请阅读并勾选志愿辅助服务购买规则与条款',
			);
			return;
		}
		this.props.navigator.push({
			title:'购买',
			component:WishPayment
		});
	}

	onChoise(){
		this.setState({isChoise:!this.state.isChoise});
	}

	render(){
		return(
			<ScrollView
			  contentContainerStyle={styles.contentContainer}>

			 	<Image
			 		style={{alignSelf:'center'}}
			    	source={require('image!will_agreement_tips')} />
			  	<Text style={{marginTop:20,fontSize:18,color:'#444444',alignSelf:'center'}}>志愿参考服务 <Text style={{fontSize:20,color:'#ff902d'}}>¥98</Text></Text>
				<Text style={{marginTop:12,fontSize:15,color:'#666666'}}>本服务会结合考生本年高考分数及全省位次，
					往年高考录取数据，本年度各院校招生计划，并按照如院校省份，专业，推荐策略等附加条件推荐出适合您的院校及专业。
				</Text>
				<Text style={{marginTop:20,fontSize:13,color:'#888888'}}>PS：本服务只针对进入第一阶段位次的文科及理科考生开放</Text>
				<View style={{marginTop:22,backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width-20}}></View>

				<View style={{flexDirection:'row',alignItems:'center'}}>
					<TouchableHighlight
					onPress={()=>this.onChoise()}
					underlayColor='#ffffff'>
						<View style={{paddingTop:24,paddingLeft:10,paddingBottom:24,flexDirection:'row',alignItems:'center'}}>
							<Image
							  source={this.state.isChoise? require('image!will_checkbox_press'):require('image!will_checkbox_none')} />
							<Text style={{marginLeft:10,fontSize:14,color:'#888888'}}>我同意</Text>
						</View>
					</TouchableHighlight>
					<View style={{paddingTop:24,paddingBottom:24}}>
						<Text style={{marginLeft:10,fontSize:14,color:'#4a90e2'}}>志愿辅助服务购买规则与条款</Text>
					</View>
				</View>

				<TouchableHighlight
					style={{justifyContent:'center',alignItems:'center',
						backgroundColor:'#ff902d',height:45,borderRadius:3}}
					onPress={()=>this.onSubmit()}
					underlayColor='#fcfcfc'>
					<Text style={{fontSize:16,color:'white'}}>购买</Text>
				</TouchableHighlight>

			</ScrollView>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:45,
		paddingLeft:20,
		paddingRight:20,
		backgroundColor:'white',

	},
});


