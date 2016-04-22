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
  ScrollView,
  Image,
  Dimensions,
  NavigatorIOS,
  Text,
  View
} from 'react-native';
import OfferResult_Fail from './component/OfferResult_Fail';
import OfferResult_Success from './component/OfferResult_Success';
import App_Title from '../common/App_Title';

export default class OfferResult extends React.Component{
	constructor(props){
		super(props);
		this.state={
			
		}
	}

	render(){
		return(
			<View>
			<App_Title title={'录取结果'} navigator={this.props.navigator}/>
			<ScrollView
			  contentContainerStyle={styles.contentContainer}>
			  	<OfferResult_Success/>
			</ScrollView>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		backgroundColor:'white',
	},
});


