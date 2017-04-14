'use strict';
import React,{Component} from 'react';
import {
	AppRegistry,
	StyleSheet,
	View,
	ScrollView,
	TouchableOpacity,
	PixelRatio,
	ProgressViewIOS,
	Text,
	Image} from 'react-native';

import {BUS_700201,netClientTest,ERROR_TIPS,REQ_TIPS} from '../../util/NetUtil';

export default class University_Detail_One extends Component{

	constructor(props) {
	  	super(props);
	  	this.state = {
	  	};
	}

	render(){
		const a = 50;
		const b = 50;
		return(
			<View>
				<View style={{flexDirection:'row',alignItems:'center',justifyContent:'space-between',paddingTop:18,paddingBottom:18,paddingLeft:30,paddingRight:30}}>
					<View style={{alignItems:'center'}}>
						<Image source={require('image!icon_boy')}/>
						<Text style={{fontSize:9,color:'#67aef7',fontWeight:'bold',marginTop:7}}>BOY</Text>
					</View>
					<View style={{flex:1}}>
						<Text style={{alignSelf:'center',color:'#505977',fontSize:12}}>男女生比例</Text>
						<View style={{flexDirection:'row',marginLeft:15,marginRight:15,marginTop:6,marginBottom:6}}>
							<View style={{flex:a,height:5,backgroundColor:'#73b8ff'}}/>
							<View style={{flex:b,height:5,backgroundColor:'#ff6e4b'}}/>
						</View>
						<Text style={{alignSelf:'center',color:'#505977',fontSize:12}}>{a}:{b}</Text>
					</View>
					<View style={{alignItems:'center'}}>
						<Image source={require('image!icon_girl')}/>
						<Text style={{fontSize:9,color:'#ff6e4b',fontWeight:'bold',marginTop:7}}>GIRL</Text>
					</View>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<View style={{padding:15,flexDirection:'row'}}>
					<View style={{flex:1}}>
						<Text style={{color:'#777777',fontSize:12,fontWeight:'bold'}}>类型：财经院校</Text>
						<Text style={{color:'#777777',fontSize:12,fontWeight:'bold',marginTop:6}}>电话：400802820</Text>
						<Text ref="test" style={{color:'#777777',fontSize:12,fontWeight:'bold',marginTop:6}}>地址：南京市汉口路22号南京大学学生工作处</Text>
					</View>
					<View style={{width:64,alignItems:'flex-end'}}>
						<Image source={require('image!btn_call')}/>
					</View>
				</View>
				<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
				<Text ref="test" style={{margin:15,color:'#777777',fontSize:12,fontWeight:'bold'}}>复旦大学（Fudan University），简称“复旦”，位于上海市，由中华人民共和国教育部直属，中央直管副部级建制，位列“211工程”、“985工程”，入选“珠峰计划”、“111计划”、“2011计划”、“卓越医生教育培养计划”，为“九校联盟”成员、中国大学校长联谊会成员、东亚研究型大学协会成员、环太平洋大学协会成员、21世纪大学协会成员，是一所综合性研究型的全国重点大学。[1] 
复旦大学创建于1905年，原名复旦公学，是中国人自主创办的第一所高等院校，创始人为中国近代知名教育家马相伯，首任校董为国父孙中山。校名“复旦”二字选自《尚书大传·虞夏传》名句“日月光华，旦复旦兮”，意在自强不息，寄托当时中国知识分子自主办学、教育强国的希望。1917年复旦公学改名为私立复旦大学；1937年抗战爆发后，学校内迁重庆北碚，并于1941年改为“国立”；1946年迁回上海江湾原址；1952年全国高等学校院系调整后，复旦大学成为以文理科为基础的综合性大学；1959年成为全国重点大学。2000年，原复旦大学与原上海医科大学合并成新的复旦大学。
复旦师生谨记“博学而笃志，切问而近思”的校训，严守“文明、健康、团结、奋发”的校风，力行“刻苦、严谨、求实、创新”的学风，发扬“爱国奉献、学术独立、海纳百川、追求卓越”的复旦精神，以服务国家为己任，以培养人才为根本，以改革开放为动力，为实现中国梦作出新贡献。[2] 


				</Text>

			</View>
		);
	}
}

const styles = StyleSheet.create({

})