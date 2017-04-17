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

import University_Detail_Point from './University_Detail_Point';
import University_Detail_Major_Point from './University_Detail_Major_Point';

export default class University_Detail_Two extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {
	  	};
	}

	_pointClick= ()=>{
		this.props.navigator.push({
			component:University_Detail_Point
		})
	};

	_rowClick= ()=>{
		this.props.navigator.push({
			component:University_Detail_Major_Point
		})
	};

	render(){
		return(
			<View style={{width:global.windowWidth}}>
				<View style={{flexDirection:'row',height:35,paddingLeft:15,alignItems:'center'}}>
					<View style={{width:5,height:5,backgroundColor:'#90cd22'}}/>
					<Text style={{marginLeft:8,fontSize:12,fontWeight:'bold',color:'#444444'}}>历年专业录取分数线</Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',paddingLeft:28,paddingRight:15,height:44}}
					onPress={this._pointClick}>
					<Text style={{fontSize:14,color:'#444444'}}>2016年各专业录取分数线</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',paddingLeft:28,paddingRight:15,height:44}}
					onPress={this._rowClick}>
					<Text style={{fontSize:14,color:'#444444'}}>2015年各专业录取分数线</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>

			</View>
		);
	}
}

const styles = StyleSheet.create({

})