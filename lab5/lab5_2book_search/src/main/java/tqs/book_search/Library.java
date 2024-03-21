package tqs.book_search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
	private final List<Book> store = new ArrayList<>();

	public void addBook(final Book book) {
		store.add(book);
	}

	public List<Book> findBooksBetweenDates(final LocalDate from, final LocalDate to) {
		LocalDate end = to.plusYears(1);

		return store.stream()
				.filter(book -> from.isBefore(book.getPublished()) && end.isAfter(book.getPublished()))
				.sorted(Comparator.comparing(Book::getPublished).reversed())
				.collect(Collectors.toList());
	}

	public List<Book> findBooksByTitle(final String title) {
		return store.stream()
				.filter(book -> book.getTitle().contains(title))
				.collect(Collectors.toList());
	}

    public List<Book> findBooksByAuthor(String author) {
		return store.stream()
				.filter(book -> book.getAuthor().equals(author))
				.collect(Collectors.toList());
    }
}