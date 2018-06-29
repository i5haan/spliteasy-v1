package servicesImpl;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import model.MapUserLogin;
import model.UserInfo;
import services.UserService;
import util.GroupUtil;
import util.UserUtil;

@Path("/")
public class UserServiceImpl implements UserService
{
	UserUtil userutil = new UserUtil();
	MapUserLogin lMap= new MapUserLogin();
	GroupUtil grouputil = new GroupUtil();
	
	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signUp(@FormParam("email") String email, @FormParam("password") String password, @FormParam("name") String name, @FormParam("photo") String photo)
	{
		System.out.println("signup");
		int res = userutil.create(name,email,password);
		if(res == 1)
		{
			URI uri = UriBuilder.fromPath("/spliteasy/login.jsp").build();
			return Response.seeOther(uri).build();
		}
		else
		if(res == 0)
		{
			/*return Response.ok()
					.entity("Email ID already registered!!")
					.build();*/
			URI uri = UriBuilder.fromPath("/spliteasy/signup.jsp").queryParam("q", 0).build();
			return Response.seeOther(uri).build();
		}
		else
		{
			return Response.ok()
					.entity(null)
					.build();
		}
		
	}
	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("email") String email, @FormParam("password") String password)
	{
		
		int userid = userutil.validate(email,password);
		if(userid != 0)
		{
			String sid = Integer.toString(userid) ;
		    URI uri = UriBuilder.fromPath("/spliteasy/welcome.jsp")
		            .queryParam("sid", sid)
		            .build();
		    return Response.seeOther(uri).build();
		}
		return Response.ok()
				.entity("Invalid Credentials!")
				.build();
	}	
	
	@POST
	@Path("updateprofile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateProfile(@FormParam("name") String name, @FormParam("id") int id)
	{
		if(id != 0)
		{
			if(lMap.checkMapData(Integer.toString(id)))
			{
				
			userutil.updateUser(name,id);
		    URI uri = UriBuilder.fromPath("/spliteasy/hello.jsp")		            
		            .build();
		    return Response.seeOther(uri).build();
		}
			}
		return Response.ok()
				.entity("Errr!")
				.build();
	}	
	
	@POST
	@Path("changepassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response changePassword(@FormParam("id") int id, @FormParam("password") String password, @FormParam("npassword") String npassword, @FormParam("cpassword") String cpassword)
	{
		if(id != 0)
		{
			boolean res = userutil.changePassword(id,password,npassword,cpassword);
			if(res){
			String sid = Integer.toString(id) ;
		    URI uri = UriBuilder.fromPath("/spliteasy/hello.jsp")
		    		.queryParam("sid", sid)
		            .build();
		    return Response.seeOther(uri).build();
			}
		}
		return Response.ok()
				.entity("Errr!")
				.build();
	}	
	
	@GET
	@Path("logout")
	public Response logout()
	{
			boolean res = userutil.logout();
			if(res){

		    URI uri = UriBuilder.fromPath("/spliteasy/logout.jsp")
		            .build();
		    return Response.seeOther(uri).build();
			}
		
		return Response.ok()
			.entity(null)
			.build();
	}	
	
	@POST
	@Path("signmeup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signUpEmail(@FormParam("email") String email, @FormParam("password") String password, @FormParam("name") String name, @FormParam("gid") String gId, @FormParam("photo") String photo)
	{
		int res = userutil.create(name,email,password);
		if(res == 1)
		{
			boolean result = grouputil.addMember(email, gId);
			if(result)
			{
				int uID = Integer.parseInt(userutil.findUserid(email));
				UserInfo.userid = uID;
				URI uri = UriBuilder.fromPath("/spliteasy/welcome.jsp").queryParam("g", gId).build();
				return Response.seeOther(uri).build();
			}
			else
				return Response.ok().entity(null).build();
		}
		else
		if(res == 0)
		{
			/*return Response.ok()
					.entity("Email ID already registered!!")
					.build();*/
			URI uri = UriBuilder.fromPath("/spliteasy/signup.jsp").queryParam("q", 0).build();
			return Response.seeOther(uri).build();
		}
		else
		{
			return Response.ok().entity(null).build();
		} 
	}
	
}


