var gid;
document.querySelector("div.expensefocus").addEventListener("click",function(){
				$("div.expensefocus").addClass("hide")
			})
			
var init=function(){
	
	$(".groupheading").text("Select a Group from the List!!")
	$("#groupusercreate").text("")
	$("#groupuserdate").text("")
	$(".memberlist").text("")
	$("div.addmemberinputform").addClass("hide")
	$(".eformtoggle").addClass("hide")
	$(".settleuptoggle").addClass("hide")
	$(".deletegroup").addClass("hide")
	
	$(".settleupfocus").addClass("hide")
	$(".noexpense").addClass("hide")
	$(".expensepanel").addClass("hide")
	$(".f-container").fadeOut(0)
	$(".gf-container").fadeOut(0)
	$("div.list-group").text("")
	$.get("/spliteasy/webapi/group",function(data){
		
		if(data.status && data.status=="F")
		{
			alert(data.message)
			window.location="/spliteasy/login.jsp"
		}
		else
		{
			loadGroupList(data)
		}
		
	})
}
			

		
var loadGroupList=function(data){
	 
	data.forEach(function(group){
		$("div.list-group").append("<button class='list-group-item groupbutton' value='"+group.grpid+"'>"+group.gname+"</button>")
	})
	$("div.list-group").append("<button class='list-group-item addgroupbutton active' >Add New Group</button>")
	$(".addgroupbutton").unbind().click(function(){
		$(".gf-container").fadeToggle(500)
	})
	$("button.groupbutton").unbind().click(function(){
		$(".settleupfocus").addClass("hide")
		$(".f-container").fadeOut(0)
		gid=$(this).attr("value")
		loadGroup(gid)
		loadForm(gid)
	})
}
		
		
		
		
$(".eformtoggle").unbind().click(function(){
	$(".f-container").fadeToggle(500)
	ascrollto("f-container")
})

var loadGroup=function(gid){
	$.get("/spliteasy/webapi/group/"+gid,function(data){
		$(".eformtoggle").removeClass("hide")
		$(".settleuptoggle").removeClass("hide")
		$(".deletegroup").removeClass("hide")
		$("#splitmember").html('<span class="col"><b>MEMBERS</b></span><span class="col"><b>SHARE</b></span><br>')
		$(".noexpense").addClass("hide")
		$(".expensepanel").addClass("hide")
		$(".expensefocus").addClass("hide")
		$(".groupheading").text(data.gname)
		$("#groupusercreate").text("Created by "+data.user)
		$("#groupuserdate").text("Create On "+data.created_at.slice(0,10))
		$(".memberlist").text("")
		$(".memberlist").append("<p><strong>Group Members List</strong></p>")
		data.member.forEach(function(m){
			$(".memberlist").append("<p><span class='colm'>"+m.name+"</span><span class='colm'><button class='btn btn-xs btn-danger btn-remove-group-member' data='"+m.userid+"' route='/spliteasy/webapi/group/"+gid+"/user'>Delete This member</button></span></p>")
		})
		$(".btn-remove-group-member").unbind().click(function(){
			var p=confirm("Removing a member can't be undone. Are you sure?")
			if(p){
				var route=$(this).attr("route")
				var data="uid="+$(this).attr("data")
				$.ajax({
					url:route,
					type:"DELETE",
					data:data,
					success:function(res){
						if(res.status && res.status=="F"){
							alert(res.message)
						}
						else{
							alert(res.message)
							init()
						}
					}
				})
			}
		})
		$
		$(".memberlist").append("<button class='btn btn-xs addmembertoggle'>Add a member</button>")
		$("div.addmemberinputform").addClass("hide")
		$(".addmembertoggle").unbind().click(function(){
			$("div.addmemberinputform").toggleClass("hide")
		})
		ascrollto("caption-full")
		loadSettleUp(gid)
		loadExpense(gid)
});
}

