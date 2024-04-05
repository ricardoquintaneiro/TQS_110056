package tqs.testcontainers.data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public Book findById(long id);

    public Book findByTitle(String title);

    @NonNull
    public List<Book> findAll();

    public List<Book> findByAuthor(String author);

    public List<Book> findByPublicationDateBetween(LocalDate from, LocalDate to);

    public int countByAuthor(String author);

}
