package tqs.book_search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
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

	@DataTableType
	public Book bookEntry(Map<String, String> tableEntry) {
		return new Book(
				tableEntry.get("title"),
				tableEntry.get("author"),
				Utils.isoTextToLocalDate(tableEntry.get("published")));
	}

	@Given("I have a list of books")
	public void addListOfBooks(DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> columns : rows) {
			library.addBook(bookEntry(columns));
		}
	}

	@Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
	public void addNewBook(final String title, final String author, final LocalDate published) {
		library.addBook(new Book(title, author, published));
	}

	@When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
	public void setSearchParameters(final LocalDate from, final LocalDate to) {
		result = library.findBooksBetweenDates(from, to);
	}

	@When("I search for books by title {string}")
	public void searchByTitle(final String title) {
		result = library.findBooksByTitle(title);
	}

	@When("I search for books by author {string}")
	public void searchByAuthor(final String author) {
		result = library.findBooksByAuthor(author);
	}

	@Then("I should find {int} books")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}

	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}

}