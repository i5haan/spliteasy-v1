<!DOCTYPE HTML>
<html>
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>SplitEasy</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
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
    <div class="col-md-6">
      <p class="lead"><em>Your Groups</em></p>
      <div class="list-group">
        
      </div>
      <div class="gf-container">
            <h1 class="form-heading"><b>CREATE GROUP</b></h1>
            <div class="form-container">
                <div method="post" action="/spliteasy/webapi/group">
                    <div class="form-group">
                        <p class="form-label">GROUP NAME</p>
                        <input class="form-control gnamesend" type="text" name="name" placeholder="Enter the group Name" required="">
                    </div>
                   <div class="form-group"> 
                   	<div id="textboxDiv"></div>  
                   </div>
                   <div class='form-group'><p class='form-label'>ENTER MEMBER NAME</p><input class='form-control members' name="members" placeholder='Enter the Member Name' type='text'/></div>
                   <div class="form-group"><button id="Add"><!-- <img  height="42" width="42" src="images/add_member.png" /> --><i class="fas fa-plus"></i></button></div>
                    
                    <div class="form-group">
                        <button class="btn btn-lg btn-success btn-block btn-group-add"><b>SUBMIT</b></button>
                    </div>
                </div>
            </div>
    </div>
    </div>
    <div class="col-md-6">
            <div class="thumbnail">
              <div class="caption-full">
              <button class="eformtoggle btn btn-success pull-right hide">Add Expense</button>
              <button class="settleuptoggle btn btn-danger pull-right hide">Settle Up</button>
                <h2 class="groupheading">Select a Group from the List!!</h2>
                
                <h4><em><span id="groupusercreate"></span></em></h4>
                <p><span id="groupuserdate"></span></p>
              </div>

            </div>
            
            <div class="f-container">
            	<h3 class="form-heading">ADD EXPENSE</h3>
	            <div class="form-container">
	                <div method="post" action="#" id="expenseformdata">
	                    <div class="form-group">
	                        <p class="form-label">Enter a description</p>
	                        <input class="form-control ename" type="text" name="name" placeholder="Enter a description" required="">
	                    </div>
	                    <div class="form-group">
	                        <p class="form-label">Enter the amount</p>
	                        <input class="form-control eamount" type="text" name="amount" placeholder="Enter the amount" required="">
	                    </div>
	                    <div id="splitmember" "form-group" >
	                    	<span class="col"><b>MEMBERS</b></span><span class="col"><b>SHARE</b></span><br>
	                    </div>
	                   <br><br> 
	                    <div class="form-group">
	                        <button class="btn btn-lg btn-success btn-block expensesubmit">Add Expense</button>
	                    </div>
	                    
	                </div>
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
            	<div id="split">
            		
            	</div>
      		</div>
      		<div class="settleupfocus hide">
            	<h2>You can Settle Up in the following ways!</h2>
            	<hr>
            	<div id="settle">
            		
            	</div>
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