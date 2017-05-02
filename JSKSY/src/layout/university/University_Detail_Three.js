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
import {YEAR_2017,YEAR_2016,YEAR_2015,YEAR_2014,YEAR_2013,BUS_700501,netClientPost,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
export default class University_Detail_Three extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {

	  	};
	}

	_rowClick(index){
		this.props.navigator.push({
			component:University_Detail_Enroll,
			params:{
				year:index,
				code:this.props.detail.code
			}
		})
	};

	render(){
		const detail = this.props.detail
		return(
			<View style={{width:global.windowWidth}}>
				<View style={{padding:15}}>
					<View style={{flexDirection:'row',justifyContent:'space-between'}}>
						<Text style={{flex:1,marginBottom:4,color:'#444444',fontSize:15,fontWeight:'bold'}}>{detail.name}</Text>
						<Text style={{marginBottom:4,color:'#444444',fontSize:12,fontWeight:'bold'}}>学院代号 {detail.code}</Text>
					</View>
					<Text style={{color:'#777777',fontSize:12,marginTop:6}}>联系电话：{detail.tel}</Text>
					<Text style={{color:'#777777',fontSize:12,marginTop:6}}>联系网址：{detail.web}</Text>
					<Text style={{color:'#777777',fontSize:12,marginTop:6}}>学院地址：{detail.address}</Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',padding:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2017)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2017}年招生计划</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',padding:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2016)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2016}年招生计划</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',padding:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2015)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2015}年招生计划</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',padding:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2014)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2014}年招生计划</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',padding:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2013)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2013}年招生计划</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>

			</View>
		);
	}
}

const styles = StyleSheet.create({

})