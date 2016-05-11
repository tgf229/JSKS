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
  AsyncStorage,
  Image,
  TouchableHighlight,
  View
} from 'react-native';

import {STORAGE_KEY_ALIAS} from '../util/Global';
import Welcome_Guide from './Welcome_Guide';

export default class Welcome extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isFirst:false,
		}
	}

	async _loadInitialState(){
	       try{
	          var alias = await AsyncStorage.getItem(STORAGE_KEY_ALIAS);
	          console.log('别名＝'+alias);
	          	if (!alias) {
	          		//如果是第一次打开APP，则展示引导页
	          		this.setState({isFirst:true});

			    	alias = new Date().getTime()+"";
				    await AsyncStorage.setItem(STORAGE_KEY_ALIAS,alias);   
	          	}else{
	          		//如果不是第一次打开APP，则进欢迎页loading
	          		this.setState({isFirst:false});
	          		//开启欢迎页定时3秒 打开主界面任务
	          		this.setToggleTimeout();
	          	}
	       }catch(error){
	            console.log('AsyncStorage错误'+error.message);
	       }
	  }

	setToggleTimeout(){
	    this.timer = setTimeout(
	        ()=>{
	            this.props.homeObj.setState({
					loadAD:false,
				});
	        },3000
	    )
	}

	componentDidMount() {
		this._loadInitialState().done();
	}

	componentWillUnmount() {
	  	this.timer && clearTimeout(this.timer);
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
			<View>
			{
				this.state.isFirst
					?
					<Welcome_Guide homeObj={this.props.homeObj}/>
					:
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
			}
			</View>
		)
	}
}

const styles = StyleSheet.create({
	
});


