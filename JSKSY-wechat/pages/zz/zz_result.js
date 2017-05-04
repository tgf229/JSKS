Page({
    data:{
        windowWidth:'',
        dataInfo:{},
        PIC_COLORS:[
            '#f9b755',
            '#abdb55',
            '#96c8fc',
            '#a8b4f1',
            '#ffabbc',
            '#fc967e',
            '#8ededc'
        ]
    },

    onLoad:function(option){
        this.setData({
            windowWidth:getApp().globalData.windowInfo.windowWidth,
            dataInfo:JSON.parse(option.data)
        })
    },
})