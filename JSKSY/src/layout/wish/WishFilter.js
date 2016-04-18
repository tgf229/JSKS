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

var BATCH_DATA = ['本科一批','本科二批','取消'];
var TYPE_DATA = [{id:'01',name:'综合院校'},{id:'02',name:'工科院校'},{id:'03',name:'农业院校'}];
var PROVINCE_DATA = [{id:'',name:'全国'},{id:'320000',name:'江苏省'},{id:'110000',name:'北京市'}];

export default class WishFilter extends React.Component{
	constructor(props){
		super(props);
		this.state={
			batch:'请选择',
			batchVal:'',

			provId:'',
			provVal:'请选择',

			typeId:'',
			typeVal:'请选择',

			eyy:false,
			jbw:false,
		};
	}

	showBatchActionSheet() {
	    ActionSheetIOS.showActionSheetWithOptions({
	      options: BATCH_DATA,
	      cancelButtonIndex: 2,
	    },
	    (buttonIndex) => {
	    	if (buttonIndex !== 2) {
	    		this.setState({ batch: BATCH_DATA[buttonIndex], batchVal: buttonIndex});
	    	}
	    });
	}

	//院校类型 选择
	showTypeList(flag){
		switch(flag){
			case 1:
				//传参type 1 院校类型   2 省份
				this.props.navigator.push({
					title:'院校类型',
					component:WishFilter_List,
					passProps:{listData:TYPE_DATA, filterObj:this, selectedID:this.state.typeId, type:1}
				});
			break;
			case 2:
				//传参type 1 院校类型   2 省份
				this.props.navigator.push({
					title:'院校类型',
					component:WishFilter_List,
					passProps:{listData:PROVINCE_DATA, filterObj:this, selectedID:this.state.typeId, type:2}
				});
			break;
			default:
			break;
		}
	}

	eyyValChange(){
		this.setState({eyy:!this.state.eyy});
	}
	jbwValChange(){
		this.setState({jbw:!this.state.jbw});
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
				  	  onPress={e=>this.showTypeList(2)}
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
				  	  onPress={e=>this.showTypeList(1)}
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
				  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
				  		<Text style={{fontSize:15,color:'#666666'}}>开设专业</Text>
				  		<Text style={{fontSize:15,color:'#8dadce',position:'absolute',top:19,right:40}}>心理学啦啦啦啦啦</Text>
				  		<Image
				  		  style={{position:'absolute',top:19,right:20}}
				  		  source={require('image!arrow_grey')} />
				  	</View>

				  	<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
				  		<Text style={{fontSize:15,color:'#666666'}}>211</Text>
				  		<Switch onTintColor='#ff902d'style={{position:'absolute',top:12,right:20}}
				  			value={this.state.eyy}
				  			onValueChange={e=>this.eyyValChange()}
				  		/>
				  	</View>

				  	<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				  	<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:20,backgroundColor:'white'}}>
				  		<Text style={{fontSize:15,color:'#666666'}}>985</Text>
				  		<Switch onTintColor='#ff902d'style={{position:'absolute',top:12,right:20}}
				  			value={this.state.jbw}
				  			onValueChange={e=>this.jbwValChange()}
				  		/>
				  	</View>

				</ScrollView>
				<View style={{flexDirection:'row',alignItems:'center',justifyContent:'center',backgroundColor:'white',height:55,paddingLeft:28,paddingRight:28,paddingTop:8,paddingBottom:8}}>
					<View style={{backgroundColor:'#8dc3fa',flex:1,height:39,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:16,color:'white'}}>重置</Text>
					</View>
					<View style={{backgroundColor:'#ff902d',flex:1,height:39,alignItems:'center',justifyContent:'center',marginLeft:20}}>
						<Text style={{fontSize:16,color:'white'}}>确定</Text>
					</View>
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


