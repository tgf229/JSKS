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
  TextInput,
  View
} from 'react-native';
import OfferResult from './OfferResult';

export default class OfferSearch extends React.Component{
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
			title:'录取查询',
			rightButtonTitle:'取消预约',
			component:OfferResult,
		});
	}

	render(){
		return(
			<ScrollView
			  contentContainerStyle={styles.contentContainer}>
			  	<View style={{flex:1}}>
			  	<Image
			  		style={{alignSelf:'center'}}
			    	source={require('image!offer_tongzhi')} />
			    <Text style={{color:'#666666',fontSize:15,marginTop:34}}>
			    	志愿填报后如何最快得到录取结果？赶快预约我们最新的录取通知服务吧，我们将在您被录取后的第一时间将录取结果推送至您的手机。
			    </Text>
			  
			  	<TextInput
					style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:21}}
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
					<Text style={{fontSize:16,color:'white'}}>预约通知</Text>
				</TouchableHighlight>
				</View>
			</ScrollView>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:54,
		paddingLeft:20,
		paddingRight:20,
		backgroundColor:'white',
	},
});


