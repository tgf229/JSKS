/**
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
  Image,
  Text,
  AlertIOS,
  AsyncStorage,
  TextInput,
  View
} from 'react-native';
import OfferResult from './OfferResult';
import App_Title from '../common/App_Title';
import { STORAGE_KEY_SNUM,STORAGE_KEY_STICKET,STORAGE_KEY_ALIAS} from '../../util/Global';

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

var aliasVal;
var POINT_CHECK_NUM = ["A","B","C","D","E","F","G","H"];
var POINT_CHECK_POINT = ["1","2","3","4","5","6","7","8"];
export default class OfferSearch extends React.Component{
	constructor(props){
		super(props);
		this.checkA_Num = POINT_CHECK_NUM[Math.floor((Math.random()*8))];
		this.checkA_Point = POINT_CHECK_POINT[Math.floor((Math.random()*8))];
		this.checkB_Num = POINT_CHECK_NUM[Math.floor((Math.random()*8))];
		this.checkB_Point = POINT_CHECK_POINT[Math.floor((Math.random()*8))];
		this.state={
			sNumStr:'',
			sTicketStr:'',
		}
	}

	//初始化数据-默认从AsyncStorage中获取数据
	  async _loadInitialState(){
	       try{
	          var sNumVal=await AsyncStorage.getItem(STORAGE_KEY_SNUM);
	          var sTicketVal=await AsyncStorage.getItem(STORAGE_KEY_STICKET);
	          this.checkA  =await AsyncStorage.getItem('SCHECKA');
	          this.checkB  =await AsyncStorage.getItem('SCHECKB');
	           aliasVal = await AsyncStorage.getItem(STORAGE_KEY_ALIAS);
	          if(sNumVal!=null && sTicketVal!=null){
	          	this.setState({
	          		sNumStr:sNumVal,
	          		sTicketStr:sTicketVal,
	          	});
	          	this.onSubmit();
	            console.log('从存储中获取到数据为:'+sNumVal+' 和 '+sTicketVal);
	          }else{
	            console.log('存储中无数据,初始化为空数据');
	          }
	       }catch(error){
	            console.log('AsyncStorage错误'+error.message);
	       }
	  }

	componentDidMount() {
		this._loadInitialState().done();
	}

	onNumChangeText(e){
		this.setState({sNumStr:e});
	}

	onTicketChangeText(e){
		this.setState({sTicketStr:e});
	}

	onSubmit(){
		var num = trim(this.state.sNumStr);
		var tick = trim(this.state.sTicketStr);
		// num="16320621450919";
		// tick="1606214501922";
		if (num === '') {
			AlertIOS.alert(
				'温馨提示',
				'请输入你的考生号',
			);
			return;
		}
		if (tick === '') {
			AlertIOS.alert(
				'温馨提示',
				'请输入你的动态口令',
			);
			return;
		}
		this.props.navigator.push({
			component:OfferResult,
			params:{
				sNum:num,
				sTicket:tick,
				sCheckA:this.checkA?this.checkA:this.checkA_Num+this.checkA_Point,
				sCheckB:this.checkB?this.checkB:this.checkB_Num+this.checkB_Point,
				alias:aliasVal,
			}
		});
	}

	scrollViewTo(e){
		let target = e.nativeEvent.target;
　　　　 let scrollLength = 275;
　　　　 this.refs.scroll.scrollTo(scrollLength);
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'录取结果'} navigator={this.props.navigator}/>
			<ScrollView
				ref='scroll'
				keyboardShouldPersistTaps={true}
				keyboardDismissMode={'on-drag'}
			  	contentContainerStyle={styles.contentContainer}>
			  	<View  onStartShouldSetResponderCapture={(e) => {
		　　　　　　　　const target = e.nativeEvent.target;
		　　　　　　　　if (target !== React.findNodeHandle(this.refs.sNumInput) && target !== React.findNodeHandle(this.refs.sTicketInput)) {
		　　　　　　　　　　this.refs.sNumInput.blur();
		　　　　　　　　　　this.refs.sTicketInput.blur();
		　　　　　　　　}}}>
				  	<Image
				  		style={{alignSelf:'center'}}
				    	source={require('image!offer_tongzhi')} />
				    <Text style={{color:'#666666',fontSize:15,marginTop:34,lineHeight:22}}>
				    	志愿填报后如何最快得到录取结果？赶快预约我们的录取通知服务吧！录取工作开始后我们将每天三次更新录取信息并通过客户端推送通知考生。同时今年江苏省考试院升级了数据接口，除了录取信息，考生还可查询到本人的退档情况和原因。
				    </Text>
				  
				  	<Text style={{marginTop:10,fontSize:13,color:'#d0021b',lineHeight:23}}>PS：本服务将于2017-6-25日正式启用</Text>

				  	<TextInput
						style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:20}}
						clearButtonMode='while-editing'
						keyboardType='numeric'
						maxLength={20}
						value={this.state.sNumStr}
						onChangeText={e=>this.onNumChangeText(e)}
						onSubmitEditing={()=>this.onSubmit()}
						enablesReturnKeyAutomatically={true}
						placeholder='请输入你的考生号'

						ref = 'sNumInput'
　　　　　　　　　　    	onFocus={this.scrollViewTo.bind(this)}
　　　　　　　　　　  		onEndEditing={()=>{this.refs.scroll.scrollTo(275)}}
						/>
						<View 
								style={{marginTop:21,flexDirection:'row',height:50}}>
								<TextInput
									style={{flex:1,borderWidth:1,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999'}}
									clearButtonMode='while-editing'
									keyboardType='numeric'
									ref = 'sTicketInput'
　　　　　　　　　　 					onFocus={this.scrollViewTo.bind(this)}
　　　　　　　　　　 					onEndEditing={()=>{this.refs.scroll.scrollTo(275)}}
									maxLength={6}
									value={this.state.sTicketStr}
									onChangeText={e=>this.onTicketChangeText(e)}
									onSubmitEditing={()=>this.onSubmit()}
									enablesReturnKeyAutomatically={true}
									placeholder='请输入动态口令'
									/>
								<View
									style={{backgroundColor:'#F3F3F3',width:100,marginLeft:20,justifyContent:'center',alignItems:'center'}}>
									<Text
										style={{fontSize:18,color:'#666666'}}>{this.checkA?this.checkA:this.checkA_Num+this.checkA_Point} : {this.checkB?this.checkB:this.checkB_Num+this.checkB_Point}</Text>
								</View>
							</View>

							<Text
								style={{marginTop:12,fontSize:12,color:'#d0021b'}}>
								请填写动态口令卡上如上所示坐标位置的两组三位数字
							</Text>
					<TouchableHighlight
						style={{marginTop:30,justifyContent:'center',alignItems:'center',
							backgroundColor:'#ff902d',height:45,borderRadius:3}}
						onPress={()=>this.onSubmit()}
						underlayColor='#fcfcfc'>
						<Text style={{fontSize:16,color:'white'}}>预约通知</Text>
					</TouchableHighlight>
				</View>
				<View style={{height:50}}/>
			</ScrollView>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:54,
		paddingLeft:20,
		paddingRight:20,
	},
});