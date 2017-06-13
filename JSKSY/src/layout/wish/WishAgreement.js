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
  Dimensions,
  ScrollView,
  ActivityIndicatorIOS,
  AlertIOS,
  Text,
  Image,
  View
} from 'react-native';

// import WishPayment from './WishPayment';
import App_Title from '../common/App_Title';
import WishSearch from './WishSearch';
import PointWait from '../point/component/PointWait'
import { netClientPost,BUS_200101} from '../../util/NetUtil';

var cuTime;
var exTime;
export default class WishAgreement extends React.Component{
	constructor(props){
		super(props);
		this.state={
			isLoaded:false,	//是否调用时间基准接口完毕
			isPointSearchOpen:false, //查分是否已开始
		}
	}

	onSubmit(){
		this.props.navigator.replace({
			component:WishSearch
		});
	}

	//时间基准接口回调
	BUS_200101_CB(object,json){
		console.log('BUS_200101_CB = '+json)
		if (json.retcode === '000000') {
			var isOpen = false;
			cuTime = json.cuTime;
			exTime = json.wsTime;
			//若当前时间大于等于目标时间，则设置isOpen为true 打开查询流程
			if (json.wsTime <= json.cuTime) {
				isOpen = true;
			}

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

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
			<App_Title title={'志愿参考'} navigator={this.props.navigator}/>
			{
				this.state.isLoaded?
					(this.state.isPointSearchOpen?
						<ScrollView
						  contentContainerStyle={styles.contentContainer}>

						 	<Image
						 		style={{alignSelf:'center'}}
						    	source={require('image!will_agreement_tips')} />
							<Text style={{marginTop:12,fontSize:15,color:'#666666',lineHeight:22}}>    志愿参考服务结合考生当年高考成绩及所在科类全省位次、近四年高考录取数据、本年度各院校省内招生计划，并按照院校所在省份、专业、批次等附加条件检索出志愿参考院校及所含专业信息。本服务不仅是考生填报普通高校的参考资料，同时对家长、招生工作人员和中学教师也具有一定的参考价值。

							</Text>
							<View
								style={{flexDirection:'row',marginTop:20}}>
								<Text style={{width:35,fontSize:13,color:'#d0021b',lineHeight:23}}>PS：</Text>
								<Text style={{flex:1,fontSize:13,color:'#d0021b',lineHeight:22}}>本服务只针对进入文理科第一阶段的考生开放；{'\n'}院校数据仅包含文理科第一批次及第二批次院校；{'\n'}本服务为测试版。由于时间仓促，内容繁多，难免有疏漏、不当之处，恳请用户批评指正。</Text>
							</View>

							<TouchableHighlight
								style={{justifyContent:'center',alignItems:'center',
									backgroundColor:'#ff902d',height:45,borderRadius:3,marginTop:22}}
								onPress={()=>this.onSubmit()}
								underlayColor='#fcfcfc'>
								<Text style={{fontSize:16,color:'white'}}>下一步</Text>
							</TouchableHighlight>

							<View style={{height:50}}/>

						</ScrollView>
						:
						<PointWait cuTime={cuTime} exTime={exTime} content={'距离志愿参考服务开放还有'} flag={'1'} searchObj={this}/>)
					:
					<ActivityIndicatorIOS style={{marginTop:Dimensions.get('window').height/2-65}}/>
			}
			</View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		paddingTop:45,
		paddingLeft:20,
		paddingRight:20,
	},
});


