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
  TouchableHighlight,
  Text,
  View
} from 'react-native';
import App_Title from '../common/App_Title';
import PointSearch from './PointSearch';

    function checkTime(i)    
    {    
        if (i < 10) {    
            i = "0" + i;    
        }    
        return i;    
    }   

export default class PointWait extends React.Component{
	constructor(props){
		super(props);
		this.state={
			day:'--',
			hour:'--',
			minute:'--',
			second:'--',
		}
	}

	countDown(){
		this.timer = setTimeout(
			()=>{
				//var ts = (new Date(2016, 6, 1, 0, 0, 0)) - (new Date());//计算剩余的毫秒数  
				var ts = new Date('2016/06/01 00:00:00').getTime() - new Date().getTime();
                var dd = parseInt(ts / 1000 / 60 / 60 / 24);//计算剩余的天数  
                var hh = parseInt(ts / 1000 / 60 / 60 % 24);//计算剩余的小时数  
                var mm = parseInt(ts / 1000 / 60 % 60);//计算剩余的分钟数  
                var ss = parseInt(ts / 1000 % 60);//计算剩余的秒数  
                dd = checkTime(dd);  
                hh = checkTime(hh);  
                mm = checkTime(mm);  
                ss = checkTime(ss);  
                console.log(dd + "天" + hh + "时" + mm + "分" + ss + "秒")
                this.setState({
                	day:dd,
                	hour:hh,
                	minute:mm,
                	second:ss,
                });
				this.countDown();
		},1000);
	}

	componentDidMount() {
		this.countDown();
	}

	componentWillUnmount() {
	  	this.timer && clearTimeout(this.timer);
	}

	onSubmit(){
		this.props.navigator.push({
			component:PointSearch,
		});
	}

	render(){
		return(
			<View>
			<App_Title title={'高考查分'} navigator={this.props.navigator} />
			<View style={{flex:1,alignItems:'center'}}>
				<Text style={{fontSize:18,color:'#999999',marginTop:158}}>
					距离江苏省高考成绩发布还有
				</Text>
				<View style={{flexDirection:'row',marginTop:33,alignItems:'center'}}>
					<Image
					  style={{justifyContent:'center',alignItems:'center',backgroundColor:'transparent'}}
					  source={require('image!time_bg')} >
					  	<Text style={{fontSize:35,color:'#ff902d'}}>{this.state.day}</Text>
					</Image>
					<View style={{width:26,height:26,borderRadius:13,margin:4,backgroundColor:'#AFD7FF',justifyContent:'center',alignItems:'center'}}>
						<Text style={{fontSize:12,color:'#ffffff'}}>天</Text>
					</View>
					<Image
					  style={{justifyContent:'center',alignItems:'center',backgroundColor:'transparent'}}
					  source={require('image!time_bg')} >
					  	<Text style={{fontSize:35,color:'#ff902d'}}>{this.state.hour}</Text>
					</Image>
					<Image
					  style={{margin:6}}
					  source={require('image!time_point')} />
					<Image
					  style={{justifyContent:'center',alignItems:'center',backgroundColor:'transparent'}}
					  source={require('image!time_bg')} >
					  	<Text style={{fontSize:35,color:'#ff902d'}}>{this.state.minute}</Text>
					</Image>
					<Image
					  style={{margin:6}}
					  source={require('image!time_point')} />
					<Image
					  style={{justifyContent:'center',alignItems:'center',backgroundColor:'transparent'}}
					  source={require('image!time_bg')} >
					  	<Text style={{fontSize:35,color:'#ff902d'}}>{this.state.second}</Text>
					</Image>
				</View>

				<TouchableHighlight
					style={{marginTop:30,justifyContent:'center',alignItems:'center',
						backgroundColor:'#ff902d',height:45,borderRadius:3}}
					onPress={()=>this.onSubmit()}
					underlayColor='#fcfcfc'>
					<Text style={{fontSize:16,color:'white'}}>测试用，点击下一步</Text>
				</TouchableHighlight>
				</View>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


