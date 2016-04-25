'use strict';
import React, {
  AppRegistry,
  Component,
  Image,
  Dimensions,
  Text,
  TouchableHighlight,
  StyleSheet,
  View
} from 'react-native';
import Swiper from 'react-native-swiper';
import { netClientPost,BUS_100201} from '../../../util/NetUtil';
import NetUtil from '../../../util/NetUtil';
import Web from '../../webview/Web';

var listData = [];

export default class Banner extends React.Component{
	constructor(props){
		super(props);
		this.state={
			data:[],
		}
	}

	onBannerClick(){
		this.props.homeObj.props.navigator.push({
			component:Web
		});
	}

	//轮播通告回调
	BUS_100201_CB(object,json){
		if (json.retcode === '000000') {
			listData=listData.concat(json.doc);
			object.setState({
				data: listData,
			});
			console.log('成功='+listData);
		}else{
			isLoadEnd = true;
			console.log('失败');
		}
	}

	//轮播通告请求
	BUS_100201_REQ(){
		var params = {
			encrypt:'none',
		};
		netClientPost(this,BUS_100201,this.BUS_100201_CB,params);
	}

	componentDidMount() {
		this.BUS_100201_REQ();
	}

	render(){
		var bannerThis = this;
		return(
				<Swiper autoplay={true} loop={true}
					showsPagination={true}
					height={Dimensions.get('window').width*7/15+30} 
					paginationStyle={{bottom:40,}}
					>
					{this.state.data.map(function(item){
						return(
							<View key={item.aId} style={{backgroundColor:'#ffffff'}}>
								<TouchableHighlight
							  	  onPress={()=>bannerThis.onBannerClick()}
							  	  underlayColor='#fcfcfc'>
									<Image
									  style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*7/15}}
									  source={{uri: item.imageUrl}} />
								</TouchableHighlight>
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
