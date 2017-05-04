var util = require('../../utils/util');
let netutil = require('../../utils/netutil');

var BUS_600101_CB = function(object,json){
  wx.hideToast();
  if(json.data.retcode){
    if("000000" === json.data.retcode && json.data.doc.length>0){
      //微信透传数据需要string
        wx.navigateTo({
          url: '/pages/zz/zz_result?data='+JSON.stringify(json.data),
        })
    }else{
      wx.showToast({
        title:json.data.retinfo,
      })
    }
  }else{
    wx.showToast({
      title:'请求失败，请稍后再试',
    })
  }
  
}

var BUS_600101_REQ = function(object){
  var params = {
    encrypt:'none',
    sNum:object.data.sNumStr,
    sTicket:object.data.sTicketStr
  }
  netutil.netClientPost(object,netutil.BUS_600101,BUS_600101_CB, params);
}

Page({
   data: {
    sNumStr:'',
    sTicketStr:'',
  },

  bindInputChange: function(e){
    var flag = e.currentTarget.dataset.flag;
    if("num" === flag){
      this.setData({
        sNumStr:e.detail.value
      });
    }else if("tick" === flag){
      this.setData({
        sTicketStr:e.detail.value
      });
    }
  },

  onBtnClick:function(e){
    var num = util.trim(this.data.sNumStr);
    var tick = util.trim(this.data.sTicketStr);
    if(num === ''){
        wx.showToast({
          title:'请输入你的考籍号',
          mask:true
        })
        return;
    }
    if(tick === '' || tick.length < 6){
        wx.showToast({
          title:'请输入你的身份证号后6位',
          mask:true
        })
        return;
    }

    var that = this;
    wx.showToast({
      title:'加载中',
      icon:'loading',
      duration:10000,
      success:function(){
        BUS_600101_REQ(that);
      }
    })

  }
})