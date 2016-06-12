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
  Text,
  TouchableOpacity,
  View
} from 'react-native';

import {STORAGE_KEY_ALIAS} from '../util/Global';
import { netClientPost,BUS_100401} from '../util/NetUtil';
import Welcome_Guide from './Welcome_Guide';
import Web from './webview/Web';

export default class Welcome extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isFirst:false,
			adData:'',
			skipTime:4,
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
	          		//查询APP欢迎广告
	          		this.BUS_100401_REQ();
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
	        	var t = this.state.skipTime-1;
	        	this.setState({skipTime:t})
	        	if (this.state.skipTime === 0) {
	        		this.props.homeObj.setState({
						loadAD:false,
					});
					return;
	        	}
	            this.setToggleTimeout();
	        },1000
	    )
	}

	//APP欢迎页广告回调
	BUS_100401_CB(object,json){
		if (json.retcode === '000000') {
			object.setState({adData:json});
		}else{

		}
	}

	//APP欢迎页广告
	BUS_100401_REQ(){
		var params = {
			encrypt:'none',
		};
		netClientPost(this,BUS_100401,this.BUS_100401_CB,params);
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
		this.props.homeObj.setState({
			loadAD:false,
			adUrl:this.state.adData.aUrl,
		});
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
							{
								this.state.adData?
									<TouchableOpacity
										underlayColor='#fcfcfc'
										onPress={()=>this.onADPress()}>
										<Image
											style={{height:Dimensions.get('window').height-100,width:Dimensions.get('window').width}}
											source={{uri:this.state.adData.imageUrl}}
											/>
									</TouchableOpacity>
									:
									<View
										style={{height:Dimensions.get('window').height-100,width:Dimensions.get('window').width}}/>
							}
							
						
						<TouchableOpacity
							style={{position:'absolute',top:15,right:1,padding:20}}
							underlayColor='#fcfcfc'
							onPress={()=>this.onSkipPress()}>
							<Image	
								style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}
								source={require('image!loading_skip')}
								>
								<Text style={{fontSize:14,color:'#444444'}}>跳过{this.state.skipTime}</Text>
							</Image>
						</TouchableOpacity>
						<View style={{height:100,backgroundColor:'white',alignItems:'center',justifyContent:'center'}}>
							<Image
								style={{height:44,width:264}}
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


