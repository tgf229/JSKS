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
  Image,
  View
} from 'react-native';
import Header from './component/Header'
import { BUS_100101,BUS_100301 ,netClientPost} from '../../util/NetUtil';
import GiftedListView from 'react-native-gifted-listview';
import App_Title from '../common/App_Title';
import DeviceUUID from "react-native-device-uuid";
var NativeBridge = require('react-native').NativeModules.NativeBridge;

var ds;
var PAGE_NUM = 1;
var NUM = 10;
//列表数据集合
var listData = [];
//是否加载完毕所有数据
var isLoadEnd = false;

export default class Home extends React.Component{

	constructor(props){
		super(props);
		ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1.guid !== r2.guid});
		this.state={
			dataSource: ds.cloneWithRows(listData),
		};
	}

	//行点击
	rowPressed(rowData){
		console.log(rowData.title);
	}

	//渲染cell
	renderRow(rowData,sectionID,rowID){
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
			  				<Text style={styles.price}>{rowData.time}</Text>
			  			</View>
			  		</View>
			  		<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:10}}></View>
			  	</View>
			</TouchableHighlight>
		)
	}
	
	//头部界面
	renderHeader(){
		return(
			<Header homeObj={this}></Header>
		)
	}

	//列表底部loading
	renderFooter() {
	    return <ActivityIndicatorIOS  style={{marginVertical: 30,marginBottom: 30}} />;
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
			version:'1.0.0',
			imei:uuid,
		}
		netClientPost(this,BUS_100101,this.BUS_100101_CB,params);
	}

	componentDidMount() {
		  	//RN调用原生方法 获取UUDID和model 并调用初始化接口
			DeviceUUID.getUUID().then((uuid) => {
				NativeBridge.findEvents((error,events)=>{
					if (error) {
						console.log(error);
					}else{
						this.BUS_100101_REQ(events,uuid);
					}
				}) 
			});
	}

	//初始化接口回调
	BUS_100301_CB(object,json){
		if (json.retcode === '000000') {
			if (json.doc.length < 10) {
				isLoadEnd = true;
			}
			listData = listData.concat(json.doc);
			object.setState({dataSource:ds.cloneWithRows(listData)});
			console.log('成功='+listData);
		}else{
			isLoadEnd = true;
			console.log('失败');
		}
	}

	//初始化接口请求
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
		console.log('到达底部!')
	 	if (isLoadEnd) {
	 		return;
	 	}
		PAGE_NUM = PAGE_NUM+1;
		console.log('PAGE_NUM='+PAGE_NUM);

		this.BUS_100301_REQ();
	}

	render(){
		return(
			<View style={{flex:1}}>
				<App_Title title={'江苏省教育考试院101-4'} navigator={this.props.navigator} leftHid={true}/>
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
	price:{
		fontSize:12,
		color:'#999999',
		height:13,
	},
	title:{
		fontSize:16,
		color:'#444444',
		flex:1
	},
});


