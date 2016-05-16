/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  TouchableHighlight,
  ScrollView,
  Text,
  TextInput,
  Dimensions,
  ActivityIndicatorIOS,
  AlertIOS,
  View
} from 'react-native';
import PointResult from './PointResult'
import PointWait from './component/PointWait'
import App_Title from '../common/App_Title';
import { netClientPost,BUS_200101} from '../../util/NetUtil';

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

var cuTime;
var exTime;
export default class PointSearch extends React.Component{
	constructor(props){
		super(props);
		this.state={
			sNumStr:'',
			sTicketStr:'',
			isLoaded:false,	//是否调用时间基准接口完毕
			isPointSearchOpen:false, //查分是否一开始
		}
	}

	//时间基准接口回调
	BUS_200101_CB(object,json){
		console.log('BUS_200101_CB = '+json)
		if (json.retcode === '000000') {
			var isOpen = false;
			cuTime = json.cuTime;
			exTime = json.exTime;
			//若当前时间大于等于目标时间，则设置isOpen为true 打开查询流程
			if (json.exTime <= json.cuTime) {
				isOpen = true;
			}

			//测试用＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
			isOpen = true;
			
			object.setState({
				isLoaded:true,
				isPointSearchOpen:isOpen,
			});
			
		}else{

		}
	}

	//时间基准接口请求
	BUS_200101_REQ(){
		var params = {
			encrypt:'none',
		};
		netClientPost(this,BUS_200101,this.BUS_200101_CB,params);
	}

	componentDidMount() {
		this.BUS_200101_REQ();
	}

	onNumChangeText(e){
		this.setState({sNumStr:e});
	}

	onTicketChangeText(e){
		this.setState({sTicketStr:e});
	}

	onSubmit(){
		var num = trim(this.state.sNumStr);
		var tick = trim(this.state.sTicketStr);
		num='15320104680173';
		tick='1501046804727';
		if (num === '') {
			AlertIOS.alert(
				'温馨提示',
				'请输入你的考生号',
			);
			return;
		}
		if (tick === '') {
			AlertIOS.alert(
				'温馨提示',
				'请输入你的准考证号',
			);
			return;
		}
		this.props.navigator.push({
			component:PointResult,
			params:{
				sNum:num,
				sTicket:tick,
			}
		});
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'高考查分'} navigator={this.props.navigator} />
			{
				this.state.isLoaded?
					(this.state.isPointSearchOpen?
						<ScrollView
							keyboardDismissMode={'on-drag'}
						  	contentContainerStyle={styles.contentContainer}>
						  	<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999'}}
								clearButtonMode='while-editing'
								keyboardType='numeric'
								maxLength={20}
								value={this.state.sNumStr}
								onChangeText={e=>this.onNumChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的考生号'
							/>
							<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:21}}
								clearButtonMode='while-editing'
								keyboardType='numeric'
								maxLength={20}
								value={this.state.sTicketStr}
								onChangeText={e=>this.onTicketChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的准考证号'
							/>
							<TouchableHighlight
								style={{marginTop:30,justifyContent:'center',alignItems:'center',
									backgroundColor:'#ff902d',height:45,borderRadius:3}}
								onPress={()=>this.onSubmit()}
								underlayColor='#fcfcfc'>
								<Text style={{fontSize:16,color:'white'}}>查询</Text>
							</TouchableHighlight>
						</ScrollView>
					:
					<PointWait cuTime={cuTime} exTime={exTime} searchObj={this}/>)

				:
				<ActivityIndicatorIOS style={{marginTop:Dimensions.get('window').height/2-65}}/>
			}
			
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:54,
		paddingLeft:20,
		paddingRight:20,
	},
});


