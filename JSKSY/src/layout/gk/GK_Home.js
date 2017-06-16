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
import GK_Header from './component/GK_Header'
import {BUS_100501 ,netClientPost} from '../../util/NetUtil';
import GiftedListView from 'react-native-gifted-listview';
import App_Title from '../common/App_Title';
import Web from '../webview/Web';
import University_Detail from '../university/University_Detail';
import {URL_SCHEMA_SCHOOL_DETAIL} from '../../util/Global';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

// var ds;
// var PAGE_NUM;
// var NUM = 10;
//列表数据集合
// var listData = [];
//是否加载完毕所有数据
// var hasMore;

export default class GK_Home extends React.Component{

	constructor(props){
		super(props);
		this.listData = [];
		this.ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
		// PAGE_NUM = 1;
		// hasMore = false;
		this.state={
			isFirstIn:true,
			dataSource: this.ds.cloneWithRows(this.listData),
		};
	}

	componentDidMount() {
		//定时器， 1秒后查询数据
		this.timer = setTimeout(
	        ()=>{
	            this.setState({
	            	isFirstIn:false,
	            });
	        },1000
	    )
	}

	componentWillUnmount() {
	  this.timer && clearTimeout(this.timer);
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
			<GK_Header navigator={this.props.navigator}/>
		)
	}

	//列表底部loading
	// renderFooter() {
	// 	if (hasMore) {
	// 		return <ActivityIndicatorIOS  style={{marginVertical: 30,marginBottom: 30}} />;
	// 	}
	// }

	//列表接口回调
	BUS_100501_CB(object,json){
		if (json.retcode === '000000') {
			// if (json.doc.length >= 10) {
			// 	hasMore = true;
			// }else{
			// 	hasMore = false;
			// }
			object.listData = object.listData.concat(json.doc);
			object.setState({
				dataSource:object.ds.cloneWithRows(object.listData)
			});
		}else{
			console.log('Home BUS_100301_CB 失败');
		}
	}

	//列表接口请求
	BUS_100501_REQ(){
		var params = {
			encrypt:'none',
			type:'1'
			// page:PAGE_NUM,
			// num:NUM,
		}
		netClientPost(this,BUS_100501,this.BUS_100501_CB,params);
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		// PAGE_NUM = 1;
  		this.listData = [];

  		this.BUS_100501_REQ();

		var rows={};
		callback(rows);
    }

    //加载更多
 //    onEndReached() {
	//  	if (hasMore) {
	//  		PAGE_NUM = PAGE_NUM+1;
	// 		this.BUS_100301_REQ();
	//  	}
	// }

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
				<App_Title title={'高考频道'} navigator={this.props.navigator}/>
				{this.state.isFirstIn
				?
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Image
					  		source={require('image!load_pic')} />
					  	<ActivityIndicatorIOS />
					  	<Text style={{fontSize:20,color:'#888888',marginTop:10,marginBottom:80}}>正在加载...</Text>
					</View>
				:
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
						style={{backgroundColor:'white'}}
						/>
				}
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


