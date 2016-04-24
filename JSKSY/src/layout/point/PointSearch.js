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
  TextInput,
  View
} from 'react-native';
import PointResult from './PointResult'
import App_Title from '../common/App_Title';

export default class PointSearch extends React.Component{
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
			component:PointResult,
		});
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'高考查分'} navigator={this.props.navigator} />
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


