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

import App_Title from '../common/App_Title';
import ZZ_Result from './ZZ_Result';

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

export default class ZZ_PointSearch extends React.Component{
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
		var num = trim(this.state.sNumStr);
		var tick = trim(this.state.sTicketStr);
		if (num === '') {
			AlertIOS.alert(
				'温馨提示',
				'请输入你的考籍号',
			);
			return;
		}
		if (tick === '' || tick.length<6) {
			AlertIOS.alert(
				'温馨提示',
				'请输入你的身份证号后6位',
			);
			return;
		}
		this.props.navigator.push({
			component:ZZ_Result,
			params:{
				sNum:num,
				sTicket:tick,	
			}
		});
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
			<App_Title title={'中职学考'} navigator={this.props.navigator}/>
			
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
								placeholder='请输入你的考籍号'
							/>
							<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:21}}
								clearButtonMode='while-editing'
								keyboardType='numbers-and-punctuation'
								autoCapitalize='characters'
								autoCorrect={false}
								maxLength={6}
								value={this.state.searchString}
								onChangeText={e=>this.onTicketChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的身份证号后6位'
							/>
							<Text style={{fontSize:12,color:'#C0C0C0',marginTop:10,marginLeft:10}}>身份证字母不区分大小写</Text>
							<TouchableHighlight
								style={{marginTop:20,justifyContent:'center',alignItems:'center',
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


