package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Servlet implementation class UploadServer
 */
public class UploadtoServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 5MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	private static final String AMAZON_ACCESS_KEY = "your amazon access key";
    private static final String AMAZON_SECRET_KEY = "your amazon secret key";
    private static final String UUID_STRING = "uuid";
	private static String bucketName     = "text-apriori";
   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadtoServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stu
		
		// needed for cross-domain communication
		request.setAttribute("support",request.getParameter("support"));
	    request.setAttribute("confidence",request.getParameter("confidence"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
        PrintWriter writer = response.getWriter();
        ArrayList<String> var = new ArrayList<String>();
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        String uuidValue = UUID.randomUUID().toString().replaceAll("-", "");;
        FileItem itemFile = null;
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            // iterates over form's fields to get UUID Value
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equalsIgnoreCase(UUID_STRING)) {
                        uuidValue = item.getString();
                    }
                    //String str = item.getFieldName();
                    String value = item.getString();
                    var.add(value);
          
                }
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    itemFile = item;
                }
            }
            writer.println(" listvalues  = "+var);
          request.setAttribute("support",var.get(1));
          request.setAttribute("confidence",var.get(2));
          request.setAttribute("uuid",uuidValue);
          request.getRequestDispatcher("/waitingForReply.jsp").forward(request, response);
            if (itemFile != null) {
                // get item inputstream to upload file into s3 aws
 
                BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY);
 
                AmazonS3 s3client = new AmazonS3Client(awsCredentials);
                try {
 
                    ObjectMetadata om = new ObjectMetadata();
                    om.setContentLength(itemFile.getSize());
                    String ext = FilenameUtils.getExtension(itemFile.getName());
                    String keyName = uuidValue + '.' + ext;
 
                    s3client.putObject(new PutObjectRequest(bucketName, keyName, itemFile.getInputStream(), om));
                    s3client.setObjectAcl(bucketName, keyName, CannedAccessControlList.PublicRead);
 
                } catch (AmazonServiceException ase) {
                    ase.setErrorCode(uuidValue + ":error:" + ase.getMessage());
 
                } catch (AmazonClientException ace) {
                	System.out.println(uuidValue + ":error:" + ace.getMessage());
                }
 
 
            } else {
            	writer.println(uuidValue + ":error:" + "No Upload file");
            }
 
        } catch (Exception ex) {
        	writer.println(uuidValue + ":" + ":error: " + ex.getMessage());
        }
        System.out.println(uuidValue + ":Upload done");
        
       
       
        
    }
	

}
