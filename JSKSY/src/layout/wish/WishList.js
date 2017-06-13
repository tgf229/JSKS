/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  TouchableHighlight,
  ListView,
  ActivityIndicatorIOS,
  ScrollView,
  Text,
  Image,
  View
} from 'react-native';
import GiftedListView from 'react-native-gifted-listview';
import {URL_ADDR,BUS_300101, BUS_300201,urlForQueryAndPage, netClientPostEncrypt } from '../../util/NetUtil';
import WishFilter from './WishFilter';
import App_Title from '../common/App_Title';
import Web from '../webview/Web';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

// var listData = [	{id:'1101',name:'南京大学',batch:'本科一批',num:'158'},
// 			   		{id:'1102',name:'南京航空航天大学',batch:'本一',num:'28'},
// 			   		{id:'1103',name:'南京信息工程大学',batch:'本科二批',num:'371'},
// 			   ];
var listData = [];
var ds;

export default class WishList extends React.Component{
	constructor(props){
		super(props);
		ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
		this.isFirst = true;
		this.state={
			dataSource: ds.cloneWithRows(listData),
			
			batch:'本科一批',
			batchVal:'2',

			provId:'',
			provVal:'全国',

			typeId:'',
			typeVal:'请选择',

			marjorId:'',
			marjorVal:'请选择',

			eyy:false,
			jbw:false,

			isLoaded:false,
			errorLoading:true,
			errorTips:'正在拼命查询中，请稍候...',

		};
	}

