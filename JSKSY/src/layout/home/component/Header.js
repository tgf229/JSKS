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
					<View style={{height:0.5,backgroundColor:'#e6e6e6'}}/>
					<View style={{padding:10,}}>
						<View style={{flexDirection:'row'}}>
							<TouchableOpacity
								onPress={()=>this.onBtnClick(1)}>
								<Image 
									style={{width:(global.windowWidth-30)/2}}
									source={require('image!home_icon_channel_gk')}/>
							</TouchableOpacity>
							<TouchableOpacity
								onPress={()=>this.onBtnClick(1)}>
								<Image 
									style={{width:(global.windowWidth-30)/4}}
									source={require('image!home_icon_channel_ck')}/>
							</TouchableOpacity>
							<TouchableOpacity
								onPress={()=>this.onBtnClick(1)}>
								<Image 
									style={{width:(global.windowWidth-30)/4}}
									source={require('image!home_icon_channel_zk')}/>
							</TouchableOpacity>
						</View>
						<View style={{flexDirection:'row'}}>
							<TouchableOpacity
								onPress={()=>this.onBtnClick(1)}>
								<Image 
									style={{width:(global.windowWidth-30)/4}}
									source={require('image!home_icon_channel_yjsks')}/>
							</TouchableOpacity>
							<TouchableOpacity
								onPress={()=>this.onBtnClick(1)}>
								<Image 
									style={{width:(global.windowWidth-30)/4}}
									source={require('image!home_icon_channel_sk')}/>
							</TouchableOpacity>
							<TouchableOpacity
								onPress={()=>this.onBtnClick(3)}>
								<Image 
									style={{width:(global.windowWidth-30)/2}}
									source={require('image!home_icon_channel_zz')}/>
							</TouchableOpacity>
						</View>
					</View>
					<View style={{height:0.5,backgroundColor:'#e6e6e6'}}/>
					<View style={{height:34,flexDirection:'row',paddingLeft:15,alignItems:'center'}}>
						<View style={{backgroundColor:'#67b2ff',width:4,height:14}}/>
						<Text style={{marginLeft:6,textSize:'12',color:'#000000'}}>招考要闻</Text>
					</View>
			</View>
		)
	}

}

const styles = StyleSheet.create({

});

