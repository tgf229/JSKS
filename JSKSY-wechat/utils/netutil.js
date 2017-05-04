const URL_ADDR = "https://app.jseea.cn/";
const BUS_100201 = "Bus100201";
const BUS_100301 = "Bus100301";
const BUS_100501 = "Bus100501";
const BUS_600101 = "Bus600101";

function netClientPost(object,busName,busCB,params){
    wx.request({
        url:URL_ADDR+busName,
        data:params,
        header:{
            'Content-Type':'application/json'
        },
        success:function(res){
            console.log(res);
            busCB(object,res);
        }
    })
}



module.exports = {
    BUS_100201:BUS_100201,
    BUS_100301:BUS_100301,
    BUS_100501:BUS_100501,
    BUS_600101:BUS_600101,
    netClientPost:netClientPost,
}