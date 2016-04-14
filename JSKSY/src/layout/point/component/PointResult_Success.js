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
  Dimensions,
  Text,
  View
} from 'react-native';

export default class PointResult_Success extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<View>
				<Image
					style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*0.6}}
				  	source={require('image!point_result_top')} >
				  	<View style={{position:'absolute',left:(Dimensions.get('window').width-288)/2,bottom:10,alignItems:'center',justifyContent:'center',backgroundColor:'#1a7eb2',width:288,height:28,borderRadius:20}}>
					  	<Text style={{fontSize:12,color:'white'}}>姓名：王大雷      考生号：16262838493939</Text>
					</View>
				</Image>
				<Image
				  style={{alignItems:'center',justifyContent:'center',width:Dimensions.get('window').width,height:Dimensions.get('window').width*0.8}}
				  source={require('image!point_result_bg')} >
				  	<View style={{alignItems:'center'}}>
						<Image
							style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}
				   			source={require('image!point_result_total_big')} >
				   			<Text style={{fontSize:11,color:'#ae582e'}}>体艺文化总分</Text>
				   			<Text style={{fontSize:34,color:'white',marginTop:11}}>347<Text style={{fontSize:10}}>分</Text></Text>
				   		</Image>
				   		<Image
				   			style={{alignItems:'center',justifyContent:'center',marginTop:-40,backgroundColor:'transparent'}}
				   			source={require('image!point_result_rank')} >
				   			<Text style={{fontSize:14,color:'white'}}>体育类投档分位次：103名</Text>
				   		</Image>
				  	</View>
				</Image>

				<View style={{flexDirection:'row',marginTop:17,paddingTop:16,paddingBottom:16}}>
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:17,color:'#333333',fontWeight:'bold'}}>122<Text style={{fontSize:9}}>分</Text>+5<Text style={{fontSize:9}}>分</Text></Text>
						<Image
						  style={{marginTop:10}}
						  source={require('image!point_icon_yuwen')} />
						<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:10}}>语文＋附加分</Text>
					</View>
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:17,color:'#333333',fontWeight:'bold'}}>122<Text style={{fontSize:9}}>分</Text>+5<Text style={{fontSize:9}}>分</Text></Text>
						<Image
						  style={{marginTop:10}}
						  source={require('image!point_icon_shuxue')} />
						<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:10}}>数学＋附加分</Text>
					</View>
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:17,color:'#333333',fontWeight:'bold'}}>122<Text style={{fontSize:9}}>分</Text></Text>
						<Image
						  style={{marginTop:10}}
						  source={require('image!point_icon_yingyu')} />
						<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:10}}>外语</Text>
					</View>
				</View>

				<View style={{backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width-60,marginLeft:30}}></View>

				<View style={{flexDirection:'row'}}>
					<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-140,alignItems:'center',paddingTop:16,paddingBottom:16}}>
						<Image
						  	source={require('image!point_icon_lishi')} />
						<View style={{marginLeft:10}}>
							<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>A+</Text>
							<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>历史</Text>
						</View>
					</View>
					<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-180,alignItems:'center',paddingTop:16,paddingBottom:16}}>
						<Image
						  	source={require('image!point_icon_zhengzhi')} />
						<View style={{marginLeft:10}}>
							<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>A+</Text>
							<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>历史</Text>
						</View>
					</View>
				</View>
				<View style={{flexDirection:'row'}}>
					<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-140,alignItems:'center',paddingTop:16,paddingBottom:16}}>
						<Image
						  	source={require('image!point_icon_policy')} />
						<View style={{marginLeft:10}}>
							<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>0<Text style={{fontSize:9}}>分</Text></Text>
							<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>政策性照顾分</Text>
						</View>
					</View>
					<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-180,alignItems:'center',paddingTop:16,paddingBottom:16}}>
						<Image
						  	source={require('image!point_icon_A_add')} />
						<View style={{marginLeft:10}}>
							<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>5<Text style={{fontSize:9}}>分</Text></Text>
							<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>文理类奖励分</Text>
						</View>
					</View>
				</View>

				<Text style={{textAlign:'center',marginBottom:10,fontSize:8,color:'#666666'}}>数据来源 BY 江苏省教育考试院</Text>

			</View>
		)
	}
}

const styles = StyleSheet.create({

});


