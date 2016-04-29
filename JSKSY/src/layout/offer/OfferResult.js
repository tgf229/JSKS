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
  ScrollView,
  Image,
  Dimensions,
  NavigatorIOS,
  ActivityIndicatorIOS,
  Text,
  View
} from 'react-native';
import OfferResult_Fail from './component/OfferResult_Fail';
import OfferResult_Success from './component/OfferResult_Success';
import App_Title from '../common/App_Title';
import { netClientPost,BUS_400101} from '../../util/NetUtil';

var data;
export default class OfferResult extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isLoaded:false,
			isSuccess:false,
			errorLoading:true,
			errorTips:'正在拼命查询中，请稍候...',
		}
		data={};
	}

	BUS_400101_CB(object,json){
		console.log('BUS_400101_CB = '+json)
		if (json.retcode === '000000') {
			data = json;
			object.setState({
				isLoaded:true,
				isSuccess:true,
				errorLoading:false,
			});
		}
		else if(json.retcode === '000002'){
			data = json;
			object.setState({
				isLoaded:true,
				isSuccess:false,
				errorLoading:false,
			});
		}
		else{
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

	BUS_400101_REQ(){
		var params = {
			encrypt:'none',
			sNum:this.props.sNum,
			sTicket:this.props.sTicket,
			type:'2',
			alias:'test',
		}
		netClientPost(this,BUS_400101,this.BUS_400101_CB,params);
	}

	componentDidMount() {
		this.BUS_400101_REQ();
	}

	onRightNavCilck(){
		console.log('right');
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
			<App_Title title={'录取结果'} navigator={this.props.navigator} rightShow={true} rightText={'取消预约'} obj={this}/>
			<ScrollView
			  contentContainerStyle={styles.contentContainer}>
			  	
			  	{
					this.state.isLoaded
						? 
							this.state.isSuccess
								?	
								<OfferResult_Success data={data} sNum={this.props.sNum}/>
								:
								<OfferResult_Fail data={data} sNum={this.props.sNum}/>
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


