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

import WishPaySuccess from './WishPaySuccess';
import App_Title from '../common/App_Title';

export default class PayAgreement extends React.Component{
	constructor(props){
		super(props);
		this.state={
			payWay:1
		}
	}

	onSubmit(){
		this.props.navigator.push({
			component:WishPaySuccess
		})
	}

	onChoise(way){
		console.log(way)
		this.setState({payWay:way});
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'购买'} navigator={this.props.navigator}/>
			<ScrollView
			  contentContainerStyle={styles.contentContainer}>

			  	<View style={{height:106,flexDirection:'row',alignItems:'center'}}>
			  		<Text style={{fontSize:15,color:'#666666'}}>志愿参考服务支付金额</Text>
			  		<Text style={{fontSize:20,color:'#ff902d',position:'absolute',top:40,right:1}}>¥98</Text>
			  	</View>
			 	
			 	<Image
			 		style={{width:Dimensions.get('window').width,marginLeft:-16}}
			 		source={require('image!wish_buy_circleline')} />

			 	<TouchableHighlight
					onPress={()=>this.onChoise(1)}
					underlayColor='#ffffff'>
				 	<View style={{flexDirection:'row',alignItems:'center',height:55}}>
				 		<Image
				 		  source={require('image!zhifubao')} />
				 		<Text style={{fontSize:15,color:'#666666',marginLeft:20}}>支付宝</Text>
				 		<Image
				 			style={{position:'absolute',top:15,right:1}}
				 		  	source={this.state.payWay === 1? require('image!wish_check_circle_press'):require('image!wish_check_circle')} />
				 	</View>
			 	</TouchableHighlight>
			 	<View style={{height:0.5,marginLeft:40,width:Dimensions.get('window').width-40,backgroundColor:'#d5d5d5'}}/>

			 	<TouchableHighlight
					onPress={()=>this.onChoise(2)}
					underlayColor='#ffffff'>
				 	<View style={{flexDirection:'row',alignItems:'center',height:55}}>
				 		<Image
				 		  source={require('image!weixin')} />
				 		<Text style={{fontSize:15,color:'#666666',marginLeft:20}}>微信支付</Text>
				 		<Image
				 			style={{position:'absolute',top:15,right:1}}
				 		  	source={this.state.payWay === 2? require('image!wish_check_circle_press'):require('image!wish_check_circle')} />
				 	</View>
				</TouchableHighlight>
			 	<View style={{height:0.5,marginLeft:40,width:Dimensions.get('window').width-40,backgroundColor:'#d5d5d5'}}/>


				<TouchableHighlight
					onPress={()=>this.onChoise(3)}
					underlayColor='#ffffff'>
				 	<View style={{flexDirection:'row',alignItems:'center',height:55}}>
				 		<Image
				 		  source={require('image!yinlian')} />
				 		<Text style={{fontSize:15,color:'#666666',marginLeft:20}}>银联支付</Text>
				 		<Image
				 			style={{position:'absolute',top:15,right:1}}
				 		  	source={this.state.payWay === 3? require('image!wish_check_circle_press'):require('image!wish_check_circle')} />
				 	</View>
				 </TouchableHighlight>
			 	<View style={{height:0.5,marginLeft:40,width:Dimensions.get('window').width-40,backgroundColor:'#d5d5d5'}}/>

			 	<TouchableHighlight
					onPress={()=>this.onChoise(4)}
					underlayColor='#ffffff'>
				 	<View style={{flexDirection:'row',alignItems:'center',height:55}}>
				 		<Image
				 		  source={require('image!applepay')} />
				 		<Text style={{fontSize:15,color:'#666666',marginLeft:20}}>Apple Pay</Text>
				 		<Image
				 			style={{position:'absolute',top:15,right:1}}
				 		  	source={this.state.payWay === 4? require('image!wish_check_circle_press'):require('image!wish_check_circle')} />
				 	</View>
				 </TouchableHighlight>
			 	<View style={{height:0.5,marginLeft:40,width:Dimensions.get('window').width-40,backgroundColor:'#d5d5d5'}}/>

				<TouchableHighlight
					style={{marginTop:26, justifyContent:'center',alignItems:'center',
						backgroundColor:'#ff902d',height:45,borderRadius:3}}
					onPress={()=>this.onSubmit()}
					underlayColor='#fcfcfc'>
					<Text style={{fontSize:16,color:'white'}}>确认支付 ¥98</Text>
				</TouchableHighlight>

			</ScrollView>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingLeft:16,
		paddingRight:16,

	},
});


