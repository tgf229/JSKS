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
  View
} from 'react-native';

import PointSearch from '../../point/PointSearch';
import WishAgreement from '../../wish/WishAgreement';
import OfferSearch from '../../offer/OfferSearch';


export default class GK_Header extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	onBtnClick(flag){
		const { navigator } = this.props.homeObj.props;
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
			default:
				console.log('按钮=其他');
				break;
		}
	}

	render(){
		return(
			<View style={{backgroundColor:'#eeeeee'}}>
					<View style={{flexDirection:'row',backgroundColor:'#ffffff'}}>
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
					{/* 
						<TouchableHighlight
								style={{flex:1,paddingTop:12,paddingBottom:12}}
								onPress={()=>this.onBtnClick(4)}
								underlayColor='#fcfcfc'>
							<View>
								<Image
								  style={{alignSelf:'center'}}
								  source={require('image!home_icon_set')} />
								<Text style={{marginTop:8,fontSize:12,color:'#444444',textAlign:'center'}}>系统设置</Text>
							</View>
						</TouchableHighlight>
					*/}
					</View>
					<View style={{backgroundColor:'#ffffff',marginTop:12,height:30,flexDirection:'row',paddingLeft:10,paddingRight:10}}>
						<View style={{justifyContent:'center',alignItems:'center',flex:1,flexDirection:'row'}}>
							<View style={{backgroundColor:'#d5d5d5',flex:1,height:0.5}}></View>
							<Text style={{marginLeft:13,marginRight:13,fontSize:12,color:'#999999'}}>今日高考资讯</Text>
							<View style={{backgroundColor:'#d5d5d5',flex:1,height:0.5}}></View>
						</View>
					</View>
			</View>
		)
	}

}

const styles = StyleSheet.create({

});
