var netutil = require('../../utils/netutil.js');

var BUS_100201_CB = function(object,json){
  if('000000'===json.data.retcode && json.data.doc.length > 0){
    object.setData({
      swiperText:json.data.doc[0].name,
      swiperShow:true,
      swiperData:json.data.doc
    })
  }
}
var BUS_100201_REQ = function(object){
  var params={
		encrypt:'none',
  }
  netutil.netClientPost(object,netutil.BUS_100201,BUS_100201_CB,params);
}

var BUS_100501_CB = function(object,json){
  wx.stopPullDownRefresh(); //停止下啦刷新
  if('000000'===json.data.retcode && json.data.doc.length > 0){
    object.setData({
      listData:object.data.listData.concat(json.data.doc),
    })
  }
}
var BUS_100501_REQ = function(object){
  var params={
		encrypt:'none',
  }
  netutil.netClientPost(object,netutil.BUS_100501,BUS_100501_CB,params);
}

Page({
  data: {
    swiperShow:false,
    swiperText:'',
    windowHeight:'',
    listData:[],
    swiperData:[],
  },

  onLoad: function(){
    this.setData({
      windowHeight:getApp().globalData.windowInfo.windowHeight
    })
    BUS_100201_REQ(this);
    BUS_100501_REQ(this);
  },

  swiperChange:function(e){
    this.setData({
      swiperText:this.data.swiperData[e.detail.current].name,
    })
  },

  onPullDownRefresh: function(){
    this.setData({
      listData:[],
      swiperData:[],
    })
    BUS_100201_REQ(this);
    BUS_100501_REQ(this);
  },

  onBtnClick(e){
    var flag = e.currentTarget.dataset.flag;
    if("zz" === flag){
      wx.navigateTo({
        url:'/pages/zz/zz_point'
      })
    }
    else if("gk_channel" === flag){
      wx.navigateTo({
        url: '/pages/gk/gk_home/gk_home'
      })
    }else{
       wx.showToast({
          title:'开放时间为2017年7月',
          mask:true
        })
    }
  },

})
