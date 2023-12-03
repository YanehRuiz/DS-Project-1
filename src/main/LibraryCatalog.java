package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

import javax.xml.crypto.Data;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {
	public List<Book> bookList;
	public List<User> userList;
	public List<Book> checkedOutList;
	public List<Book> checkedOutListIDs;


	//jean.montes@upr.edu


	public LibraryCatalog() throws IOException {
		//	generateReport();




		this.bookList = new ArrayList<>();
		getBooksFromFiles();

		this.userList = new ArrayList<>();
		//getUsersFromFiles();

	}
	private List<Book> getBooksFromFiles() throws IOException {
		this.bookList= new ArrayList<>(100);
		/*
		 * BUFFERREADER FOR CATALOGUE READING. (CSV)
		 * 
		 * This processes the csv line per line, and extracts the values from it.
		 * It created the variables title, author, genre, lastCheckOut and CheckedOut.
		 * 
		 * After that it creates a new book and sets the previously created public 
		 * variables as book variables.
		 * 
		 * 
		 * */
		BufferedReader reader = new BufferedReader(new FileReader("data/catalog.csv"));
		String line;

		while ((line = reader.readLine()) != null) {
			String[] values = line.split(",");
			if(!values[0].equals("ID")) {
				int id = Integer.parseInt(values[0]);
				String title = values[1];
				String author = values[2];
				String genre = values[3];
				LocalDate lastCheckOut = LocalDate.parse(values[4]);
				boolean checkedOut = Boolean.parseBoolean(values[5]);


				Book book = new Book(id, genre, genre, genre, lastCheckOut, checkedOut);
				book.setId(id);
				book.setTitle(title);
				book.setAuthor(author);
				book.setGenre(genre);
				book.setLastCheckOut(lastCheckOut);
				book.setCheckedOut(checkedOut);

				bookList.add(book);
				float fees = book.calculateFees();
			}
		}
		reader.close();
		return bookList;

	}

	private List<User> getUsersFromFiles() throws IOException {
		this.userList= new ArrayList<>(100);

		/*
		 * BUFFERREADER FOR USER READING. (CSV)
		 * 
		 * This processes the csv line per line, and extracts the values from it.
		 * It created the variables id, Full Names and CheckedOutBooks.
		 * 
		 * After that it creates a new book and sets the previously created public 
		 * variables as book variables.
		 * 
		 * 
		 * */


		//WIP
		BufferedReader reader = new BufferedReader(new FileReader("data/user.csv"));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(",");
			if(!values[0].equals("ID")) {
				int id = Integer.parseInt(values[0]);
				String fullName = values[1];    
				List<Book> checkedOutList = new ArrayList<>();
				String[] IDs = values[2].split(" ");







				//line.split(//s)
				//I need to split that "array" value given. So it's a split within a split.
				//Se compara el id con el id del csv para obtener el libro y hacer la lista. 
				//Vacio si no tiene ningun id. 

				//		                for (int i = 2; i < values.length; i++) {
				//		                	//Book book = new Book(values[i]);
				//		                	checkedOutList.add(values[i]);




				User user = new User(id, fullName, checkedOutList);
				userList.add(user);
			}
		}
		reader.close();

		return userList;
	}

	/*
	 * Returns the book list that is extracted from the csv
	 */

	public List<Book> getBookCatalog() {
		return bookList;
	}

	/*
	 * Returns the user list that is extracted from the csv
	 */
	public List<User> getUsers() {
		return userList;
	}


	/* 
	 *  Add book method that adds new books to the csv list that has
	 *  already been created. It adds them after said list, and the
	 *  date is "today" September 15, its not checked out since it
	 *  was just added to the list, and its added to the end of the 
	 *  list so size + 1. 
	 *  
	 */


	public void addBook(String title, String author, String genre) {	
		int id = bookList.size()+1;
		LocalDate lastCheckOut = LocalDate.of(2023, 9, 15);
		boolean checkedOut = false;


		Book book = new Book(id, genre, genre, genre, lastCheckOut, checkedOut);
		book.setId(bookList.size()+1);
		book.setTitle(title);
		book.setAuthor(author);
		book.setGenre(genre);
		book.setLastCheckOut(lastCheckOut);
		book.setCheckedOut(checkedOut);

		bookList.add(book);
		float fees = book.calculateFees();
	}



	/*
	 * Removes the book from the list as long as the id
	 * given as parameter matches the id of an existing
	 * book within the list. 
	 */



	public void removeBook(int id) {


		for(int i=0; i < bookList.size() ; i++) {
			if(bookList.get(i).getId() == id) {
				bookList.remove(i);
				break;
			}

		}
	}	

	/*
	 *  Checks if the book has not been checked out. If it
	 *   hasn't then it is then checked out, changes the 
	 *   day to "today's" and returns true. If it's not then 
	 *   it returns false.
	 */

	public boolean checkOutBook(int id) {


		for (int i = 0; i < bookList.size(); i++) {
			if (bookList.get(i).getId() == id) {
				if (!bookList.get(i).isCheckedOut()) {
					bookList.get(i).setLastCheckOut(LocalDate.of(2023, 9, 15));
					bookList.get(i).setCheckedOut(true);
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Similar to checkedout, it checks if it's been 
	 * checkedout before. If it has, then you can return
	 * said book, CO is set to false and it returns true
	 * when the returning process has been completed. If
	 * it wasn't completed then it returns false.
	 */

	public boolean returnBook(int id) {
		for (int i = 0; i < bookList.size(); i++) {
			if (bookList.get(i).getId() == id) {
				if (bookList.get(i).isCheckedOut()) {
					bookList.get(i).setCheckedOut(false);
					return true;
				}
			}
		}
		return false;

	}


	/*
	 * This methods verifies if the book with the
	 * same id as the parameter id is checked out.
	 * If its not, it returns true, as its available
	 * for checkout. Otherwise it returns false.
	 */

	public boolean getBookAvailability(int id) {
		if (!bookList.get(id).isCheckedOut()) {
			return true;
		}

		return false;
	}



	public int bookCount(String title) {
		int count = 0;

		for (int i = 0; i < bookList.size(); i++) {
			if (bookList.get(i).getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	public void generateReport() throws IOException {

		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + (/*Place here the amount of adventure books*/) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (/*Place here the amount of fiction books*/) + "\n";
		output += "Classics\t\t\t\t\t" + (/*Place here the amount of classics books*/) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (/*Place here the amount of mystery books*/) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (/*Place here the amount of science fiction books*/) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (/*Place here the total number of books*/) + "\n\n";

		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */


		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" (/*Place here the total number of books that are checked out*/) + "\n\n";


		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */


		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (/*Place here the total amount of money owed to the library.*/) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.

		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */

	}

	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		return null;
	}

	public List<User> searchForUsers(FilterFunction<User> func) {
		return null;
	}

}
