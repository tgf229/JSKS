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
import University_Detail from '../../university/University_Detail';
import {URL_SCHEMA_SCHOOL_DETAIL} from '../../../util/Global';

export default class Banner extends React.Component{
	constructor(props){
		super(props);
		this.state={
			data:[],
			isDataLoaded:false,
		}
	}

	onBannerClick(item){
		if (item.aUrl.indexOf(URL_SCHEMA_SCHOOL_DETAIL)!= -1) {
			const dId = item.aUrl.substring(item.aUrl.lastIndexOf("/")+1);
			this.props.navigator.push({
				component:University_Detail,
				params:{
					uCode:dId,
				},
			});
		}else{
			this.props.navigator.push({
				component:Web,
				params:{
					url:item.aUrl,
				},
			});
		}
	}

	//轮播通告回调
	BUS_100201_CB(object,json){
		if (json.retcode === '000000') {
			if (json.doc.length !== 0) {
				object.setState({
					data: json.doc,
					isDataLoaded:true,
				});
			}
		}else{
			console.log('失败');
		}
	}

	//轮播通告请求
	BUS_100201_REQ(){
		var params = {
			encrypt:'none',
			type:this.props.type?this.props.type:'',
		};
		netClientPost(this,BUS_100201,this.BUS_100201_CB,params);
	}

	componentDidMount() {
		this.BUS_100201_REQ();
	}

	render(){
		var bannerThis = this;
		console.warn('banner render')
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
