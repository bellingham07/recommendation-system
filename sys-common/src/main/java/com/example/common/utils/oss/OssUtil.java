package com.example.common.utils.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

@Service
public class OssUtil {

    @Resource
    private OssProperties ossProperties;

//    传入作者的名字生成一个 作者名+时间+后缀的文件
    public String upload(MultipartFile file, String hostName) throws IOException {
        if (file == null || file.isEmpty()) return null;
        String fileName = file.getOriginalFilename();
        String expName = "";
        if (fileName != null) expName = fileName.substring(fileName.indexOf(".") + 1);
        String endPoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getKeyId();
        String keySecret = ossProperties.getKeySecret();
        String bucketName = ossProperties.getBucketName();
        Date date = new Date();
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, keySecret);
        String url = "image/" + "cjb_" + date.toString() + "." + expName;
        ossClient.putObject(bucketName, url, file.getInputStream());
        ossClient.shutdown();
        return "https://my-blog-springboot.oss-cn-beijing.aliyuncs.com/" + url;
    }
}
