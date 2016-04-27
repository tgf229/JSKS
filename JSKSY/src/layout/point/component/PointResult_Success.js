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
  Image,
  Dimensions,
  Text,
  View
} from 'react-native';

var totalTitle; 
var totalPoint;
var chineseTitle;
var mathTitle;

var KM4Pic;
var KM5Pic;
var isKMshow; //科目是否隐藏，当type为 3或4是 隐藏为false

//奖励类分的title和分值 和图片分类
var addTitle;
var addPoint;
var addPic;

var isSingle; //是单项true（1 2 3 4）  还是双向false（type 5 6 7 8）

export default class PointResult_Success extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}

		chineseTitle='语文';
		mathTitle='数学';
		isKMshow=true;
		addPic = <Image source={require('image!point_icon_A_add')} />;
		isSingle = true;

		//文科
		if (this.props.data.type === '1') {
			totalTitle = '文科类总分';
			totalPoint = this.props.data.chTotal;
			chineseTitle = '语文＋附加分';
			addTitle = '文理类奖励分';
			addPoint = this.props.data.cmAdd;
		}
		//理科
		else if(this.props.data.type === '2'){
			totalTitle = '理科类总分';
			totalPoint = this.props.data.maTotal;
			mathTitle = '数学＋附加分';
			addTitle = '文理类奖励分';
			addPoint = this.props.data.cmAdd;
		}
		//艺术  体育
		else if(this.props.data.type === '3' || this.props.data.type === '4'){
			totalTitle = '体艺文化总分';
			totalPoint = this.props.data.saTotal;
			addTitle = '体艺类奖励分';
			addPoint = this.props.data.saAdd;
			addPic = <Image source={require('image!point_icon_B_add')} />;
			isKMshow = false;
		}
		//艺术兼文 体育兼文
		else if(this.props.data.type === '5' || this.props.data.type === '7' ){
			isSingle = false;
			totalTitle = '文科类总分';
			totalPoint = this.props.data.chTotal;
			chineseTitle = '语文＋附加分';
		}
		//艺术兼理 体育兼理
		else if(this.props.data.type === '6' || this.props.data.type === '8' ){
			isSingle = false;
			totalTitle = '理科类总分';
			totalPoint = this.props.data.maTotal;
			mathTitle = '数学＋附加分';
		}

		if (this.props.data.KM4Name === '历史') {
			KM4Pic = <Image source={require('image!point_icon_lishi')} />
		}else if(this.props.data.KM4Name === '政治') {
			KM4Pic = <Image source={require('image!point_icon_zhengzhi')} />
		}
		else if(this.props.data.KM4Name === '地理') {
			KM4Pic = <Image source={require('image!point_icon_dili')} />
		}
		else if(this.props.data.KM4Name === '化学') {
			KM4Pic = <Image source={require('image!point_icon_huaxue')} />
		}
		else if(this.props.data.KM4Name === '生物') {
			KM4Pic = <Image source={require('image!point_icon_shengwu')} />
		}
		else if(this.props.data.KM4Name === '物理') {
			KM4Pic = <Image source={require('image!point_icon_wuli')} />
		}else{
			KM4Pic = <Image source={require('image!point_icon_A_add')} />
		}

		if (this.props.data.KM5Name === '历史') {
			KM5Pic = <Image source={require('image!point_icon_lishi')} />
		}else if(this.props.data.KM5Name === '政治') {
			KM5Pic = <Image source={require('image!point_icon_zhengzhi')} />
		}
		else if(this.props.data.KM5Name === '地理') {
			KM5Pic = <Image source={require('image!point_icon_dili')} />
		}
		else if(this.props.data.KM5Name === '化学') {
			KM5Pic = <Image source={require('image!point_icon_huaxue')} />
		}
		else if(this.props.data.KM5Name === '生物') {
			KM5Pic = <Image source={require('image!point_icon_shengwu')} />
		}
		else if(this.props.data.KM5Name === '物理') {
			KM5Pic = <Image source={require('image!point_icon_wuli')} />
		}else{
			KM5Pic = <Image source={require('image!point_icon_A_add')} />
		}
	}

	render(){
		
		return(
			<View>
				<Image
					style={{width:Dimensions.get('window').width,height:Dimensions.get('window').width*0.6}}
				  	source={require('image!point_result_top')} >
				  	<View style={{position:'absolute',left:(Dimensions.get('window').width-288)/2,bottom:10,alignItems:'center',justifyContent:'center',backgroundColor:'#1a7eb2',width:288,height:28,borderRadius:20}}>
					  	<Text style={{fontSize:12,color:'white'}}>姓名：xxxx      考生号：xxxxxxxxx</Text>
					</View>
				</Image>
				<Image
				  style={{alignItems:'center',justifyContent:'center',width:Dimensions.get('window').width,height:Dimensions.get('window').width*0.8}}
				  source={require('image!point_result_bg')} >
				  	<View style={{alignItems:'center'}}>
				  		{
				  			isSingle
				  				?
					  			<Image
									style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}
						   			source={require('image!point_result_total_big')} >
						   			<Text style={{fontSize:11,color:'#ae582e'}}>{totalTitle}</Text>
						   			<Text style={{fontSize:34,color:'white'}}>{totalPoint}<Text style={{fontSize:10}}>分</Text></Text>
						   		</Image>
						   		:
						   		<View style={{flexDirection:'row'}}>
						   			<Image
										style={{alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}
							   			source={require('image!point_result_total_small')} >
							   			<Text style={{fontSize:11,color:'#ae582e'}}>体艺文化总分</Text>
							   			<Text style={{fontSize:34,color:'white'}}>{this.props.data.saTotal}<Text style={{fontSize:10}}>分</Text></Text>
							   		</Image>
							   		<Image
										style={{marginLeft:3,alignItems:'center',justifyContent:'center',backgroundColor:'transparent'}}
							   			source={require('image!point_result_total_small')} >
							   			<Text style={{fontSize:11,color:'#ae582e'}}>{totalTitle}</Text>
							   			<Text style={{fontSize:34,color:'white'}}>{totalPoint}<Text style={{fontSize:10}}>分</Text></Text>
							   		</Image>
						   		</View>
				  		}
						{
							this.props.data.totalName
								?
								<Image
						   			style={{alignItems:'center',justifyContent:'center',marginTop:-40,backgroundColor:'transparent'}}
						   			source={require('image!point_result_rank')} >
						   			<Text style={{fontSize:14,color:'white',fontWeight:'bold'}}>{this.props.data.totalName}：{this.props.data.totalLevel}名</Text>
						   		</Image>
						   		:
						   		null
						}
				   		
				  	</View>
				</Image>

				<View style={{flexDirection:'row',marginTop:17,paddingTop:16,paddingBottom:16}}>
		{/* 语文 */}
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:17,color:'#333333',fontWeight:'bold'}}>{this.props.data.chinese}<Text style={{fontSize:9}}>分</Text>
							{
								this.props.data.chAdd ? <Text>+{this.props.data.chAdd}<Text style={{fontSize:9}}>分</Text></Text>
													:
													null
							}
						</Text>
						<Image
						  style={{marginTop:10}}
						  source={require('image!point_icon_yuwen')} />
						<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:10}}>{chineseTitle}</Text>
					</View>
		{/* 数学 */}
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:17,color:'#333333',fontWeight:'bold'}}>{this.props.data.math}<Text style={{fontSize:9}}>分</Text>
							{
								this.props.data.maAdd ? <Text>+{this.props.data.maAdd}<Text style={{fontSize:9}}>分</Text></Text>
													:
													null
							}
						</Text>
						<Image
						  style={{marginTop:10}}
						  source={require('image!point_icon_shuxue')} />
						<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:10}}>{mathTitle}</Text>
					</View>
		{/* 英语 */}
					<View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
						<Text style={{fontSize:17,color:'#333333',fontWeight:'bold'}}>{this.props.data.english}<Text style={{fontSize:9}}>分</Text></Text>
						<Image
						  style={{marginTop:10}}
						  source={require('image!point_icon_yingyu')} />
						<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:10}}>外语</Text>
					</View>
				</View>

				<View style={{backgroundColor:'#d5d5d5',height:0.5,width:Dimensions.get('window').width-60,marginLeft:30}}></View>

				{
					isKMshow
						?
							<View style={{flexDirection:'row'}}>
					{/* 科目四 */}
								<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-140,alignItems:'center',paddingTop:16,paddingBottom:16}}>
									{KM4Pic}
									<View style={{marginLeft:10}}>
										<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>{this.props.data.KM4Level}</Text>
										<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>{this.props.data.KM4Name}</Text>
									</View>
								</View>
					{/* 科目五 */}
								<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-180,alignItems:'center',paddingTop:16,paddingBottom:16}}>
									{KM5Pic}
									<View style={{marginLeft:10}}>
										<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>{this.props.data.KM5Level}</Text>
										<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>{this.props.data.KM5Name}</Text>
									</View>
								</View>
							</View>
						:
						null
				}
				
				<View style={{flexDirection:'row'}}>
		{/* 政策性照顾分 */}
					<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-140,alignItems:'center',paddingTop:16,paddingBottom:16}}>
						<Image
						  	source={require('image!point_icon_policy')} />
						<View style={{marginLeft:10}}>
							<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>{this.props.data.poAdd}<Text style={{fontSize:9}}>分</Text></Text>
							<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>政策性照顾分</Text>
						</View>
					</View>
		{/* 奖励分（文理类奖励分  体艺类奖励分） */}
					<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-180,alignItems:'center',paddingTop:16,paddingBottom:16}}>
						{addPic}
							{
								isSingle
									?
									<View style={{marginLeft:10}}>
										<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>{addPoint}<Text style={{fontSize:9}}>分</Text></Text>
										<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>{addTitle}</Text>
									</View>
									:
									<View style={{marginLeft:10}}>
										<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>{this.props.data.cmAdd}<Text style={{fontSize:9}}>分</Text></Text>
										<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>文理类奖励分</Text>
									</View>
							}
					</View>
				</View>

				{
				isSingle
					? null
					:
					<View>
		{/* 双向成绩 的 政策性照顾分 */}
						<View style={{flex:1,flexDirection:'row',marginLeft:Dimensions.get('window').width/2-140,alignItems:'center',paddingTop:16,paddingBottom:16}}>
							<Image source={require('image!point_icon_B_add')} />
							<View style={{marginLeft:10}}>
								<Text style={{fontSize:17,fontWeight:'bold',color:'#333333'}}>{this.props.data.saAdd}<Text style={{fontSize:9}}>分</Text></Text>
								<Text style={{fontSize:11,fontWeight:'bold',color:'#999999',marginTop:2}}>体艺类奖励分</Text>
							</View>
						</View>
					</View>
				}

				<Text style={{textAlign:'center',marginBottom:10,fontSize:8,color:'#666666'}}>数据来源 BY 江苏省教育考试院</Text>

			</View>
		)
	}
}

const styles = StyleSheet.create({

});


