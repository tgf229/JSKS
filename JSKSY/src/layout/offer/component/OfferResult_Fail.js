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

export default class OfferResult_Fail extends React.Component{
	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			  	<View>
			  		<View style={{backgroundColor:'#479cf4',flexDirection:'row',height:45,alignItems:'center',justifyContent:'center'}}>
						<Image source={require('image!offer_icon_user')}/>
						<Text style={{marginLeft:10,fontSize:12,color:'white'}}>{this.props.data.sName}    {this.props.sNum}</Text>
					</View>
					<View style={{height:200,flexDirection:'row',justifyContent:'center',alignItems:'center'}}>
						<Image
				    		source={require('image!icon_wait')} />
				    		<View>
								<Text style={{marginLeft:10,fontSize:16,color:'#999999'}}>暂未查询到录取结果</Text>
				    		</View>
				    		
					</View>
			  		{
						this.props.data.doc
						  ?
						  this.props.data.doc.map(function(item){
							return(
								<View>
									<View style={{backgroundColor:'#f0f0f0',height:10}}/>
									<View style={{flexDirection:'row',justifyContent:'space-between',alignItems:'center',height:30,paddingLeft:15,paddingRight:15}}>
										<Text style={{fontSize:11,color:'#444444'}}>退档信息</Text>
										<Text style={{fontSize:11,color:'#444444'}}>{item.time}</Text>
									</View>
									<View style={{backgroundColor:'#d5d5d5',height:1}}/>
									<View style={{flexDirection:'row'}}>
										<View style={{flex:1,paddingTop:15,paddingBottom:15}}>
											<Text style={{textAlign:'center',fontSize:9,color:'#cecece'}}>退档院校</Text>
											<Text style={{textAlign:'center',marginTop:6,fontSize:10,color:'#444444'}}>{item.schoolName}</Text>
										</View>
										<View style={{backgroundColor:'#d5d5d5',height:50,width:0.5,alignSelf:'center'}}/>
										<View style={{flex:1,paddingTop:15,paddingBottom:15}}>
											<Text style={{textAlign:'center',fontSize:9,color:'#cecece'}}>批次</Text>
											<Text style={{textAlign:'center',marginTop:6,fontSize:10,color:'#444444'}}>{item.batch}</Text>
										</View>
										<View style={{backgroundColor:'#d5d5d5',height:50,width:0.5,alignSelf:'center'}}/>
										<View style={{flex:1,paddingTop:15,paddingBottom:15}}>
											<Text style={{textAlign:'center',fontSize:9,color:'#cecece'}}>科类</Text>
											<Text style={{textAlign:'center',marginTop:6,fontSize:10,color:'#444444'}}>{item.clazz}</Text>
										</View>
										<View style={{backgroundColor:'#d5d5d5',height:50,width:0.5,alignSelf:'center'}}/>
										<View style={{flex:1.5,paddingTop:15,paddingBottom:15}}>
											<Text style={{textAlign:'center',fontSize:9,color:'#cecece'}}>院校退档原因</Text>
											<Text style={{textAlign:'center',marginTop:6,fontSize:10,color:'#444444'}}>{item.reason}</Text>
										</View>
									</View>
									<View style={{backgroundColor:'#d5d5d5',height:1}}/>
									<View style={{paddingTop:10,paddingBottom:10,paddingLeft:15,paddingRight:15}}>
										<Text style={{fontSize:10,color:'#999999'}}>备注：{item.ps}</Text>
									</View>
								</View>
							)
						})
						:
						null
					}
			   </View>
		)
	}
}

const styles = StyleSheet.create({
	contentContainer:{
		backgroundColor:'white',
	},
});


