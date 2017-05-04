'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	View,
	PixelRatio,
	Text,
	Image} from 'react-native';

import GiftedListView from 'react-native-gifted-listview';
import {BUS_700101,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';

export default class University_Detail extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {

	  	};
	}

	render(){
		return(
			<View style={{flex:1}}>
				<Image 
					style={{width:global.windowWidth, height:global.windowWidth*3/5,backgroundColor:'#d5d5d5'}}
					source={{uri:'http://192.168.0.107/jszk/fdpic.png'}}
					>
					<View style={{position:'absolute',bottom:0,backgroundColor:'rgba(0, 0, 0, 0.4)',width:global.windowWidth,height:70,flexDirection:'row',alignItems:'center'}}>
						<Image 
							style={{width:50,height:50,borderRadius:25,backgroundColor:'#d5d5d5',marginLeft:11,marginRight:13}}
							source={{uri:'http://192.168.0.107/jszk/fdlogo.png'}}
							/>
						<View>
							<Text style={{fontSize:15,color:'white'}}>南京大学</Text>
							<View style={{flexDirection:'row',marginTop:10}}>
								<Text style={styles.tipsText}>排名123</Text>
								<Text style={styles.tipsText}>211</Text>
								<Text style={styles.tipsText}>985</Text>
						</View>
						</View>
					</View>
				</Image>
			</View>
		);
	}
}

const styles = StyleSheet.create({
	tipsText:{
		marginRight:8,
		paddingTop:3,
		paddingBottom:3,
		paddingLeft:6,
		paddingRight:6,
		backgroundColor:'rgba(255, 255, 255, 0.3)',
		color:'white',
		fontSize:10
	}
})