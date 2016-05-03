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
  AlertIOS,
  AsyncStorage,
  ActivityIndicatorIOS,
  Text,
  View
} from 'react-native';
import OfferResult_Fail from './component/OfferResult_Fail';
import OfferResult_Success from './component/OfferResult_Success';
import App_Title from '../common/App_Title';
import { netClientPost,BUS_400101,BUS_400201} from '../../util/NetUtil';
import { STORAGE_KEY_SNUM,STORAGE_KEY_STICKET} from '../../util/Global';

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

	//进行存储数据删除_ONE
	  async _removeValue(){
	      try{
	         await AsyncStorage.removeItem(STORAGE_KEY_SNUM);
	         await AsyncStorage.removeItem(STORAGE_KEY_STICKET);
	         console.log('数据删除成功...');
	      }catch(error){
	         console.log('AsyncStorage错误'+error.message);
	      }
	  }

	//进行储存数据_ONE
	async _saveValue(){
	     try{
		    await AsyncStorage.setItem(STORAGE_KEY_SNUM,this.props.sNum);
		    await AsyncStorage.setItem(STORAGE_KEY_STICKET,this.props.sTicket);
		       	console.log('保存到存储的数据成功');
		    }catch(error){
		        console.log('AsyncStorage错误'+error.message);
		    }
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
			//如果进入预约流程，则保存用户信息
			object._saveValue();
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
			alias:this.props.alias,
		}
		netClientPost(this,BUS_400101,this.BUS_400101_CB,params);
	}

	BUS_400201_CB(object,json){
		console.log('BUS_400201_CB = '+json)
		if (json.retcode === '000000') {
			object._removeValue();
			AlertIOS.alert(
				'温馨提示',
				'取消预约成功',
			);
		}
		else{
			AlertIOS.alert(
				'温馨提示',
				'很抱歉，取消预约失败',
			);
		}

	}

	BUS_400201_REQ(){
		var params = {
			encrypt:'none',
			sNum:this.props.sNum,
			sTicket:this.props.sTicket,
			alias:this.props.alias,
		}
		netClientPost(this,BUS_400201,this.BUS_400201_CB,params);
	}

	componentDidMount() {
		this.BUS_400101_REQ();
	}

	onRightNavCilck(){
		this.BUS_400201_REQ();
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white'}}>
				{
					this.state.isSuccess
						?	
					<App_Title title={'录取结果'} navigator={this.props.navigator} rightShow={true} obj={this}/>
						:
					<App_Title title={'录取结果'} navigator={this.props.navigator} rightShow={true} rightText={'取消预约'} obj={this}/>
				}
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


