<%@ page import="java.sql.*" %>
<%@ page import="model.UserInfo" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>Update</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="stylesheet/app.css">
        <script
          src="https://code.jquery.com/jquery-3.3.1.min.js"
          integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
          crossorigin="anonymous"></script>
        </head>
    <body>
     <%
   
           int userid= UserInfo.userid;        		
    String name="",image="";
    Connection con = null;
    String url = "jdbc:mysql://localhost:3306/spliteasy";
	String username = "root";
	String password = "pwd";
	try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, username, password);
		try
		{
			String q="select * from user where userid=?";
			PreparedStatement mystatement=con.prepareStatement(q);
			mystatement.setInt(1,userid);
			ResultSet myres=mystatement.executeQuery();
			if(myres.next())
			{
				name=myres.getString("name");
				image=myres.getString("photo");
			}
			else
			{
				out.print("Data not found");
			}
		}
			catch(Exception e)
			{
				out.print(e.getMessage());
			}
		
		}
		
	catch(Exception e) {
		out.print(e.getMessage());
	}
	finally
	{
		con.close();
	}
    %>
    
    
            <nav class="navbar navbar-inverse">
          <div class="container-fluid container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#"><b>SPLITEASY</b></a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul class="nav navbar-nav navbar-right">
                <li><a href="/spliteasy/updateuser.jsp"><b>UPDATE PROFILE</b></a></li>
                <li><a href="/spliteasy/changepassword.jsp"><b>CHANGE PASSWORD</b></a></li>
                <li><a href="/spliteasy/webapi/logout"><b>LOGOUT</b></a></li>
              </ul>
            </div><!-- /.navbar-collapse -->
                     </div><!-- /.container-fluid -->
        </nav>

        <div class="container">
            <h1 class="form-heading">UPDATE PROFILE</h1>
            <div class="form-container">
                <form method="post" action="/spliteasy/webapi/updateprofile">
                    <div class="form-group">
                        <p class="form-label">Name</p>
                        <input class="form-control" type="text" name="name" placeholder="Name" value='<%=name%>'>
                    </div>
                   <input type="hidden" name="id" value='<%=userid%>'>
                    
                    <div class="form-group">
                        <p class="form-label">Photo</p>
                        <input class="form-control" type="text" name="photo" placeholder="Photo URL" value='<%=image%>'>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-lg btn-success btn-block"><b>SUBMIT</b></button>
                    </div>
                </form>
                <a href="/" class="btn btn-lg btn-default btn-block"><b>GO BACK<b></a>
            </div>
    
    
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <p class="bottom">Trademark SplitEasy</p> 
    </body>
</html>
        
        