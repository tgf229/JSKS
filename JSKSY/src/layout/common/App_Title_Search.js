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
  TextInput,
  TouchableOpacity,
  Text,
  View
} from 'react-native';
import SetActivity from '../set/SetActivity';


export default class App_Title extends React.Component{
	constructor(props){
		super(props);
		this.state={
			uName:this.props.cbObj.uName?this.props.cbObj.uName:'',
		}
	}

	onLeftClick(){
		this.props.navigator.pop();
	}

	_onNameChangeText = (e)=>{
		this.setState({uName:e})
	};

	_onSubmit = ()=>{
		this.props.cbObj.FILTER_REQ(this.state.uName);
	};

	render(){
		return(
			<View>
				<View style={{height:20}}/>
				<View style={{height:44,flexDirection:'row',alignItems:'center'}}>
					<TouchableOpacity
						style={{paddingRight:20,paddingLeft:20,paddingTop:10,paddingBottom:10}}
						onPress={()=>this.onLeftClick()}>
						<Image 
							source={require('image!btn_back_black')}/>
					</TouchableOpacity>
						<View style={{flex:1,height:34,marginRight:20,backgroundColor:'#f7f7f7',flexDirection:'row',alignItems:'center'}}>
							<Image 
								style={{marginLeft:15,marginRight:10}}
								source={require('image!school_icon_search')}/>
							<TextInput
								style={{flex:1,color:'#c1c1c1',fontSize:14}}
								clearButtonMode='while-editing'
								maxLength={20}
								autoCorrect={false}
								value={this.state.uName}
								returnKeyType={'search'}
								onChangeText={this._onNameChangeText}
								onSubmitEditing={this._onSubmit}
								enablesReturnKeyAutomatically={false}
								placeholder='请输入院校名称或院校代号'
								/>
						</View>	
				</View>
				<View style={{height:0.5,backgroundColor:'#c6c6cc'}}/>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


