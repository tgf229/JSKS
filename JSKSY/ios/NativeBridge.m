//
//  NativeBridge.m
//  MyProject
//
//  Created by 涂高峰 on 16/4/25.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "NativeBridge.h"
#import "RCTConvert.h"

#import "RCTBridge.h"
#import "RCTEventDispatcher.h"

#import "JPUSHService.h"
#import "DES3Util.h"

#import <sys/utsname.h>



#import <ShareSDK/ShareSDK.h>
#import <ShareSDKUI/ShareSDK+SSUI.h>
#import <ShareSDKUI/SSUIShareActionSheetStyle.h>

@implementation NativeBridge

//RCT_EXPORT_MODULE();
RCT_EXPORT_MODULE();

//*********************************************************
RCT_EXPORT_METHOD(NATIVE_shareSDKWithOutUI:(id)dic channel:(int)channel content:(NSString *)content url:(NSString *)url)
{
  //1、创建分享参数
  NSArray* imageArray = @[[UIImage imageNamed:@"set_icon.png"]];
  //创建分享参数
  NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];

  [shareParams SSDKSetupShareParamsByText:[content stringByAppendingString: url]
                                   images:imageArray
                                      url:[NSURL URLWithString:url]
                                    title:content
                                     type:SSDKContentTypeAuto];
  SSDKPlatformType plat;
  if (channel == 1) {
    plat = SSDKPlatformSubTypeWechatSession;
  }else if(channel == 2){
    plat = SSDKPlatformSubTypeWechatTimeline;
  }else if(channel == 3){
    plat = SSDKPlatformTypeQQ;
  }else if(channel == 4){
    plat = SSDKPlatformSubTypeQZone;
  }
  
  dispatch_async(dispatch_get_main_queue(), ^{
  //进行分享
  [ShareSDK share:plat //传入分享的平台类型
       parameters:shareParams
   onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) {
//     switch (state) {
//       case SSDKResponseStateSuccess:
//       {
//         UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"分享成功"
//                                                             message:nil
//                                                            delegate:nil
//                                                   cancelButtonTitle:@"确定"
//                                                   otherButtonTitles:nil];
//         [alertView show];
//         break;
//       }
//       case SSDKResponseStateFail:
//       {
//         UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享失败"
//                                                         message:[NSString stringWithFormat:@"%@",error]
//                                                        delegate:nil
//                                               cancelButtonTitle:@"OK"
//                                               otherButtonTitles:nil, nil];
//         [alert show];
//         break;
//       }
//       default:
//         break;
//     }
      }];
   });
}


