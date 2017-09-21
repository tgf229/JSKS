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
  TouchableOpacity,
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
			<View>
				<View style={{height:20}}/>
				<View style={{height:44,flexDirection:'row',alignItems:'center',justifyContent:'center'}}>
					<TouchableOpacity
						style={{position:'absolute',left:10,paddingRight:60,paddingLeft:10,paddingTop:14,paddingBottom:10}}
						onPress={()=>this.onLeftClick(this.props.obj)}>
						<Image 
							source={require('image!btn_back_black')}/>
					</TouchableOpacity>
				<Text style={{textAlign:'center',fontSize:18,color:'black'}}>{this.props.title}</Text>
					<TouchableOpacity
						style={{position:'absolute',right:5,paddingRight:10,top:5,paddingLeft:20,paddingTop:8,paddingBottom:10}}
						onPress={()=>this.onRightClick(this.props.obj)}>
						<Image source={require('image!btn_share')}/>
					</TouchableOpacity>
				</View>
				<View style={{height:0.5,backgroundColor:'#c6c6cc'}}/>
			</View>
		)
	}
}


