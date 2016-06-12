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
export default class OfferSearch extends React.Component{
	constructor(props){
		super(props);
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
				'请输入你的准考证号',
			);
			return;
		}
		this.props.navigator.push({
			component:OfferResult,
			params:{
				sNum:num,
				sTicket:tick,
				alias:aliasVal,
			}
		});
	}

	scrollViewTo(e){
		let target = e.nativeEvent.target;
　　　　 let scrollLength = 250;
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
				    	志愿填报后如何最快得到录取结果？赶快预约我们最新的录取通知服务吧，我们将在录取结果发布后第一时间将录取结果推送至您的手机。
				    </Text>
				  
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
　　　　　　　　　　  		onEndEditing={()=>{this.refs.scroll.scrollTo(220)}}
						/>
					<TextInput
						style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:19}}
						clearButtonMode='while-editing'
						keyboardType='numeric'
						maxLength={20}
						value={this.state.sTicketStr}
						onChangeText={e=>this.onTicketChangeText(e)}
						onSubmitEditing={()=>this.onSubmit()}
						enablesReturnKeyAutomatically={true}
						placeholder='请输入你的准考证号'

						ref = 'sTicketInput'
　　　　　　　　　　 		onFocus={this.scrollViewTo.bind(this)}
　　　　　　　　　　 		onEndEditing={()=>{this.refs.scroll.scrollTo(220)}}
						/>
					<TouchableHighlight
						style={{marginTop:30,justifyContent:'center',alignItems:'center',
							backgroundColor:'#ff902d',height:45,borderRadius:3}}
						onPress={()=>this.onSubmit()}
						underlayColor='#fcfcfc'>
						<Text style={{fontSize:16,color:'white'}}>预约通知</Text>
					</TouchableHighlight>
				</View>
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


