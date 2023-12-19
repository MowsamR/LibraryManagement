package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

class bookTest {
    private int id;
    private String title = "Animal Farm";
    private String author = "George Orwell";
    private String publicationYear = "1945";
    private String publisher = "Birlinn";
    Book newBook = new Book(0, title, author, publicationYear, publisher);
    
	@Test
	void testTitle() {
		assertEquals(newBook.getTitle(), "Animal Farm");
	}
	
	@Test
	void testAuthor() {
		assertEquals(newBook.getAuthor(), "George Orwell");
	}
	
	@Test
	void testPublicationYear() {
		assertEquals(newBook.getPublicationYear(), "1945");
	}
	
	@Test
	void testpublisher() {
		assertEquals(newBook.getPublisher(), "Birlinn");
	}
	
	//43::The War of The Worlds::H.G. Wells::1898::William Heinemann::
	@Test
	void testLoadedTitle() throws LibraryException, IOException {
		Library library = LibraryData.load();
		Book storedBook  = library.getBookByID(43);
		assertEquals(storedBook.getTitle(), "The War of The Worlds");
	}
	
	@Test
	void testLoadedAuthor() throws LibraryException, IOException {
		Library library = LibraryData.load();
		Book storedBook  = library.getBookByID(43);
		assertEquals(storedBook.getAuthor(), "H.G. Wells");
	}
	
	@Test
	void testLoadedPublicationYear() throws LibraryException, IOException {
		Library library = LibraryData.load();
		Book storedBook  = library.getBookByID(43);
		assertEquals(storedBook.getPublicationYear(), "1898");
	}
	
	@Test
	void testLoadedPublisher() throws LibraryException, IOException {
		Library library = LibraryData.load();
		Book storedBook  = library.getBookByID(43);
		assertEquals(storedBook.getPublisher(), "William Heinemann");
	}

}
