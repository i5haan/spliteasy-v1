package services;

import javax.ws.rs.core.Response;

public interface ExpenseService 
{
	public Response addExpense();
	public Response removeExpense();
	public Response updateExpense();
	public Response deleteExpense();

}