//cb:(RCTResponseSenderBlock)callback
RCT_EXPORT_METHOD(NATIVE_shareSDK:(id)dic content:(NSString *)content url:(NSString *)url)
{
  //1、创建分享参数
  NSArray* imageArray = @[[UIImage imageNamed:@"set_icon.png"]];
  //（注意：图片必须要在Xcode左边目录里面，名称必须要传正确，如果要分享网络图片，可以这样传iamge参数 images:@[@"http://mob.com/Assets/images/logo.png?v=20150320"]）
  if (imageArray) {
    
    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
//    [shareParams SSDKSetupShareParamsByText:[content stringByAppendingString: url]
    [shareParams SSDKSetupShareParamsByText:[content stringByAppendingString: url]
                                     images:imageArray
                                        url:[NSURL URLWithString:url]
                                      title:content
                                       type:SSDKContentTypeAuto];


//    [shareParams SSDKSetupSinaWeiboShareParamsByText:[content stringByAppendingString: url]
//                                               title:@"测试标题"
//                                               image:imageArray[0]
//                                                 url:[NSURL URLWithString:url]
//                                            latitude:0
//                                           longitude:0
//                                            objectID:@"分享"
//                                                type:SSDKContentTypeAuto];
    //2、分享（可以弹出我们的分享菜单和编辑界面）
   
    [SSUIShareActionSheetStyle setShareActionSheetStyle:ShareActionSheetStyleSimple];
//    [shareParams SSDKEnableUseClientShare];
    
    dispatch_async(dispatch_get_main_queue(), ^{
    SSUIShareActionSheetController *sheet = [ShareSDK showShareActionSheet:nil
                                                                     items:@[
                                                                             @(SSDKPlatformSubTypeWechatSession),
                                                                             @(SSDKPlatformSubTypeWechatTimeline),
                                                                             @(SSDKPlatformTypeQQ),
                                                                             @(SSDKPlatformSubTypeQZone),
                                                                             @(SSDKPlatformTypeSinaWeibo),
                                                                             ]
                                                               shareParams:shareParams
                                                       onShareStateChanged:^(SSDKResponseState state, SSDKPlatformType platformType, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error, BOOL end) {
                                                         switch (state) {
                                                           case SSDKResponseStateSuccess:{
                                            UIAlertView *alertView = [[UIAlertView alloc]
                                                                      initWithTitle:@"分享成功"
                                                                      message:nil
                                                                      delegate:nil
                                                                      cancelButtonTitle:@"确定"
                                                                      otherButtonTitles:nil];
                                                      [alertView show];
                                                             break;
                                                           }
                                                           case SSDKResponseStateFail:
                                                             NSLog(@"分享失败%@",error);
                                                             break;
                                                           case SSDKResponseStateCancel:
                                                             NSLog(@"分享已取消");
                                                             break;
                                                           default:
                                                             break;
                                                         }
                                                       }];
    //删除和添加平台示例
//    [sheet.directSharePlatforms removeObject:@(SSDKPlatformTypeWechat)];
    [sheet.directSharePlatforms addObject:@(SSDKPlatformTypeSinaWeibo)];
    });
    
//    dispatch_async(dispatch_get_main_queue(), ^{
//    [ShareSDK showShareActionSheet:nil //要显示菜单的视图, iPad版中此参数作为弹出菜单的参照视图，只有传这个才可以弹出我们的分享菜单，可以传分享的按钮对象或者自己创建小的view 对象，iPhone可以传nil不会影响
//                             items:@[
//                                     @(SSDKPlatformSubTypeWechatSession),
//                                     @(SSDKPlatformSubTypeWechatTimeline),
//                                     @(SSDKPlatformTypeQQ),
//                                     @(SSDKPlatformSubTypeQZone),
//                                     @(SSDKPlatformTypeSinaWeibo),
//                                     ]
//                       shareParams:shareParams
//               onShareStateChanged:^(SSDKResponseState state, SSDKPlatformType platformType, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error, BOOL end) {
//                 
//                 switch (state) {
//                   case SSDKResponseStateSuccess:
//                   {
//                     UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"分享成功"
//                                                                         message:nil
//                                                                        delegate:nil
//                                                               cancelButtonTitle:@"确定"
//                                                               otherButtonTitles:nil];
//                     [alertView show];
//                     break;
//                   }
//                   case SSDKResponseStateFail:
//                   {
//                     UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享失败"
//                                                                     message:[NSString stringWithFormat:@"%@",error]
//                                                                    delegate:nil
//                                                           cancelButtonTitle:@"OK"
//                                                           otherButtonTitles:nil, nil];
//                     [alert show];
//                     break;
//                   }
//                   default:
//                     break;
//                 }
//               }
//        ];
//    });
  }
//  callback(@[@"nothing"]);
}
//*********************************************************

//RN调原生方法并获取回调   取手机model
RCT_EXPORT_METHOD(NATIVE_getDeviceModel:(RCTResponseSenderBlock)callback)
{
  NSString *model =  [self deviceModelString];
  callback(@[[NSNull null],model]);
}

//RN调原生方法   设置JPUSH别名
RCT_EXPORT_METHOD(NATIVE_setAlias:(NSString *)alias)
{
  [self jpushSetAlias:alias];
}

RCT_EXPORT_METHOD(NATIVE_callPhone:(NSString *)num)
{
  [self callPhone:num];
}

//RN调原生方法并获取回调   取加密信息
RCT_EXPORT_METHOD(NATIVE_getEncryptData:(NSDictionary *)dict callback:(RCTResponseSenderBlock)callback)
{
  NSMutableDictionary *dicts = [NSMutableDictionary dictionary];
  for (NSString *key in dict) {
    NSLog(@"key: %@ value: %@", key, dict[key]);
    NSString *data = [DES3Util tripleDES:dict[key] encryptOrDecrypt:kCCEncrypt];
    [dicts setObject:data forKey:key];
  }
    callback(@[[NSNull null],dicts]);
}

