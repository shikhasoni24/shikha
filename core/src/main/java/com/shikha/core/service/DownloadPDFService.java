package com.shikha.core.service;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Image;

public interface DownloadPDFService {
	public void createPdf(ByteArrayOutputStream boas,int userId,String userName) ;
	public Image readImageFromDam();


}
