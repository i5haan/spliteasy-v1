<!DOCTYPE HTML>
<html>
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>SplitEasy</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="stylesheet/app.css">
        <script
          src="js/jquery-3.3.1.min.js"
          integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
          crossorigin="anonymous"></script>
	<script src="js/jquery.validate.min.js"></script>

        </head>
    <body><strong><font color="red"><center>
    <%
    	if(request.getParameter("q")!=null)
    	out.print("Email ID already registered!!");
    %></center></font></strong>
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
              <a class="navbar-brand" href="#">SplitEasy</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Login</a></li>
                <li><a href="#">Sign Up</a></li>
              </ul>
            </div><!-- /.navbar-collapse -->
          </div><!-- /.container-fluid -->
        </nav>

        <div class="container">
            <h1 class="form-heading">Sign Up</h1>
            <div class="form-container">
            
            <% if(request.getParameter("g")==null) { %>
                <form method="post" id="form1" action="/spliteasy/webapi/signup">
            <% } else { %>
           		<form method="post" id="form1" action="/spliteasy/webapi/signmeup">
           		<input type = "hidden" name="gid" value="<%=request.getParameter("g")%>" >
           		<% } %>
                    <div class="form-group">
                        <p class="form-label">Name</p>
                        <input class="form-control" type="text" name="name" placeholder="Name"  >
                    </div>
                    <div class="form-group">
                        <p class="form-label">Email</p>
                        <input class="form-control" type="text" name="email" placeholder="Email Address"  >
                    </div>
                    <div class="form-group">
                        <p class="form-label">Password</p>
                        <input class="form-control" type="password" name="password" id="password" placeholder="New Password"  >
                    </div>
                    <div class="form-group">
                        <p class="form-label">Re-type Password</p>
                        <input class="form-control" type="password" name="passwordretype" placeholder="Retype Password"  >
                    </div>
                    <div class="form-group">
                        <button  type="submit" class="btn btn-lg btn-success btn-block">Submit</button>
                    </div>
                </form>
                <a href="/" class="btn btn-lg btn-default btn-block">Go Back</a>
            </div>
             </div>
            <script>
    
    $(document).ready(function(){
        	$("#form1").validate({
        		rules: {
				name:{
        				required: true
        			},
				email:{
        				required: true
        			},
        			password:{
        				required: true,
					minlength:5
        			},
        			passwordretype:{
        				required:true,
					minlength:5,
					equalTo:"#password"
        			}
        		},
        		messages:{
				name:"<b><font color='#7f0000' face='Arial Black'>Please enter your Name</b>" ,
				email:"<b><font color='#7f0000' face='Arial Black'>Please enter your a valid email address</b>" ,
        			password:
				{
					required:"<b><font color='#7f0000' face='Arial Black'>Please enter your password</b>",
					minlength:"<b><font color='#7f0000' face='Arial Black'>Password should must be 5 char long</b>" ,
				} ,
        			passwordretype:{
        				required:"<b><font color='#7f0000' face='Arial Black'>Please enter your confirm password</b>",
					minlength:"<b><font color='#7f0000' face='Arial Black'>Password should must be 5 char long</b>" ,
        				equalTo:"<b><font color='#7f0000' face='Arial Black'>New and confirm password doesnot match</b>"
        			}
        		}
        	});
        });   
    </script>
       
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <p class="bottom">Trademark SplitEasy</p> 
    </body>
</html>
        
        