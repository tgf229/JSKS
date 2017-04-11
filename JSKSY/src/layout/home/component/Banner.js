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
import Web from '../../webview/Web';

var listData = [];

export default class Banner extends React.Component{
	constructor(props){
		super(props);
		this.state={
			data:[],
			isDataLoaded:false,
		}
	}

	onBannerClick(item){
		this.props.navigator.push({
			component:Web,
			params:{
				url:item.aUrl,
			},
		});
	}

	//轮播通告回调
	BUS_100201_CB(object,json){
		if (json.retcode === '000000') {
			if (json.doc.length !== 0) {
				listData=listData.concat(json.doc);
				object.setState({
					// data: listData,
					data:[{"imageUrl":"http://58.213.145.36:8081/imgs/edu/images/99/6d/d0/70a17cbf-acab-431e-8cf1-3b0e7065cf5f.jpg","aId":"140","name":"江苏教育考试院官方高清版","aUrl":"http://58.213.145.36:8081/imgs/edu/html/da/2016ml.html"},
			{"imageUrl":"http://58.213.145.36:8081/imgs/edu/images/99/6d/d0/70a17cbf-acab-431e-8cf1-3b0e7065cf5f.jpg","aId":"140","name":"江苏教育考试院官方高清版","aUrl":"http://58.213.145.36:8081/imgs/edu/html/da/2016ml.html"}
			],
					isDataLoaded:true,
				});
				console.log('成功='+listData);
			}
		}else{
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
			<View>
				{
					this.state.isDataLoaded?
					<Swiper autoplay={true} loop={true}
						showsPagination={true}
						height={Dimensions.get('window').width*7/15+30} 
						paginationStyle={{bottom:40,}}
						>
						{this.state.data.map(function(item){
							return(
								<View key={item.aId} style={{backgroundColor:'#ffffff'}}>
									<TouchableHighlight
								  	  onPress={()=>bannerThis.onBannerClick(item)}
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
					:
					<Image
						style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*7/15}}
						source={require('image!banner_default')} />
				}
			</View>
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
