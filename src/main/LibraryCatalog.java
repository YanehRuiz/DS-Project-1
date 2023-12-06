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
	public List<Book> bookSearch;
	public List<User> UserSearch;
	public List<Integer> BooksIDs;
	public float fees;




	public LibraryCatalog() throws IOException {
		
		this.bookList = new ArrayList<>();
		getBooksFromFiles();

		this.userList = new ArrayList<>();
		//getUsersFromFiles();
			
		generateReport();
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


				Book book = new Book(id, title, author, genre, lastCheckOut, checkedOut);
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
		List<Integer> BooksIDs = new ArrayList<>(25);
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(",");
			if(!values[0].equals("ID")) {
				int id = Integer.parseInt(values[0]);
				String fullName = values[1];    
				this.checkedOutList = new ArrayList<>(100);
				String[] BookIDsList = values[2].split(" ");
				for (int i = 0; i < BookIDsList.length; i++) {
					BooksIDs.add(Integer.parseInt(BookIDsList[i]));
					if(!values[2].isBlank()) {
						for (int j = 0; j < BooksIDs.size(); j++) {
							if(bookList.get(j).getId()==(BooksIDs.get(j))){
								checkedOutList.add(bookList.get(id));
							}
						}
					}
				}


				User user = new User(id, fullName, checkedOutList);
				user.setId(id);
				user.setName(fullName);
				user.setCheckedOutList(checkedOutList);			

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


		Book book = new Book(id, title, author, genre, lastCheckOut, checkedOut);
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

	/*
	 * This methods verifies if a book with the
	 * title parameter has the same title as a
	 * book that is in the list. It then counts
	 * how many occurences that title has.
	 */

	public int bookCount(String title) {
		int count = 0;

		for (int i = 0; i < bookList.size(); i++) {
			if (bookList.get(i).getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	
	/*
	 * This method is an extra that works just like the
	 * title method. It is here to help with the report
	 * generating method.
	 */
	
	public int bookCountGenre(String genre) {
		int countGenre = 0;

		for (int i = 0; i < bookList.size(); i++) {
			if (bookList.get(i).getGenre().equals(genre)) {
				countGenre++;
			}
		}
		return countGenre;
	}
	
	
	
	
	
	
	public void generateReport() throws IOException {
		
	
		
		// Count the number of books in each genre
	    int adventureCount = bookCountGenre("Adventure");
	    int fictionCount = bookCountGenre("Fiction");
	    int classicsCount = bookCountGenre("Classics");
	    int mysteryCount = bookCountGenre("Mystery");
	    int scienceFictionCount = bookCountGenre("Science Fiction");
		
		
	    int totalBooks = adventureCount + fictionCount + classicsCount + mysteryCount + scienceFictionCount;
		

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
		output += "Adventure\t\t\t\t\t" + (adventureCount) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (fictionCount) + "\n";
		output += "Classics\t\t\t\t\t" + (classicsCount) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (mysteryCount) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (scienceFictionCount) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (totalBooks) + "\n\n";

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
		
	    for (Book booksCO : bookList) {
	        if (booksCO.isCheckedOut()) {
	            output += booksCO.toString() + "\n";
	        }
	    }

	    int checkedOutCount = searchForBook(Book::isCheckedOut).size();

		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (checkedOutCount) + "\n\n";


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
		output += "\t\t\t\tTOTAL DUE\t$" + (fees) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.

		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		
		BufferedWriter reportwriter = new BufferedWriter(new FileWriter("report.txt"));
		reportwriter.write(output);
		reportwriter.close();
	}

	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */






	public List<Book> searchForBook(FilterFunction<Book> func) {
		this.bookSearch = new ArrayList<>(100); 
		for (Book book : bookList) {
			if (func.filter(book)) {
				bookSearch.add(book);
			}
		}
		return bookSearch;
	}


	public List<User> searchForUsers(FilterFunction<User> func) {
		this.UserSearch = new ArrayList<>(100); 
		for (User user : userList) {
			if (func.filter(user)) {
				UserSearch.add(user);
			}
		}
		return UserSearch;
	}

}
