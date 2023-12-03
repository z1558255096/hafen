//package com.panshi.honor.common.utils;
//
//import com.aliyun.oss.*;
//import com.aliyun.oss.model.GeneratePresignedUrlRequest;
//import com.aliyun.oss.model.OSSObject;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.Date;
//
///**
// * @program: honor
// * @description:
// * @author: Moil
// * @create: 2022-12-12 10:22
// **/
//@Component
//public class OssUtil {
//    public static void main(String[] args) throws Throwable {
//        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
//        // 从STS服务获取的临时访问密钥（AccessKey ID和AccessKey Secret）。
//        String accessKeyId = "LTAI5tCUE1a62zpVpSRRJhec";
//        String accessKeySecret = "Dzyat2b62iLM6450QeaEp16D3nqESF";
//        // 从STS服务获取的安全令牌（SecurityToken）。
////        String securityToken = "yourSecurityToken";
//        // 填写Bucket名称，例如examplebucket。
//        String bucketName = "pshonor";
//        // 填写Object完整路径，例如exampleobject.txt。Object完整路径中不能包含Bucket名称。
//        String objectName = "honor/20221212/adf8efb42eb04cafa8533f0d7062a5b6.xlsx";
//
//        // 从STS服务获取临时访问凭证后，您可以通过临时访问密钥和安全令牌生成OSSClient。
//        // 创建OSSClient实例。
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        try {
//            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
//            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
//
//            // 读取文件内容。
//            System.out.println("Object content:");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
//            while (true) {
//                String line = reader.readLine();
//                if (line == null) break;
//
//                System.out.println("\n" + line);
//            }
//            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
//            reader.close();
//            // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
//            ossObject.close();
//
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (Throwable ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
//}
