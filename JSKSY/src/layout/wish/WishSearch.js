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

export default class WishSearch extends React.Component{
	constructor(props){
		super(props);
		this.state={
			searchString:'',
		}
	}

	onChangeText(e){
		// this.setState({searchString:e});
	}

	onSubmit(){
		AlertIOS.alert(
			'温馨提示',
			'暂未开放，请耐心等待...',
		);
		// this.props.navigator.push({
			// component:WishList
		// });
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
								value={this.state.searchString}
								onChangeText={e=>this.onChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的考生号'
							/>
							<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:21}}
								clearButtonMode='while-editing'
								value={this.state.searchString}
								onChangeText={e=>this.onChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的准考证号'
							/>
							<TouchableHighlight
								style={{marginTop:30,justifyContent:'center',alignItems:'center',
									backgroundColor:'#ff902d',height:45,borderRadius:3}}
								onPress={()=>this.onSubmit()}
								underlayColor='#fcfcfc'>
								<Text style={{fontSize:16,color:'white'}}>下一步</Text>
							</TouchableHighlight>
							<Text style={{marginTop:18,fontSize:12,color:'#999999'}}>PS: 已购买过该项服务的考生，可在验证后再次查询</Text>

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


