/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  TouchableOpacity,
  ListView,
  ActivityIndicatorIOS,
  PixelRatio,
  Dimensions,
  Image,
  View
} from 'react-native';
import Header from './component/Header';
import { BUS_100101,BUS_100501 ,BUS_100601,netClientPost,URL_ADDR} from '../../util/NetUtil';
import GiftedListView from 'react-native-gifted-listview';
import App_Title from '../common/App_Title';
import DeviceUUID from "react-native-device-uuid";
import Web from '../webview/Web';
import University_Detail from '../university/University_Detail';
import University_List from '../university/University_List';
import {URL_SCHEMA_SCHOOL_DETAIL,URL_SCHEMA_SCHOOL_LIST} from '../../util/Global';
import NewsComponent from './component/NewsComponent';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

var ds;
//列表数据集合
var listData = [];

export default class Home extends React.Component{

	constructor(props){
		super(props);

		ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
		this.state={
			dataSource: ds.cloneWithRows(listData),
		};
	}

		//广告日志接口回调
	BUS_100601_CB(object,json){
	}

	//广告日志接口
	BUS_100601_REQ(uuid,aType,aId){
		var params = {
			encrypt:'none',
			imei:uuid,
			aType:aType,
			aId:aId
		}
		netClientPost(this,BUS_100601,this.BUS_100601_CB,params);
	}

	//行点击
	rowPressed(rowData){
		if (rowData.type == '1') {
			this.BUS_100601_REQ(global.uuid,'3',rowData.aId);
		}
		if (rowData.aUrl.indexOf(URL_SCHEMA_SCHOOL_DETAIL)!= -1) {
			const dId = rowData.aUrl.substring(rowData.aUrl.lastIndexOf("/")+1);
			this.props.navigator.push({
				component:University_Detail,
				params:{
					uCode:dId,
				},
			});
		}else if (rowData.aUrl.indexOf(URL_SCHEMA_SCHOOL_LIST)!= -1) {
			const keyword = rowData.aUrl.substring(rowData.aUrl.lastIndexOf("/")+1);
			this.props.navigator.push({
				component:University_List,
				params:{
					uName:keyword,
				},
			});
		}else{
			this.props.navigator.push({
				component:Web,
				params:{
					url:rowData.aUrl,
				},
			});
		}
	}

	//渲染cell
	renderRow(rowData,sectionID,rowID){
		return(
			<NewsComponent rowData={rowData}/>
		)
	}
		
	
	//头部界面
	renderHeader(){
		return(
			<Header navigator={this.props.navigator}></Header>
		)
	}

	//初始化接口回调
	BUS_100101_CB(object,json){
		if (json.retcode === '000000') {
			if (json.conf) {
				console.log('BUS_100101_CB成功');
				global.init_gkAdSchool = json.conf.gkAdSchool?json.conf.gkAdSchool:''
			}
		}else{

		}
	}

	//初始化接口请求
	BUS_100101_REQ(model,uuid){
		var params = {
			encrypt:'none',
			type:'2',
			model:model,
			version:'2.3.8',
			imei:uuid,
		}
		netClientPost(this,BUS_100101,this.BUS_100101_CB,params);
	}

	componentDidMount() {
		  	//RN调用原生方法 获取UUDID和model 并调用初始化接口
			DeviceUUID.getUUID().then((uuid) => {
				NativeBridge.NATIVE_getDeviceModel((error,events)=>{
					if (error) {
						console.log(error);
					}else{
						global.uuid = uuid;
						this.BUS_100101_REQ(events,uuid);
						if (this.props.aId) {
							this.BUS_100601_REQ(uuid,'1',this.props.aId);	
						}
					}
				}) 
			});
		if (this.props.adUrl) {
			const adUrl = this.props.adUrl;
			if (adUrl.indexOf(URL_SCHEMA_SCHOOL_DETAIL)!= -1) {
				const dId = adUrl.substring(adUrl.lastIndexOf("/")+1);
				this.props.navigator.push({
					component:University_Detail,
					params:{
						uCode:dId,
					},
				});
			}else{
				this.props.navigator.push({
					component:Web,
					params:{
						url:adUrl,
					},
				});
			}
		}
	}

	//列表接口回调
	BUS_100501_CB(object,json){
		if (json.retcode === '000000') {
			listData = listData.concat(json.doc);
			object.setState({dataSource:ds.cloneWithRows(listData)});
		}else{
			console.log('Home BUS_100501_CB 失败');
		}
	}

	//列表接口请求
	BUS_100501_REQ(){
		var params = {
			encrypt:'none',
		}
		netClientPost(this,BUS_100501,this.BUS_100501_CB,params);
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		listData = [];

  		this.BUS_100501_REQ();

		var rows={};
		callback(rows);
    }

	render(){
		return(
			<View style={{flex:1}}>
				<App_Title title={'江苏招考'} navigator={this.props.navigator} leftHid={true} setShow={true}/>
					<GiftedListView
						dataSource={this.state.dataSource}
						renderRow={(rowData) => this.renderRow(rowData)} 

						pageSize={10}
						onEndReachedThreshold={50}
						automaticallyAdjustContentInsets={false}
		        		showsVerticalScrollIndicator={false}

		        		renderHeader={this.renderHeader.bind(this)}
						
						onFetch={this._onFetch.bind(this)}
			          	refreshable={true}   
						/>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});