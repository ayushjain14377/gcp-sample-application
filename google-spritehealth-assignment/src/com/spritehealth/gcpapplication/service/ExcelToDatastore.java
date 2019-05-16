package com.spritehealth.gcpapplication.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.monitorjbl.xlsx.StreamingReader;
import com.spritehealth.gcpapplication.factory.DatastoreFactory;
import com.spritehealth.gcpapplication.factory.UserFactory;


/**
 * Servlet implementation class Upload
 */
@WebServlet(description = "This servlet store the uploaded excel data into Google Datastore.", urlPatterns = { "/ExcelToDatastore" })
@MultipartConfig
public class ExcelToDatastore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ExcelToDatastore.class.getName());
	Storage storage;
	DatastoreOptions options;
	Datastore datastore;

	String msg = "";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcelToDatastore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {

		storage = StorageOptions.getDefaultInstance().getService();
		datastore = DatastoreOptions.getDefaultInstance().getService();

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

		BlobInfo fileInfo = new CloudStorageService().UploadToGCS(request, response, DatastoreFactory.bucket, storage);
		Blob blob = storage.get(fileInfo.getBlobId());
		ReadChannel rc = blob.reader();
		InputStream inputStream = Channels.newInputStream(rc);

		Workbook workbook = StreamingReader.builder()
				.rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
				.bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
				.open(inputStream);   // InputStream or File for XLSX file (required)

		try {
			UserFactory user = new UserFactory();
			workbook_to_datastore_entity(workbook, user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (msg.equals("")) {
			PrintWriter out = response.getWriter(); response.setContentType("text/html");
			out.println("<html>"); 
			out.println("<body>"); 
			out.println("<br/>");
			out.println("Sucessfully Uploaded all Entries of Excel file.</br>");
			out.println("<a href='index.jsp'> Click here to Login </a>"); 
			out.println("</body>");
			out.println("</html>");
		}
		else if (msg.contains("Error")) {
			PrintWriter out = response.getWriter(); response.setContentType("text/html");
			out.println("<html>"); 
			out.println("<body>"); 
			out.println("<br/>");
			out.println(msg);
			out.println("<a href='ExcelToDatastore.jsp'> Click here to Upload again </a>"); 
			out.println("</body>");
			out.println("</html>");
			msg = "";

		}
		else {
			PrintWriter out = response.getWriter(); response.setContentType("text/html");
			out.println("<html>"); 
			out.println("<body>"); 
			out.println("<br/>");
			out.println(msg);
			out.println("Only Uploaded all unique Entries of Excel file.</br>");
			out.println("<a href='index.jsp'> Click here to Login </a>"); 
			out.println("</body>");
			out.println("</html>");
			msg = "";
		}
	}

	public String getKey(LinkedHashSet<String> property, ArrayList<String> values)  {
		int propertyCount = 0;
		for(String key : property) {
			if(key.equals("Username")) {
				return values.get(propertyCount);
			}
			propertyCount++;
		}

		return null;
	}

	public void addDataRowToDatastore(LinkedHashSet<String> properties,ArrayList<String> values) {
		
		System.out.println(properties);
		System.out.println(values);

		if (properties.size() == 5 && values.size() == 5 && (properties.size() == values.size()) ) {

			KeyFactory keyFactory = datastore.newKeyFactory().setKind(DatastoreFactory.KIND_NAME);
			String key = getKey(properties, values);
			Entity checkuser = datastore.get(keyFactory.newKey(key));

			if (checkuser != null) {
				System.out.println("user already exist");
				msg += "Cannot add" + checkuser.getString(DatastoreFactory.KEY_NAME) + " already exists. </br> ";

			}
			else {
				Entity.Builder userBuilder = Entity.newBuilder(DatastoreFactory.createEntityKey(datastore, key));
				Iterator<String> propertiesIterator = properties.iterator();
				for (int i = 0; i < values.size() && propertiesIterator.hasNext() == true; i++) {	
					userBuilder.set(propertiesIterator.next(), values.get(i));
				}

				Entity user = userBuilder.build();
				datastore.put(user);
			}

		}
		else {
			System.out.println("excel sheet wrong");
			msg += "<font color=\"red\">Error. All entries are mandatory. Please stictly follow the format given in <a href='https://storage.googleapis.com/spritehealth-gcp-application.appspot.com/SampleContent/Sample.xlsx'> Sample.xlsx </a> </font> </br>";
		}

	}

	public void workbook_to_datastore_entity(Workbook workbook, UserFactory user) throws Exception {

		for (Sheet sheet : workbook) { 
			Iterator<Row> sheetIterator = sheet.iterator();

			if (sheetIterator.hasNext()) { 		
				Row headerRow = sheetIterator.next(); 

				for (Cell headerValues : headerRow) {			
					user.getProperties().add(headerValues.getStringCellValue());

				} //header values iteration ends here 

			} //header row ends here 

			while (sheetIterator.hasNext()) {
				Row dataRows = sheetIterator.next(); 

				for (Cell dataValues : dataRows) {
					user.getValues().add(dataValues.getStringCellValue()); 
				}
				
				//System.out.println(user.getProperties());
				//System.out.println(user.getValues());

				addDataRowToDatastore(user.getProperties(), user.getValues());
				user.getValues().clear();

			} //sheet iteration ends here

		} //workbook loop ends here

	}

}
