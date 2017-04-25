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
import University_Chart from './University_Chart';

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
		const detail = this.props.detail;
		const clazzDoc = this.props.detail.clazzDoc?this.props.detail.clazzDoc:[];
		return(
			<View style={{width:global.windowWidth}}>
				{/*第一部分*/}
				<View style={{flexDirection:'row',height:35,paddingLeft:15,alignItems:'center'}}>
					<View style={{width:5,height:5,backgroundColor:'#fc7655'}}/>
					<Text style={{marginLeft:8,fontSize:12,fontWeight:'bold',color:'#444444'}}>院校历年录取分数线</Text>
				</View>
				<View style={{backgroundColor:'#f1f7fc',flexDirection:'row',flexWrap:'wrap'}}>
					{
						clazzDoc.map(function(item,index){
							if (index == 0) {
								return(
									<View style={{width:50,height:25,borderRadius:20,backgroundColor:'#4990e2',marginLeft:15,marginTop:8,marginBottom:8,justifyContent:'center',alignItems:'center'}}>
										<Text style={{fontSize:10,color:'white'}}>{item.name}</Text>
									</View>
								)
							}else{
								return(
									<View style={{width:50,height:25,borderRadius:20,borderWidth:1,borderColor:'#4990e2',marginLeft:15,marginTop:8,marginBottom:8,justifyContent:'center',alignItems:'center'}}>
										<Text style={{fontSize:10,color:'#4990e2'}}>{item.name}</Text>
									</View>
								)
							}
						})
					}
					
				</View>
				<View style={{flexDirection:'row',justifyContent:'center',alignItems:'center',paddingTop:12}}>
					<View style={{marginLeft:10,marginRight:10,flexDirection:'row',}}>
						<Image source={require('image!inputbox_press')}/>
						<Text style={{fontSize:11,color:'#444444',marginLeft:5}}>本科一批</Text>
					</View>
				</View>
				{/*图表*/}
				<University_Chart/>
				<View style={{flexDirection:'row',justifyContent:'center',alignItems:'center',marginBottom:12}}>
					<View style={{width:5,height:5,backgroundColor:'#8ededc'}}/>
					<Text style={{fontSize:9,color:'#999999',marginLeft:7,marginRight:10}}>历年录取最高分</Text>
					<View style={{width:5,height:5,backgroundColor:'#fc7655',marginLeft:10}}/>
					<Text style={{fontSize:9,color:'#999999',marginLeft:7}}>历年录取最低分</Text>
				</View>

				<View style={{height:10,backgroundColor:'#d5d5d5'}}/>
			{/*第二部分*/}
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