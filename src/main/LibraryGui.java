package main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;




/*
 * GUI For the Library! 
 * 
 * The JFrame is the pop up window made for the GUI.
 * The color is set to green, and since the buttons are small, so is the window.
 * 
 * It creates a new list given the author, title and genre given by the user.
 * You can then add, remove, or display said list at will.
 * The constructor for the books has "today's" date as a default.
 */



public class LibraryGui extends JFrame {
    private List<Book> bookList;

    private JButton addButton;
    private JButton removeButton;
    private JButton displayButton;

    public LibraryGui() {
        bookList = new ArrayList<>();

        setTitle("Library GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(300, 150);
        setLayout(new FlowLayout());

        addButton = new JButton("Add Book");
        removeButton = new JButton("Remove Book");
        displayButton = new JButton("Display Books");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBooks();
            }
        });

        add(addButton);
        add(removeButton);
        add(displayButton);

        getContentPane().setBackground(new Color(0x8A9A5B));
        setVisible(true);
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog(this, "Enter the title of the book:");
        String author = JOptionPane.showInputDialog(this, "Enter the author of the book:");
        String genre = JOptionPane.showInputDialog(this, "Enter the genre of the book:");

        if (title != null && !title.isEmpty() && author != null && !author.isEmpty() && genre != null && !genre.isEmpty()) {
            int id = bookList.size() + 1;
            Book book = new Book(id, title, author, genre, LocalDate.of(2023, 9, 15), false);
            bookList.add(book);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        }
    }

    private void removeBook() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the ID of the book to remove:"));

        boolean removed = false;
        for (Book book : bookList) {
            if (book.getId() == id) {
                bookList.remove(book);
                removed = true;
                break;
            }
        }

        if (removed) {
            JOptionPane.showMessageDialog(this, "Book removed successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Book not found in the library.");
        }
    }

    private void displayBooks() {
        StringBuilder report = new StringBuilder("Books in the Library:\n\n");
        for (Book book : bookList) {
            report.append(book.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, report.toString(), "Books in the Library", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        new LibraryGui();
    }
}