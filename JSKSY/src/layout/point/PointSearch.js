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
  View
} from 'react-native';
import PointResult from './PointResult'
import PointWait from './component/PointWait'
import App_Title from '../common/App_Title';
import { netClientPost,BUS_200101} from '../../util/NetUtil';

var cuTime;
var exTime;
export default class PointSearch extends React.Component{
	constructor(props){
		super(props);
		this.state={
			searchString:'',
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

	onChangeText(e){
		this.setState({searchString:e});
	}

	onSubmit(){
		this.props.navigator.push({
			component:PointResult,
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
						  contentContainerStyle={styles.contentContainer}>
						  	<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999'}}
								clearButtonMode='while-editing'
								value={this.state.searchString}
								onChangeText={e=>this.onChangeText(e)}
								onSubmitEditing={()=>this.onSubmit()}
								enablesReturnKeyAutomatically={true}
								placeholder='请输入你的考生号'
							/>
							<TextInput
								style={{borderWidth:1,height:50,borderColor:'#d5d5d5',borderRadius:3,padding:5,fontSize:15,color:'#999999',marginTop:21}}
								clearButtonMode='while-editing'
								value={this.state.searchString}
								onChangeText={e=>this.onChangeText(e)}
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


