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
  Text,
  Dimensions,
  Navigator,
  TouchableHighlight,
  TouchableOpacity,
  View
} from 'react-native';
import Banner from './Banner';

import GK_Home from '../../gk/GK_Home';
import ZZ_PointSearch from '../../zz/ZZ_PointSearch';
import University_List from '../../university/University_List';

export default class Header extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	onBtnClick(flag){
		const { navigator } = this.props;
		switch(flag){
			case 1:
				if(navigator) {
			            navigator.push({
			                component: GK_Home,
			            })
			        }
				break;
			case 2:
				if (navigator) {
					navigator.push({
						component:University_List,
					})
				}
				break;
			case 3:
				if (navigator) {
					navigator.push({
						component:ZZ_PointSearch,
					})
				}
				break;
			default:
				console.log('按钮=其他');
				break;
		}
	}

	render(){
		return(
			<View style={{backgroundColor:'#ffffff'}}>
					<Banner navigator={this.props.navigator}></Banner>
					<View style={{height:10,backgroundColor:'#f3f3f3'}}/>
					<View style={{padding:9,flexDirection:'row'}}>
						<TouchableOpacity
							onPress={()=>this.onBtnClick(1)}>
							<Image 
								style={{width:(global.windowWidth-34)/3}}
								source={require('image!home_btn_gaokao')}/>
						</TouchableOpacity>
						<TouchableOpacity
							onPress={()=>this.onBtnClick(2)}>
							<Image
								style={{marginLeft:8,marginRight:8,width:(global.windowWidth-34)/3}}
								source={require('image!home_btn_yuanxiaoku')}/>
						</TouchableOpacity>
						<TouchableOpacity
							onPress={()=>this.onBtnClick(3)}>
							<Image
								style={{width:(global.windowWidth-34)/3}}
								source={require('image!home_btn_zhongzhi')}/>
						</TouchableOpacity>
					</View>
					<View style={{height:10,backgroundColor:'#f3f3f3'}}/>
					<View style={{height:33,flexDirection:'row',paddingLeft:15,alignItems:'center'}}>
						<Image 
							source={require('image!icon_news')}/>
						<Text style={{marginLeft:5,textSize:'12',color:'#444444'}}>招考要闻</Text>
					</View>
					<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
			</View>
		)
	}

}

const styles = StyleSheet.create({

});

