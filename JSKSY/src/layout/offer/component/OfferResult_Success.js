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

export default class OfferResult_Success extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<View>
				<Image
				  style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*1.06,alignItems:'center'}}
				  source={require('image!offer_result_top')} >
				  <View style={{alignItems:'center',justifyContent:'center',backgroundColor:'#a31c26',width:288,height:28,marginTop:20,borderRadius:20}}>
				  	<Text style={{fontSize:12,color:'white'}}>姓名：{this.props.data.sName}      考生号：{this.props.sNum}</Text>
				  </View>
				</Image>
				<Image
				  style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*0.55}}
				  source={require('image!offer_result_bottom')} >
				  	<View style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}>
				  		<Text style={{fontSize:17,color:'white',fontWeight:'bold'}}>恭喜您！</Text>
				  		{
				  			this.props.data.major
				  				?
				  				<Text style={{fontSize:13,color:'white',fontWeight:'bold',marginTop:6}}>已被{this.props.data.school}－{this.props.data.major}录取</Text>
				  				:
				  				<Text style={{fontSize:13,color:'white',fontWeight:'bold',marginTop:6}}>已被{this.props.data.school}预录取</Text>
				  		}
				  		
				 	</View>
				  
				</Image>
		    </View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		backgroundColor:'white',
	},
});


