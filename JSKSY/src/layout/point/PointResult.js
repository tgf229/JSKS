/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  ScrollView,
  ActivityIndicatorIOS,
  Image,
  Text,
  View
} from 'react-native';
import PointResult_Success from './component/PointResult_Success';
import App_Title from '../common/App_Title';

export default class PointResult extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isLoading:true,
		}
	}

	componentDidMount() {
		this.timer = setTimeout(
			()=>{this.setState({isLoading:false})},
		500);
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'高考查分'} navigator={this.props.navigator} />
			<ScrollView

			  	contentContainerStyle={styles.contentContainer}>
			
				{
					this.state.isLoading? <View style={{flex:1,alignItems:'center',marginTop:90}}>
						<Image
				  			source={require('image!load_pic')} />
						<ActivityIndicatorIOS
							style={{marginTop:10}}
						  	animating={true}
						  	color={'#808080'}
						  	size={'small'} />
						<Text style={{fontSize:20,color:'#888888',marginTop:10}}>正在拼命查询中，请稍后...</Text>
					</View>:<PointResult_Success/>
				}
			</ScrollView>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{

	},
});


