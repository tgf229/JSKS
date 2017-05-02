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
import {BUS_700301,netClientPost,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
import App_Title from '../common/App_Title';

export default class University_Detail_Point extends Component{

	constructor(props) {
	  	super(props);
		this.ds = new ListView.DataSource({rowHasChanged:(r1,r2) => r1 != r2});
		this.listData = [];
	  	this.state = {
	  		dataSource : this.ds.cloneWithRows(this.listData),
	  		flag_success : true,
	  	};
	}

	// //列表请求回调
	// _BUS_700301_CB(object,json){
	// 	console.log(json);
	// 	if (json.retcode === '000000') {
	// 		object.listData = object.listData.concat(json.doc);
	// 		object.setState({
	// 			dataSource:object.ds.cloneWithRows(object.listData),
	// 			flag_success:true
	// 		})
	// 	}else{
	// 		object.setState({
	// 			flag_success:false
	// 		})
	// 	}
	// }

	// //列表请求
	// _BUS_700301_REQ(){
	// 	var params={
	// 		encrypt:'none'
	// 	}
	// 	netClientTest(this,BUS_700301,this._BUS_700301_CB,params);
	// }

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		this.listData = [];

  		// this._BUS_700301_REQ();

		var rows={};
		callback(rows);
    }


	_renderRow(rowData,sectionID,rowID){
		return(
			<View>
				<View style={{padding:15}}>
					<Text style={{color:'#8dadce',fontSize:12}}>{rowData.year}年 {rowData.clazz}</Text>
					<Text style={{color:'#4a4a4a',fontSize:15,marginTop:10}}>{rowData.batch}</Text>
					<Text style={{position:'absolute',right:15,bottom:15,color:'#4a4a4a',fontSize:12}}>录取人数：<Text style={{color:'#ff902d'}}>{rowData.num}</Text></Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:10,marginRight:10}}/>
				<View style={{padding:15}}>
					<Text style={{color:'#999999',fontSize:14}}>最高分：<Text style={{color:'#8dadce'}}>{rowData.hScore}</Text>            名次：<Text style={{color:'#8dadce'}}>{rowData.hBatch}</Text></Text>
					<Text style={{color:'#999999',fontSize:14,marginTop:10}}>最低分：<Text style={{color:'#8dadce'}}>{rowData.lScore}</Text>            名次：<Text style={{color:'#8dadce'}}>{rowData.lBatch}</Text></Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
			</View>
		)
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
				<App_Title navigator={this.props.navigator}/>
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
	tipsText:{
		marginRight:8,
		paddingTop:3,
		paddingBottom:3,
		paddingLeft:6,
		paddingRight:6,
		backgroundColor:'#F0F0F0',
		color:'#9ba0b0',
		fontSize:10
	}
})