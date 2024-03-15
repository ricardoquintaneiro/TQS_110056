package tqs.book_search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();

	@ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDate iso8601Date(String year, String month, String day) {
		return Utils.localDateFromDateParts(year, month, day);
	}

	@Given("book with the title '{string}', written by '{string}', published in {iso8601Date}")
	public void addNewBook(final String title, final String author, final Date published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}

	@When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
	public void setSearchParameters(final Date from, final Date to) {
		result = library.findBooks(from, to);
	}

	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}

	@Then("Book {int} should have the title '{string}'")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}
}