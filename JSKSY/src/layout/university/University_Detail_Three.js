'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	View,
	ScrollView,
	TouchableOpacity,
	Text,
	Image} from 'react-native';

import University_Detail_Enroll from './University_Detail_Enroll';
export default class University_Detail_Three extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {
	  	};
	}

	_rowClick = ()=>{
		this.props.navigator.push({
			component:University_Detail_Enroll
		})
	};

	render(){
		return(
			<View style={{width:global.windowWidth}}>
				<View style={{padding:15}}>
					<Text style={{position:'absolute',right:15,color:'#444444',fontSize:12,fontWeight:'bold'}}>学院代号 110101</Text>
					<Text style={{marginBottom:4,color:'#444444',fontSize:15,fontWeight:'bold'}}>南京大学</Text>
					<Text style={{color:'#777777',fontSize:12,marginTop:6}}>选测科目等级要求：AA</Text>
					<Text style={{color:'#777777',fontSize:12,marginTop:6}}>联系电话：4001928404</Text>
					<Text style={{color:'#777777',fontSize:12,marginTop:6}}>联系网址：www.jseea.cn</Text>
					<Text style={{color:'#777777',fontSize:12,marginTop:6}}>学院地址：南京市西撒多撒大所大所大所大所大所多南京市西撒多撒大所大所大所大所大所多南京市西撒多撒大所大所大所大所大所多南京市西撒多撒大所大所大所大所大所多</Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',padding:15,height:44}}
					onPress={this._rowClick}>
					<Text style={{fontSize:14,color:'#444444'}}>2016年招生计划</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<View style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',padding:15,height:44}}>
					<Text style={{fontSize:14,color:'#444444'}}>2015年招生计划</Text>
					<Image source={require('image!arrow_grey')}/>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>

			</View>
		);
	}
}

const styles = StyleSheet.create({

})