	//列表接口回调
	BUS_300101_CB(object,response){
		//加解密参数
		NativeBridge.NATIVE_getDecryptData(response._bodyText,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				console.log('BUS_300101_CB = '+events);
				var json = JSON.parse(events);
				if (json.retcode === '000000') {
					if (json.doc.length > 0) {
						listData = listData.concat(json.doc);
						object.setState({
							dataSource:ds.cloneWithRows(listData),
							isLoaded:true,
							errorLoading:false,
						});	
					}
					else{
						object.setState({
							dataSource:ds.cloneWithRows(listData),
							isLoaded:false,
							errorLoading:false,
							errorTips:'未查询到数据哦，点右上角筛选按钮再试试吧',
						});	
					}
					
				}else{
					let error='请求失败，请稍后再试';
					if (json.retinfo) {
						error = json.retinfo;
					}
					object.setState({
						errorLoading:false,
						errorTips:error,
					});
				}
			}	
		})
	}

	//列表接口请求
	BUS_300101_REQ(){
		listData = [];
		var dict = {
			sNum : this.props.sNum,
			sTicket : this.props.sTicket,
			isJbw : this.state.jbw ? '1' : '',
			isEyy : this.state.eyy ? '1' : '',
			batch : this.state.batchVal,
			province : this.state.provId,
			type : this.state.typeId,
			major: this.state.marjorId,
		};
		NativeBridge.NATIVE_getEncryptData(dict,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				events.encrypt='simple';
				netClientPostEncrypt(this,BUS_300101,this.BUS_300101_CB,events);
			}
		})
	}

	FILTER_REQ(){
		this.BUS_300101_REQ();
	}

	componentDidMount() {
		this.BUS_300101_REQ();
	}

	rowClick(code){
		let aUrl = URL_ADDR+BUS_300201+'?code='+code+'&sNum='+this.props.sNum+'&pcdm='+this.state.batchVal;
		this.props.navigator.push({
			component:Web,
			params:{
				url:aUrl,
			},
		});
		
	}

	//渲染cell
	renderRow(rowData,sectionID,rowID){
		return(
			<View style={{marginLeft:10,marginRight:10,}}>
				<View style={{backgroundColor:'white',marginTop:12,height:114,
					paddingTop:8,paddingLeft:14,paddingRight:14,flexDirection:'row'}}>
					<View style={{flex:5,alignItems:'center',justifyContent:'center'}}>
						<Text style={{position:'absolute',top:1,fontSize:12,color:'#8dadce'}}>院校代号{rowData.code}</Text>
						<Text style={{fontSize:17,color:'#555555'}}>{rowData.name}</Text>
					</View>
					<View style={{width:0.5,height:59,backgroundColor:'#d5d5d5',marginTop:31}}/>
					<View style={{flex:3,alignItems:'center',justifyContent:'center'}}>
						<Text style={{position:'absolute',top:1,right:1,fontSize:12,color:'#8dadce'}}>{rowData.batch}</Text>
						<Text style={{fontSize:16,color:'#4a4a4a'}}>{rowData.num}</Text>
						<Text style={{marginTop:9,fontSize:12,color:'#ff902d'}}>计划数</Text>
					</View>
				</View>
				<TouchableHighlight
				onPress={e=>this.rowClick(rowData.code)}
				underlayColor='#fcfcfc'>
					<View style={{height:38,backgroundColor:'f3f9ff',paddingLeft:14,paddingRight:14,justifyContent:'center'}}>
						<Text style={{fontSize:12,color:'#779cc3'}}>往年院校录取情况/往年专业录取情况/2016年招生计划</Text>
						<Image
							style={{position:'absolute',right:14,top:13}}
							source={require('image!arrow_blue')} />
						
					</View>
				</TouchableHighlight>
			</View>
			
		)
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){

  		if (!this.isFirst) {
  			this.BUS_300101_REQ();
  		}

  		this.isFirst=false;

		var rows={};
		callback(rows);
    }

    onFilter(){
    	this.props.navigator.push({
    		component:WishFilter,
    		params:{
    			filterObj:this,
    			batch:this.state.batch,
    			batchVal:this.state.batchVal,
    			provId:this.state.provId,
    			provVal:this.state.provVal,
    			typeId:this.state.typeId,
    			typeVal:this.state.typeVal,
    			marjorId:this.state.marjorId,
    			marjorVal:this.state.marjorVal,
    			eyy:this.state.eyy,
    			jbw:this.state.jbw}
    	});
    }

    //头部界面
	renderHeader(){
		return(
			<View style={{backgroundColor:'white'}}>
			<ScrollView horizontal={true}
				 	contentContainerStyle={{backgroundColor:'white',}}
					showsHorizontalScrollIndicator={false}>
				<TouchableHighlight
				  	onPress={()=>this.onFilter()}
				  	underlayColor='white'>
				<View style={{height:45,padding:6,alignItems:'center',flexDirection:'row',flex:1}}>
					<View style={{marginLeft:4,marginRight:4,backgroundColor:'#f3f9ff',borderRadius:2,
						padding:8 ,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:13,color:'#85add7'}}>{this.state.batch}</Text>
					</View>
					<View style={{marginLeft:4,marginRight:4,backgroundColor:'#f3f9ff',borderRadius:2,
						padding:8 ,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:13,color:'#85add7'}}>{this.state.provVal}</Text>
					</View>
					{this.state.typeId === ''? 
						<View/>
						:
						<View style={{marginLeft:4,marginRight:4,backgroundColor:'#f3f9ff',borderRadius:2,
							height:32,width:64,alignItems:'center',justifyContent:'center'}}>
							<Text style={{fontSize:13,color:'#85add7'}}>{this.state.typeVal}</Text>
						</View>
					}
					{this.state.marjorId === ''? 
						<View/>
						:
						<View style={{marginLeft:4,marginRight:4,backgroundColor:'#f3f9ff',borderRadius:2,
							padding:8 ,alignItems:'center',justifyContent:'center'}}>
							<Text style={{fontSize:13,color:'#85add7'}}>{this.state.marjorVal}</Text>
						</View>
					}
					{this.state.eyy === false? 
						<View/>
						:
						<View style={{marginLeft:4,marginRight:4,backgroundColor:'#f3f9ff',borderRadius:2,
							height:32,width:64,alignItems:'center',justifyContent:'center'}}>
							<Text style={{fontSize:13,color:'#85add7'}}>211</Text>
						</View>
					}
					{this.state.jbw === false? 
						<View/>
						:
						<View style={{marginLeft:4,marginRight:4,backgroundColor:'#f3f9ff',borderRadius:2,
							height:32,width:64,alignItems:'center',justifyContent:'center'}}>
							<Text style={{fontSize:13,color:'#85add7'}}>985</Text>
						</View>
					}		
				</View>
				</TouchableHighlight>
			</ScrollView>
			</View>
		)
	}

	onRightNavCilck(){
		this.onFilter();
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			{
			this.state.errorLoading? 
				<App_Title title={'志愿参考'} navigator={this.props.navigator} rightShow={false} obj={this}/>
				:
				<App_Title title={'志愿参考'} navigator={this.props.navigator} rightShow={true} rightText={'筛选'} obj={this}/>
			}
				
				{
					this.state.isLoaded? 
						<GiftedListView
							style={{backgroundColor:'#eeeeee',}}
							dataSource={this.state.dataSource}
							renderRow={(rowData) => this.renderRow(rowData)} 

							pageSize={10}

							automaticallyAdjustContentInsets={false}
			        		showsVerticalScrollIndicator={false}

							renderHeader={this.renderHeader.bind(this)}

							onFetch={this._onFetch.bind(this)}
				          	refreshable={true}           

							/>
						:
						<View style={{flex:1,alignItems:'center',marginTop:90}}>
							<Image
					  			source={require('image!load_pic')} />
					  		{
					  			this.state.errorLoading
					  				?
					  				<ActivityIndicatorIOS
										style={{marginTop:10}}
									  	animating={true}
									  	color={'#808080'}
									  	size={'small'} />
									:
									null
					  		}
							
							<Text style={{fontSize:20,color:'#888888',marginTop:10}}>{this.state.errorTips}</Text>
						</View>
					}
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


