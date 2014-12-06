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
}
