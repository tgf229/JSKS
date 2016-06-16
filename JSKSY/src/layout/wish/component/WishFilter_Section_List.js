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
  Text,
  Image,
  ListView,
  View
} from 'react-native';

import App_Title from '../../common/App_Title';


var dataBlob = {};
var MAJOR_DATA = [{id:'0015',name:'文科试验班类'},{id:'00201',name:'经济管理试验班'},{id:'00201_1',name:'经济管理实验班'},
	{id:'00300',name:'法学试验班'},{id:'0060',name:'工科试验班类'},{id:'0070',name:'理科试验班类'},
	{id:'0080',name:'医学试验班类'},{id:'2009',name:'预科班'},{id:'5009',name:'预科班'},
	{id:'0101',name:'哲学类'},{id:'0201',name:'经济学类'},{id:'0202',name:'财政学类'},
	{id:'0203',name:'经济学类'},{id:'0204',name:'经济与贸易类'}
	,{id:'0301',name:'法学类'},{id:'0302',name:'政治学类'},{id:'0303',name:'社会学类'},{id:'0304',name:'民族学类'},{id:'0305',name:'马克思主义理论类'},{id:'0306',name:'公安学类'}
	,{id:'0401',name:'教育学类'},{id:'0402',name:'体育学类'}
	,{id:'0501',name:'中国语言文学类'},{id:'0502',name:'外国语言文学类'},{id:'0503',name:'新闻传播学类'}
	,{id:'0601',name:'历史学类'}
	,{id:'0701',name:'数学类'},{id:'0702',name:'物理学类'},{id:'0703',name:'化学类'},{id:'0704',name:'天文学类'},{id:'0705',name:'地理科学类'},{id:'0706',name:'大气科学类'},{id:'0707',name:'海洋科学类'},{id:'0708',name:'地球物理学类'},{id:'0709',name:'地质学类'},{id:'0710',name:'生物科学类'},{id:'0711',name:'心理学类'},{id:'0712',name:'统计学类'}
	,{id:'0801',name:'力学类'},{id:'0802',name:'机械类'},{id:'0803',name:'仪器类'},{id:'0804',name:'材料类'},{id:'0805',name:'能源动力类'},{id:'0806',name:'电气类'},{id:'0807',name:'电子信息类'},{id:'0808',name:'自动化类'},{id:'0809',name:'计算机类'},{id:'0810',name:'土木类'},{id:'0811',name:'水利类'},{id:'0812',name:'测绘类'},{id:'0813',name:'化工与制药类'},{id:'0814',name:'地质类'},{id:'0815',name:'矿业类'},{id:'0816',name:'纺织类'},{id:'0817',name:'轻工类'},{id:'0818',name:'交通运输类'},{id:'0819',name:'海洋工程类'},{id:'0820',name:'航空航天类'},{id:'0821',name:'兵器类'},{id:'0822',name:'核工程类'},{id:'0823',name:'农业工程类'},{id:'0824',name:'林业工程类'},{id:'0825',name:'环境科学与工程类'},{id:'0826',name:'生物医学工程类'},{id:'0827',name:'食品科学与工程类'},{id:'0828',name:'建筑类'},{id:'0829',name:'安全科学与工程类'},{id:'0830',name:'生物工程类'},{id:'0831',name:'公安技术类'}
	,{id:'0901',name:'植物生产类'},{id:'0902',name:'自然保护与环境生态类'},{id:'0903',name:'动物生产类'},{id:'0904',name:'动物医学类'},{id:'0905',name:'林学类'},{id:'0906',name:'水产类'},{id:'0907',name:'草学类'}
	,{id:'1001',name:'基础医学类'},{id:'1002',name:'临床医学类'},{id:'1003',name:'口腔医学类'},{id:'1004',name:'公共卫生与预防医学类'},{id:'1005',name:'中医学类'},{id:'1006',name:'中西医结合类'},{id:'1007',name:'药学类'},{id:'1008',name:'中药学类'},{id:'1009',name:'法医学类'},{id:'1010',name:'医学技术类'},{id:'1011',name:'护理学类'}
	,{id:'1201',name:'管理科学与工程类'},{id:'1202',name:'工商管理类'},{id:'1203',name:'农业经济管理类'},{id:'1204',name:'公共管理类'},{id:'1205',name:'图书情报与档案管理类'},{id:'1206',name:'物流管理与工程类'},{id:'1207',name:'工业工程类'},{id:'1208',name:'电子商务类'},{id:'1209',name:'旅游管理类'}
	,{id:'1301',name:'艺术学理论类'},{id:'1302',name:'音乐与舞蹈学类'},{id:'1303',name:'戏剧与影视学类'},{id:'1304',name:'美术学类'},{id:'1305',name:'设计学类'}
];
var sectionIDs = ['实验班','哲学','经济学','法学','教育学','文学','历史学','理学','工学','农学','医学','管理学','艺术学'];
var rowIDs = [];
rowIDs[0] = ['0015','00201','00201_1','00300','0060','0070','0080','2009','5009'];
rowIDs[1] = ['0101'];
rowIDs[2] = ['0201','0202','0203','0204'];
rowIDs[3] = ['0301','0302','0303','0304','0305','0306'];
rowIDs[4] = ['0401','0402'];
rowIDs[5] = ['0501','0502','0503'];
rowIDs[6] = ['0601'];
rowIDs[7] = ['0701','0702','0703','0704','0705','0706','0707','0708','0709','0710','0711','0712'];
rowIDs[8] = ['0801','0802','0803','0804','0805','0806','0807','0808','0809','0810','0811','0812','0813','0814','0815','0816','0817','0818','0819','0820','0821','0822','0823','0824','0825','0826','0827','0828','0829','0830','0831'];
rowIDs[9] = ['0901','0902','0903','0904','0905','0906','0907'];
rowIDs[10] = ['1001','1002','1003','1004','1005','1006','1007','1008','1010','0710','1011'];
rowIDs[11] = ['1201','1202','1203','1204','1205','1206','1207','1208','1209'];
rowIDs[12] = ['1301','1302','1303','1304','1305'];

