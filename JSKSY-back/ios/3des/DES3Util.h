//
//  DES3Util.h
//  JRProperty
//
//  Created by wz on 14-11-7.
//  Copyright (c) 2014年 YZY. All rights reserved.
//  加解密类

#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonCryptor.h>
#import <Security/Security.h>


/**
 * 会话秘钥
 */
static NSString * sessionKey;

@interface DES3Util : NSObject

/**
 * 加解密方法
 * @param plainText 需要加解密的字符串
 * @param encryptOrDecrypt 加解密类型      加密：kCCEncrypt   解密 kCCDecrypt
 * @return 加解密后的字符串
 */
+ (NSString*) tripleDES:(NSString*)plainText encryptOrDecrypt:(CCOperation)encryptOrDecrypt;

/**
 * 获取会话秘钥
 * @param baseKey 会话秘钥密文
 * @return 会话秘钥明文
 */
+ (void)tripleDESWithAppkey:(NSString*)baseKey;

@end
