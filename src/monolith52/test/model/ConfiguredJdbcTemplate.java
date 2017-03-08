package monolith52.test.model;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@ApplicationScoped
public class ConfiguredJdbcTemplate extends JdbcTemplate {

	@PostConstruct
	public void configure() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		DriverManagerDataSource source = new DriverManagerDataSource(
				// "jdbc:mysql://localhost/bookStore", "test", "test");
				"jdbc:sqlite:C:/project/SpringTest/sqlite3/bookStore.sqlite3");
		source.setDriverClassName("org.sqlite.JDBC");
		setDataSource(source);
	}
}
