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
  ScrollView,
  Text,
  Image,
  View
} from 'react-native';
import GiftedListView from 'react-native-gifted-listview';
import { urlForQueryAndPage, netClient } from '../../util/NetUtil';
import WishFilter from './WishFilter';

var listData = [	{id:'1101',name:'南京大学',batch:'本科一批',num:'158'},
			   		{id:'1102',name:'南京航空航天大学',batch:'本一',num:'28'},
			   		{id:'1103',name:'南京信息工程大学',batch:'本科二批',num:'371'},
			   ];
var ds;

export default class WishList extends React.Component{
	constructor(props){
		super(props);
		ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
		this.state={
			dataSource: ds.cloneWithRows(listData),
			
			batch:'本科一批',
			batchVal:'1',

			provId:'',
			provVal:'全国',

			typeId:'',
			typeVal:'',

			marjorId:'',
			marjorVal:'',

			eyy:false,
			jbw:false,

		};
	}

	//渲染cell
	renderRow(rowData,sectionID,rowID){
		return(
			<View style={{marginLeft:10,marginRight:10,}}>
				<View style={{backgroundColor:'white',marginTop:12,height:114,
					paddingTop:8,paddingLeft:14,paddingRight:14,flexDirection:'row'}}>
					<View style={{flex:5,alignItems:'center',justifyContent:'center'}}>
						<Text style={{position:'absolute',top:1,fontSize:12,color:'#8dadce'}}>院校代号{rowData.id}</Text>
						<Text style={{fontSize:17,color:'#555555'}}>{rowData.name}</Text>
					</View>
					<View style={{width:0.5,height:59,backgroundColor:'#d5d5d5',marginTop:31}}/>
					<View style={{flex:3,alignItems:'center',justifyContent:'center'}}>
						<Text style={{position:'absolute',top:1,right:1,fontSize:12,color:'#8dadce'}}>{rowData.batch}</Text>
						<Text style={{fontSize:16,color:'#4a4a4a'}}>{rowData.num}</Text>
						<Text style={{marginTop:9,fontSize:12,color:'#ff902d'}}>计划数</Text>
					</View>
				</View>
				<View style={{height:38,backgroundColor:'f3f9ff',paddingLeft:14,paddingRight:14,justifyContent:'center'}}>
					<Text style={{fontSize:12,color:'#779cc3'}}>历年学院录取分数线/历年专业录取分数线/招生计划</Text>
					<Image
						style={{position:'absolute',right:14,top:13}}
						source={require('image!arrow_blue')} />
					
				</View>
			</View>
		)
	}

	//列表请求数据 或下拉刷新
  	_onFetch(page = 1, callback, options){

		var rows={};
		callback(rows);
    }

    onFilter(){
    	this.props.navigator.push({
    		title:'筛选',
    		component:WishFilter,
    		passProps:{filterObj:this,batch:this.state.batch,
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

	render(){
		return(
			<View style={{flex:1,marginTop:64}}>
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
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


