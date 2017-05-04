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
import SetActivity from '../set/SetActivity';


export default class App_Title extends React.Component{
	constructor(props){
		super(props);
		this.state={
			
		}
	}

	onLeftClick(){
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
			<View style={{backgroundColor:'#67aef7'}} >
				<View style={{height:20}}/>
				<View style={{height:44,flexDirection:'row',alignItems:'center',justifyContent:'center'}}>
				{
					this.props.leftHid?<View/>
								:
							<TouchableHighlight
								style={{position:'absolute',left:10,paddingRight:60,paddingLeft:10,paddingTop:10,paddingBottom:10}}
								onPress={()=>this.onLeftClick()}
								underlayColor='#67aef7'>
								<Image 
									source={require('image!back_btn')}/>
							</TouchableHighlight>
				}
				<Text style={{textAlign:'center',fontSize:18,color:'white'}}>{this.props.title}</Text>
				{
					this.props.rightShow?
						<TouchableHighlight
							style={{position:'absolute',right:5,paddingRight:10,top:1,paddingLeft:20,paddingTop:14,paddingBottom:10}}
							onPress={e=>this.onRightClick(this.props.obj)}
							underlayColor='#67aef7'>
							<Text style={{fontSize:14,color:'white'}}>{this.props.rightText}</Text>
						</TouchableHighlight>
						:
					this.props.setShow?
						<TouchableHighlight
							style={{position:'absolute',right:5,paddingRight:10,top:1,paddingLeft:20,paddingTop:8,paddingBottom:10}}
							onPress={e=>this.onSetClick()}
							underlayColor='#67aef7'>
							<Image source={require('image!setting')}/>
						</TouchableHighlight>
						:
						<View/>
				}
				</View>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


