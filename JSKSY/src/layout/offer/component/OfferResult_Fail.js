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
  Image,
  Dimensions,
  Text,
  View
} from 'react-native';

export default class OfferResult_Fail extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			  	<View>
			  	<View style={{backgroundColor:'#67aef7',height:54,flexDirection:'row',padding:18,alignItems:'center'}}>
			  		<Text style={{fontSize:14,color:'#d8ebff'}}>姓名</Text>
			  		<Text style={{fontSize:14,color:'#d8ebff',position:'absolute',top:18,right:20}}>王大雷</Text>
			  	</View>
			  	<View style={{height:0.2,width:Dimensions.get('window').width,backgroundColor:'#d5d5d5'}}></View>
			  	<View style={{backgroundColor:'#67aef7',height:54,flexDirection:'row',padding:18,alignItems:'center'}}>
			  		<Text style={{fontSize:14,color:'#d8ebff'}}>考生号</Text>
			  		<Text style={{fontSize:14,color:'#d8ebff',position:'absolute',top:18,right:20}}>17751028374</Text>
			  	</View>

			  	<Image
			  		style={{alignSelf:'center',marginTop:52}}
			    	source={require('image!tips_none')} />
			    <Text style={{marginTop:36,fontSize:16,color:'#999999',textAlign:'center'}}>暂未得出录取结果，请耐心等待...</Text>
			   </View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		backgroundColor:'white',
	},
});


