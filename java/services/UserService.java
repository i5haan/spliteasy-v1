package services;

import javax.ws.rs.core.Response;

public interface UserService 
{
	public Response signUp(String email, String password, String name, String photo);	
	public Response login(String email, String password);
	public Response updateProfile(String name,int id);
	public Response changePassword(int id,String password,String npassword,String cpassword);
	public Response logout();
}
