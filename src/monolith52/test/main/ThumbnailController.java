package monolith52.test.main;

import java.io.ByteArrayInputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import monolith52.test.model.BookDAOImpl;

@Path("thumbnail/")
@RequestScoped
public class ThumbnailController {

	@Inject private BookDAOImpl bookDAO;
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") Integer id) {
		byte[] bytes = bookDAO.findThumbnailByBookId(id);
		if (bytes == null) {
			return Response.status(404).build();
		}
		
		ResponseBuilder response = Response.ok(new ByteArrayInputStream(bytes), MediaType.valueOf("image/jpeg"));
		response.header("ContentLength", bytes.length);
		return response.build();
	}
	
}
