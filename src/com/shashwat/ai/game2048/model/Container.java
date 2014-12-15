package com.shashwat.ai.game2048.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Container implements Cloneable {
	
    public static final int CONTAINER_SIZE = 4;
    
    public static final int TARGET = 2048;
    
    public static final int MINIMUM_WIN_SCORE = 18432;
    
    private int score = 0;
    
    private int[][] containerArray;
    
    private final Random randomGenerator;
    
    private Integer cache_emptyCells=null;
    
	public Container() {
		this.containerArray = new int[CONTAINER_SIZE][CONTAINER_SIZE];
		this.randomGenerator = new Random(System.currentTimeMillis());

		addRandomNumberedCell();
		addRandomNumberedCell();
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int[][] getContainerArray() {
		return this.containerArray;
	}
	
	public Random getRandomGenerator() {
		return this.randomGenerator;
	}
	
	private boolean addRandomNumberedCell() {
		List<Integer> emptyCells = getEmptyCellIds();

		int listSize = emptyCells.size();

		if (listSize == 0) {
			return false;
		}

		int randomCellId = emptyCells.get(this.randomGenerator
				.nextInt(listSize));
		int randomValue = (this.randomGenerator.nextDouble() < 0.9) ? 2 : 4;

		int i = randomCellId / CONTAINER_SIZE;
		int j = randomCellId % CONTAINER_SIZE;

		setEmptyCell(i, j, randomValue);

		return true;
	}

	public void setEmptyCell(int i, int j, int value) {
		if (this.containerArray[i][j] == 0) {
			this.containerArray[i][j] = value;
			this.cache_emptyCells = null;
		}
	}
	
	public List<Integer> getEmptyCellIds() {
		List<Integer> cellList = new ArrayList<>();
		for (int i = 0; i < CONTAINER_SIZE; ++i) {
			for (int j = 0; j < CONTAINER_SIZE; ++j) {
				if (this.containerArray[i][j] == 0) {
					cellList.add(CONTAINER_SIZE * i + j);
				}
			}
		}
		return cellList;
	}
	
	/**
	 * Perform moves
	 * @param dir {@link DirectionEnum}
	 * @return {@link StatusEnum}
	 * @throws CloneNotSupportedException
	 */
	public StatusEnum moves(DirectionEnum dir) throws CloneNotSupportedException {
		StatusEnum status = StatusEnum.CONTINUE;
		
		int[][] curContainerArray = getContainerArray();
		int newPoint = move(dir);
		int[][] newContainerArray = getContainerArray();
		
		boolean isNewCellAdded = false;
		if (!isEqual(curContainerArray, newContainerArray)) {
			isNewCellAdded = addRandomNumberedCell();
		}
		
		if (newPoint == 0 && isNewCellAdded == false) {
			if (isGameOver()) {
				status = StatusEnum.NO_MORE_MOVE;
			} else {
				status = StatusEnum.INCORRECT_MOVE;
			}
		} else {
			if (newPoint >=  TARGET) {
				status = StatusEnum.WIN;
			} else {
				if (isGameOver()) {
					status = StatusEnum.NO_MORE_MOVE;
				}
			}
		}
		return status;
	}
	
	public int move(DirectionEnum dir) {
		int points = 0;
		if (dir == DirectionEnum.UP) {
			rotateLeft();
		} else if (dir == DirectionEnum.RIGHT) {
			rotateLeft();
			rotateLeft();
		} else if (dir == DirectionEnum.DOWN) {
			rotateRight();
		}
		
		for (int i = 0; i < CONTAINER_SIZE; i++) {
			int lastMergePos = 0;
			for (int j = 1; j <CONTAINER_SIZE; j++) {
				if (this.containerArray[i][j] == 0) {
					continue;
				}
				
				int previousPosition = j - 1;
				while (previousPosition > lastMergePos
						&& this.containerArray[i][previousPosition] == 0) { // skip all the zeros
					--previousPosition;
				}
				
				if (this.containerArray[i][previousPosition] == 0) {
					this.containerArray[i][previousPosition] = this.containerArray[i][j];
					this.containerArray[i][j] = 0;
				} else if (this.containerArray[i][previousPosition] == this.containerArray[i][j]) {
					this.containerArray[i][previousPosition]*= 2;
					this.containerArray[i][j] = 0;
					points += this.containerArray[i][previousPosition];
					lastMergePos = previousPosition + 1;
				} else if (this.containerArray[i][previousPosition] != this.containerArray[i][j]
						&& previousPosition + 1 != j) {
					this.containerArray[i][previousPosition + 1] = this.containerArray[i][j];
					this.containerArray[i][j] = 0;
				}
			}
		}
		
		this.score += points;
		if (dir == DirectionEnum.UP) {
			rotateRight();
		} else if (dir == DirectionEnum.RIGHT) {
			rotateRight();
			rotateRight();
		} else if (dir == DirectionEnum.DOWN) {
			rotateLeft();
		}
		
		return points;
	}
	
	private void rotateLeft() {
		int[][] curContainer = new int[CONTAINER_SIZE][CONTAINER_SIZE];
		
		for (int i = 0; i < CONTAINER_SIZE; i++) {
			for (int j = 0; j < CONTAINER_SIZE; j++) {
				curContainer[CONTAINER_SIZE - j - 1][i] = this.containerArray[i][j];
			}
		}
		this.containerArray = curContainer;
	}
	
	private void rotateRight() {
		int[][] curContainer = new int[CONTAINER_SIZE][CONTAINER_SIZE];
		
		for (int i = 0; i < CONTAINER_SIZE; i++) {
			for (int j = 0; j < CONTAINER_SIZE; j++) {
				curContainer[i][j] = this.containerArray[CONTAINER_SIZE - j - 1][i];
			}
		}
		this.containerArray = curContainer;
	}
}
