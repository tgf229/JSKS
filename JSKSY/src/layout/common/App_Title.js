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
  Text,
  View
} from 'react-native';

export default class App_Title extends React.Component{
	constructor(props){
		super(props);
		this.state={
			
		}
	}

	onBack(){
		this.props.navigator.pop();
	}

	render(){
		return(
			<View style={{backgroundColor:'#67aef7'}} >
				<View style={{height:20}}/>
				<View style={{height:44,flexDirection:'row',alignItems:'center',justifyContent:'center'}}>
				{
					this.props.leftHid?<View/>
								:
							<TouchableHighlight
								style={{position:'absolute',left:10,paddingRight:60,paddingLeft:10,paddingTop:10,paddingBottom:10}}
								onPress={()=>this.onBack()}
								underlayColor='#67aef7'>
								<Image 
									source={require('image!back_btn')}/>
							</TouchableHighlight>
				}
				<Text style={{textAlign:'center',fontSize:18,color:'white'}}>{this.props.title}</Text>
				</View>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


