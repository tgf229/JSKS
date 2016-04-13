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
import { urlForQueryAndPage, netClient } from '../../util/NetUtil';
import GiftedListView from 'react-native-gifted-listview';

var ds;
var PAGE_NUM = 1;
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
		var price = rowData.price_formatted.split(' ')[0];
		return(
			<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='#fcfcfc'>
			  	<View>
			  		<View style={{flexDirection:'row',padding:14}}>
			  			<Image
			  			  style={{width:PixelRatio.get() * 130/4,height:PixelRatio.get() * 130/4}}
			  			  source={{uri: rowData.img_url}} />
			  			<View style={{height:PixelRatio.get() * 130/4,marginLeft:13,flex:1}}>
			  				<Text style={styles.title} numberOfLines={2}>{rowData.title}</Text>
			  				<Text style={styles.price}>{price}</Text>
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

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		PAGE_NUM = 1;
  		listData = [];
	    //封装链接地址
		var query = urlForQueryAndPage('place_name','london',PAGE_NUM);
		//调用接口
		netClient(this,query);

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
		console.log('PAGE_NUM= '+PAGE_NUM);
		//封装链接地址
		var query = urlForQueryAndPage('place_name','london',PAGE_NUM);
		//调用接口
		netClient(this,query); 
	}

	//请求回调
	busCB(json){
		if (json.response.application_response_code.substr(0,1) === '1') {
			listData = listData.concat(json.response.listings);
			this.setState({dataSource:ds.cloneWithRows(listData)});
			console.log('成功');
		}else{
			isLoadEnd = true;
			console.log('失败');
		}
	}

	render(){
		return(
			<View style={{flex:1,marginTop:64}}>
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


