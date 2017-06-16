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

export default class App_Title_Share extends React.Component{
	constructor(props){
		super(props);
		this.state={
			
		}
	}

	onLeftClick(obj){
		this.props.navigator.pop();
	}

	onRightClick(obj){
		obj.onRightNavCilck();
	}

	render(){
		return(
			<View style={{backgroundColor:'#67aef7'}} >
				<View style={{height:20}}/>
				<View style={{height:44,flexDirection:'row',alignItems:'center',justifyContent:'center'}}>
					<TouchableHighlight
						style={{position:'absolute',left:10,paddingRight:60,paddingLeft:10,paddingTop:10,paddingBottom:10}}
						onPress={()=>this.onLeftClick(this.props.obj)}
						underlayColor='#67aef7'>
						<Image 
							source={require('image!back_btn')}/>
					</TouchableHighlight>
				<Text style={{textAlign:'center',fontSize:18,color:'white'}}>{this.props.title}</Text>
					<TouchableHighlight
						style={{position:'absolute',right:5,paddingRight:10,top:5,paddingLeft:20,paddingTop:8,paddingBottom:10}}
						onPress={()=>this.onRightClick(this.props.obj)}
						underlayColor='#67aef7'>
						<Image source={require('image!btn_share')}/>
					</TouchableHighlight>
				</View>
			</View>
		)
	}
}


