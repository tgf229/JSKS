'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	View,
	ScrollView,
	TouchableOpacity,
	PixelRatio,
	ProgressViewIOS,
	Text,
	Linking,
	Image} from 'react-native';

	var NativeBridge = require('react-native').NativeModules.NativeBridge;

export default class University_Detail_One extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {
	  	};
	}

	callPhone = ()=>{
		const tels = this.props.detail.tel;
		if (tels) {
			var tel = tels.split(",");
			NativeBridge.NATIVE_callPhone(tel[0]);
		}
	};

	render(){
		const detail = this.props.detail;
		const boy = parseInt(detail.maleRatio);
		const girl = parseInt(detail.femaleRatio);
		const type = detail.type;
		const tel = detail.tel;
		const address = detail.address;
		const introduce = detail.introduce;
		return(
			<View style={{width:global.windowWidth}}>
				<View style={{flexDirection:'row',alignItems:'center',justifyContent:'space-between',paddingTop:18,paddingBottom:18,paddingLeft:30,paddingRight:30}}>
					<View style={{alignItems:'center'}}>
						<Image source={require('image!icon_boy')}/>
						<Text style={{fontSize:9,color:'#67aef7',fontWeight:'bold',marginTop:7}}>BOY</Text>
					</View>
					<View style={{flex:1}}>
						<Text style={{alignSelf:'center',color:'#505977',fontSize:12}}>男女生比例</Text>
						<View style={{flexDirection:'row',marginLeft:15,marginRight:15,marginTop:6,marginBottom:6}}>
							<View style={{flex:boy,height:5,backgroundColor:'#73b8ff'}}/>
							<View style={{flex:girl,height:5,backgroundColor:'#ff6e4b'}}/>
						</View>
						<Text style={{alignSelf:'center',color:'#505977',fontSize:12}}>{boy}:{girl}</Text>
					</View>
					<View style={{alignItems:'center'}}>
						<Image source={require('image!icon_girl')}/>
						<Text style={{fontSize:9,color:'#ff6e4b',fontWeight:'bold',marginTop:7}}>GIRL</Text>
					</View>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<View style={{padding:15,flexDirection:'row',alignItems:'center'}}>
					<View style={{flex:1}}>
						<Text style={{color:'#777777',fontSize:12,fontWeight:'bold'}}>类型：{type}</Text>
						<Text style={{color:'#777777',fontSize:12,fontWeight:'bold',marginTop:6}}>电话：{tel}</Text>
						<Text ref="test" style={{color:'#777777',fontSize:12,fontWeight:'bold',marginTop:6}}>地址：{address}</Text>
					</View>
					<TouchableOpacity 
						onPress={this.callPhone}
						style={{width:64,alignItems:'flex-end'}}>
						<Image 
							style={{width:PixelRatio.get()*80/4,height:PixelRatio.get()*80/4}}
							source={require('image!btn_call')}/>
					</TouchableOpacity>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<Text ref="test" style={{margin:15,color:'#777777',fontSize:12,fontWeight:'bold',lineHeight:18}}>
					{introduce}
				</Text>
			</View>
		);
	}
}

const styles = StyleSheet.create({

})