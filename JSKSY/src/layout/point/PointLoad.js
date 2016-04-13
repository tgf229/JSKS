/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  ActivityIndicatorIOS,
  Image,
  Text,
  View
} from 'react-native';

export default class PointLoad extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<View style={{flex:1,justifyContent:'center',alignItems:'center'}}>
				<Image
				  style={{width:264,height:214}}
				  source={{uri: 'load_pic'}} />
				<ActivityIndicatorIOS
					style={{marginTop:10}}
				  	animating={true}
				  	color={'#808080'}
				  	size={'small'} />

				<Text style={{fontSize:20,color:'#888888',marginTop:10}}>正在拼命查询中，请稍后...</Text>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


