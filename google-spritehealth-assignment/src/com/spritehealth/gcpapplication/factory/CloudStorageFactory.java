package com.spritehealth.gcpapplication.factory;

import javax.servlet.http.Part;

import com.google.cloud.storage.BlobInfo;

public class CloudStorageFactory {
	
	public BlobInfo excelFile;
	public Part filePart;

	public BlobInfo getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(BlobInfo excelFile) {
		this.excelFile = excelFile;
	}

	public Part getFilePart() {
		return filePart;
	}

	public void setFilePart(Part filePart) {
		this.filePart = filePart;
	}

}
