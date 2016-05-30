//
//  DES3Util.m
//

#import "DES3Util.h"
#import <CommonCrypto/CommonCryptor.h>
#import "GTMBase64.h"

@implementation DES3Util

/**
 * 加解密基础方法
 * @param plainText 需要加解密的字符串
 * @param desKey 会话秘钥
 * @param encryptOrDecrypt 加解密类型      加密：kCCEncrypt   解密 kCCDecrypt
 * @return 加解密后的字符串
 */
+ (NSString*) tripleDES:(NSString*)plainText tripleKey:(NSString *) desKey encryptOrDecrypt:(CCOperation)encryptOrDecrypt
{
    const void *vplainText;
    size_t plainTextBufferSize;
    
    if (encryptOrDecrypt == kCCDecrypt)
    {
        // des解密
        NSData *EncryptData = [GTMBase64 decodeData:[plainText dataUsingEncoding:NSUTF8StringEncoding]];
        plainTextBufferSize = [EncryptData length];
        vplainText = [EncryptData bytes];
    }
    else //加密
    {
        //  base64 加密
        NSLog(@"plainText is %@",plainText);
        NSData* dataTemp = [plainText dataUsingEncoding:NSUTF8StringEncoding];
        NSLog(@"plainText over");
        NSString * base64 = [[NSString alloc] initWithData:[GTMBase64 encodeData:dataTemp] encoding:NSUTF8StringEncoding];
            
        // des 加密准备
        NSData* data = [base64 dataUsingEncoding:NSUTF8StringEncoding];
        plainTextBufferSize = [data length];
        vplainText = (const void *)[data bytes];
    }
        
    CCCryptorStatus ccStatus;
    uint8_t *bufferPtr = NULL;
    size_t bufferPtrSize = 0;
    size_t movedBytes = 0;
        
    bufferPtrSize = (plainTextBufferSize + kCCBlockSize3DES) & ~(kCCBlockSize3DES - 1);
    bufferPtr = malloc( bufferPtrSize * sizeof(uint8_t));
    memset((void *)bufferPtr, 0x0, bufferPtrSize);
    // memset((void *) iv, 0x0, (size_t) sizeof(iv));
        
    const void *vkey = (const void *)[desKey UTF8String];
    // NSString *initVec = @"init Vec";
    //const void *vinitVec = (const void *) [initVec UTF8String];
    //  Byte iv[] = {0x12, 0x34, 0x56, 0x78, 0x90, 0xAB, 0xCD, 0xEF};
    ccStatus = CCCrypt(encryptOrDecrypt,
                        kCCAlgorithm3DES,
                        kCCOptionPKCS7Padding | kCCOptionECBMode,
                        vkey,
                        kCCKeySize3DES,
                        nil,
                        vplainText,
                        plainTextBufferSize,
                        (void *)bufferPtr,
                        bufferPtrSize,
                        &movedBytes);
    //if (ccStatus == kCCSuccess) NSLog(@"SUCCESS");
    /*else if (ccStatus == kCC ParamError) return @"PARAM ERROR";
        else if (ccStatus == kCCBufferTooSmall) return @"BUFFER TOO SMALL";
        else if (ccStatus == kCCMemoryFailure) return @"MEMORY FAILURE";
        else if (ccStatus == kCCAlignmentError) return @"ALIGNMENT";
        else if (ccStatus == kCCDecodeError) return @"DECODE ERROR";
        else if (ccStatus == kCCUnimplemented) return @"UNIMPLEMENTED"; */
        
    NSString *result;
    
    if (encryptOrDecrypt == kCCDecrypt)
    {
        result = [[NSString alloc] initWithData:[NSData dataWithBytes:(const void *)bufferPtr length:(NSUInteger)movedBytes] encoding:NSUTF8StringEncoding] ;
            
        //  base64 解密
        NSData* dataTemps = [result dataUsingEncoding:NSUTF8StringEncoding];
        result = [[NSString alloc] initWithData:[GTMBase64 decodeData:dataTemps] encoding:NSUTF8StringEncoding];
    }
    else
    {
        NSData *myData = [NSData dataWithBytes:(const void *)bufferPtr length:(NSUInteger)movedBytes];
        result = [GTMBase64 stringByEncodingData:myData];
    }
    free(bufferPtr);
    return result;
}

/**
 * 加解密方法
 * @param plainText 需要加解密的字符串
 * @param encryptOrDecrypt 加解密类型      加密：kCCEncrypt   解密 kCCDecrypt
 * @return 加解密后的字符串
 */
+ (NSString *) tripleDES:(NSString*)plainText encryptOrDecrypt:(CCOperation)encryptOrDecrypt
{
    return [self tripleDES:plainText tripleKey:@"2016051616090012345678tm" encryptOrDecrypt:encryptOrDecrypt];
}

/**
 * 获取会话秘钥
 * @param baseKey 会话秘钥密文
 * @return 会话秘钥明文
 */
+ (void)tripleDESWithAppkey:(NSString*)baseKey {
    sessionKey = [self tripleDES:baseKey tripleKey:@"201412250933001234567890" encryptOrDecrypt:kCCDecrypt];
}

@end