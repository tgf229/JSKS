'use strict'
import React,{
  Component,
  StyleSheet,
  View,
  ScrollView,
  Image,
  ActivityIndicatorIOS,
  ListView,
  Text,
  Dimensions,
} from 'react-native';
import App_Title from '../common/App_Title';
import { netClientPostEncrypt,BUS_600101} from '../../util/NetUtil';

var NativeBridge = require('react-native').NativeModules.NativeBridge;

const PIC_LIST = 
	[
		require('image!zz_yuwen'),
		require('image!zz_shuxue'),
		require('image!zz_yingyu'),
		require('image!zz_deyu'),
		require('image!zz_zhuanye'),
		require('image!zz_it'),
		require('image!zz_jineng'),
	]

export default class ZZ_Result extends React.Component{

	constructor(props) {
	  	super(props);
		this.listData = [];
		this.ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
	  	this.state = {
			dataSource: this.ds.cloneWithRows(this.listData),
	  		isLoaded:false,
	  		isIndicatorShow:true,
	  		tips:'正在拼命查询中，请稍候...',
	  		userData:{},
	  	};
	}

	BUS_600101_CB(object,response){
		//加解密参数
		NativeBridge.NATIVE_getDecryptData(response._bodyText,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				console.log('BUS_600101_CB = '+events);
				var json = JSON.parse(events);
				if (json.retcode === '000000') {
					object.listData = json.doc;
					object.setState({
						isLoaded:true,
						isIndicatorShow:false,
						dataSource:object.ds.cloneWithRows(object.listData),
						userData:{
							sIdCard : json.sIdCard,
							sName: json.sName,
						},
					});
				}else{
					var error='请求失败，请稍后再试';
					if (json.retinfo) {
						error = json.retinfo;
					}
					object.setState({
						isIndicatorShow:false,
						tips:error,
					});
				}
			}	
		})
	}

	BUS_600001_REQ(){
		var dict = {
			sNum : this.props.sNum,
			sTicket : this.props.sTicket,
		};
		NativeBridge.NATIVE_getEncryptData(dict,(error,events)=>{
			if (error) {
				console.log(error);
			}else{
				events.encrypt='simple';
				netClientPostEncrypt(this,BUS_600101,this.BUS_600101_CB,events);
			}
		})  
	}

	componentDidMount() {
		this.BUS_600001_REQ();
	}


	renderRow(rowData,sectionID,rowID){
		var cheatShowName = '';
		switch(rowData.cheatType){
			case("0"):
				cheatShowName = '违纪';
				break;
			case("1"):
				cheatShowName = '作弊';
				break;
			case("2"):
				cheatShowName = '违规';
				break;
			case("3"):
				cheatShowName = '其他';
				break;
			default:
				break;
		}

		var isLevelChinese = false;
		if (rowData.KMLevel == '通过' || rowData.KMLevel == '不通过' ) {
			isLevelChinese = true;
		}
        return(
            <View>
            	{
            		rowData.cheatType
            		?
            		 <View style={{width:Dimensions.get('window').width/3,marginTop:30,alignItems:'center',justifyContent:'center'}}>
	            		<Image
							style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}
							source={require('image!zz_grey')}>
								<Text style={{fontSize:20,color:'white'}}>{cheatShowName}</Text>
						</Image>
						<Text style={{marginTop:10,fontSize:13,color:'#666666',alignSelf:'center'}}>{rowData.KMName}</Text>
					</View>
            		:
            		 <View style={{width:Dimensions.get('window').width/3,marginTop:30,alignItems:'center',justifyContent:'center'}}>
	            		<Image
							style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}
							source={PIC_LIST[rowID]||require('image!zz_yingyu')}>
								<Text style={isLevelChinese?{fontSize:15,color:'white'}:{fontSize:30,color:'white'}}>{rowData.KMLevel}</Text>
								{
									isLevelChinese
									?
									null
									:
									<Text style={{fontSize:9,color:'white'}}>等级</Text>
								}
						</Image>
						<Text style={{marginTop:10,fontSize:13,color:'#666666',alignSelf:'center'}}>{rowData.KMName}</Text>
					</View>
            	}
			</View>
        )
    }

	render(){
		return(
			<View style={{flex:1}}>
				<App_Title title={'中职学考成绩查询'} navigator={this.props.navigator}/>
				<ScrollView>
					{
						this.state.isLoaded
						?
						<View>
							<View style={{backgroundColor:'#7BBCFF',paddingLeft:15,paddingRight:15}}>
								<View style={{height:50,flexDirection:'row',alignItems:'center',justifyContent:'space-between'}}>
									<Text style={{color:'#ffffff',fontSize:14}}>姓名</Text>
									<Text style={{color:'#ffffff',fontSize:14}}>{this.state.userData.sName}</Text>
								</View>
								<View style={{height:0.5,backgroundColor:'#AFD6FF'}}/>
								<View style={{height:50,flexDirection:'row',alignItems:'center',justifyContent:'space-between'}}>
									<Text style={{color:'#ffffff',fontSize:14}}>考籍号</Text>
									<Text style={{color:'#ffffff',fontSize:14}}>{this.props.sNum}</Text>
								</View>
								<View style={{height:0.5,backgroundColor:'#AFD6FF'}}/>
								<View style={{height:50,flexDirection:'row',alignItems:'center',justifyContent:'space-between'}}>
									<Text style={{color:'#ffffff',fontSize:14}}>身份证号</Text>
									<Text style={{color:'#ffffff',fontSize:14}}>{this.state.userData.sIdCard}</Text>
								</View>
							</View>
							<Image 
								style={{width:Dimensions.get('window').width}}
								source={require('image!zz_cloud')}/>
							
							 <ListView
				                dataSource={this.state.dataSource}
				                renderRow={(rowData,sectionID,rowID) => this.renderRow(rowData,sectionID,rowID)} 
				                contentContainerStyle={styles.listStyle}
				                showsVerticalScrollIndicator={false}
				                showsHorizontalScrollIndicator={false}
				            />

						</View>
						:
						<View style={{marginTop:90,alignItems:'center'}}>
							<Image source={require('image!load_pic')}/>
							{
								this.state.isIndicatorShow
								?
								<ActivityIndicatorIOS
									style={{marginTop:10}}/>
								:
								null
							}
							<Text style={{fontSize:20,color:'#888888',marginTop:10}}>{this.state.tips}</Text>
						</View>
					}	
				</ScrollView>
			</View>

		)
	}


}

const styles = StyleSheet.create({
	listStyle:{
		flexDirection:'row', //改变ListView的主轴方向
        flexWrap:'wrap', //换行
	}
})