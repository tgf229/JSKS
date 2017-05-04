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

var ds;
export default class WishFilter_List extends React.Component{
	constructor(props){
		super(props);
		ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
		this.state={
			dataSource: ds.cloneWithRows(this.props.listData),
			selectedID:this.props.selectedID,
		};
	}

	//行点击
	rowPressed(rowData){
		this.setState({selectedID:rowData.id,dataSource: ds.cloneWithRows(this.props.listData),})
		if (this.props.type === 1) {
			//回调父对象的state,重新渲染父对象
			this.props.filterObj.setState({typeId:rowData.id,typeVal:rowData.name});
		}else if (this.props.type === 2) {
			//回调父对象的state,重新渲染父对象
			this.props.filterObj.setState({provId:rowData.id,provVal:rowData.name});
		}

		this.props.navigator.pop();
	}

	//渲染cell
	renderRow(rowData,sectionID,rowID){
		return(
			<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='white'>
			    <View>
					  	{
					  		this.state.selectedID === rowData.id
					  			?
					  			<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:30,backgroundColor:'white'}}>
						  			<Text style={{fontSize:15,color:'#ff902d',fontWeight:'bold'}}>{rowData.name}</Text>
						  			<Image
						  	  			style={{position:'absolute',top:21,right:30}}
						  	  			source={require('image!state_select')} />
					  	  		</View>
					  	  		:
					  	  		<View style={{height:55,flexDirection:'row',alignItems:'center',paddingLeft:30,backgroundColor:'white'}}>
					  				<Text style={{fontSize:15,color:'#7192b5'}}>{rowData.name}</Text>
					  			</View>
					  	}
					<View style={{height:0.5,backgroundColor:'#d5d5d5',marginLeft:15}}/>
				</View>
			</TouchableHighlight>
		)
	}

	render(){
		return(
			<View style={{flex:1,backgroundColor:'white',}}>
				<App_Title title={'筛选'} navigator={this.props.navigator}/>
				<ListView
				  dataSource={this.state.dataSource}
				  renderRow={(rowData) => this.renderRow(rowData)} />
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


