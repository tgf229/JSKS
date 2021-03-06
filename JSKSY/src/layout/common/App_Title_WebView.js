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

export default class App_Title_WebView extends React.Component{
	constructor(props){
		super(props);
		this.state={
			
		}
	}

	onLeftClick(obj){
		obj.onLeftNavCilck();
	}

	onLeftCloseClick(obj){
		obj.onLeftCloseNavCilck();
	}

	onRightClick(obj){
		obj.onRightNavCilck();
	}

	render(){
		return(
			<View>
				<View style={{height:20}}/>
				<View style={{height:44,flexDirection:'row',justifyContent:'space-between',alignItems:'center'}}>
					<View style={{flexDirection:'row',alignItems:'center'}}>
					<TouchableOpacity
						style={{paddingLeft:20,paddingRight:30}}
						onPress={()=>this.onLeftClick(this.props.obj)}>
						<Image 
							source={require('image!btn_back_black')}/>
					</TouchableOpacity>
					<TouchableOpacity
						style={{paddingLeft:10,paddingRight:30}}
						onPress={()=>this.onLeftCloseClick(this.props.obj)}>
						<Image 
							source={require('image!icon_btn_cancle')}/>
					</TouchableOpacity>
					</View>
					<Text 
						numberOfLines={1}
						style={{fontSize:18,color:'black',textAlign:'center',width:120}}>{this.props.title}</Text>
					{
					this.props.rightBtnHide
					?
					<View style={{paddingRight:110}}/>
					:
					<TouchableOpacity
						style={{paddingLeft:60,paddingRight:30}}
						onPress={()=>this.onRightClick(this.props.obj)}>
						<Image source={require('image!btn_share')}/>
					</TouchableOpacity>
					}
					
				</View>
				<View style={{height:0.5,backgroundColor:'#c6c6cc'}}/>
			</View>
		)
	}
}


