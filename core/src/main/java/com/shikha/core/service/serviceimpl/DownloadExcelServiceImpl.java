package com.shikha.core.service.serviceimpl;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.DownloadExcelService;
import com.shikha.core.service.DownloadPDFService;
import com.shikha.core.service.UserRoomService;

@Component(service = DownloadExcelService.class, immediate = true)
public class DownloadExcelServiceImpl implements DownloadExcelService {

	@Reference
	UserRoomService userRoomService;

	public void createExcel(ByteArrayOutputStream boas, int userId, String userName) throws ServiceException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		try {
			XSSFSheet sheet = workbook.createSheet("Rooms");
			
			XSSFRow row = sheet.createRow(0);
			XSSFCell cell = row.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(0,4,0,4));
			sheet.autoSizeColumn(2);
			row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
			XSSFFont font=workbook.createFont();
			font.setBold(true);
			XSSFRichTextString rts=new XSSFRichTextString("");
			rts.append(userName,font);
			rts.append( "\n" + "welcome");
			cell.setCellValue(rts);
			XSSFCellStyle style=workbook.createCellStyle();
			style.setWrapText(true);//write in next line of same 
			cell.setCellStyle(style);
//			byte[] b=getImageBytes();
//			int a=workbook.addPicture(b, workbook.PICTURE_TYPE_JPEG);
//			CreationHelper helper=workbook.getCreationHelper();
//			Drawing drawing=sheet.createDrawingPatriarch();
//			ClientAnchor anchor=helper.createClientAnchor();
//			anchor.setCol1(0);
//			anchor.setRow1(0);
//			Picture picture=drawing.createPicture(anchor, a);
//			picture.resize();
			XSSFRow row1 = sheet.createRow(5);

			XSSFCell cell1 = row1.createCell(0);
			cell1.setCellValue("Room No");
			XSSFCell cell2 = row1.createCell(1);
			cell2.setCellValue("Room Name");

			XSSFCell cell3 = row1.createCell(2);
			cell3.setCellValue("Room Price");
			List<Room> rooms = userRoomService.getAllRoomsOfCurrentUser(userId);
			int i = 1;
			for (Room r : rooms) {
				XSSFRow rowRoom = sheet.createRow(i + 5);
				XSSFCell cellRoomNo = rowRoom.createCell(0);
				cellRoomNo.setCellValue(Integer.toString(r.getRoomNo()));

				XSSFCell cellRoomName = rowRoom.createCell(1);
				cellRoomName.setCellValue(r.getRoomName());

				XSSFCell cellRoomPrice = rowRoom.createCell(2);
				cellRoomPrice.setCellValue("1000");
				i++;

			}
			sheet.enableLocking();

			workbook.write(boas);
		} catch (ServiceException | IOException e) {

			throw new ServiceException(e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				throw new ServiceException(e);

			}
		}
	}
	public byte[] getImageBytes(){
		byte[] b=null;

		try {
			URL url =new URL("http://localhost:4503/content/dam/shikha/room2image.jpg");
			b=IOUtils.toByteArray(url.openStream());
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return b;
		
		
	}
}
