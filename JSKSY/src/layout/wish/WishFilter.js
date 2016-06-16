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
import App_Title from '../common/App_Title';

var BATCH_DATA = ['本科一批','本科二批','取消'];
var TYPE_DATA = [{id:'',name:'全部'},{id:'01',name:'综合院校'},{id:'02',name:'工科院校'},{id:'03',name:'农业院校'},{id:'04',name:'林业院校'},{id:'05',name:'医药院校'},{id:'06',name:'师范院校'},{id:'07',name:'语言院校'},{id:'08',name:'财经院校'},{id:'09',name:'政法院校'},{id:'10',name:'体育院校'},{id:'11',name:'艺术院校'},{id:'12',name:'民族院校'},{id:'13',name:'军事院校'}];
var PROVINCE_DATA = [{id:'',name:'全国'},{id:'320000',name:'江苏省'},{id:'110000',name:'北京市'},{id:'120000',name:'天津市'},{id:'130000',name:'河北省'},{id:'140000',name:'山西省'},{id:'150000',name:'内蒙古自治区'},{id:'210000',name:'辽宁省'},{id:'220000',name:'吉林省'},{id:'230000',name:'黑龙江省'},{id:'310000',name:'上海市'},{id:'330000',name:'浙江省'},{id:'340000',name:'安徽省'},{id:'350000',name:'福建省'},{id:'360000',name:'江西省'},{id:'370000',name:'山东省'},{id:'410000',name:'河南省'},{id:'420000',name:'湖北省'},{id:'430000',name:'湖南省'},{id:'440000',name:'广东省'},{id:'450000',name:'广西壮族自治区'},{id:'460000',name:'海南省'},{id:'500000',name:'重庆市'},{id:'510000',name:'四川省'},{id:'520000',name:'贵州省'},{id:'530000',name:'云南省'},{id:'540000',name:'西藏自治区'},{id:'610000',name:'陕西省'},{id:'620000',name:'甘肃省'},{id:'630000',name:'青海省'},{id:'640000',name:'宁夏回族自治区'},{id:'650000',name:'新疆维吾尔自治区'},{id:'710000',name:'台湾省'},{id:'810000',name:'香港特别行政区'},{id:'820000',name:'澳门特别行政区'}];

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
			batchVal:'2',

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
	    		this.setState({ batch: BATCH_DATA[buttonIndex], batchVal: buttonIndex+2+''});
	    	}
	    });
	}

	//院校类型 选择
	showTypeList(flag){
		switch(flag){
			case 1:
				//传参type 1 院校类型   
				this.props.navigator.push({
					// title:'院校类型',
					// leftButtonIcon:require('image!back_btn'),
					// onLeftButtonPress: () => this.props.navigator.pop(),
					component:WishFilter_List,
					params:{listData:TYPE_DATA, filterObj:this, selectedID:this.state.typeId, type:1}
				});
			break;
			case 2:
				//传参type 2 省份
				this.props.navigator.push({
					// title:'院校省份',
					// leftButtonIcon:require('image!back_btn'),
					// onLeftButtonPress: () => this.props.navigator.pop(),
					component:WishFilter_List,
					params:{listData:PROVINCE_DATA, filterObj:this, selectedID:this.state.provId, type:2}
				});
			break;
			case 3:
				//传参type 3 开设专业
				this.props.navigator.push({
					// title:'开设专业',
					// leftButtonIcon:require('image!back_btn'),
					// onLeftButtonPress: () => this.props.navigator.pop(),
					component:WishFilter_Section_List,
					params:{filterObj:this, selectedID:this.state.marjorId}
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
		this.props.filterObj.BUS_300101_REQ();;
		this.props.navigator.pop();
	}

	render(){
		return(
				<View style={{flex:1}}>
				<App_Title title={'筛选'} navigator={this.props.navigator}/>
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


