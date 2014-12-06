package com.shashwat.ai.game2048;

import java.util.Scanner;

public class MainConsole {
	public static void main(String[] args) {
		System.out.println("Java version of 2048");
		System.out.println("====================");
		while (true) {
			showMenu();
			int option;
			try {
				Scanner scanner = new Scanner(System.in);
				option = scanner.nextInt();
				switch (option) {
				case 1:
					break;
				case 2:
					break;
				case 3 :
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void showMenu() {
		System.out.println();
        System.out.println("Options:");
        System.out.println("1. Play the 2048 Game");
        System.out.println("2. Estimate the Accuracy of AI Solver");
        System.out.println("3. Exit");
        System.out.println();
        System.out.println("Enter a number from 1-3:");
	}
	
	public static void playGame() throws CloneNotSupportedException {
		System.out.println("2048 Game!");
		System.out.println("Use 8 for UP, 6 for RIGHT, 2 for DOWN and 4 for LEFT. Type a to play automatically and q to exit. Press enter to submit your choice.");

		int hintDepth = 7;
		Board theGame = new Board();
		Direction hint = AIsolver.findBestMove(theGame, hintDepth);
		printBoard(theGame.getBoardArray(), theGame.getScore(), hint);

		try {
			InputStreamReader unbuffered = new InputStreamReader(System.in,
					"UTF8");
			char inputChar;

			ActionStatus result = ActionStatus.CONTINUE;
			while (result == ActionStatus.CONTINUE
					|| result == ActionStatus.INVALID_MOVE) {
				inputChar = (char) unbuffered.read();
				// inputChar = 'a';
				if (inputChar == '\n' || inputChar == '\r') {
					continue;
				} else if (inputChar == '8') {
					result = theGame.action(Direction.UP);
				} else if (inputChar == '6') {
					result = theGame.action(Direction.RIGHT);
				} else if (inputChar == '2') {
					result = theGame.action(Direction.DOWN);
				} else if (inputChar == '4') {
					result = theGame.action(Direction.LEFT);
				} else if (inputChar == 'a') {
					result = theGame.action(hint);
				} else if (inputChar == 'q') {
					System.out.println("Game ended, user quit.");
					break;
				} else {
					System.out
							.println("Invalid key! Use 8 for UP, 6 for RIGHT, 2 for DOWN and 4 for LEFT. Type a to play automatically and q to exit. Press enter to submit your choice.");
					continue;
				}

				if (result == ActionStatus.CONTINUE
						|| result == ActionStatus.INVALID_MOVE) {
					hint = AIsolver.findBestMove(theGame, hintDepth);
				} else {
					hint = null;
				}
				printBoard(theGame.getBoardArray(), theGame.getScore(), hint);

				if (result != ActionStatus.CONTINUE) {
					System.out.println(result.getDescription());
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
