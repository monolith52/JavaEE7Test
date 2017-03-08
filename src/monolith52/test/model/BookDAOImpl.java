package monolith52.test.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;

@ApplicationScoped
public class BookDAOImpl {
	
	@Inject private JdbcTemplate db;
	
	public boolean hasEntry(String siteId) {
		List<String> result = db.queryForList(
				"select siteId from `book` where siteId = ?", 
				String.class, siteId);
		return !result.isEmpty();
	}

	public boolean hasBinary(String siteId) {
		List<String> result = db.queryForList(
				"select siteId from `book` where siteId = ? and md5 is not null", 
				String.class, siteId);
		return !result.isEmpty();
	}
	
	public byte[] findThumbnailByBookId(Integer bookId) {
		byte[][] bytes = new byte[1][];
		db.query("select `thumbnail` from book where bookId = ?", (ResultSet rs) -> {
			bytes[0] = rs.getBytes("thumbnail");
		}, bookId);
		return bytes[0];
	}
	
	public int getCount() {
		return db.queryForObject("select count(bookId) from book", Integer.class); 
	}
	
	public List<Book> select(int limit, int offset) {
		return db.query("select `bookId`,`title`,`siteId`,`fileName`,`md5` from book order by `bookId` desc limit ? offset ?", this::mapRow, limit, offset);
	}
	
	private Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setBookId(rs.getInt("bookId"));
		book.setTitle(rs.getString("title"));
		book.setSiteId(rs.getString("siteId"));
		book.setFilename(rs.getString("fileName"));
		book.setMd5(rs.getString("md5"));
		return book;
	}
}
