<!DOCTYPE HTML>
<html>
    <head>
        <title>Create Group</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="stylesheet/app.css">
        <script
          src="https://code.jquery.com/jquery-3.3.1.min.js"
          integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
          crossorigin="anonymous"></script>
        </head>
    <body>
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
              <a class="navbar-brand" href="#"><b>SPLITEASY<b></a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><b>EDIT PROFILE</b></a></li>
                <li><a href="#"><b>LOGOUT</b></a></li>
              </ul>
            </div><!-- /.navbar-collapse -->
          </div><!-- /.container-fluid -->
        </nav>

        <div class="container">
            <h1 class="form-heading"><b>CREATE GROUP</b></h1>
            <div class="form-container">
                <form method="post" action="/spliteasy/webapi/group">
                    <div class="form-group">
                        <p class="form-label">GROUP NAME</p>
                        <input class="form-control" type="text" name="name" placeholder="Enter the group Name" required="">
                    </div>
                   <div class="form-group"> 
                   	<div id="textboxDiv"></div>  
                   </div>
                   <div class='form-group'><p class='form-label'>ENTER MEMBER NAME</p><input class='form-control' name="members" placeholder='Enter the Member Name' type='text'/></div>
                   <div class="form-group"><button id="Add"><img  height="42" width="42" src="images/add_member.png" /></button></div>
                    
                    <div class="form-group">
                        <button class="btn btn-lg btn-success btn-block"><b>SUBMIT</b></button>
                    </div>
                </form>
                <a href="/" class="btn btn-lg btn-default btn-block"><b>GO BACK</b></a>
            </div>
    
    <script>  
        $(document).ready(function() {  
            $("#Add").on("click", function() {  
                $("#textboxDiv").append("<div class='form-group'><p class='form-label'>ENTER MEMBER NAME</p><input class='form-control' name=\"members\" placeholder='Enter the Member Name' type='text'/></div>");  
            });    
        });  
    </script>  
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <p class="bottom">Trademark SplitEasy</p> 
    </body>
</html>
        
        