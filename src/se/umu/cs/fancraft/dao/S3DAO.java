package se.umu.cs.fancraft.dao;

import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3DAO {

	private static AmazonS3 s3client;

	private static final String BUCKET_NAME = "fancraft-bucket";

	static {
		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		s3client = new AmazonS3Client(credentialsProvider);
	}

	public String storeFile(CommonsMultipartFile file, String userId,
			String postTimestamp) {
		try {
			
			ObjectMetadata omd = new ObjectMetadata();
			omd.setContentType("application/octet-stream");
			omd.addUserMetadata("originalfilename", file.getOriginalFilename());
			
			String path = "files/" + userId + "_" + postTimestamp.replace(':', '_') + "_" + file.getOriginalFilename();
			
			PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
					path,
					file.getInputStream(), omd);
						
			s3client.putObject(request);
			
			s3client.setObjectAcl(BUCKET_NAME, path, CannedAccessControlList.PublicRead);

			return "http://s3.amazonaws.com/" + BUCKET_NAME + "/" + path;
		} catch (IOException e) {
			return null;
		}

	}

	public String storePicture(CommonsMultipartFile file, String userId,
			String postTimestamp) {
		try {
			
			ObjectMetadata omd = new ObjectMetadata();
			omd.setContentType("application/octet-stream");
			omd.addUserMetadata("originalfilename", file.getOriginalFilename());
			
			String path = "pictures/" + userId + "_" + postTimestamp.replace(':', '_') + "_" + file.getOriginalFilename();
			
			PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
					path,
					file.getInputStream(), omd);
			
			s3client.putObject(request);
			
			s3client.setObjectAcl(BUCKET_NAME, path, CannedAccessControlList.PublicRead);

			return "http://s3.amazonaws.com/" + BUCKET_NAME + "/" + path;
		} catch (IOException e) {
			return null;
		}

	}
}
