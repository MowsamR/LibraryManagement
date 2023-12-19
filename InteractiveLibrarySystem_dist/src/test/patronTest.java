package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

class patronTest {
	private final String name = "mark";
	private final String phone = "0123456789";
	private final String email = "testemail@gmail.com";
	Patron newPatron = new Patron(0, name, phone, email);
	
	@Test
	void testName() {	
		assertEquals(newPatron.getName(), "mark");
	}
	
	@Test
	void testPhone() {	
		assertEquals(newPatron.getPhone(), "0123456789");
	}
	
	@Test
	void testEmail() {	
		assertEquals(newPatron.getEmail(), "testemail@gmail.com");
	}
	
	@Test
	void testLoadedName() throws LibraryException, IOException {
		Library library = LibraryData.load();
		Patron storedPatron  = library.getPatronByID(3);
		assertEquals(storedPatron.getName(), "dani");
	}
	
	@Test
	void testLoadedPhone() throws LibraryException, IOException {
		Library library = LibraryData.load();
		Patron storedPatron  = library.getPatronByID(3);
		assertEquals(storedPatron.getPhone(), "07519611522");
	}
	
	@Test
	void testLoadedEmail() throws LibraryException, IOException {
		Library library = LibraryData.load();
		Patron storedPatron  = library.getPatronByID(3);
		assertEquals(storedPatron.getEmail(), "dani@gmail.com");
	}
}
