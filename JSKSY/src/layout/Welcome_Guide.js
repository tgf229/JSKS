'use strict';
import React, {
  AppRegistry,
  Component,
  Image,
  Dimensions,
  TouchableHighlight,
  StyleSheet,
  View
} from 'react-native';
import Swiper from 'react-native-swiper';

export default class Welcome_Guide extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	onClick(){
		this.props.homeObj.setState({
			loadAD:false,
		});
	}

	render(){
		var guideThis = this;
		return(

					<Swiper 
						style={styles.wrapper}
						autoplay={false} loop={false}
						showsPagination={true}
						>
								<View style={styles.slide}>
					             	<Image style={styles.image} source={require('image!guide1')} />
					            </View>
					            <View style={styles.slide}>
					             	<Image style={styles.image} source={require('image!guide2')} />
					            </View>
					            <View style={styles.slide}>
					             	<Image style={styles.image} source={require('image!guide3')} />
					            </View>
					            <View style={styles.slide}>
					             	<Image style={styles.image} source={require('image!guide4')} />
					            </View>
					            <View style={styles.slide}>
					             	<Image style={styles.image} source={require('image!guide5')} />
					            </View>
					            <TouchableHighlight
					            	underlayColor='#fcfcfc'
					            	onPress={()=>guideThis.onClick()}
					            	>
					            <View style={styles.slide}>
					            	<Image style={styles.image} source={require('image!guide6')} />
					            </View>
					            </TouchableHighlight>
					</Swiper>
		)
	}
}

const styles = StyleSheet.create({
	wrapper: {
    
  },
	slide: {
    flex: 1,
    // backgroundColor: '#f00',
  },
  image: {
  	width:Dimensions.get('window').width,
  	height:Dimensions.get('window').height
  }

});
