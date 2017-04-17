'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	ListView,
	View,
	PixelRatio,
	Text,
	Image} from 'react-native';

import GiftedListView from 'react-native-gifted-listview';
import {BUS_700501,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
import App_Title from '../common/App_Title';

export default class University_Detail_Enroll extends Component{

	constructor(props) {
	  	super(props);
		this.ds = new ListView.DataSource({rowHasChanged:(r1,r2) => r1 != r2});
		this.listData = [];
	  	this.state = {
	  		dataSource : this.ds.cloneWithRows(this.listData),
	  		schoolData:{},
	  		flag_success : true,
	  	};
	}

	//列表请求回调
	_BUS_700501_CB(object,json){
		console.log(json);
		if (json.retcode === '000000') {
			object.listData = object.listData.concat(json.doc);
			object.setState({
				dataSource:object.ds.cloneWithRows(object.listData),
				schoolData:json,
				flag_success:true
			})
		}else{
			object.setState({
				flag_success:false
			})
		}
	}

	//列表请求
	_BUS_700501_REQ(){
		var params={
			encrypt:'none'
		}
		netClientTest(this,BUS_700501,this._BUS_700501_CB,params);
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		this.listData = [];

  		this._BUS_700501_REQ();

		var rows={};
		callback(rows);
    }


	_renderRow(rowData,sectionID,rowID){
		return(
			<View style={{padding:15}}>
				<View style={{flexDirection:'row'}}>
					<Text style={{flex:1,fontSize:14,color:'#4a4a4a'}}>{rowData.name}</Text>
					<Text style={{fontSize:12,color:'#4a4a4a'}}>专业代号{rowData.code}</Text>
				</View>
				<View style={{flexDirection:'row',justifyContent:'space-around',marginTop:10}}>
					<Text style={{fontSize:14,color:'#999999'}}>计划数：<Text style={{color:'#bdadce'}}>{rowData.num}人</Text></Text>
					<Text style={{fontSize:14,color:'#999999'}}>学制：<Text style={{color:'#bdadce'}}>{rowData.year}年</Text></Text>
					<Text style={{fontSize:14,color:'#999999'}}>学费：<Text style={{color:'#bdadce'}}>{rowData.cost}元</Text></Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5',marginTop:15}}/>
			</View>
		)
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
				<App_Title navigator={this.props.navigator}/>
				<Image 
					style={{width:global.windowWidth,height:global.windowWidth/2,backgroundColor:'transparent'}}
					source={require('image!detail_bg_cloud')}>
					<View style={{height:40,paddingLeft:20,paddingRight:20,flexDirection:'row',alignItems:'center',justifyContent:'space-between'}}>
						<Text style={{fontSize:15,color:'white'}}>{this.state.schoolData.name}</Text>
						<Text style={{fontSize:12,color:'white'}}>院校代号 {this.state.schoolData.code}</Text>
					</View>
					<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:10,marginRight:10}}/>
					<View style={{paddingLeft:20,paddingRight:20,paddingTop:12}}>
						<Text style={{fontSize:12,color:'white'}}>选测科目等级要求：{this.state.schoolData.rank}</Text>
						<Text style={{fontSize:12,color:'white',marginTop:6}}>联系电话：{this.state.schoolData.tel}</Text>
						<Text style={{fontSize:12,color:'white',marginTop:6}}>联系网址：{this.state.schoolData.web}</Text>
						<Text style={{fontSize:12,color:'white',marginTop:6}}>学院地址：{this.state.schoolData.address}</Text>
					</View>
				</Image>
				{
					this.state.flag_success
					?
					<GiftedListView
						dataSource={this.state.dataSource}
						renderRow={(rowData)=>this._renderRow(rowData)}

						onFetch={this._onFetch.bind(this)}
					/>
					:
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Image
					  		source={require('image!load_pic')} />
					  	<Text style={{fontSize:20,color:'#888888',marginTop:10,marginBottom:80}}>{ERROR_TIPS}</Text>
					</View>
				}
			</View>
		);
	}
}

const styles = StyleSheet.create({
	
})