package servicesImpl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.SplitExpense;
import persistance.Expense;

@Path("expense")

public class ExpenseServiceImpl {
	
	@POST
	@Path("addExpense")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addExpense() 
	{
		return ;
		
	}
	
	
	@POST
	@Path("removeExpense")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response removeExpense() {
		
		return ;
		
	}
	
	@POST
	@Path("updateExpense")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateExpense() {
		
		return ;
		
		
	}
	


}
