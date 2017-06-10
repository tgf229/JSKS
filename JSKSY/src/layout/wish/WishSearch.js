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
  Text,
  Dimensions,
  TextInput,
  AlertIOS,
  View
} from 'react-native';

import WishList from './WishList';
import App_Title from '../common/App_Title';
import Web from '../webview/Web';
import { URL_ADDR} from '../../util/NetUtil';

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

export default class WishSearch extends React.Component{
	constructor(props){
		super(props);
		this.state={
			sNumStr:'',
			sTicketStr:'',
		}
	}

	onNumChangeText(e){
		this.setState({sNumStr:e});
	}

	onTicketChangeText(e){
		this.setState({sTicketStr:e});
	}

	onSubmit(){
		// AlertIOS.alert(
		// 	'温馨提示',
		// 	'暂未开放此功能，请耐心等待或更新App版本',
		// );

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
		// this.props.navigator.push({
		// 	component:WishList,
		// 	params:{
		// 		sNum:num,
		// 		sTicket:tick,
		// 	}
		// });

		//暂时跳转 H5版本
		var url = URL_ADDR+"scoreDetail?encrypt=none&sNum="+num+"&sTicket="+tick
		this.props.navigator.push({
				component:Web,
				params:{
					url:url,
					rightBtnHide:true,
				},
			});
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
			<App_Title title={'录取资料'} navigator={this.props.navigator}/>
			
						<ScrollView
						  contentContainerStyle={styles.contentContainer}>
						  	<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999'}}
								clearButtonMode='while-editing'
								keyboardType='numeric'
								maxLength={20}
								value={this.state.searchString}
								onChangeText={e=>this.onNumChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的考生号'
							/>
							<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:21}}
								clearButtonMode='while-editing'
								keyboardType='numeric'
								maxLength={20}
								value={this.state.searchString}
								onChangeText={e=>this.onTicketChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的准考证号'
							/>
							<TouchableHighlight
								style={{marginTop:30,justifyContent:'center',alignItems:'center',
									backgroundColor:'#ff902d',height:45,borderRadius:3}}
								onPress={()=>this.onSubmit()}
								underlayColor='#fcfcfc'>
								<Text style={{fontSize:16,color:'white'}}>查询</Text>
							</TouchableHighlight>

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


