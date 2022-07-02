package com.shikha.core.service;

import java.io.ByteArrayOutputStream;

import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface DownloadExcelService {
	public void createExcel(ByteArrayOutputStream boas, int userId, String userName) throws ServiceException;

}
