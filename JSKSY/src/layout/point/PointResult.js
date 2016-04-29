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
import { netClientPost,BUS_200201} from '../../util/NetUtil';

var data;
export default class PointResult extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isLoaded:false,
			errorLoading:true,
			errorTips:'正在拼命查询中，请稍候...',
		}
		data = {};
	}

	BUS_200201_CB(object,json){
		console.log('BUS_200201_CB = '+json)
		if (json.retcode === '000000') {
			data = json;
			object.setState({
				isLoaded:true,
				errorLoading:false,
			});
		}else{
			var error='请求失败，请稍后再试';
			if (json.retinfo) {
				error = json.retinfo;
			}
			object.setState({
				errorLoading:false,
				errorTips:error,
			});
		}
	}

	BUS_200201_REQ(){
		var params = {
			encrypt:'none',
			sNum:this.props.sNum,
			sTicket:this.props.sTicket,
		}
		netClientPost(this,BUS_200201,this.BUS_200201_CB,params);
	}

	componentDidMount() {
		this.BUS_200201_REQ();
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'高考查分'} navigator={this.props.navigator} />
			<ScrollView

			  	contentContainerStyle={styles.contentContainer}>
			
				{
					this.state.isLoaded? <PointResult_Success data={data} sNum={this.props.sNum}/>
						:
						<View style={{flex:1,alignItems:'center',marginTop:90}}>
							<Image
					  			source={require('image!load_pic')} />
					  		{
					  			this.state.errorLoading
					  				?
					  				<ActivityIndicatorIOS
										style={{marginTop:10}}
									  	animating={true}
									  	color={'#808080'}
									  	size={'small'} />
									:
									null
					  		}
							
							<Text style={{fontSize:20,color:'#888888',marginTop:10}}>{this.state.errorTips}</Text>
						</View>
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


