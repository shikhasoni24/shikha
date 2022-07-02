package com.shikha.core;

public class Starpattern {
	static void printstar(int m) {
		for (int i = 1; i <= m; i++) {
			for (int j = 0; j <m - i; j++) {
				System.out.print(" ");
			}
			for (int k = 0; k <i; k++) {
				System.out.print("*");

			}
			System.out.println();
		}
	}

	public static void main(String args[]) {
		int m = 6;
		Starpattern.printstar(m);
	}

}
