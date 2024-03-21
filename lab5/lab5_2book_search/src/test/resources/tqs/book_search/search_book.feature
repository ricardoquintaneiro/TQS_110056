@book_search
Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Background: List of books
    Given I have a list of books 
      | title                                           | author            | published   |
      | Refactoring                                     | Martin Fowler     | 1999-07-08  |
      | Clean Code                                      | Robert C. Martin  | 2008-08-01  |
      | Clean Architecture                              | Robert C. Martin  | 2017-09-01  |
      | Agile Software Development                      | Robert C. Martin  | 2002-10-01  |
      | Patterns of Enterprise Application Architecture | Martin Fowler     | 2002-11-15  |

  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-03-15
      And a book with the title 'Some other book', written by 'Tim Tomson', published in 2014-07-23
      And a book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-01-01
    When the customer searches for books published between 2013-01-01 and 2014-12-31
    Then I should find 2 books
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'One good book'

  Scenario: Search by author
    When I search for books by author "Martin Fowler"
    Then I should find 2 books

  Scenario: Search by title
    When I search for books by title "Clean"
    Then I should find 2 books