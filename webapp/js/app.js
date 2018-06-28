document.querySelector("div.expensefocus").addEventListener("click",function(){
				$("div.expensefocus").addClass("hide")
			})
	
			$.get("/spliteasy/webapi/group/72",function(data){
				console.log(data);
				$("h3.groupheading").text(data.gname)
				console.log(data.gname);
				$("span#groupusercreate").text(data.user)
				$("span#groupuserdate").text(data.created_at)
				$.get("/spliteasy/webapi/group/72/expense",function(data){
					console.log(data);
					data.forEach(function(e){
						console.log(e);
						$("div.expenses").append("<div class='col-md-12 expense' value='"+e.eid+"'><h4>"+e.paidBy+" spent Rs "+e.amount+" for "+e.ename+" on "+e.created_at+"</h4></div>")
						$("div.expense").unbind().click(function(){
							$("div.expensefocus").removeClass("hide")
							var id=$(this).attr("value")
							$.get("/spliteasy/webapi/group/72/expense/"+id,function(data){
								$("div.expensefocus").text(JSON.stringify(data))
							})
						})
					})
				});
			});