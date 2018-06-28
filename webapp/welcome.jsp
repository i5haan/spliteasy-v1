<!DOCTYPE HTML>
<html>
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>SplitEasy</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="stylesheet/app.css">
        <script
          src="https://code.jquery.com/jquery-3.3.1.min.js"
          integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
          crossorigin="anonymous"></script>
          <style type="text/css">
          		
          		
          </style>
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
  <div class="row">
    <div class="col-md-3">
      <p class="lead"><em>Your Groups</em></p>
      <div class="list-group">
        
      </div>
    </div>
    <div class="col-md-9">
            <div class="thumbnail">
              <div class="caption-full">
                <h2 class="groupheading">Select a Group from the List!!</h2>
                <h4><em><span id="groupusercreate"></span></em></h4>
                <p><span id="groupuserdate"></span></p>
              </div>

            </div>
            <div class="expensefocus hide">
            	<h2 id="ename"></h2>
            	<em><div id="createdby"></div></em>
            	<em><div id="createdon"></div></em>
            	<h4 id="amount"></h4>
            	<hr>
            	Split Details
            	<hr>
            	<ul id="split">
            		
            	</ul>
      		</div>
            <div class="well hide expensepanel">
              <div class="text-center">
                <h3>Group Expenses</h3>
              </div>
              <hr>
                <div class="row expenses">
                	
                </div>
            </div>
            
            <div class="well hide noexpense">
              <div class="text-center">
                <h3>It seems you are having a boring life :P</h3>
              </div>
              <hr>
            </div>
            
          </div>
        </div>
      </div>
      


		<script type="text/javascript" src="js/app.js">
			


		</script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <p class="bottom">Trademark SplitEasy</p> 
    </body>
</html>