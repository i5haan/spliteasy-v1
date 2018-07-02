<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="java.sql.*" %>

<%
   File file ;
   String ff = "",id = "";
   int maxFileSize = 5000 * 1024;
   int maxMemSize = 5000 * 1024;
   ServletContext context = pageContext.getServletContext();
   String filePath =  context.getInitParameter("file-upload");

 
   // Verify the content type
   String contentType = request.getContentType();
   if ((contentType.indexOf("multipart/form-data") >= 0)) 
   {
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("C:\\Temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );
      try{ 
         // Parse the request to get file items.
         List<FileItem> fileItems = upload.parseRequest(request);

         // Process the uploaded file items
         Iterator<FileItem> i = fileItems.iterator();

        
         while ( i.hasNext () ) 
         {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () )	
            {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
			ff=fileName;
			boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){
            file = new File( filePath + 
            fileName.substring( fileName.lastIndexOf("\\"))) ;
            }
			else{
			
            file = new File( filePath + 
            fileName.substring(fileName.lastIndexOf("\\")+1)) ;
            }
           // out.println("Uploaded Filename: " + filePath +                     fileName + "<br>");
            fi.write( file ) ;
           // out.println("Uploaded Filename: " + filePath +             fileName + "<br>");
		   
		 
            }
			else
			{
				String fieldName = fi.getFieldName();
            	String fileName = fi.getName();
				id = fieldName;
			}
         }
         
        String url = "jdbc:mysql://localhost:3306/spliteasy";
 		String username = "root";
 		String password = "pwd";
		Connection con = null;
		Statement stmt = null;
		try{
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(url, username, password);
				stmt=con.createStatement();
				String sql="update user set photo = '" + ff + "' where userid = "+ id;
				stmt.executeUpdate(sql);
			}
		catch(Exception e)
		{
			out.print(e);
		}
	
		response.sendRedirect("items.jsp");
       
      }catch(Exception ex) {
         System.out.println(ex);
      }
   }
else
{
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      out.println("<p>No file uploaded</p>"); 
      out.println("</body>");
      out.println("</html>");
}
%>