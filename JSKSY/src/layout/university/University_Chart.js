import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Platform
} from 'react-native';

import Echarts from 'native-echarts';
import Dimensions from 'Dimensions';

export default class University_Chart extends Component {

  constructor(props) {
      super(props);
      var years = [];
      var hScores = [];
      var lScores = [];
      var chartData = this.props.chartData;
      for(var i=0; i<chartData.length; i++){
        years.push(chartData[i].year);
        hScores.push(chartData[i].hScore);
        lScores.push(chartData[i].lScore);
      }

      this.state = {
        year:years,
        lScore:lScores,
        hScore: hScores,
        maxY:Math.max.apply(null,hScores)+10,
        minY:Math.min.apply(null,lScores)-10
      }
  }

  shouldComponentUpdate(nextProps, nextState) {
    return false;
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
                  data : this.state.year
              }
          ],
          yAxis : [
              {
                  type : 'value',
                  min: this.state.minY,
                  max: this.state.maxY
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
                  data:this.state.lScore,
                  markPoint : {
                      data : [
                          {type : 'max', name: '最大值'},
                          {type : 'min', name: '最小值'}
                      ]
                  },
                  // markLine : {
                  //     data : [
                  //         {type : 'average', name: '平均值'}
                  //     ]
                  // }
              },
              {
                  name:'录取最高分',
                  type:'line',
                  data:this.state.hScore,
                  markPoint : {
                      data : [
                          {type : 'max', name: '最大值'},
                          {type : 'min', name: '最小值'}
                      ]
                  },
                  // markLine : {
                  //     data : [
                  //         {type : 'average', name: '平均值'}
                  //     ]
                  // }
              }
          ]
        };
  return (
    	<View style={styles.container}>
				<Echarts option={option} height={300} width={global.windowWidth}/>
      	</View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex:1,
  },
});