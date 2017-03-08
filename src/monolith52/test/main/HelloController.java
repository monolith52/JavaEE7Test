package monolith52.test.main;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import monolith52.test.model.Book;
import monolith52.test.model.BookDAOImpl;

@Path("helloController")
@RequestScoped
public class HelloController {

	@Inject private BookDAOImpl db;
	
	@GET
	public String get() {
		List<Book> books = db.select(30, 0);
		
		StringBuffer result = new StringBuffer();
		books.forEach((book) -> {
			result.append(book.getTitle());
			result.append("\n");
		});
		return result.toString();
	}
}
