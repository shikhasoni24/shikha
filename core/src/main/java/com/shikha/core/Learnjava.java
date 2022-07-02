package com.shikha.core;

public class Learnjava {
	
			/**
			 * This method counts backward
			 * 
			 */
			public void backwardCounter() {
				for (int i = 100; i >= 0; i = i - 5) {
					System.out.println(i);
				}
			}

			/**
			 * This is the main method
			 * 
			 * @param args
			 */
			public static void main(String[] args) {
				Learnjava l=new Learnjava();
				l.backwardCounter();
			}

}
