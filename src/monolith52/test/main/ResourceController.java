package monolith52.test.main;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("index_files/")
public class ResourceController {
	
	@Context private HttpServletRequest request;
	
	@GET
	@Path("{id}")
	public Response getImage(@PathParam("id") String imageId) {
		String basedir = request.getServletContext().getRealPath("");
		String target = new File(request.getRequestURI()).getName();
		File file = new File(basedir + "/index_files/" + target);

		ResponseBuilder response = Response.ok((Object) file);
		if (target.endsWith(".png")) response.type(MediaType.valueOf("image/png"));
		if (target.endsWith(".jpg")) response.type(MediaType.valueOf("image/jpeg"));
		
		return response.build();
	}
}