var loadForm=function(gid){
	$.get("/spliteasy/webapi/group/"+gid,function(group){
		group.member.forEach(function(member){
			$("#splitmember").append('<div><span class="col">'+member.name+'</span><span class="col"><input value=1 type="number" name="ratio" min=0 max=10 required placeholder=1 class="ratio"></span></div>')
		})
	})
	
	$("div#expenseformdata").attr("action","/spliteasy/webapi/group/"+gid+"/expense")
}


var loadExpenseInfo =function(gid,id){
	$.get("/spliteasy/webapi/group/"+gid+"/expense/"+id,function(data){
		console.log((data))
		$("#ename").text(data.ename)
		$("#createdby").text("Created by "+data.paidBy)
		$("#createdon").text("Created on "+data.created_at.slice(0,10))
		$("#amount").text("Rs. "+data.amount)
		$("#split").text("")
		data.split.forEach(function(s){
			var str=""
			if(s.name==data.paidBy && s.s_amt==0){
				str=s.name+" Has paid Rs. "+data.amount
				console.log(str)
				$("#split").append("<p class='splitlist'>"+str+"</p>")
			}
			else if(s.name==data.paidBy){
				str=s.name+" Has paid Rs. "+data.amount+" and owes Rs. "+s.s_amt
				console.log(str)
				$("#split").append("<p class='splitlist'>"+str+"</p>")
			}
			else if(s.s_amt==0)
			{
				
			}
			else{
				str=s.name+" borrowed Rs. "+s.s_amt
				console.log(str)
				$("#split").append("<p class='splitlist'>"+str+"</p>")
			}
			ascrollto("expensefocus")
			
		})
	})
}

var loadExpense=function(gid){

	$.get("/spliteasy/webapi/group/"+gid+"/expense",function(data){
		if(data.length==0){
			$("div.noexpense").removeClass("hide")
		}
		else{
			$("div.expensepanel").removeClass("hide")
			$("div.expenses").text("")
			data.forEach(function(e){
				
				$("div.expenses").append("<div class='col-md-12 expense' value='"+e.eid+"'><h4>"+e.paidBy+" spent Rs "+e.amount+" for "+e.ename+" on "+e.created_at.slice(0,10)+"<br><button class='btn btn-xs btn-danger dltexpense' route='/spliteasy/webapi/group/"+gid+"/expense/"+e.eid+"'>Delete This Expense</button></h4></div>")
				$("div.expense").unbind().click(function(){
					$("div.expensefocus").removeClass("hide")
					var id=$(this).attr("value")
					loadExpenseInfo(gid,id)
				})
				$("button.dltexpense").unbind().click(function(e){
					e.stopPropagation();
					var id=$(this).attr("route")
					$.ajax({
					    url: id,
					    type: 'DELETE',
					    success: function(res) {
					       if(res.status && res.status=="F"){
					    	   alert(res.message)
					       }
					       else{
					    	   alert(res.message)
					    	   $(".expensepanel").addClass("hide")
					    	   loadExpense(gid)
					    	   loadSettleUp(gid)
					       }
					    }
					});
				})
				
			})
		}
		
	});
}

var loadSettleUp=function(gid){
	$.get("/spliteasy/webapi/group/"+gid+"/expense/settleup",function(data){
		$("div#settle").text("")
			if(data.length==0)
				{
					$(".settleupfocus h2").text("You Are All Settled Up!")
				}
			else
				{
				$(".settleupfocus h2").text("Settle Up using the following Scenarios")
					data.forEach(function(d){
						if(d.amount!=0)
							{
								$("div#settle").append("<p>"+d.paidBy+" can pay Rs "+d.amount+" to "+d.paidTo+"</p><button class='btn btn-xs setup' u1='"+d.padiToId+"' u2='"+d.paidById+"' route='/spliteasy/webapi/group/"+gid+"/expense/settleup'>Settle This Amount</button>")
								$("button.setup").unbind().click(function(){
									var u1=$(this).attr("u1")
									var u2=$(this).attr("u2")
									var route=$(this).attr("route")
									var sendData={
										u1:u1,
										u2:u2
									}
									console.log($.param(sendData,true))
									console.log(route)
									$.ajax({
										url:route,
										data:$.param(sendData,true),
										type:"POST",
										success:function(res){
											alert("Successfully Settleup!")
											loadSettleUp(gid)
										}
									})
								})
							}
					})
				}
		})
}

