'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	View,
	ScrollView,
	TouchableOpacity,
	ActivityIndicatorIOS,
	InteractionManager,
	Text,
	Image} from 'react-native';

import University_Detail_Major_Point from './University_Detail_Major_Point';
import {BUS_700301,netClientPost,ERROR_TIPS,REQ_TIPS,YEAR_2017,YEAR_2016,YEAR_2015,YEAR_2014,YEAR_2013} from '../../util/NetUtil';
import University_Chart from './University_Chart';

export default class University_Detail_Two extends Component{

	constructor(props) {
	  	super(props);
	  	this.isFirstIn = true;
	  	this.state = {
	  		currentClazzIndex:0,
	  		currentBatchIndex:0,
	  		chartData:[]
	  	};
	}

	//详情请求回调
	_BUS_700301_CB(object,json){
		if (json.retcode === '000000') {
			object.setState({
				chartData:json.doc
			})
		}else{

		}
	}

	//详情请求
	_BUS_700301_REQ(){
		var params={
			encrypt:'none',
			code:this.props.detail.code,
			clazz:this.props.detail.clazzDoc[this.state.currentClazzIndex].code,
			batch:this.props.detail.clazzDoc[this.state.currentClazzIndex].batchDoc[this.state.currentBatchIndex].code
		}
		netClientPost(this,BUS_700301,this._BUS_700301_CB,params);
	}

	_firstInChartSearch(){
		//定时器， 3秒后查询图表数据
		this.timer = setTimeout(
	        ()=>{
	            this._BUS_700301_REQ();
	        },2000
	    )
	}

	_clazzClick(index){
		this.setState({
			currentClazzIndex:index,
			currentBatchIndex:0,
			chartData:[]
		})
		this._BUS_700301_REQ();
	}

	_batchClick(index){
		this.setState({
			currentBatchIndex:index,
			chartData:[]
		})
		this._BUS_700301_REQ();
	}

	_rowClick(index){
		// InteractionManager.runAfterInteractions(()=>{
			this.props.navigator.push({
				component:University_Detail_Major_Point,
				params:{
					year:index,
					code:this.props.detail.code
				},
			});
		// })
	}

	componentWillUnmount() {
	  this.timer && clearTimeout(this.timer);
	}

	render(){
		const detail = this.props.detail;
		this.clazzDoc = detail.clazzDoc?detail.clazzDoc:[];
		// BUG clazzDoc为空数组是true 会导致undefined
		this.batchDoc = detail.clazzDoc?detail.clazzDoc[this.state.currentClazzIndex].batchDoc:[];
		if (this.clazzDoc.length > 0 && this.batchDoc.length >0 && this.isFirstIn) {
			this._firstInChartSearch(); //如果第一次进来，且详情接口已经返回数据且数据不为空，则查询图表数据。
			this.isFirstIn=false;
		}
		var that = this;
		return(
			<View style={{width:global.windowWidth}}>
				{/*第一部分*/}
				<View style={{flexDirection:'row',height:35,paddingLeft:15,alignItems:'center'}}>
					<View style={{width:5,height:5,backgroundColor:'#fc7655'}}/>
					<Text style={{marginLeft:8,fontSize:12,fontWeight:'bold',color:'#444444'}}>院校历年录取分数线</Text>
				</View>
				<View style={{backgroundColor:'#f1f7fc',flexDirection:'row',flexWrap:'wrap'}}>
					{
						this.clazzDoc.map(function(item,index){
							if (that.state.currentClazzIndex == index) {
								return(
									<View
										style={{width:50,height:25,borderRadius:20,backgroundColor:'#4990e2',marginLeft:15,marginTop:8,marginBottom:8,justifyContent:'center',alignItems:'center'}}>
										<Text style={{fontSize:10,color:'white'}}>{item.name}</Text>
									</View>
								)
							}else{
								return(
									<TouchableOpacity
										onPress={()=>{that._clazzClick(index)}}
										style={{width:50,height:25,borderRadius:20,borderWidth:1,borderColor:'#4990e2',marginLeft:15,marginTop:8,marginBottom:8,justifyContent:'center',alignItems:'center'}}>
										<Text style={{fontSize:10,color:'#4990e2'}}>{item.name}</Text>
									</TouchableOpacity>
								)
							}
						})
					}
					
				</View>
				<View style={{flexDirection:'row',justifyContent:'center',alignItems:'center',paddingTop:12,flexWrap:'wrap'}}>
					{
						this.batchDoc.map(function(item,index) {
							if (that.state.currentBatchIndex == index) {
								return(
									<View
										style={{padding:10,flexDirection:'row',}}>
										<Image source={require('image!inputbox_press')}/>
										<Text style={{fontSize:11,color:'#444444',marginLeft:5}}>{item.name}</Text>
									</View>
								)
							}else{
								return(
									<TouchableOpacity 
										onPress={()=>{that._batchClick(index)}}
										style={{padding:10,flexDirection:'row'}}>
										<Image source={require('image!inputbox_blank')}/>
										<Text style={{fontSize:11,color:'#444444',marginLeft:5}}>{item.name}</Text>
									</TouchableOpacity>
								)
							}
						})
					}
				</View>
				{/*图表*/}
				{
					this.state.chartData.length>0
					?
					<View>
						<University_Chart chartData={this.state.chartData}/>
						<View style={{flexDirection:'row',justifyContent:'center',alignItems:'center',marginBottom:12}}>
							<View style={{width:5,height:5,backgroundColor:'#8ededc'}}/>
							<Text style={{fontSize:9,color:'#999999',marginLeft:7,marginRight:10}}>历年录取最高分</Text>
							<View style={{width:5,height:5,backgroundColor:'#fc7655',marginLeft:10}}/>
							<Text style={{fontSize:9,color:'#999999',marginLeft:7}}>历年录取最低分</Text>
						</View>
					</View>
					:
					<ActivityIndicatorIOS  style={{marginVertical: 30,marginBottom: 30}} />
				}

				<View style={{height:10,backgroundColor:'#f3f3f3'}}/>
			{/*第二部分*/}
				<View style={{flexDirection:'row',height:35,paddingLeft:15,alignItems:'center'}}>
					<View style={{width:5,height:5,backgroundColor:'#90cd22'}}/>
					<Text style={{marginLeft:8,fontSize:12,fontWeight:'bold',color:'#444444'}}>历年专业录取分数线</Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',paddingLeft:28,paddingRight:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2016)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2016}年各专业录取分数线</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',paddingLeft:28,paddingRight:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2015)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2015}年各专业录取分数线</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',paddingLeft:28,paddingRight:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2014)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2014}年各专业录取分数线</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<TouchableOpacity 
					style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',paddingLeft:28,paddingRight:15,height:44}}
					onPress={()=>this._rowClick(YEAR_2013)}>
					<Text style={{fontSize:14,color:'#444444'}}>{YEAR_2013}年各专业录取分数线</Text>
					<Image source={require('image!arrow_grey')}/>
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>

			</View>
		);
	}
}

const styles = StyleSheet.create({

})