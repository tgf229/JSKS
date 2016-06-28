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
import { BUS_100101,BUS_100301 ,netClientPost} from '../../util/NetUtil';
import GiftedListView from 'react-native-gifted-listview';
import App_Title from '../common/App_Title';
import DeviceUUID from "react-native-device-uuid";
import Web from '../webview/Web';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

var ds;
var PAGE_NUM;
var NUM = 10;
//列表数据集合
var listData = [];
//是否加载完毕所有数据
var hasMore;

export default class Home extends React.Component{

	constructor(props){
		super(props);
		ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
		PAGE_NUM = 1;
		hasMore = false;
		this.state={
			dataSource: ds.cloneWithRows(listData),
		};
	}

	//行点击
	rowPressed(rowData){
		this.props.navigator.push({
			component:Web,
			params:{
				url:rowData.aUrl,
			},
		});
	}

	//渲染cell
	renderRow(rowData,sectionID,rowID){
		if (rowData.type === '2') {
		return(
			<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='#fcfcfc'>
			  	<View>
			  		<View style={{flexDirection:'row',padding:14}}>
			 			<Image
				  			 style={{width:PixelRatio.get() * 130/4,height:PixelRatio.get() * 130/4}}
				 			 source={{uri: rowData.imageUrl}} />
				  			<View style={{height:PixelRatio.get() * 130/4,marginLeft:13,flex:1}}>
				  				<Text style={styles.title} numberOfLines={2}>{rowData.name}</Text>
				  				<Text style={styles.time}>{rowData.time}</Text>
				  			</View>
		  			</View>
			  		<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:10}}></View>
			  	</View>
			</TouchableHighlight>
			)
		}else{
		return(
			<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='#fcfcfc'>
			  	<View>
			  		<View style={{padding:14}}>
			  			<Text style={styles.title} numberOfLines={1}>{rowData.name}</Text>
			 			<Image
				  			 style={{marginTop:11 ,width:Dimensions.get('window').width-28, height:(Dimensions.get('window').width-28)*2/7}}
				 			 source={{uri: rowData.imageUrl}} />
		  			</View>
			  		<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:10}}></View>
			  	</View>
			</TouchableHighlight>
			)
		}
		
	}
	
	//头部界面
	renderHeader(){
		return(
			<Header homeObj={this}></Header>
		)
	}

	//列表底部loading
	renderFooter() {
		if (hasMore) {
			return <ActivityIndicatorIOS  style={{marginVertical: 30,marginBottom: 30}} />;
		}
	}

	//初始化接口回调
	BUS_100101_CB(object,json){
		console.log('BUS_100101_CB成功');
	}

	//初始化接口请求
	BUS_100101_REQ(model,uuid){
		var params = {
			encrypt:'none',
			type:'2',
			model:model,
			version:'1.1.0',
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
			this.props.navigator.push({
				component:Web,
				params:{
					url:this.props.adUrl,
				},
			});
		}
	}

	//列表接口回调
	BUS_100301_CB(object,json){
		if (json.retcode === '000000') {
			if (json.doc.length >= 10) {
				hasMore = true;
			}else{
				hasMore = false;
			}
			listData = listData.concat(json.doc);
			object.setState({dataSource:ds.cloneWithRows(listData)});
		}else{
			console.log('Home BUS_100301_CB 失败');
		}
	}

	//列表接口请求
	BUS_100301_REQ(){
		var params = {
			encrypt:'none',
			page:PAGE_NUM,
			num:NUM,
		}
		netClientPost(this,BUS_100301,this.BUS_100301_CB,params);
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		PAGE_NUM = 1;
  		listData = [];

  		this.BUS_100301_REQ();

		var rows={};
		callback(rows);
    }

    //加载更多
    onEndReached() {
	 	if (hasMore) {
	 		PAGE_NUM = PAGE_NUM+1;
			this.BUS_100301_REQ();
	 	}
	}

	render(){
		return(
			<View style={{flex:1}}>
				<App_Title title={'江苏高考'} navigator={this.props.navigator} leftHid={true}/>
					<GiftedListView
						dataSource={this.state.dataSource}
						renderRow={(rowData) => this.renderRow(rowData)} 

						pageSize={10}
						onEndReachedThreshold={50}
						automaticallyAdjustContentInsets={false}
		        		showsVerticalScrollIndicator={false}

		        		onEndReached={()=>this.onEndReached()}
		        		renderHeader={this.renderHeader.bind(this)}
		        		renderFooter={() => this.renderFooter()}
						
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
		position:'absolute',
		left:1,
		bottom:1,
		height:13,
	},
	title:{
		fontSize:16,
		lineHeight:22,
		color:'#444444',
	},
});


