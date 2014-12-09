package com.shashwat.ai.game2048;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.shashwat.ai.game2048.model.Container;
import com.shashwat.ai.game2048.model.DirectionEnum;
import com.shashwat.ai.game2048.model.StatusEnum;

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
		System.out
				.println("Use 8 for UP, 6 for RIGHT, 2 for DOWN and 4 for LEFT. Type a to play automatically and q to exit. Press enter to submit your choice.");

		int hintDepth = 7;
		Container game = new Container();
		DirectionEnum hint = AIsolver.findBestMove(game, hintDepth);
		printGameSnapshot(game.getContainerArray(), game.getScore(), hint);

		try {
			InputStreamReader unbuffered = new InputStreamReader(System.in,
					"UTF8");
			char inputChar;

			StatusEnum result = StatusEnum.CONTINUE;
			while (result == StatusEnum.CONTINUE
					|| result == StatusEnum.INCORRECT_MOVE) {
				inputChar = (char) unbuffered.read();
				// inputChar = 'a';
				if (inputChar == '\n' || inputChar == '\r') {
					continue;
				} else if (inputChar == '8') {
					result = game.action(DirectionEnum.UP);
				} else if (inputChar == '6') {
					result = game.action(DirectionEnum.RIGHT);
				} else if (inputChar == '2') {
					result = game.action(DirectionEnum.DOWN);
				} else if (inputChar == '4') {
					result = game.action(DirectionEnum.LEFT);
				} else if (inputChar == 'a') {
					result = game.action(hint);
				} else if (inputChar == 'q') {
					System.out.println("Game ended, user quit.");
					break;
				} else {
					System.out
							.println("Invalid key! Use 8 for UP, 6 for RIGHT, 2 for DOWN and 4 for LEFT. Type a to play automatically and q to exit. Press enter to submit your choice.");
					continue;
				}

				if (result == StatusEnum.CONTINUE
						|| result == StatusEnum.INCORRECT_MOVE) {
					hint = AIsolver.findBestMove(game, hintDepth);
				} else {
					hint = null;
				}
				printGameSnapshot(game.getContainerArray(), game.getScore(), hint);

				if (result != StatusEnum.CONTINUE) {
					System.out.println(result.getDesc());
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	public static void printGameSnapshot(int[][] containerArray, int score, DirectionEnum direction) {
		System.out.println("=======================");
		System.out.println("Score:\t" + String.valueOf(score));
		System.out.println();
		System.out.println("Direction:\t" + direction);
		System.out.println();

		for (int i = 0; i < containerArray.length; ++i) {
			for (int j = 0; j < containerArray[i].length; ++j) {
				System.out.print(containerArray[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("=======================");
	}
}
