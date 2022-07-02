package com.shikha.core.service.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.DownloadPDFService;
import com.shikha.core.service.UserRoomService;

@Component(service = DownloadPDFService.class, immediate = true)
public class DownloadPDFServiceImpl implements DownloadPDFService {

	@Reference
	UserRoomService userRoomService;
	@Reference
	ResourceResolverFactory factory;
	
	

	public void createPdf(ByteArrayOutputStream boas, int userId, String userName) {
		Document document = new Document();
		try {
			//Image img=readImageFromDam();
			List<Room> rooms=userRoomService.getAllRoomsOfCurrentUser(userId);
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Phrase(userName));
			paragraph.setMultipliedLeading(2F);//to avoid overlapping of lines
			paragraph.setSpacingBefore(20); // to provide space before paragraph
			paragraph.setSpacingAfter(20);// to provide space after paragraph

			document.setPageSize(PageSize.A4);//write this line before document.open();portrait

			PdfWriter.getInstance(document, boas);
			document.setPageSize(PageSize.A4);//write this line before document.open();portrait

			document.open();
			
			
			PdfPTable table = new PdfPTable(3);
			float[] columnWidth=new float[3];
			for(int i = 0;i<3;i++){
				columnWidth[i]=20F;
			}
			table.addCell(new PdfPCell(new Paragraph(new Phrase("Room No"))));
			table.addCell(new PdfPCell(new Paragraph(new Phrase("Room Name"))));
			table.addCell(new PdfPCell(new Paragraph(new Phrase("Room Price"))));
			//document.add(img);
			document.add(paragraph);
			
			document.add(table);
			document.setPageSize(PageSize.A4.rotate());//landscape
			//if we will not add any content to the page, new page wont be created.
			document.newPage();
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			table1.setWidths(columnWidth);
			
			for(Room r:rooms){
				table1.addCell(new PdfPCell(new Paragraph(new Phrase(Integer.toString(r.getRoomNo())))));
				table1.addCell(new PdfPCell(new Paragraph(new Phrase(r.getRoomName()))));
				table1.addCell(new PdfPCell(new Paragraph(new Phrase("1500"))));
	}
			//table.setKeepTogether(true);//
		
			document.add(table1);
		} catch (DocumentException | ServiceException e) {

			e.printStackTrace();
		} finally {

			if (document != null && document.isOpen())
				document.close();

		}

	}
	
	public Image readImageFromDam(){
		Image image=null;
		try {
			URL url=new URL("http://localhost:4503/content/dam/shikha/room2image.jpg");
			 image = Image.getInstance(url);
		} catch (BadElementException | IOException e) {
		  e.printStackTrace();
		}
		return image;
		
		
	}
}
