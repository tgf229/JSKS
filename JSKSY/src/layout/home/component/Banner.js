'use strict';
import React, {
  AppRegistry,
  Component,
  Image,
  Dimensions,
  Text,
  StyleSheet,
  View
} from 'react-native';
import Swiper from 'react-native-swiper';
import { urlForQueryAndPage, netClient } from '../../../util/NetUtil';

var listData = [];

export default class Banner extends React.Component{
	constructor(props){
		super(props);
		this.state={
			data:[],
		}
	}

	componentDidMount() {
		var query = "http://mobile.crossroad.love/Bus100201?encrypt=none&cId=1";
		//调用接口
		netClient(this,query);
	}

	//请求回调
	busCB(json){
		if (json.retcode === '000000') {
			listData=listData.concat(json.doc);
			this.setState({
				data: listData,
			});
			console.log('成功='+listData);
		}else{
			isLoadEnd = true;
			console.log('失败');
		}
	}

	render(){
		return(
				<Swiper autoplay={true} loop={true}
					showsPagination={true}
					height={Dimensions.get('window').width*7/15+30} 
					paginationStyle={{bottom:40,}}
					>
					{this.state.data.map(function(item){
						return(
							<View key={item.id} style={{backgroundColor:'#ffffff'}}>
								<Image
								  style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*7/15}}
								  source={{uri: item.imageUrl}} />
								<Text style={{textAlign:'center',paddingTop:7,paddingBottom:7,color:'#666666',fontSize:14}}>{item.name}</Text>
							</View>
						)
					})}
				</Swiper>
		)
	}
}

const styles = StyleSheet.create({
	slide1:{
		flex:1,
		justifyContent:'center',
		alignItems:'center',
		backgroundColor:'#9DD6EB',
	},
	slide2:{
		flex:1,
		justifyContent:'center',
		alignItems:'center',
		backgroundColor:'#97CAE5',
	},

});
