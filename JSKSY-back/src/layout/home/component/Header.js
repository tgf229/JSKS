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

import PointSearch from '../../point/PointSearch';
import WishAgreement from '../../wish/WishAgreement';
import OfferSearch from '../../offer/OfferSearch';
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
			                component: PointSearch,
			            })
			        }
				break;
			case 2:
				if(navigator) {
			            navigator.push({
			                component: WishAgreement,
			            })
			        }
				break;
			case 3:
				if(navigator) {
			            navigator.push({
			                component: OfferSearch,
			            })
			        }
				break;
			case 4:
				if(navigator) {
			            navigator.push({
			                component: GK_Home,
			            })
			        }
				break;
			case 5:
				if (navigator) {
					navigator.push({
						component:ZZ_PointSearch,
					})
				}
				break;
			case 6:
				if (navigator) {
					navigator.push({
						component:University_List,
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
					<View style={{padding:7}}>
						<View style={{flexDirection:'row'}}>
							<TouchableHighlight
								onPress={()=>this.onBtnClick(4)}
								underlayColor='#fcfcfc'>
								<Image 
									style={{width:(Dimensions.get('window').width-15)*2/5,height:(Dimensions.get('window').width-15)*2/5}}
									source={require('image!gaokao_right')}/>
							</TouchableHighlight>

							<View style={{marginLeft:1,width:(Dimensions.get('window').width-15)*3/5,height:(Dimensions.get('window').width-15)*2/5}}>
								<View style={{flexDirection:'row',flex:1}}>
									<TouchableHighlight
										onPress={()=>this.onBtnClick(2)}
										underlayColor='#fcfcfc'>
										<Image 
											style={{flex:1,width:(Dimensions.get('window').width-15)*3/5/2}}
											source={require('image!gaokao_ziliao')}/>
									</TouchableHighlight>
									<TouchableHighlight
										style={{flex:1,marginLeft:1}}
										onPress={()=>this.onBtnClick(3)}
										underlayColor='#fcfcfc'>
										<Image 
											style={{flex:1,width:(Dimensions.get('window').width-15)*3/5/2}}
											source={require('image!gaokao_jieguo')}/>
									</TouchableHighlight>

								</View>
								<TouchableHighlight
									onPress={()=>this.onBtnClick(1)}
									style={{flex:1,marginTop:1}}
									underlayColor='#fcfcfc'>
									<Image
										style={{flex:1,width:(Dimensions.get('window').width-15)*3/5+1}}
										source={require('image!gaokao_check')}/>
								</TouchableHighlight>

							</View>
						</View>
						<TouchableHighlight
							onPress={()=>this.onBtnClick(5)}
							underlayColor='#fcfcfc'>
							<Image 
								style={{marginTop:7,width:Dimensions.get('window').width-14,height:(Dimensions.get('window').width-15)/5}}
								source={require('image!zhongzhi')}/>
						</TouchableHighlight>
						<TouchableOpacity
							onPress={()=>this.onBtnClick(6)}>
							<Image 
								style={{marginTop:7,width:Dimensions.get('window').width-14,height:(Dimensions.get('window').width-15)/5}}
								source={require('image!home_icon_university')}/>
						</TouchableOpacity>
					</View>
					<View style={{marginTop:3,backgroundColor:'#eeeeee',height:32,flexDirection:'row',paddingLeft:12,paddingRight:12}}>
						<View style={{justifyContent:'center',alignItems:'center',flex:1,flexDirection:'row'}}>
							<View style={{backgroundColor:'#d5d5d5',flex:1,height:0.5}}></View>
							<Text style={{marginLeft:13,marginRight:13,fontSize:13,color:'#666666'}}>招考要闻</Text>
							<View style={{backgroundColor:'#d5d5d5',flex:1,height:0.5}}></View>
						</View>
					</View>
			</View>
		)
	}

}

const styles = StyleSheet.create({

});

