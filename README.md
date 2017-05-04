# JSKS

###-开发小结
---
本次开发第一次正式使用RN，所以只开发了iOS版本，Android还是使用原生代码进行编写。
版本管理：Git
开发工具：Sublime Text3 + Xcode7
使用组件：
－热更新使用 react-native-code-push
－Banner使用 react-native-swiper
－列表上拉下拉使用 react-native-gifted-listview
－UUID  react-native-device-uuid
未使用redux，也暂时没有移植Android版本，后续跟进。


###-过程记录v1.0.0
---
4-11 ios UI布局：主页<br>
4-12 ios UI布局：查分<br>
4-13 ios UI布局：录取查询<br>
4-14 ios UI布局：志愿推荐<br>
4-15 ios UI布局：志愿推荐<br>

4-18 android UI布局：首页 查分 录取查询<br>
4-19 android UI布局：志愿推荐<br>
4-20 ios UI遗漏补全<br>
4-21 ios UI遗漏补全 查看支付文档<br>
4-22 ios&android rn-NavigatorIOS因不灵活，无法单页面定制title内容而迁移为Navigator  ios热更新测试(还是无法更新图片)<br>

4-25 接口调测：初始化，轮播<br>
4-26 接口调测：首页列表，查分时间基准<br>
4-27 接口调测：查分<br>
4-28 调休<br>
4-29 接口调测：查分，录取查询<br>

5-2  全国放假<br>
5-3  接口调测：录取查询，取消录取<br>
5-4  android UI布局：设置   android推送<br>
5-5  ios  UI布局：设置   百度统计<br>
5-6  调休<br>

5-9  appstore证书，ios推送，ios Loading页<br>
5-10 ios Loading页  android Loading页  接口更新设计<br>
5-11 ios 引导页  Appstore预发布准备<br>
5-12 android 引导页<br>
5-13 ios Appstore发布 android 欢迎广告<br>

5-16 ios 欢迎广告  加解密方案 <br>

5-30 android 细节调整 加解密<br>
5-31 ios发布1.0.2 调测现网查分数据正确性 android自动更新 android发布1.0.0<br>

6-12 接口调测：口令查分<br>
6-13 接口调测：查分广告<br>
6-15 接口调测：志愿<br>
6-16 细节调整<br>
6-17 发布<br>

###-过程记录v2.1.0
---
4-11 本地apache服务搭建
4-12 ios UI布局：列表<br>
4-13 ios UI布局：筛选<br>
4-14 ios UI布局：详情构建，自定义手势滑动swiper&anim<br>

4-17 ios UI布局：详情3个tab<br>
4-18 ios UI布局：图表native-echarts<br>
4-19 ios UI布局：分数线List&招生计划List<br>
4-20 ios 接口本地内测
4-21 Android UI布局：列表+筛选<br>

4-24 Android UI布局：详情Tablayout+FragmentViewPager<br>
4-25 Android UI布局：分数线List&招生计划List<br>
4-26 Android UI布局：图表helloCharts<br>
4-27 Android UI布局：查缺补漏+MaterialDesign<br>
4-28 Android 接口本地内测<br>

5-2  ios 接口alpha测：All<br>
5-3  ios 版本打包内测+性能调优(push卡顿->异步刷新&图表Component不触发非必要render)<br>




