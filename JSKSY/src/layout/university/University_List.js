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
import {BUS_700101,netClientTest} from '../../util/NetUtil';
import App_Title from '../common/App_Title';

export default class University_List extends Component{

	constructor(props) {
	  	super(props);
		this.ds = new ListView.DataSource({rowHasChanged:(r1,r2) => r1 != r2});
		this.listData = [];
	  	this.state = {
	  		dataSource : this.ds.cloneWithRows(this.listData),
	  	};
	}

	//列表请求回调
	_BUS_700101_CB(object,json){
		if (json.retcode === '000000') {
			object.listData = object.listData.concat(json.doc);
			object.setState({
				dataSource:object.ds.cloneWithRows(object.listData)
			})
		}else{
			//TODO
		}
	}

	//列表请求
	_BUS_700101_REQ(){
		var params={
			encrypt:'none'
		}
		netClientTest(this,BUS_700101,this._BUS_700101_CB,params);
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){
  		this.listData = [];

  		this._BUS_700101_REQ();

		var rows={};
		callback(rows);
    }

	_renderRow(rowData,sectionID,rowID){
		return(
			<View>
				<View style={{flexDirection:'row',padding:15}}>
					<Image 
						style={{width:PixelRatio.get()*120/4,height:PixelRatio.get()*120/4}}
						source={{uri:rowData.logo}}
						/>
					<View style={{marginLeft:15,marginTop:5}}>
						<Text style={{fontSize:15,color:'#4a4a4a'}}>{rowData.name}</Text>
						<View style={{flexDirection:'row',marginTop:15}}>
							{
								rowData.type
								?
								<Text style={styles.tipsText}>{rowData.type}</Text>
								:
								null
							}
							{
								rowData.rank
								?
								<Text style={styles.tipsText}>排名{rowData.rank}</Text>
								:
								null
							}
							{
								rowData.isEyy === '1'
								?
								<Text style={styles.tipsText}>211</Text>
								:
								null
							}
							{
								rowData.isJbw === '1'
								?
								<Text style={styles.tipsText}>985</Text>
								:
								null
							}
						</View>
					</View>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
			</View>
		)
	}

	render(){
		return(
			<View style={{flex:1}}>
				<App_Title navigator={this.props.navigator}/>
				<GiftedListView
					dataSource={this.state.dataSource}
					renderRow={(rowData)=>this._renderRow(rowData)}

					onFetch={this._onFetch.bind(this)}
				/>
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