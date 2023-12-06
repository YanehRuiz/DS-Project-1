package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import interfaces.List;


/*
 * User Class where the id, name and checkedoutlist constructor is passed.
 */


public class User {
    public int id;
    public String name;
    public List<Book> checkedOutList;

    public User(int id, String name, List<Book> checkedOutList) {
        this.id = id;
        this.name = name;
        this.checkedOutList = checkedOutList;
    }
	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id=id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public List<Book> getCheckedOutList() {
		return checkedOutList;
	}

	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList=checkedOutList;
	}
	
}
