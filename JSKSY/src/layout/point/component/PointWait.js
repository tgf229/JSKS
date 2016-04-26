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

    function checkTime(i)    
    {    
        if (i < 10) {    
            i = "0" + i;    
        }    
        return i;    
    }  

    function formatStrToDate(dateString) {
		var pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
		var formatedDate = dateString.replace(pattern, '$1-$2-$3 $4:$5:$6');
		return formatedDate;
     } 

     function dateCon(d,num){
	    var d = new Date(d.substring(0,4),
	    d.substring(5,7)-1,
	    d.substring(8,10),
	    d.substring(11,13),
	    d.substring(14,16),
	    d.substring(17,19));
	    d.setTime(d.getTime()+num*1000);
	    // console.log(d.toLocaleString());
	    return d.getFullYear()+"-"
	    +checkTime(d.getMonth()+1)
	    +"-"+checkTime(d.getDate())
	    +" "+checkTime(d.getHours())
	    +":"+checkTime(d.getMinutes())
	    +":"+checkTime(d.getSeconds());
	}

var currentTime;
var examTime;
export default class PointWait extends React.Component{
	constructor(props){
		super(props);

		//测试用＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		currentTime = '2016-04-26 11:36:01';
		examTime = '2016-04-26 11:36:05';
		// currentTime = dateCon(formatStrToDate(this.props.cuTime),1);
		// examTime = formatStrToDate(this.props.exTime);
		
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
				currentTime = dateCon(currentTime,1);

				if (currentTime >= examTime) {
					this.timer && clearTimeout(this.timer);
					this.props.searchObj.setState({
						isPointSearchOpen:true,
					});
					return;
				}

				var ts = new Date(examTime).getTime() - new Date(currentTime).getTime();
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

	render(){
		return(
			<View style={{flex:1,alignItems:'center',backgroundColor:'white'}}>
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
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


