package sjsu.dalal.cs146.project2;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestMaze {
	
	@Test
	// Basic test to generate a maze of the specified size and an input seed
	public void test1(int size, int seed)
	{
		long startMaze,endMaze, startDFS, endDFS, startBFS, endBFS; 
		Maze maze = new Maze(size,seed);
		startMaze = System.currentTimeMillis();
		maze.genMaze();
		endMaze = System.currentTimeMillis();
		System.out.println(" Maze of size ---> " + size);
		System.out.println("");
		System.out.println("**** Maze ****");
		System.out.println("");
		maze.printMaze();
		startDFS = System.currentTimeMillis();
		maze.solveMazeDFS();
		endDFS = System.currentTimeMillis();
		System.out.println("");
		System.out.println("**** DFS solution ****");
		System.out.println("");
		maze.printDFSsolution();
		System.out.println("");
		System.out.println("**** DFS path ****");
		System.out.println("");
		maze.printDFSPath();
		System.out.println("");
		startBFS = System.currentTimeMillis();
		maze.solveMazeBFS();
		endBFS = System.currentTimeMillis();
		System.out.println("**** BFS solution ****");
		System.out.println("");
		maze.printBFSsolution();
		System.out.println("");
		System.out.println("**** BFS path ****");
		System.out.println("");
		maze.printBFSPath();
		System.out.println("");
		System.out.println("Time to generate the maze --> " +(endMaze - startMaze));
		System.out.println("Time to solve the maze using DFS --> " +(endDFS - startDFS));
		System.out.println("Time to solve the maze using BFS --> " +(endBFS - startBFS));
		System.out.println("");
	}
	
	@Test
	// Tests whether the path for solving a 4*4 maze by the BFS and DFS algorithm is correct
	public void test2()
	{
		Maze maze = new Maze(4,3);
		maze.genMaze();
		maze.solveMazeDFS();
		maze.solveMazeBFS();
		//System.out.println("Check cell (1,1) ");
		assertEquals(true, maze.getCell(1, 1).isOnDfsPath());
		assertEquals(true, maze.getCell(1, 1).isOnBfsPath());
		//System.out.println("Check cell (2,1)");
		assertEquals(true, maze.getCell(2, 1).isOnDfsPath());
		assertEquals(true, maze.getCell(2, 1).isOnBfsPath());
		//System.out.println("Check cell (3,1)");
		assertEquals(true, maze.getCell(3, 1).isOnDfsPath());
		assertEquals(true, maze.getCell(3, 1).isOnBfsPath());
		//System.out.println("Check cell (4,1)");
		assertEquals(true, maze.getCell(4, 1).isOnDfsPath());
		assertEquals(true, maze.getCell(4, 1).isOnBfsPath());
		//System.out.println("Check cell (4,2)");
		assertEquals(true, maze.getCell(4, 2).isOnDfsPath());
		assertEquals(true, maze.getCell(4, 2).isOnBfsPath());
		//System.out.println("Check cell (4,3)");
		assertEquals(true, maze.getCell(4, 3).isOnDfsPath());
		assertEquals(true, maze.getCell(4, 3).isOnBfsPath());
		//System.out.println("Check cell (4,4)");
		assertEquals(true, maze.getCell(4, 4).isOnDfsPath());
		assertEquals(true, maze.getCell(4, 4).isOnBfsPath());
	}
	public void test3(int size, int seed)
	{
		Maze maze = new Maze(size,size);
		maze.genMaze();
		maze.solveMazeDFS();
		maze.solveMazeBFS();
		for (int i =1; i <= size; i++)
		{
			for (int j =1; j <= size; j++)
			{
				assertEquals(maze.getCell(i, j).isOnBfsPath(),maze.getCell(i, j).isOnDfsPath());
			}
		}
			
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestMaze testMaze = new TestMaze();
		testMaze.test1(4,3);
		testMaze.test1(5,8);
		testMaze.test1(6, 2);
		testMaze.test1(7,5);
		testMaze.test1(8,0);
		testMaze.test1(10, 6);
		testMaze.test2();
		testMaze.test3(7,8);
	}

}
