package monolith52.test.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Viewable;

import monolith52.test.model.Book;
import monolith52.test.model.BookDAOImpl;

@Path("")
@RequestScoped
public class HelloController {

	@Inject private BookDAOImpl bookDAO;
	@Context private HttpServletRequest request;
	
	@GET
	@Path("page:{page}")
	public Viewable get(@PathParam("page") Integer page) {
		if (page < 1) page = 1;
		int count = bookDAO.getCount();
		int pageMax = Math.max(1, ((count-1)/30)+1);
		if (page > pageMax) page = pageMax;
		
		List<Book> books = bookDAO.select(30, (page -1)*30);
		List<Integer> bookPages = IntStream.range(1, pageMax+1).boxed().collect(Collectors.toList());
		Integer next = (page < pageMax) ? page+1 : null;
		Integer back = (page > 1) ? page-1 : null;
		
		Map<String, Object> model = new HashMap<>();
		model.put("books", books);
		model.put("title", "this is title");
		model.put("topPage", page);
		model.put("topBookPages", bookPages);
		model.put("topNext", next);
		model.put("topBack", back);
		
		return new Viewable("index.html", model);
	}
}
