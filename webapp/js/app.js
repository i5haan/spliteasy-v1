document.querySelector("div.expensefocus").addEventListener("click",function(){
				$("div.expensefocus").addClass("hide")
			})
			
			$.get("/spliteasy/webapi/group",function(data){
				data.forEach(function(group){
					$("div.list-group").append("<button class='list-group-item groupbutton' value='"+group.grpid+"'>"+group.gname+"</button>")
				})
				$("button.groupbutton").unbind().click(function(){
					var gid=$(this).attr("value")
					$.get("/spliteasy/webapi/group/"+gid,function(data){
						$(".noexpense").addClass("hide")
						$(".expensepanel").addClass("hide")
						$(".expensefocus").addClass("hide")
						$(".groupheading").text(data.gname)
						$("#groupusercreate").text("Created by "+data.user)
						$("#groupuserdate").text("Create On "+data.created_at.slice(0,10))
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
									})
								})
							}
						});
				});
			})
		})
			