//RN调原生方法并获取回调   取解密密信息
RCT_EXPORT_METHOD(NATIVE_getDecryptData:(NSString *)str callback:(RCTResponseSenderBlock)callback)
{
  NSString *data = [DES3Util tripleDES:str encryptOrDecrypt:kCCDecrypt];
  callback(@[[NSNull null],data]);
}

-(void)callPhone:(NSString *)num{
  NSMutableString* str=[[NSMutableString alloc] initWithFormat:@"tel:%@",num];
//   NSLog(@"str======%@",str);
  [[UIApplication sharedApplication] openURL:[NSURL URLWithString:str]];
}

//设置别名
-(void)jpushSetAlias:(NSString *)alias{
  [JPUSHService setAlias:alias callbackSelector:@selector(tagsAliasCallback:tags:alias:) object:self];
}

// 设置别名方法回调
- (void)tagsAliasCallback:(int)iResCode tags:(NSSet*)tags alias:(NSString*)alias {
  if(iResCode == 0){
    NSLog(@"设置别名成功");
  }else{
    NSLog(@"设置别名失败");
  }
}

// 清除别名
-(void)clearAliasForLoginUser
{
  [JPUSHService setAlias:@"" callbackSelector:@selector(tagsAliasCallback:tags:alias:) object:self];
}

//获取手机model
-(NSString*)deviceModelString
{
  struct utsname systemInfo;
  uname(&systemInfo);
  NSString *deviceString = [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];
  
  if ([deviceString isEqualToString:@"iPhone8,1"])    return @"iPhone 6Plus";
  if ([deviceString isEqualToString:@"iPhone8,2"])    return @"iPhone 6Plus";
  if ([deviceString isEqualToString:@"iPhone8,3"])    return @"iPhone 6Plus";
  if ([deviceString isEqualToString:@"iPhone7,1"])    return @"iPhone 6";
  if ([deviceString isEqualToString:@"iPhone7,2"])    return @"iPhone 6";
  if ([deviceString isEqualToString:@"iPhone7,3"])    return @"iPhone 6";
  if ([deviceString isEqualToString:@"iPhone6,1"])    return @"iPhone 5s";
  if ([deviceString isEqualToString:@"iPhone6,2"])    return @"iPhone 5s";
  if ([deviceString isEqualToString:@"iPhone6,3"])    return @"iPhone 5s";
  if ([deviceString isEqualToString:@"iPhone5,1"])    return @"iPhone 5";
  if ([deviceString isEqualToString:@"iPhone5,2"])    return @"iPhone 5";
  if ([deviceString isEqualToString:@"iPhone4,1"])    return @"iPhone 4S";
  if ([deviceString isEqualToString:@"iPhone3,1"])    return @"iPhone 4";
  if ([deviceString isEqualToString:@"iPhone3,2"])    return @"iPhone 4";
  if ([deviceString isEqualToString:@"iPhone2,1"])    return @"iPhone 3GS";
  if ([deviceString isEqualToString:@"iPhone1,1"])    return @"iPhone 1G";
  if ([deviceString isEqualToString:@"iPhone1,2"])    return @"iPhone 3G";
  if ([deviceString isEqualToString:@"iPod1,1"])      return @"iPod Touch 1G";
  if ([deviceString isEqualToString:@"iPod2,1"])      return @"iPod Touch 2G";
  if ([deviceString isEqualToString:@"iPod3,1"])      return @"iPod Touch 3G";
  if ([deviceString isEqualToString:@"iPod4,1"])      return @"iPod Touch 4G";
  if ([deviceString isEqualToString:@"iPad1,1"])      return @"iPad";
  if ([deviceString isEqualToString:@"iPad2,1"])      return @"iPad 2 (WiFi)";
  if ([deviceString isEqualToString:@"iPad2,2"])      return @"iPad 2 (GSM)";
  if ([deviceString isEqualToString:@"iPad2,3"])      return @"iPad 2 (CDMA)";
  if ([deviceString isEqualToString:@"i386"])         return @"Simulator";
  if ([deviceString isEqualToString:@"x86_64"])       return @"Simulator";
  NSLog(@"NOTE: Unknown device type: %@", deviceString);
  return deviceString;
}

@end
