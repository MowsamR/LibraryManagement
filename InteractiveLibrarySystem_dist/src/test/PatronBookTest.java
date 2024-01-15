
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;

class PatronBookTest {
	int patronID = 1;
	String name = "james";
	String phone = "012345678";
	String email = "test@mail.com";
	Patron patron = new Patron(patronID, name, phone, email);

	@Test
	void patronGetIDTest() {
		assertEquals(patron.getId(), patronID);
	}
	
	@Test
	void patronGetNameTest() {
		assertEquals(patron.getName(), name);
	}
	
	@Test
	void patronGetPhoneTest() {
		assertEquals(patron.getPhone(), phone);
	}
	
	@Test
	void patronGetEmailTest() {
		assertEquals(patron.getEmail(), email);
	}
	
	int newPatronID = 2;
	String newName = "nick";
	String newPhone = "876543210";
	String newEmail = "test2@mail.com";
	@Test
	void patronSetIDTest() {
		patron.setId(newPatronID);
		assertEquals(patron.getId(), newPatronID);
	}
	
	@Test
	void patronSetNameTest() {
		patron.setName(newName);
		assertEquals(patron.getName(), newName);
	}
	
	@Test
	void patronSetPhoneTest() {
		patron.setPhone(newPhone);
		assertEquals(patron.getPhone(), newPhone);
	}
	
	@Test
	void patronSetEmailTest() {
		patron.setEmail(newEmail);
		assertEquals(patron.getEmail(), newEmail);
	}
	
	int bookID  = 1;
	String title = "Children of time";
	String author = "Adrian Tchaikovsky";
	String year = "2015";
	String publisher = "Pan Books";
	Book book =  new Book(bookID, title, author, year, publisher);
	
	@Test
	void bookGetIDTest() {
		assertEquals(book.getId(), bookID);
	}
	
	@Test
	void bookGetTitleTest() {
		assertEquals(book.getTitle(), title);
	}
	
	@Test
	void bookGetAuthorTest() {
		assertEquals(book.getAuthor(), author);
	}
	
	@Test
	void bookGetYearTest() {
		assertEquals(book.getPublicationYear(), year);
	}
	
	@Test
	void bookGetPublisherTest() {
		assertEquals(book.getPublisher(), publisher);
	}
	
	int newBookID  = 2;
	String newTitle = "Rich dad poor dad";
	String newAuthor = "Robert Kiyosaki";
	String newYear = "1997";
	String newPublisher = "Plata";
	@Test
	void bookSetIDTest() {
		book.setId(newBookID);
		assertEquals(book.getId(), newBookID);
	}
	
	@Test
	void bookSetTitleTest() {
		book.setTitle(newTitle);
		assertEquals(book.getTitle(), newTitle);
	}
	
	@Test
	void bookSetAuthorTest() {
		book.setAuthor(newAuthor);
		assertEquals(book.getAuthor(), newAuthor);
	}
	
	@Test
	void bookSetYearTest() {
		book.setPublicationYear(newYear);
		assertEquals(book.getPublicationYear(), newYear);
	}
	
	@Test
	void bookSetPublisherTest() {
		book.setPublisher(newPublisher);
		assertEquals(book.getPublisher(), newPublisher);
	}
}
