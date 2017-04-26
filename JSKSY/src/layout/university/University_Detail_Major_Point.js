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
import {BUS_700401,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
import App_Title from '../common/App_Title';

export default class University_Detail_Major_Point extends Component{

	constructor(props) {
	  	super(props);
		this.ds = new ListView.DataSource({rowHasChanged:(r1,r2) => r1 != r2});
		this.listData = [];
	  	this.state = {
	  		dataSource : this.ds.cloneWithRows(this.listData),
	  		flag_success : true,
	  	};
	}

	//列表请求回调
	_BUS_700401_CB(object,json){
		console.log(json);
		if (json.retcode === '000000') {
			object.listData = object.listData.concat(json.doc);
			object.setState({
				dataSource:object.ds.cloneWithRows(object.listData),
				flag_success:true
			})
		}else{
			object.setState({
				flag_success:false
			})
		}
	}

	//列表请求
	_BUS_700401_REQ(){
		var params={
			encrypt:'none'
		}
		netClientTest(this,BUS_700401,this._BUS_700401_CB,params);
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		this.listData = [];

  		this._BUS_700401_REQ();

		var rows={};
		callback(rows);
    }


	_renderRow(rowData,sectionID,rowID){
		return(
			<View>
				<View style={{flexDirection:'row',height:40,justifyContent:'space-between',alignItems:'center',paddingLeft:15,paddingRight:15}}>
					<Text style={{fontSize:12,color:'#666666'}}>专业代号 {rowData.code}    {rowData.clazz}</Text>
					<Text style={{fontSize:12,color:'#666666'}}>{rowData.batch}</Text>
				</View>
				{
					rowData.doc.map(function(item){
						return(
							<View>
								<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
								<View style={{padding:15}}>
									<View style={{flexDirection:'row',height:50}}>
										<View style={{flex:1,justifyContent:'space-between'}}>
											<Text style={{fontSize:12,color:'#4a4a4a'}}>{item.name}</Text>
											<Text style={{fontSize:16,color:'#ff902d'}}>{item.num}</Text>
											<Text style={{fontSize:10,color:'#999999'}}>{item.year}年录取人数</Text>
										</View>
										<View style={{width:1,backgroundColor:'#d5d5d5'}}/>
										<View style={{flex:2,marginLeft:15,justifyContent:'space-between'}}>
											<Text style={{fontSize:14,color:'#999999'}}>最高分：<Text style={{fontSize:14,color:'#8dadce'}}>{item.hScore}</Text>    名次：<Text style={{fontSize:14,color:'#8dadce'}}>{item.hBatch}</Text></Text>
											<Text style={{fontSize:14,color:'#999999'}}>最低分：<Text style={{fontSize:14,color:'#8dadce'}}>{item.lScore}</Text>    名次：<Text style={{fontSize:14,color:'#8dadce'}}>{item.lBatch}</Text></Text>
										</View>
									</View>
									<View style={{marginTop:15,backgroundColor:'#f3f9ff',height:22,flexDirection:'row',justifyContent:'space-around',alignItems:'center',borderRadius:4}}>
										<Text style={{fontSize:12,color:'#8dadce'}}>平行志愿人数：{item.pNum}</Text>
										<Text style={{fontSize:12,color:'#8dadce'}}>平行志愿人数：{item.sNum}</Text>
										<Text style={{fontSize:12,color:'#8dadce'}}>平行志愿人数：{item.oNum}</Text>
									</View>
								</View>
							</View>
						)
					})
				}
				<View style={{height:10,backgroundColor:'#d5d5d5'}}/>
			</View>
		)
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
				<App_Title title={'专业录取分数线'} navigator={this.props.navigator}/>
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