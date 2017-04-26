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
import {BUS_700501,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
export default class University_Detail_Three extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {
	  		detail:{}
	  	};
	}

	//列表请求回调
	_BUS_700501_CB(object,json){
		console.log(json);
		if (json.retcode === '000000') {
			object.setState({
				detail:json.detail
			})
		}else{

		}
	}

	//请求
	_BUS_700501_REQ(){
		var params={
			encrypt:'none'
		}
		netClientTest(this,BUS_700501,this._BUS_700501_CB,params);
	}

	_rowClick = ()=>{
		this.props.navigator.push({
			component:University_Detail_Enroll
		})
	};

	componentDidMount() {
		this._BUS_700501_REQ();
	}

	render(){
		console.log('render3')
		const detail = this.state.detail
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