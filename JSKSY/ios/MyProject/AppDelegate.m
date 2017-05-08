/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "AppDelegate.h"

#import "RCTRootView.h"
#import "CodePush.h"
//百度统计
#import "BaiduMobStat.h"
//jpush
#import "JPUSHService.h"
#import <AdSupport/ASIdentifierManager.h>

#import "UIImageView+WebCache.h"

@interface AppDelegate ()
@property (strong, nonatomic) UIView *lunchView;
@end

@implementation AppDelegate
@synthesize lunchView;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  NSURL *jsCodeLocation;

  /**
   * Loading JavaScript code - uncomment the one you want.
   *
   * OPTION 1
   * Load from development server. Start the server from the repository root:
   *
   * $ npm start
   *
   * To run on device, change `localhost` to the IP address of your computer
   * (you can get this by typing `ifconfig` into the terminal and selecting the
   * `inet` value under `en0:`) and make sure your computer and iOS device are
   * on the same Wi-Fi network.
   */
//  jsCodeLocation = [NSURL URLWithString:@"http://localhost:8081/index.ios.bundle?platform=ios&dev=false"];

  /**
   * OPTION 2
   * Load from pre-bundled file on disk. The static bundle is automatically
   * generated by "Bundle React Native code and images" build step.
   */
//   jsCodeLocation = [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];

//     curl http://localhost:8081/index.ios.bundle -o main.jsbundle
//  #ifdef DEBUG
//    jsCodeLocation = [NSURL URLWithString:@"http://localhost:8081/index.ios.bundle?platform=ios&dev=true"];
//  #else
    jsCodeLocation = [CodePush bundleURL];
//  #endif
  
//  [self.window makeKeyAndVisible];
//  lunchView= [[NSBundle mainBundle ]loadNibNamed:@"LaunchScreen" owner:nil options:nil][0];
//  lunchView.frame = CGRectMake(0, 0, self.window.screen.bounds.size.width,
//                               self.window.screen.bounds.size.height);
//  [self.window addSubview:lunchView];
//  UIImageView *imageV = [[UIImageView alloc] initWithFrame:CGRectMake(0, 50, 320, 300)];
//  NSString *str = @"http://www.jerehedu.com/images/temp/logo.gif";
//  [imageV sd_setImageWithURL:[NSURL URLWithString:str] placeholderImage:[UIImage imageNamed:@"load_pic"]];
//  [lunchView addSubview:imageV];
//  [self.window bringSubviewToFront:lunchView];
//  [NSTimer scheduledTimerWithTimeInterval:30 target:self selector:@selector(removeLun) userInfo:nil repeats:NO];
//  
//    [NSThread sleepForTimeInterval:10.0];//设置启动页面时间
//  return YES;
  

  
  [self configBaiduMobStat];

  RCTRootView *rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
                                                      moduleName:@"MyProject"
                                               initialProperties:nil
                                                   launchOptions:launchOptions];

  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  UIViewController *rootViewController = [UIViewController new];
  rootViewController.view = rootView;
  self.window.rootViewController = rootViewController;
  [self.window makeKeyAndVisible];
  
  //清空消息
  [UIApplication sharedApplication].applicationIconBadgeNumber = 0;
  
//  NSString *advertisingId = [[[ASIdentifierManager sharedManager] advertisingIdentifier] UUIDString];
  //Required
  if ([[UIDevice currentDevice].systemVersion floatValue] >= 8.0) {
    //可以添加自定义categories
    [JPUSHService registerForRemoteNotificationTypes:(UIUserNotificationTypeBadge |
                                                      UIUserNotificationTypeSound |
                                                      UIUserNotificationTypeAlert)
                                          categories:nil];
  } else {
    //categories 必须为nil
    [JPUSHService registerForRemoteNotificationTypes:(UIRemoteNotificationTypeBadge |
                                                      UIRemoteNotificationTypeSound |
                                                      UIRemoteNotificationTypeAlert)
                                          categories:nil];
  }
  //Required
  //如需兼容旧版本的方式，请依旧使用[JPUSHService setupWithOption:launchOptions]方式初始化和同时使用pushConfig.plist文件声明appKey等配置内容。
  [JPUSHService setupWithOption:launchOptions];
  
  return YES;
}

-(void)removeLun
{
  [lunchView removeFromSuperview];
}

- (void)application:(UIApplication *)application
didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
  
  /// Required - 注册 DeviceToken
  [JPUSHService registerDeviceToken:deviceToken];
}
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
  
  // Required,For systems with less than or equal to iOS6
  [JPUSHService handleRemoteNotification:userInfo];
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
  
  // IOS 7 Support Required
  [JPUSHService handleRemoteNotification:userInfo];
  completionHandler(UIBackgroundFetchResultNewData);
  
  // 接收推送过来的消息
  [UIApplication sharedApplication].applicationIconBadgeNumber = 0;
  [[UIApplication sharedApplication] cancelAllLocalNotifications];
  
  if (application.applicationState != UIApplicationStateActive){
    // 如果应用在不在前台，跳转到我的消息页面
//    [self pushToMyMessageViewController];
  }
  else{
    // 应用在前台，弹出提示
    NSString* alertValue = [[userInfo valueForKey:@"aps"] valueForKey:@"alert"];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"您收到一条新消息"
                                                        message:alertValue
                                                       delegate:self
                                              cancelButtonTitle:@"我知道了"
                                              otherButtonTitles:nil, nil];
    [alertView show];
  }
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
  //Optional
  // 注册失败
  NSLog(@"消息推送注册失败！");
}

/**
 * 百度统计
 */
#pragma mark -
#pragma mark 百度统计
-(void)configBaiduMobStat
{
  BaiduMobStat* statTracker = [BaiduMobStat defaultStat];
  statTracker.enableExceptionLog = YES;   // 是否允许截获并发送崩溃信息，请设置YES或者NO
  statTracker.channelId = @"AppStore";    // 设置您的app的发布渠道
  statTracker.logStrategy = BaiduMobStatLogStrategyAppLaunch;// 根据开发者设定的时间间隔接口发送 也可以使用启动时发送策略
  statTracker.logSendInterval = 1;        // 为1时表示发送日志的时间间隔为1小时
  statTracker.logSendWifiOnly = YES;      // 是否仅在WIfi情况下发送日志数据
  // 设置应用进入后台再回到前台为同一次session的间隔时间[0~600s],超过600s则设为600s，默认为30s,测试时使用1S可以用来测试日志的发送。
  statTracker.sessionResumeInterval = 35;
  // 参数为NSString * 类型,自定义app版本信息，如果不设置，默认从CFBundleVersion里取
  statTracker.shortAppVersion  = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
  statTracker.enableDebugOn = YES;        // 打开sdk调试接口，会有log打印
  [statTracker startWithAppId:@"d094734473"];// 设置您在mtj网站上添加的app的appkey
}

@end
