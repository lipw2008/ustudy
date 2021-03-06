package com.ustudy.exam.utility;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Base64Utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;


public class OSSUtil {
	
	private final static Logger logger = LogManager.getLogger(OSSUtil.class);
	

	private static String bucketURL = null;
	private static String bucketName = null;
	
	private static OSSClient ossClient = null;
	
	public static OSSClient getClient() {
		return ossClient;
	}
	
	public static void initOSS(OSSMetaInfo omi) {
		ossClient = new OSSClient(omi.getEndpoint(), omi.getAccessKeyId(), omi.getAccessKeySecret());
		bucketURL = omi.getBucketURL();
		bucketName = omi.getBucketName();
		logger.info("initOSS(), OSS Client initialized!");
	}
	
	/** 
	 * put object into OSS bucket
     * @param bucketName, key, file 
     * @return 
     */
	public static void putObject(String key, File file) throws Exception{
    	try {    		
    		ossClient.putObject(bucketName, key, file);
    	} catch (OSSException oe) {
    		logger.warn("Caught an OSSException");
    		logger.warn("Error Message: " + oe.getErrorMessage());
    		logger.warn("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.warn("Caught an ClientException");
    		logger.warn("Error Message: " + ce.getErrorMessage());
    		logger.warn("Error Code: " + ce.getErrorCode());
    		throw new Exception("can not put object due to client exception", ce);
    	}
    }

	/** 
	 * put object into OSS bucket
     * @param bucketName, key, inputStream 
     * @return 
     */
	public static void putObject(String key, InputStream inputStream) throws Exception {
    	try {
    		ossClient.putObject(bucketName, key, inputStream);
    	} catch (OSSException oe) {
    		logger.warn("Caught an OSSException");
    		logger.warn("Error Message: " + oe.getErrorMessage());
    		logger.warn("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.warn("Caught an ClientException");
    		logger.warn("Error Message: " + ce.getErrorMessage());
    		logger.warn("Error Code: " + ce.getErrorCode());
    		throw new Exception("can not put object due to client exception", ce);
    	}
    }
    
	/** 
	 * combine two images into one and then put object
     * @param baseKey, markKey, targetKey, x, y, w, h
     * @return 
     */
	public static void putObject(String baseKey, String markKey, String targetKey, String x, 
			String y, String w, String h) throws Exception {
    	try {
    		String base64MarkKey = Base64Utils.encodeToUrlSafeString(markKey.getBytes());
    		String url = bucketURL + "/" + baseKey;
    		url += "?x-oss-process=image/crop,x_" + x + ",y_" + y + ",w_" + w + ",h_" + h;
    		url += "/watermark,";
    		url += "image_" + base64MarkKey;
    		url += ",x_0,y_0,g_nw";
    		logger.debug("URL of the combined file: " + url);
    		InputStream in = new URL(url).openStream();
    		ossClient.putObject(bucketName, targetKey, in);
    	} catch (OSSException oe) {
    		logger.error("Caught an OSSException");
    		logger.error("Error Message: " + oe.getErrorMessage());
    		logger.error("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.error("Caught an ClientException");
    		logger.error("Error Message: " + ce.getErrorMessage());
    		logger.error("Error Code: " + ce.getErrorCode());
    		ce.getStackTrace();
    		throw new Exception("can not put object due to client exception", ce);
    	} catch (Exception e) {
    		logger.error("Error Message: " + e.getMessage());
    		throw new Exception("can not put object due to exception", e);
    	}
    } 
	
}
