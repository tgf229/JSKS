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
  PixelRatio,
  Navigator,
  TouchableHighlight,
  TouchableOpacity,
  View
} from 'react-native';

import PointSearch from '../../point/PointSearch';
import WishAgreement from '../../wish/WishAgreement';
import OfferSearch from '../../offer/OfferSearch';
import Banner from '../../home/component/Banner';
import University_List from '../../university/University_List';


export default class GK_Header extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	onBtnClick(flag){
		switch(flag){
			case 1:
			    this.props.navigator.push({
			            component: PointSearch,
	            })
				break;
			case 2:
				 this.props.navigator.push({
			                component: WishAgreement,
			        })
				break;
			case 3:
				 this.props.navigator.push({
			                component: OfferSearch,
			            })
				break;
			case 4:
				 this.props.navigator.push({
			                component: University_List,
			            })
				break;
			case 5:
				 this.props.navigator.push({
			            component: University_List,
			            params:{
			            	channel:'1'
			            }
			        })
				break;
			default:
				console.log('按钮=其他');
				break;
		}
	}

	render(){
		return(
			<View style={{backgroundColor:'#f3f3f3'}}>
					<Banner navigator={this.props.navigator} type={'1'}></Banner>
					<View style={{flexDirection:'row',backgroundColor:'#ffffff',marginTop:10}}>
						<TouchableHighlight
								style={{flex:1,paddingTop:12,paddingBottom:12}}
								onPress={()=>this.onBtnClick(1)}
								underlayColor='#fcfcfc'>
							<View>
								<Image
								  style={{alignSelf:'center'}}
								  source={require('image!home_icon_check')} />
								<Text style={{marginTop:8,fontSize:12,color:'#444444',textAlign:'center'}}>高考查分</Text>
							</View>
						</TouchableHighlight>
						<TouchableHighlight
								style={{flex:1,paddingTop:12,paddingBottom:12}}
								onPress={()=>this.onBtnClick(2)}
								underlayColor='#fcfcfc'>
							<View>
								<Image
								  style={{alignSelf:'center'}}
								  source={require('image!home_icon_query')} />
								<Text style={{marginTop:8,fontSize:12,color:'#444444',textAlign:'center'}}>录取资料</Text>
							</View>
						</TouchableHighlight>
						<TouchableHighlight
								style={{flex:1,paddingTop:12,paddingBottom:12}}
								onPress={()=>this.onBtnClick(3)}
								underlayColor='#fcfcfc'>
							<View>
								<Image
								  style={{alignSelf:'center'}}
								  source={require('image!home_icon_volunteer')} />
								<Text style={{marginTop:8,fontSize:12,color:'#444444',textAlign:'center'}}>录取结果</Text>
							</View>
						</TouchableHighlight>
						<TouchableHighlight
								style={{flex:1,paddingTop:12,paddingBottom:12}}
								onPress={()=>this.onBtnClick(4)}
								underlayColor='#fcfcfc'>
							<View>
								<Image
								  style={{alignSelf:'center'}}
								  source={require('image!icon_yuanxiaoku')} />
								<Text style={{marginTop:8,fontSize:12,color:'#444444',textAlign:'center'}}>院校库</Text>
							</View>
						</TouchableHighlight>
					</View>
					{
						global.init_gkAdSchool === '1'
						?
						<TouchableOpacity
							onPress={()=>this.onBtnClick(5)}>
							<Image 
								style={{marginTop:11,marginLeft:11,marginRight:11,width:global.windowWidth-22}}
								source={require('image!gk_school_icon')} />
						</TouchableOpacity>
						:
						null
					}
					<View style={{height:33,flexDirection:'row',marginTop:11,paddingLeft:15,alignItems:'center',backgroundColor:'white'}}>
						<Image 
							source={require('image!icon_news')}/>
						<Text style={{marginLeft:5,textSize:'12',color:'#444444'}}>高考资讯</Text>
					</View>
					<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
			</View>
		)
	}

}

const styles = StyleSheet.create({

});

