package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import hash.HashTable;

public class WordPuzzleSolver {

	public static void main(String[] args) {

		/* Kick everything off by calling solve() */
		try {
			Scanner in = new Scanner(System.in);
			String dicFile = in.next();
			String gridFile = in.next();
			in.close();

			new WordPuzzleSolver().solve(dicFile, gridFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Solve the puzzle */
	private void solve(String dictFile, String gridFile) throws IOException {
		/* Implement this method. Open the files and solve the word puzzle!! */

		HashTable<String, String> dict = new HashTable<String, String>();
		
		BufferedReader in = new BufferedReader(new FileReader(dictFile));
		String word;
		while ((word = in.readLine()) != null) {
			dict.insert(word, word);
		}
		in.close();
		BufferedReader in1 = new BufferedReader(new FileReader(gridFile));
		int row = Integer.parseInt(in1.readLine());
		int column = Integer.parseInt(in1.readLine());
		String string = in1.readLine();
		in1.close();
		char[][] grid = new char[row][column];
		int let = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				grid[i][j] = string.charAt(let);
				let++;
			}
		}
		long time = System.currentTimeMillis();
		int numWords = 0;
		String dirs[] = { "N", "E", "S", "W", "NE", "SE", "SW", "NW" };
		String sword = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				for (String dir : dirs) {
					// north
					if (dir == "N") {
						sword = String.valueOf(grid[i][j]);
						int x = j;
						int y;
						for (y = i - 1; y >= 0; y--) {
							sword += grid[y][x];
							if (sword.length() >= 3 && sword.length() <= 22) {
								if (dict.contains(sword)) {
									numWords++;
								}
							}
						}
					} else if (dir == "E") {

						// east
						sword = String.valueOf(grid[i][j]);
						int y = i;
						int x;
						for (x = j + 1; x < grid[0].length; x++) {
							sword += grid[y][x];
							if (sword.length() >= 3 && sword.length() <= 22) {
								if (dict.contains(sword))
									numWords++;
							}
						}
					} else if (dir == "S") {
						// south
						int x = j;
						int y;
						sword = String.valueOf(grid[i][j]);
						for (y = i + 1; y < grid.length; y++) {
							sword += grid[y][x];
							if (sword.length() >= 3 && sword.length() <= 22) {
								if (dict.contains(sword))
									numWords++;
							}
						}
					} else if (dir == "W") {
						// west
						int y = i;
						int x;
						sword = String.valueOf(grid[i][j]);
						for (x = j - 1; x >= 0; x--) {
							sword += grid[y][x];
							if (sword.length() >= 3 && sword.length() <= 22) {
								if (dict.contains(sword))
									numWords++;
							}
						}
					} else if (dir == "NE") {
						// northeast
						int y = i - 1;
						int x = j + 1;
						sword = String.valueOf(grid[i][j]);
						while (x < grid[0].length && y >= 0) {
							sword += grid[y][x];
							if (sword.length() >= 3 && sword.length() <= 22) {
								if (dict.contains(sword))
									numWords++;
							}
							x++;
							y--;
						}
					} else if (dir == "SE") {

						// southeast
						int x = j + 1;
						int y = i + 1;
						sword = String.valueOf(grid[i][j]);
						while (x < grid[0].length && y < grid.length) {
							sword += grid[y][x];
							if (sword.length() >= 3 && sword.length() <= 22) {
								if (dict.contains(sword))
									numWords++;
							}
							x++;
							y++;
						}
					} else if (dir == "SW") {

						// southwest
						int x = j - 1;
						int y = i + 1;
						sword = String.valueOf(grid[i][j]);
						while (x >= 0 && y < grid.length) {
							sword += grid[y][x];
							if (sword.length() >= 3 && sword.length() <= 22) {
								if (dict.contains(sword))
									numWords++;
							}
							x--;
							y++;
						}
					} else if (dir == "NW") {
						// northwest
						int x = j - 1;
						int y = i - 1;
						sword = String.valueOf(grid[i][j]);
						while (x >= 0 && y >= 0) {
							sword += grid[y][x];
							if (dict.contains(sword) && sword.length() >= 3 && sword.length() <= 22) {
								numWords++;
							}
							x--;
							y--;
						}
					}
				}
			}
		}
		System.out.println(numWords + " words found");
	}
}
