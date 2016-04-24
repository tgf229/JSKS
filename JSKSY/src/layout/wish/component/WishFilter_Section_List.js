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
];
var sectionIDs = ['实验班','哲学','经济学'];
var rowIDs = [];
rowIDs[0] = ['0015','00201','00201_1','00300','0060','0070','0080','2009','5009'];
rowIDs[1] = ['0101'];
rowIDs[2] = ['0201','0202','0203','0204'];

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


