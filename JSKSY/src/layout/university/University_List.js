'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	ListView,
	View,
	PixelRatio,
	Text,
	TouchableOpacity,
	Image} from 'react-native';

import GiftedListView from 'react-native-gifted-listview';
import {BUS_700101,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';
import App_Title_Search from '../common/App_Title_Search';
import University_Detail from './University_Detail';
import WishFilter from '../wish/WishFilter';

export default class University_List extends Component{

	constructor(props) {
	  	super(props);
		this.ds = new ListView.DataSource({rowHasChanged:(r1,r2) => r1 != r2});
		this.listData = [];
	  	this.state = {
	  		dataSource : this.ds.cloneWithRows(this.listData),
	  		flag_success : true,

	  		batch:'请选择',
			batchVal:'',

			provId:'',
			provVal:'全国',

			typeId:'',
			typeVal:'请选择',

			marjorId:'',
			marjorVal:'请选择',

			eyy:false,
			jbw:false,
	  	};
	}

	//列表请求回调
	_BUS_700101_CB(object,json){
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

    _rowClick = ()=>{
    	this.props.navigator.push({
    		component:University_Detail,
    	});
    };

    _onFilter(){
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

    FILTER_REQ(uName){
    	console.log("uName= "+uName);
    	console.log("batch= "+this.state.batch);
    	console.log("batchVal= "+this.state.batchVal);
    	console.log("provId= "+this.state.provId);
    	console.log("provVal= "+this.state.provVal);
    	console.log("typeId= "+this.state.typeId);
    	console.log("typeVal= "+this.state.typeVal);
    	console.log("marjorId= "+this.state.marjorId);
    	console.log("marjorVal= "+this.state.marjorVal);
    	console.log("eyy= "+this.state.eyy);
    	console.log("jbw= "+this.state.jbw);
	}

	_renderRow(rowData,sectionID,rowID){
		return(
			<View>
				<TouchableOpacity 
					onPress={this._rowClick}>
				<View style={{flexDirection:'row',padding:15}}>
					<Image 
						style={{width:120/4*PixelRatio.get(),height:120/4*PixelRatio.get()}}
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
							{/*
								rowData.rank
								?
								<Text style={styles.tipsText}>排名{rowData.rank}</Text>
								:
								null
							*/}
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
				</TouchableOpacity>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
			</View>
		)
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
				<App_Title_Search navigator={this.props.navigator} cbObj={this}/>
				<TouchableOpacity
					activeOpacity={1}
					onPress={this._onFilter.bind(this)}>
				<View style={{height:44,flexDirection:'row'}}>
					<View style={{flex:1,flexDirection:'row',justifyContent:'center',alignItems:'center'}}>
						<Text style={{fontSize:13,color:'#888888'}}>地区</Text>
						<Image style={{marginLeft:5}} source={this.state.provId?require('image!school_triangle_orange'):require('image!school_triangle_grey')}/>
					</View>
					<View style={{flex:1,flexDirection:'row',justifyContent:'center',alignItems:'center'}}>
						<Text style={{fontSize:13,color:'#888888'}}>批次</Text>
						<Image style={{marginLeft:5}} source={this.state.batchVal?require('image!school_triangle_orange'):require('image!school_triangle_grey')}/>
					</View>
					<View style={{flex:1,flexDirection:'row',justifyContent:'center',alignItems:'center'}}>
						<Text style={{fontSize:13,color:'#888888'}}>特色</Text>
						<Image style={{marginLeft:5}} source={(this.state.eyy||this.state.jbw)?require('image!school_triangle_orange'):require('image!school_triangle_grey')}/>
					</View>
					<View style={{flex:1,flexDirection:'row',justifyContent:'center',alignItems:'center'}}>
						<Text style={{fontSize:13,color:'#888888'}}>专业</Text>
						<Image style={{marginLeft:5}} source={this.state.marjorId?require('image!school_triangle_orange'):require('image!school_triangle_grey')}/>
					</View>
					<View style={{flex:1,flexDirection:'row',justifyContent:'center',alignItems:'center'}}>
						<Text style={{fontSize:13,color:'#888888'}}>类别</Text>
						<Image style={{marginLeft:5}} source={this.state.typeId?require('image!school_triangle_orange'):require('image!school_triangle_grey')}/>
					</View>
				</View>
				</TouchableOpacity>
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