$(".expensesubmit").unbind().click(function(){
	var arr=[]
	$(".ratio").each(function(){
		arr.push(Number($(this).val()))
	})
	var name=$(".ename").val()
	var amount=$(".eamount").val()
	var route=$("#expenseformdata").attr("action")
	var sendData={
		name:name,
		amount:Number(amount),
		ratio:arr
	}
	console.log($.param(sendData,true))
	$.ajax({
		url:route,
		data:$.param(sendData,true),
		type:"POST",
		success:function(res){
			if(res.status && res.status=="F")
				{
					alert(res.message);
				}
			else
				{
					alert("Expense Created Successfully!")
					$(".f-container").fadeOut(0)
					$(".ename").val("")
					$(".eamount").val("")
					$(".noexpense").addClass("hide")
					$(".ratio").each(function(){
						$(this).val(1)
					})
					loadExpense(gid)
					loadSettleUp(gid)
					$(".noexpense").addClass("hide")
				}
		}
	})
	console.log(sendData)
	console.log(route)
})


function ascrollto(id) {
	var etop = $('.' + id).offset().top;
	$('html, body').animate({
	  scrollTop: etop
	}, 1000);
}



$(document).ready(function() {  
    $("#Add").on("click", function() {  
        $("#textboxDiv").append("<div class='form-group'><p class='form-label'>ENTER MEMBER EMAIL</p><input class='form-control members' name=\"members\" placeholder='Enter Member Email' type='email'/></div>");  
    });    
});


$(".settleuptoggle").unbind().click(function(){
	$(".settleupfocus").toggleClass("hide")
	ascrollto("settleupfocus")
})

$(".btn-group-add").unbind().click(function(){
	var arr=[]
	$(".members").each(function(){
		arr.push($(this).val())
	})
	var name=$(".gnamesend").val()
	var route="/spliteasy/webapi/group"
	var sendData={
		name:name,
		members:arr
	}
	console.log($.param(sendData,true))
	$.ajax({
		url:route,
		data:$.param(sendData,true),
		type:"POST",
		success:function(res){
			if(res.status && res.status=="F"){
				alert(res.message);
			}
			else{
				alert(res.message)
				$(".gnamesend").val("")
				$("#textboxDiv").text("")
				$("#textboxDiv").html("<div class='form-group'><p class='form-label'>ENTER MEMBER EMAIL</p><input class='form-control members' name=\"members\" placeholder='Enter Member Email' type='email'/></div>");  

				init()
			}
			
		}
	})
	console.log(sendData)
	console.log(route)
})

$("button.btn-group-member-add").unbind().click(function(){
	var route="/spliteasy/webapi/group/"+gid+"/member"
	var email=$(".addmemberinput").val()
	console.log(route)
	console.log(email)
	$.ajax({
		url:route,
		data:"email="+email,
		type:"POST",
		success:function(res){
			if(res.status && res.status=="F"){
				alert(res.message);
			}
			else{
				alert(res.message)
				$
				loadGroup(gid)
				loadForm(gid)
			}
			
		}
	})
})

$(".deletegroup").unbind().click(function(){
	var p=confirm("Are you sure, the group info would be lost and can't be recovered back!")
	if(p){
		$.ajax({
			type:"DELETE",
			url:"/spliteasy/webapi/group/"+gid,
			success:function(res){
				if(res.status && res.status=="F"){
					alert(res.message)
				}
				else{
					alert(res.message)
					init()
				}
			}
		})
	}
})

init()
