package com.spritehealth.gcpapplication.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.spritehealth.gcpapplication.factory.CloudStorageFactory;

@SuppressWarnings("deprecation")
public class CloudStorageService {

	CloudStorageFactory obj = new CloudStorageFactory();


	public BlobInfo UploadToGCS(HttpServletRequest req, HttpServletResponse resp,
			final String bucket, Storage storage) throws IOException, ServletException {

		obj.setFilePart(req.getPart("filetoupload"));

		String fileName = obj.filePart.getSubmittedFileName();

		if(fileName.contains("\\")) {
			fileName = obj.filePart.getSubmittedFileName().substring(obj.filePart.getSubmittedFileName().lastIndexOf("\\") +1 );
		}

		if(fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
			obj.setExcelFile(uploadToBucket(obj.getFilePart(), bucket, storage, fileName));

		}

		return obj.getExcelFile();

	}

	private BlobInfo uploadToBucket(Part filePart, final String bucketName, Storage storage, String fileName) throws IOException {		        

		BlobInfo blobInfo =
				storage.create(
						BlobInfo
						//.newBuilder(bucketName, fileName)
						.newBuilder(bucketName, fileName)
						// Modify access list to allow all users with link to read file
						.setAcl(new ArrayList<Acl>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
						.build(),
						filePart.getInputStream());
		// return the BlobId of a file
		return blobInfo;
	}



}
