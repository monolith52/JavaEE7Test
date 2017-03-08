package monolith52.test.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import monolith52.test.model.Book;
import monolith52.test.model.BookDAOImpl;

@Path("helloController")
@RequestScoped
public class HelloController {

	@Inject private BookDAOImpl db;
	@Context private HttpServletRequest request;
	
	@GET
	@Template(name = "index.html")
	public Viewable get() {
		List<Book> books = db.select(30, 0);
		
		Map<String, Object> model = new HashMap<>();
		model.put("books", books);
		return new Viewable("index.html", model);
//		return "my test";
	}
}
