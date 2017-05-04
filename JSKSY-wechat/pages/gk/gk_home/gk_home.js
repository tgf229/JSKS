var netutil = require('../../../utils/netutil.js');

var PAGE_NUM = 1;
var NUM = 10;
var hasMore = true;

var BUS_100301_CB = function(object,json){
  if('000000'===json.data.retcode && json.data.doc.length >= NUM){
    hasMore = true;
  }else{
    hasMore = false;
  }
  object.setData({
    listData:object.data.listData.concat(json.data.doc),
  })
}
var BUS_100301_REQ = function(object){
  var params={
		encrypt:'none',
    page:PAGE_NUM,
    num:NUM,
  }
  netutil.netClientPost(object,netutil.BUS_100301,BUS_100301_CB,params);
}

Page({
  data: {
    windowHeight:'',
    listData:[],
  },

  onLoad: function(){
    this.setData({
      windowHeight:getApp().globalData.windowInfo.windowHeight
    })
    BUS_100301_REQ(this);
  },

  lower:function(e){
    if(hasMore){
      PAGE_NUM = PAGE_NUM+1;
      BUS_100301_REQ(this);
    }
  },

  onBtnClick(e){
    wx.showToast({
      title:'开放时间为2017年7月',
      mask:true
    })
  },

})
