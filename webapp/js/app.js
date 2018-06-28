var gid;
document.querySelector("div.expensefocus").addEventListener("click",function(){
				$("div.expensefocus").addClass("hide")
			})
			
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
		
var loadGroupList=function(data){
	
	
	data.forEach(function(group){
		$("div.list-group").append("<button class='list-group-item groupbutton' value='"+group.grpid+"'>"+group.gname+"</button>")
	})
	$("button.groupbutton").unbind().click(function(){
		$(".f-container").addClass("hide")
		gid=$(this).attr("value")
		$.get("/spliteasy/webapi/group/"+gid,function(data){
			$(".eformtoggle").removeClass("hide")
			$("#splitmember").html('<span class="col"><b>MEMBERS</b></span><span class="col"><b>SHARE</b></span><br>')
			$(".noexpense").addClass("hide")
			$(".expensepanel").addClass("hide")
			$(".expensefocus").addClass("hide")
			$(".groupheading").text(data.gname)
			$("#groupusercreate").text("Created by "+data.user)
			$("#groupuserdate").text("Create On "+data.created_at.slice(0,10))
			loadExpense(gid)
	});
		
		
	loadForm(gid);
	})
}
		
		
		
		
$(".eformtoggle").unbind().click(function(){
	$(".f-container").toggleClass("hide")
})

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
				$("#split").append("<li class='splitlist'>"+str+"</li>")
			}
			else if(s.name==data.paidBy){
				str=s.name+" Has paid Rs. "+data.amount+" and owes Rs. "+s.s_amt
				console.log(str)
				$("#split").append("<li class='splitlist'>"+str+"</li>")
			}
			else if(s.s_amt==0)
			{
				
			}
			else{
				str=s.name+" borrowed Rs. "+s.s_amt
				console.log(str)
				$("#split").append("<li class='splitlist'>"+str+"</li>")
			}
			
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
				
				$("div.expenses").append("<div class='col-md-12 expense' value='"+e.eid+"'><h4>"+e.paidBy+" spent Rs "+e.amount+" for "+e.ename+" on "+e.created_at.slice(0,10)+"</h4></div>")
				$("div.expense").unbind().click(function(){
					$("div.expensefocus").removeClass("hide")
					var id=$(this).attr("value")
					loadExpenseInfo(gid,id)
				})
			})
		}
	});
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
					loadExpense(gid)
				}
		}
	})
	console.log(sendData)
	console.log(route)
})