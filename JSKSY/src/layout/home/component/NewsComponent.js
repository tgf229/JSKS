'use strict';
import React, {
  AppRegistry,
  Component,
  Image,
  Dimensions,
  Text,
  TouchableOpacity,
  StyleSheet,
  View
} from 'react-native';

export default class NewsComponent extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}


	render(){
		const rowData = this.props.rowData;
		switch(rowData.type){
			case '1':
				return(
					<TouchableOpacity
						onPress={()=>this.rowPressed(rowData)}>
					  	<View>
					  		<View style={{padding:14,flexDirection:'row'}}>
					  			<Image
						  			style={{width:80, height:80,borderRadius:5}}
						 			source={{uri: rowData.imageUrl}} />
						 		<View style={{marginLeft:10,justifyContent:'center',height:80,width:Dimensions.get('window').width-28-80-10,}}>
						  			<Text style={styles.title} numberOfLines={2}>{rowData.name}</Text>
							 		<Text style={styles.time}>{rowData.time}</Text>
						 		</View>
				  			</View>
					  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
					  	</View>
					</TouchableOpacity>
				)
			break;
			case '2':
				return(
					<TouchableOpacity
						onPress={()=>this.rowPressed(rowData)}>
					  	<View>
						  	<View style={{height:84,paddingTop:12,paddingBottom:12,paddingLeft:15,paddingRight:15,justifyContent:'center'}}>
						  		<Text style={styles.title} numberOfLines={2}>{rowData.name}</Text>
						  		<Text style={styles.time}>{rowData.time}</Text>
						  	</View>
					  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
					  	</View>
					</TouchableOpacity>
				)
			break;
			case '3':
				return(
					<TouchableOpacity
						onPress={()=>this.rowPressed(rowData)}>
					  	<View>
					  		<View style={{padding:14}}>
					  			<Text style={styles.title} numberOfLines={1}>{rowData.name}</Text>
					  			<View style={{flexDirection:'row',marginTop:7,justifyContent:'space-between'}}>
					 				<Image
						  				style={{width:(Dimensions.get('window').width-48)/3, height:(Dimensions.get('window').width-48)/3*9/11,borderRadius:5}}
						 				source={{uri: rowData.imageUrl}} />
						 			<Image
						  				style={{width:(Dimensions.get('window').width-48)/3, height:(Dimensions.get('window').width-48)/3*9/11,borderRadius:5}}
						 				source={{uri: rowData.imageUrl}} />
						 			<Image
						  				style={{width:(Dimensions.get('window').width-48)/3, height:(Dimensions.get('window').width-48)/3*9/11,borderRadius:5}}
						 				source={{uri: rowData.imageUrl}} />
					 			</View>
						 		<Text style={styles.time}>{rowData.time}</Text>
				  			</View>
					  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
					  	</View>
					</TouchableOpacity>
				)
			break;
			default:
				return(
					<TouchableOpacity
						onPress={()=>this.rowPressed(rowData)}>
					  	<View>
					  		<View style={{padding:14}}>
					  			<Text style={styles.title} numberOfLines={1}>{rowData.name}</Text>
					 			<Image
						  			 style={{width:Dimensions.get('window').width-28, height:(Dimensions.get('window').width-28)*2/7,marginTop:7,borderRadius:5}}
						 			 source={{uri: rowData.imageUrl}} />
						 		<Text style={styles.time}>{rowData.time}</Text>
				  			</View>
					  		<View style={{height:0.5,backgroundColor:'#d5d5d5'}}></View>
					  	</View>
					</TouchableOpacity>
				)
			break;
		}
	}
}

const styles = StyleSheet.create({
	time:{
		fontSize:10,
		color:'#999999',
		marginTop:7,
	},
	title:{
		fontSize:14,
		lineHeight:20,
		color:'#444444',
	},
});
