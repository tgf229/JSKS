import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  ActivityIndicatorIOS,
  Platform
} from 'react-native';

import Echarts from 'native-echarts';
import Dimensions from 'Dimensions';

export default class University_Chart extends Component {

  constructor(props) {
      super(props);
      this.state = {
        lScore:[200, 210, 220, 230],
        hScore: [300, 240, 200, 330],
        isLoaded:true
      }
  }

  render() {
    const option = {
          //点击某一个点的数据的时候，显示出悬浮窗
          tooltip : {
              trigger: 'axis'
          },
          xAxis : [
              {
                  //就是一月份这个显示为一个线段，而不是数轴那种一个点点
                  boundaryGap:true,
                  type : 'category',
                  // name : '时间',
                  data : ['2013年','2014年','2015年','2016年']
              }
          ],
          yAxis : [
              {
                  type : 'value',
                  // name : '销量(kg)'
              }
          ],
          //图形的颜色组
          color:['#fc7655','#8ededc'],
          //需要显示的图形名称，类型，以及数据设置
          series : [
              {
                  name:'录取最低分',
                  //默认显
                  type:'line',
                  data:this.state.lScore
              },
              {
                  name:'录取最高分',
                  type:'line',
                  data:this.state.hScore
              }
          ]
        };
  return (
    	<View style={styles.container}>
    		{
    			this.state.isLoaded
    			?
				<Echarts option={option} height={300} width={global.windowWidth}/>
				:
				<ActivityIndicatorIOS  style={{marginVertical: 30,marginBottom: 30}} />
    		}
      	</View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex:1,
  },
});