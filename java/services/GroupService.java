package services;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public interface GroupService {
	public Response add(String gName, List<String> members);
	public Response addMember(String gId, String email);
//	public Response edit();
	public Response getGroup(String gId);
	public Response getAllGroups();
}
