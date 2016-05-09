/**
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  Dimensions,
  StyleSheet,
  Image,
  View
} from 'react-native';

export default class Welcome extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<View style={{backgroundColor:'#f6f6f6'}}>
				<Image
					style={{height:Dimensions.get('window').height-100,width:Dimensions.get('window').width}}
					source={require('image!loading_bg')}
					/>
				<View style={{height:100,backgroundColor:'white',alignItems:'center',justifyContent:'center'}}>
					<Image
						source={require('image!loading_logo')}
						/>
				</View>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	
});


