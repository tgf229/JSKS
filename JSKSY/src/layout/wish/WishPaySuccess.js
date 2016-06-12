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
  Image,
  TouchableHighlight,
  ScrollView,
  Text,
  TextInput,
  View
} from 'react-native';

import WishList from './WishList';
import App_Title from '../common/App_Title';

export default class WishPaySuccess extends React.Component{
	constructor(props){
		super(props);
		this.state={
			searchString:'',
		}
	}

	onChangeText(e){
		this.setState({searchString:e});
	}

	onSubmit(){
		this.props.navigator.push({
			component:WishList
		});
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
			<App_Title title={'录取资料'} navigator={this.props.navigator}/>
			<ScrollView
			  contentContainerStyle={styles.contentContainer}>

			  	<Image
			 		style={{alignSelf:'center'}}
			    	source={require('image!pic_success')} />
			  	<Text style={{marginTop:28,fontSize:18,color:'#444444',alignSelf:'center'}}>恭喜你，支付成功</Text>

				<TouchableHighlight
					style={{justifyContent:'center',alignItems:'center',marginTop:40,
						backgroundColor:'#ff902d',height:45,borderRadius:3}}
					onPress={()=>this.onSubmit()}
					underlayColor='#fcfcfc'>
					<Text style={{fontSize:16,color:'white'}}>查看</Text>
				</TouchableHighlight>

			</ScrollView>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:44,
		paddingLeft:20,
		paddingRight:20,

	},
});


