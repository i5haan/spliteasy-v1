package servicesImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import services.GroupService;
import util.GroupUtil;

@Path("/group")
public class GroupServiceImpl implements GroupService
{
	GroupUtil grouputil = new GroupUtil();
	
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(@FormParam("name") String gName, @FormParam("members") List<String> members)
	{
		System.out.println(gName);
		boolean res=grouputil.create(gName,"26",members);
		if(res)
		{
			return Response.ok()
					.entity(true)
					.build();
		}
		else
		{
			return Response.ok()
					.entity(false)
					.build();
		}
		
	}
	
	
	@POST
	@Path("/{id}/user")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addMember(@PathParam("id") String gId, @FormParam("email") String email)
	{
		System.out.println(gId);
		System.out.println(email);
		boolean res=grouputil.addMemeber(email, gId);
		return Response.ok()
				.entity(res)
				.build();
	}
	
}
