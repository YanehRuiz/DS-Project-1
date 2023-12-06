package main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class LibraryGui {

	public static void main (String[] args) {
		
		JFrame libraryframe = new JFrame();
		
		libraryframe.setTitle("Library GUI");
		libraryframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		libraryframe.setResizable(false);
		libraryframe.setSize(666,666);
		libraryframe.setVisible(true);
		
		libraryframe.getContentPane().setBackground(new Color(0x8A9A5B));
		
		
	}
	
	
	
	
}
