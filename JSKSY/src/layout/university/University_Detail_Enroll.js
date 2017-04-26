'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	ListView,
	View,
	SectionList,
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
			<View>
				<View style={{height:44,alignItems:'center',justifyContent:'center'}}>
					<Text style={{fontSize:13,color:'#444444'}}>{rowData.clazz}{rowData.batch}</Text>
					<Text style={{fontSize:11,color:'#444444'}}>选测科目等级要求： {rowData.doc[0].rank}</Text>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				{
					rowData.doc.map(function(item,index){
						return(
							<View>
								<View style={{flexDirection:'row',alignItems:'center',paddingTop:10,paddingBottom:10}}>
									<View style={{flex:4,justifyContent:'center'}}>
										<Text style={{fontSize:14,color:'#444444',marginLeft:10,}}>{item.name}</Text>
										<Text style={{fontSize:10,color:'#666666',marginTop:4,marginLeft:10,}}>专业代号 {item.code}</Text>
									</View>
									<View style={{flex:1,alignItems:'center',minHeight:'300px'}}>
										<Text style={{fontSize:12,color:'#444444'}}>{item.num}人</Text>
									</View>
									<View style={{flex:1,alignItems:'center'}}>
										<Text style={{fontSize:12,color:'#444444'}}>{item.year}</Text>
									</View>
									<View style={{flex:1,alignItems:'center'}}>
										<Text style={{fontSize:12,color:'#444444'}}>{item.cost}</Text>
									</View>
								</View>
								<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
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
				<App_Title title={'招生计划'} navigator={this.props.navigator}/>
				<View style={{flexDirection:'row',height:30,backgroundColor:'#fafafa'}}>
					<View style={{flex:4,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:11,color:'#999999'}}>专业</Text>
					</View>
					<View style={{width:0.5,backgroundColor:'#d5d5d5'}}/>
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:11,color:'#999999'}}>计划数</Text>
					</View>
					<View style={{width:0.5,backgroundColor:'#d5d5d5'}}/>
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:11,color:'#999999'}}>学制</Text>
					</View>
					<View style={{width:0.5,backgroundColor:'#d5d5d5'}}/>
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:11,color:'#999999'}}>学费</Text>
					</View>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
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