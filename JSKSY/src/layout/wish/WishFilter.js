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
  ScrollView,
  Text,
  Switch,
  Dimensions,
  Image,
  ActionSheetIOS,
  View
} from 'react-native';
import WishFilter_List from './component/WishFilter_List';
import WishFilter_Section_List from './component/WishFilter_Section_List';

var BATCH_DATA = ['本科一批','本科二批','取消'];
var TYPE_DATA = [{id:'',name:'全部'},{id:'01',name:'综合院校'},{id:'02',name:'工科院校'},{id:'03',name:'农业院校'}];
var PROVINCE_DATA = [{id:'',name:'全国'},{id:'320000',name:'江苏省'},{id:'110000',name:'北京市'}];

export default class WishFilter extends React.Component{
	constructor(props){
		super(props);
		this.state={
			batch:this.props.batch,
			batchVal:this.props.batchVal,

			provId:this.props.provId,
			provVal:this.props.provVal,

			typeId:this.props.typeId,
			typeVal:this.props.typeVal,

			marjorId:this.props.marjorId,
			marjorVal:this.props.marjorVal,

			eyy:this.props.eyy,
			jbw:this.props.jbw,
		};
	}

	onReset(){
		this.setState({
			batch:'本科一批',
			batchVal:'1',

			provId:'',
			provVal:'全国',

			typeId:'',
			typeVal:'请选择',

			marjorId:'',
			marjorVal:'请选择',

			eyy:false,
			jbw:false,
		});
	}

	//录取批次选择
	showBatchActionSheet() {
	    ActionSheetIOS.showActionSheetWithOptions({
	      options: BATCH_DATA,
	      cancelButtonIndex: 2,
	    },
	    (buttonIndex) => {
	    	if (buttonIndex !== 2) {
	    		this.setState({ batch: BATCH_DATA[buttonIndex], batchVal: buttonIndex+1});
	    	}
	    });
	}

	//院校类型 选择
	showTypeList(flag){
		switch(flag){
			case 1:
				//传参type 1 院校类型   
				this.props.navigator.push({
					title:'院校类型',
					component:WishFilter_List,
					passProps:{listData:TYPE_DATA, filterObj:this, selectedID:this.state.typeId, type:1}
				});
			break;
			case 2:
				//传参type 2 省份
				this.props.navigator.push({
					title:'院校省份',
					component:WishFilter_List,
					passProps:{listData:PROVINCE_DATA, filterObj:this, selectedID:this.state.provId, type:2}
				});
			break;
			case 3:
				//传参type 3 开设专业
				this.props.navigator.push({
					title:'开设专业',
					component:WishFilter_Section_List,
					passProps:{filterObj:this, selectedID:this.state.marjorId}
				});
			break;
			default:
			break;
		}
	}

	//211选择
	eyyValChange(){
		this.setState({eyy:!this.state.eyy});
	}
	//985选择
	jbwValChange(){
		this.setState({jbw:!this.state.jbw});
	}

	onSubmit(){
		this.props.filterObj.setState({
			batch:this.state.batch,
			batchVal:this.state.batchVal,

			provId:this.state.provId,
			provVal:this.state.provVal,

			typeId:this.state.typeId,
			typeVal:this.state.typeVal,

			marjorId:this.state.marjorId,
			marjorVal:this.state.marjorVal,

			eyy:this.state.eyy,
			jbw:this.state.jbw,
		});
		this.props.navigator.pop();
	}

	render(){
		return(
			<View>
				<ScrollView
				  contentContainerStyle={styles.contentContainer}>
				  	<TouchableHighlight
				  	  onPress={()=>this.showBatchActionSheet()}
				  	  underlayColor='white'>
				  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
				  		<Text style={{fontSize:15,color:'#666666'}}>录取批次</Text>
				  		<Text style={{fontSize:15,color:'#8dadce',position:'absolute',top:19,right:40}}>{this.state.batch}</Text>
				  		<Image
				  		  style={{position:'absolute',top:19,right:20}}
				  		  source={require('image!arrow_grey')} />
				  	</View>
				  	</TouchableHighlight>

					<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
					<TouchableHighlight
				  	  onPress={()=>this.showTypeList(2)}
				  	  underlayColor='white'>
					  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
					  		<Text style={{fontSize:15,color:'#666666'}}>院校省份</Text>
					  		<Text style={{fontSize:15,color:'#8dadce',position:'absolute',top:19,right:40}}>{this.state.provVal}</Text>
					  		<Image
					  		  style={{position:'absolute',top:19,right:20}}
					  		  source={require('image!arrow_grey')} />
					  	</View>
				  	</TouchableHighlight>
				  	
				  	<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				  	<TouchableHighlight
				  	  onPress={()=>this.showTypeList(1)}
				  	  underlayColor='white'>
					  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
					  		<Text style={{fontSize:15,color:'#666666'}}>院校类型</Text>
					  		<Text style={{fontSize:15,color:'#8dadce',position:'absolute',top:19,right:40}}>{this.state.typeVal}</Text>
					  		<Image
					  		  style={{position:'absolute',top:19,right:20}}
					  		  source={require('image!arrow_grey')} />
					  	</View>
				  	</TouchableHighlight>

				  	<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				  	<TouchableHighlight
				  	  onPress={()=>this.showTypeList(3)}
				  	  underlayColor='white'>
					  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
					  		<Text style={{fontSize:15,color:'#666666'}}>开设专业</Text>
					  		<Text style={{fontSize:15,color:'#8dadce',position:'absolute',top:19,right:40}}>{this.state.marjorVal}</Text>
					  		<Image
					  		  style={{position:'absolute',top:19,right:20}}
					  		  source={require('image!arrow_grey')} />
					  	</View>
				  	</TouchableHighlight>

				  	<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
				  		<Text style={{fontSize:15,color:'#666666'}}>211</Text>
				  		<Switch onTintColor='#ff902d'style={{position:'absolute',top:12,right:20}}
				  			value={this.state.eyy}
				  			onValueChange={()=>this.eyyValChange()}
				  		/>
				  	</View>

				  	<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
				  		<Text style={{fontSize:15,color:'#666666'}}>985</Text>
				  		<Switch onTintColor='#ff902d'style={{position:'absolute',top:12,right:20}}
				  			value={this.state.jbw}
				  			onValueChange={()=>this.jbwValChange()}
				  		/>
				  	</View>

				</ScrollView>

				<View style={{flexDirection:'row',alignItems:'center',justifyContent:'center',backgroundColor:'white',height:55,paddingLeft:28,paddingRight:28,paddingTop:8,paddingBottom:8}}>
					<TouchableHighlight
						style={{backgroundColor:'#8dc3fa',flex:1,height:39,alignItems:'center',justifyContent:'center'}}
				  	 	onPress={()=>this.onReset()}
				  	  	underlayColor='#fcfcfc'>
							<Text style={{fontSize:16,color:'white'}}>重置</Text>
					</TouchableHighlight>
					<TouchableHighlight
						style={{backgroundColor:'#ff902d',flex:1,height:39,alignItems:'center',justifyContent:'center',marginLeft:20}}
				  	 	onPress={()=>this.onSubmit()}
				  	  	underlayColor='#fcfcfc'>
						<View>
							<Text style={{fontSize:16,color:'white'}}>确定</Text>
						</View>
					</TouchableHighlight>
				</View>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		height:Dimensions.get('window').height-55,
		backgroundColor:'#eeeeee',
	},
});


