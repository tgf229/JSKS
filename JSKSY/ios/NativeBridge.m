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

#import <sys/utsname.h>

@implementation NativeBridge

//RCT_EXPORT_MODULE();
RCT_EXPORT_MODULE();

//RN调原生方法并获取回调
//此方法为取model
RCT_EXPORT_METHOD(findEvents:(RCTResponseSenderBlock)callback)
{
  NSString *model =  [self deviceModelString];
  callback(@[[NSNull null],model]);
}

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
