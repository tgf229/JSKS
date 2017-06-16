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
  TouchableHighlight,
  ListView,
  ActivityIndicatorIOS,
  PixelRatio,
  Dimensions,
  Image,
  View
} from 'react-native';
import Header from './component/Header'
import { BUS_100101,BUS_100501 ,netClientPost,URL_ADDR} from '../../util/NetUtil';
import GiftedListView from 'react-native-gifted-listview';
import App_Title from '../common/App_Title';
import DeviceUUID from "react-native-device-uuid";
import Web from '../webview/Web';
import University_Detail from '../university/University_Detail';
import {URL_SCHEMA_SCHOOL_DETAIL} from '../../util/Global';

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

	//行点击
	rowPressed(rowData){
		if (rowData.aUrl.indexOf(URL_SCHEMA_SCHOOL_DETAIL)!= -1) {
			const dId = rowData.aUrl.substring(rowData.aUrl.lastIndexOf("/")+1);
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
					url:rowData.aUrl,
				},
			});
		}
	}

	//渲染cell
	renderRow(rowData,sectionID,rowID){
		if (rowData.type === '1') {
		return(
			<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='#fcfcfc'>
			  	<View>
			  		<View style={{padding:14}}>
			 			<Image
				  			 style={{width:Dimensions.get('window').width-28, height:(Dimensions.get('window').width-28)*2/7}}
				 			 source={{uri: rowData.imageUrl}} />
		  			</View>
			  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
			  	</View>
			</TouchableHighlight>
			)
		}else{
		return(
			<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='#fcfcfc'>
			  	<View>
				  	<View style={{height:84,paddingTop:12,paddingBottom:12,paddingLeft:15,paddingRight:15,justifyContent:'center'}}>
				  		<Text style={styles.title} numberOfLines={2}>{rowData.name}</Text>
				  		<Text style={styles.time}>发布于{rowData.time}</Text>
				  	</View>
			  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
			  	</View>
			</TouchableHighlight>
			)
		}
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
			version:'2.3.0',
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
						this.BUS_100101_REQ(events,uuid);
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
	time:{
		fontSize:12,
		color:'#999999',
		marginTop:5,
	},
	title:{
		fontSize:15,
		lineHeight:20,
		color:'#444444',
	},
});