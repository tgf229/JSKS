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
import SetActivity from '../set/SetActivity';

export default class App_Title extends React.Component{
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

	onSetClick(){
	    this.props.navigator.push({
			component: SetActivity,
		})
	}

	render(){
		return(
			<View style={{backgroundColor:'white'}}>
				<View style={{height:20}}/>
				<View style={{height:44,flexDirection:'row',alignItems:'center',justifyContent:'center'}}>
				{
					this.props.setShow
								?
							<TouchableOpacity
								style={{position:'absolute',left:10,paddingRight:60,paddingLeft:10,paddingTop:12,paddingBottom:10}}
								onPress={e=>this.onSetClick()}>
								<Image source={require('image!btn_setting')}/>
							</TouchableOpacity>
								:
							<TouchableOpacity
								style={{position:'absolute',left:10,paddingRight:60,paddingLeft:10,paddingTop:14,paddingBottom:10}}
								onPress={()=>this.onLeftClick(this.props.obj)}>
								<Image 
									source={require('image!btn_back_black')}/>
							</TouchableOpacity>
				}
				<Text style={{textAlign:'center',fontSize:18,color:'black'}}>{this.props.title}</Text>
				{
					this.props.rightShow?
						<TouchableOpacity
							style={{position:'absolute',right:5,paddingRight:10,top:1,paddingLeft:20,paddingTop:14,paddingBottom:10}}
							onPress={e=>this.onRightClick(this.props.obj)}>
							<Text style={{fontSize:14,color:'white'}}>{this.props.rightText}</Text>
						</TouchableOpacity>
						:
					this.props.setShow?
						<TouchableOpacity
							style={{position:'absolute',right:5,paddingRight:10,top:1,paddingLeft:20,paddingTop:12,paddingBottom:10}}
							onPress={e=>this.onSetClick()}>
							<Image source={require('image!btn_message')}/>
						</TouchableOpacity>
						:
						<View/>
				}
				</View>
				<View style={{height:0.5,backgroundColor:'#c6c6cc'}}/>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


