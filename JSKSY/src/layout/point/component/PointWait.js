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
  ScrollView,
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
		var formatedDate = dateString.replace(pattern, '$1/$2/$3 $4:$5:$6');
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
	    return d.getFullYear()+"/"
	    +checkTime(d.getMonth()+1)
	    +"/"+checkTime(d.getDate())
	    +" "+checkTime(d.getHours())
	    +":"+checkTime(d.getMinutes())
	    +":"+checkTime(d.getSeconds());
	}

var currentTime;
var examTime;
export default class PointWait extends React.Component{
	constructor(props){
		super(props);

		currentTime = dateCon(formatStrToDate(this.props.cuTime),1);
		examTime = formatStrToDate(this.props.exTime);
		//测试用＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		// currentTime = '2016/04/26 11:36:01';
		// examTime = '2016/04/26 11:36:03';
		
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
                // console.log(dd + "天" + hh + "时" + mm + "分" + ss + "秒")
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
			<View style={{flex:1,backgroundColor:'white'}}>
				<ScrollView>
					<Text style={{fontSize:18,color:'#999999',marginTop:158,textAlign:'center'}}>
						{this.props.content}
					</Text>
					<View style={{flexDirection:'row',marginTop:33,alignItems:'center',justifyContent:'center'}}>
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

					{
						this.props.flag === '1'?
						<View>
							<Text style={{paddingLeft:20,paddingRight:20,marginTop:20,fontSize:15,color:'#666666',lineHeight:22}}>    志愿参考服务结合考生当年高考成绩及所在科类全省位次、近四年高考录取数据、本年度各院校省内招生计划，并按照院校所在省份、专业、批次等附加条件检索出志愿参考院校及所含专业信息。本服务不仅是考生填报普通高校的参考资料，同时对家长、招生工作人员和中学教师也具有一定的参考价值。

							</Text>
							<View
								style={{flexDirection:'row',marginTop:10,paddingLeft:20,paddingRight:20}}>
								<Text style={{width:35,fontSize:13,color:'#d0021b',lineHeight:23}}>PS：</Text>
								<Text style={{flex:1,fontSize:13,color:'#d0021b',lineHeight:22}}>本服务只针对进入文理科第一阶段的考生开放；{'\n'}院校数据仅包含文理科第一批次及第二批次院校；{'\n'}本服务为测试版。由于时间仓促，内容繁多，难免有疏漏、不当之处，恳请用户批评指正。</Text>
							</View>
						</View>
						:
						null
					}
				</ScrollView>
			</View>
		)
	}
}

const styles = StyleSheet.create({

});