var listData = {};
MAJOR_DATA.map(function(item){
	listData[item.id] = item.name;
});

var ds;
export default class WishFilter_Section_List extends React.Component{
	constructor(props){
		super(props);
		var getSectionData = (dataBlob, sectionID) => {
	      return sectionID;
	    };
	    var getRowData = (dataBlob, sectionID, rowID) => {
	      return rowID;
	    };

		ds = new ListView.DataSource({
			getRowData: getRowData,
      		getSectionHeaderData: getSectionData,
			rowHasChanged: (r1, r2) => r1 !== r2,
			sectionHeaderHasChanged: (s1, s2) => s1 !== s2,
		});
		this.state={
			dataSource: ds.cloneWithRowsAndSections(dataBlob, sectionIDs, rowIDs),
			selectedID: this.props.selectedID,
		};
	}

	//行点击
	rowPressed(rowData){
		this.setState({selectedID:rowData,dataSource: ds.cloneWithRowsAndSections(dataBlob, sectionIDs, rowIDs),})
		//回调父对象的state,重新渲染父对象
		this.props.filterObj.setState({marjorId:rowData,marjorVal:listData[rowData]});
		
		this.props.navigator.pop();
	}

	renderSectionHeader(sectionData, sectionID) {
	    return (
	    	<View style={{backgroundColor:'#f3f3f3'}}>
		    	<View style={{height:40,paddingLeft:15,justifyContent:'center'}}>
			    	<Text style={{color:'#666666'}}>
			          {sectionData}
			        </Text>
		    	</View>
		    	<View style={{height:0.5,backgroundColor:'#d5d5d5'}}/>
	    	</View>
	    );
	  }

	renderRow(rowData, sectionID, rowID){
	    return (
	    	<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='white'>
			    <View>
					  	{
					  		this.state.selectedID === rowData
					  			?
					  			<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:30,backgroundColor:'white'}}>
						  			<Text style={{fontSize:15,color:'#ff902d',fontWeight:'bold'}}>{listData[rowData]}</Text>
						  			<Image
						  	  			style={{position:'absolute',top:21,right:30}}
						  	  			source={require('image!state_select')} />
					  	  		</View>
					  	  		:
					  	  		<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:30,backgroundColor:'white'}}>
					  				<Text style={{fontSize:15,color:'#7192b5'}}>{listData[rowData]}</Text>
					  			</View>
					  	}
					<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				</View>
			</TouchableHighlight>
	    	);
	  }


	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
				<App_Title title={'筛选'} navigator={this.props.navigator}/>
				<ListView
				  dataSource={this.state.dataSource}
				  pageSize={10}
				  renderSectionHeader={(sectionData)=>this.renderSectionHeader(sectionData)}
				  renderRow={(rowData) => this.renderRow(rowData)} />
			 </View>
		)
	}
}

const styles = StyleSheet.create({

});


