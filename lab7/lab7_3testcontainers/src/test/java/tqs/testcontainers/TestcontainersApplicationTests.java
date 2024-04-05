package tqs.testcontainers;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import tqs.testcontainers.data.Book;
import tqs.testcontainers.data.BookRepository;

@Testcontainers
@SpringBootTest
class TestcontainersApplicationTests {

	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("ricardo")
			.withPassword("teste")
			.withDatabaseName("testcontainers");
	
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}

	@Autowired
	private BookRepository bookRepository;

	@Test
	@Order(1)
	void saveBook() {
		Book book = new Book("The Hobbit", "J.R.R. Tolkien", LocalDate.of(1937, 9, 21));
		bookRepository.save(book);
	}

	@Test
	@Order(2)
	void getBook() {
		Book book = bookRepository.findByTitle("The Hobbit");
		assert book != null;
		assert book.getAuthor().equals("J.R.R. Tolkien");
		assert book.getPublicationDate().equals(LocalDate.of(1937, 9, 21));
	}

}