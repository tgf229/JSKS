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
  TouchableHighlight,
  View
} from 'react-native';

export default class Welcome extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	onSkipPress(){
		this.props.homeObj.setState({
			loadAD:false,
		});
	}

	onADPress(){
		console.log('12312312')
	}

	render(){
		return(
			<View style={{backgroundColor:'#f6f6f6'}}>
				<TouchableHighlight
					underlayColor='#fcfcfc'
					onPress={()=>this.onADPress()}>
					<Image
						style={{height:Dimensions.get('window').height-100,width:Dimensions.get('window').width}}
						source={require('image!loading_bg')}
						/>
				</TouchableHighlight>
				<TouchableHighlight
					style={{position:'absolute',top:15,right:1,padding:20}}
					underlayColor='#fcfcfc'
					onPress={()=>this.onSkipPress()}>
					<Image	
						source={require('image!loading_skip')}
						/>
				</TouchableHighlight>
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


