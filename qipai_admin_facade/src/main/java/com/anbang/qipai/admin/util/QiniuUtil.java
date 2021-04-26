package com.anbang.qipai.admin.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtil {

	private static String accessKey = "qQj7mRKyvE7dOOjObMC8W58i6Yn3penfr7-_fg4d";
	private static String secretKey = "9f70kmAddF1maP1U0jy0vRNAhwWNv_huR1xDSH_s";
	private static String bucket = "anbang";
	private static Auth auth = Auth.create(accessKey, secretKey);
	// 构造一个带指定Zone对象的配置类
	private static Configuration config = new Configuration(Zone.zone0());
	private static UploadManager uploadManager = new UploadManager(config);
	private static BucketManager bucketManager = new BucketManager(auth, config);

	public static void deleteImage(String fileName) throws QiniuException {
		bucketManager.delete(bucket, fileName);
	}

	public static String getUpToken() {
		return auth.uploadToken(bucket);
	}
}
