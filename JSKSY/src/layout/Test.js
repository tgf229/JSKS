/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  TouchableHighlight,
  ListView,
  ActivityIndicatorIOS,
  PixelRatio,
  Image,
  View
} from 'react-native';
import Header from '../component/Header'
import { urlForQueryAndPage, netClient } from '../util/NetUtil';
import GiftedListView from 'react-native-gifted-listview';
import RefreshableListView from 'react-native-refreshable-listview';

var ds;
var PAGE_NUM = 1;
var listData = [];
var isLoadEnd = false;

export default class List extends React.Component{

	constructor(props){
		super(props);
		ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1.guid !== r2.guid});
		this.state={
			dataSource: ds.cloneWithRows(listData),
			isFirstLoading:true,
		};
	}

	onFetch(page = 1, callback, options){
		return new Promise((resolve, reject) => {
	      	//封装链接地址
			var query = urlForQueryAndPage('place_name','london',PAGE_NUM);
			//调用接口
			netClient(this,query);
	    });
        
    }

	componentDidMount() {
		// //封装链接地址
		// var query = urlForQueryAndPage('place_name','london',PAGE_NUM);
		// //调用接口
		// netClient(this,query);
	}

	busCB(response){
		if (response.application_response_code.substr(0,1) === '1') {
			listData = listData.concat(response.listings);
			this.setState({isFirstLoading:false,dataSource:ds.cloneWithRows(listData)});
			console.log('成功');
		}else{
			isLoadEnd = true;
			console.log('失败');
		}
	}

	rowPressed(rowData){
		console.log(rowData.title);
	}

	renderRow(rowData,sectionID,rowID){
		var price = rowData.price_formatted.split(' ')[0];
		return(
			<TouchableHighlight
				onPress={()=>this.rowPressed(rowData)}
			    underlayColor='#dddddd'>
			  	<View>
			  		<View style={{flexDirection:'row',padding:10}}>
			  			<Image
			  			  style={{width:PixelRatio.get() * 30,height:PixelRatio.get() * 30}}
			  			  source={{uri: rowData.img_url}} />
			  			<View style={{flex:1,marginLeft:10}}>
			  				<Text style={styles.price}>{price}</Text>
			  				<Text style={styles.title} numberOfLines={1}>{rowData.title}</Text>
			  			</View>
			  		</View>
			  		<View style={{height:1,backgroundColor:'#dddddd'}}></View>
			  	</View>
			</TouchableHighlight>
		)
	}

	onEndReached() {
		console.log('到达底部!')
	 	if (isLoadEnd) {
	 		return;
	 	}

		PAGE_NUM = PAGE_NUM+1;
		console.log('PAGE_NUM= '+PAGE_NUM);
		//封装链接地址
		var query = urlForQueryAndPage('place_name','london',PAGE_NUM);
		//调用接口
		netClient(this,query);
	    
	}

	renderHeader(){
		return(
			<Header></Header>
		)
	}

	renderFooter() {
	    return <ActivityIndicatorIOS  style={{marginVertical: 30,marginBottom: 30}} />;
	}

	render(){
		return(
			<View style={{flex:1,marginTop:64}}>
				<GiftedListView
						dataSource={this.state.dataSource}
						onEndReached={()=>this.onEndReached()}
						pageSize={10}
						onEndReachedThreshold={100}
						automaticallyAdjustContentInsets={false}
		        		showsVerticalScrollIndicator={false}
		        		renderHeader={()=>this.renderHeader()}
		        		renderFooter={() => this.renderFooter()}
						renderRow={(rowData) => this.renderRow(rowData)} 

						// loadData={()=>this.onRefresh()}
						// refreshDescription="Refreshing articles"
						onFetch={()=>this.onFetch()}
			          	refreshable={true} // enable pull-to-refresh for iOS and touch-to-refresh for Android


						/>
				
			</View>
		)
	}
}

const styles = StyleSheet.create({
	price:{
		fontSize:25,
		fontWeight:'bold',
		color:'#48BBEC',
	},
	title:{
		fontSize:20,
		color:'#656565',
	},
});

var customStyles = StyleSheet.create({
  separator: {
    height: 1,
    backgroundColor: '#CCC'
  },
  refreshableView: {
    height: 50,
    backgroundColor: '#fff',
    justifyContent: 'center',
    alignItems: 'center',
  },
  actionsLabel: {
    fontSize: 20,
    color: '#007aff',
  },
  paginationView: {
    height: 44,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFF',
  },
  defaultView: {
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  defaultViewTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 15,
  },
  row: {
    padding: 10,
    height: 44,
  },
  header: {
    backgroundColor: '#50a4ff',
    padding: 10,
  },
  headerTitle: {
    color: '#fff',
  },
});


