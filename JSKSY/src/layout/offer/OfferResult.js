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
  Text,
  View
} from 'react-native';
import OfferResult_Fail from './component/OfferResult_Fail';
import OfferResult_Success from './component/OfferResult_Success';


export default class OfferResult extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<ScrollView
			  contentContainerStyle={styles.contentContainer}>
			  	<OfferResult_Success/>
			</ScrollView>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		backgroundColor:'white',
	},
});


