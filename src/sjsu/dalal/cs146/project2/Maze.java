package sjsu.dalal.cs146.project2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;



public class Maze {

	public int n; //size of the maze
	public int seed; // seed for generating the random maze
	public Hashtable<Integer,Cell> cellList;  // list of all the cells. Hash table is used to search a cell given its (x,y)coordinates in O(1)
	private Stack<Cell> cellStack; 			  // Stack to be used for maze generation dfs
	public int totalCells;					  // total no. of cells in the maze
	private boolean[][] visitedCell;		  // Matrix indicating whether cell(x,y) has been visited during maze generation. Uses two dummy rows and columns to ensure boundary conditions during maze generation.
	private Random random;
	int time; // Time for DFS & BFS solver used to define the order in which vertices are discovered
	//ArrayList <int []> adjacencyList; // Represents the id's of the neighbors. 0 - North neighbors id, 1 - East, 2- South, 3 - West
	int r; // Random no. between 0 and 3 to select a neighbor. ) -North, 1- East, 2 -South, 3 - West
	private boolean dfsPathDone;  // Indicates DFS has reached the last cell i.e. maze solved using DFS
	private boolean bfsPathDone;  // Indicates BFS has reached the last cell i.e. maze solved using DFS
	
	public Maze(int n, int seed){
		this.n = n;
		cellList = new Hashtable<Integer,Cell>(n*n);
		visitedCell = new boolean[n+2][n+2];
		for (int x=1; x <= n; x++)
			for (int y=1; y <= n; y++)
			{
				cellList.put((n*(x-1) + y), new Cell(x,y));
				totalCells++;
				visitedCell[x][y] = false;
				
			}
		
		for (int x = 0; x < n+2; x++) {
			visitedCell[x][0] = true;
			visitedCell[x][n+1] = true;
        }
        for (int y = 0; y < n+2; y++) {
        	visitedCell[0][y] = true;
        	visitedCell[n+1][y] = true;
        }
		totalCells = n*n;
		this.seed = seed;
		random = new Random(seed);
	}
	
	// Generates a random maze of the given size n
	public void genMaze()
	{
		//System.out.println("In gen maze");
		Cell currentCell;
		Cell adjCell;
		cellList.get(1).setVisited(true);
		cellList.get(1).setNorth(false);
		visitedCell[1][1] = true;
		cellStack = new Stack<Cell>();
		cellStack.push(cellList.get(1));
//		System.out.println(" Start cell x -->" + cellList.get(1).getX() );
//		System.out.println(" Start cell y -->" + cellList.get(1).getY() );
		
		while( !cellStack.isEmpty())
		{
			currentCell = cellStack.peek();
			adjCell = getAdjCellWI(currentCell);
			int x = currentCell.getX(); // Current cell's coordinates
			int y = currentCell.getY();
			if( adjCell != null )
			{
				if(r == 0)
				{
					currentCell.setNorth(false);
					adjCell.setSouth(false);
					adjCell.setVisited(true);
					visitedCell[x-1][y] = true;
					
				}
				if(r == 1)
				{
					currentCell.setEast(false);
					adjCell.setWest(false);
					adjCell.setVisited(true);
					visitedCell[x][y+1] = true;
				}
				if(r == 2)
				{
					currentCell.setSouth(false);
					adjCell.setNorth(false);
					adjCell.setVisited(true);
					visitedCell[x+1][y] = true;
				}
				if(r == 3)
				{
					currentCell.setWest(false);
					adjCell.setEast(false);
					adjCell.setVisited(true);
					visitedCell[x][y-1] = true;
				}
				cellStack.push(adjCell);
			}
			else
				cellStack.pop();			
		}
		cellList.get(n*n).setSouth(false);
		//System.out.println("Exit gen maze");
	}
	
	// Returns an adjacent cell with walls intact. If there are multiple cells, returns a random cell with walls intact
	public Cell getAdjCellWI(Cell  cell)
	{
		int x = cell.getX(); // Current cell's coordinates
		int y = cell.getY();
		
		
		if(visitedCell[x-1][y] && visitedCell[x][y+1] && visitedCell[x+1][y] && visitedCell[x][y-1])
			return null;
		
		while (true)
		{
	//		System.out.println("In while loop");
			r = (int)(4*getRandom());
	//		System.out.println("r --->" + r);
			if( (r == 0) && !visitedCell[x-1][y] )
			{
				//System.out.println(" Case 0: North");
				return getCell((x-1),y );
				
			}
			// Check if the cell east to current cell is visited
			if( (r == 1) && !visitedCell[x][y+1])
			{
				//System.out.println(" Case 1: East");
				return getCell( x,y+1 );
				
			}
					
			
			// Check if the cell south to current cell is visited
			if( (r == 2) && !visitedCell[x+1][y] )
			{
				//System.out.println(" Case 2: South");
				return getCell(x+1,y);
			}		
			
			// Check if the cell west to current cell is visited
			if( (r == 3) && !visitedCell[x][y-1] )
			{
				//System.out.println(" Case 2: South");
				return getCell( x,(y-1) );
				
			}
					
			
		}
	}
	
	// Gives the adjacent cells to a cell and stores null if there is no neighbor: 0- North cell, 1- East cell, 2, South Cell, 3- West Cell
	public ArrayList<Cell> getAdjCell(Cell currentCell)
	{
		//System.out.println("--------------- In get Adj cell---------------");
		int x = currentCell.getX(); // Current cell's coordinates
		int y = currentCell.getY();
		ArrayList<Cell> adjList = new ArrayList<Cell>(4);
		adjList.add(getCell(x-1,y));
		adjList.add(getCell(x,y+1));
		adjList.add(getCell(x+1,y));
		adjList.add(getCell(x,y-1));
		
		//System.out.println("--------------- Out of Adj cell---------------");
		return adjList;
	}
	
	// Returns a uniformly double random no. in [0,1)
	public double getRandom()
	{
		return random.nextDouble();
	}
	
	// Returns the cell with input coordinates. Returns null if the cell does not exist int he hash table
	public Cell getCell(int i, int j)
	{
		if( (i > 0) && (i <= n ) && (j > 0) && ( j <= n) )
			return cellList.get( (n*(i-1)) + j);
		else 
			return null;
	}
	
	// Prints the generated maze
	public void printMaze(){
		Cell currentCell;
		for(int i =1; i <= n; i++)
		{
			for(int j =1; j <= n; j++)
			{
				System.out.print("+");
				currentCell = getCell(i,j);
				
				if(currentCell.isNorth())
					System.out.print("-");
				else
					System.out.print(" ");
			}
			System.out.print("+");
			System.out.println();
			for(int k =1; k <= n; k++)
			{
				currentCell = getCell(i,k);
				if(currentCell.isWest())
					System.out.print("|");
				else
					System.out.print(" ");
				System.out.print(" ");

				if(k == n)
					System.out.print("|");
			}
			System.out.println("");
			if(i == n)
			{
				for(int l = 1; l <= n; l++)
				{
					currentCell = getCell(i,l);
					System.out.print("+");
					if(currentCell.isSouth())
						System.out.print("-");
					else
						System.out.print(" ");
				}
				System.out.print("+");
			}
			
		}
			
		System.out.println("");
	}

	
	/*public void solveDFS()
	{	
		//System.out.println(" In DFS solver");
		time = 0;
		for(int i = 1; i <= n; i ++)
			for(int j = 1; j <= n; j++)
				getCell(i,j).setColor(Cell.WHITE);
		
		for(int i = 1; i <= n; i ++)
		{
			for(int j = 1; j <= n; j++)
			{
				Cell currentCell = getCell(i,j);
				System.out.println(" Before Calling dfs visit for");
				System.out.println("x -->" + currentCell.getX());
				System.out.println("y -->" + currentCell.getY());
				System.out.println("color -->" + currentCell.getColor());
				if( currentCell.getColor() == Cell.WHITE)
				{
					System.out.println("Calling dfs visit for");
					System.out.println("x -->" + currentCell.getX());
					System.out.println("y -->" + currentCell.getY());
					System.out.println("color -->" + currentCell.getColor());
					dfsVisit(currentCell);	
				}
			}	
		}
				
		
	}*/
	
	// Solves the maze using DFS
	public void solveMazeDFS()
	{	
		//System.out.println(" In DFS solver");
		time = 0;
		for(int i = 1; i <= n; i ++)
			for(int j = 1; j <= n; j++)
			{
				Cell currentCell = getCell(i,j);
				currentCell.setColor(Cell.WHITE);
				currentCell.setOnDfsPath(false);
			}
				
		dfsPathDone = false;
		Cell startCell = getCell(1,1);
		startCell.setOnDfsPath(true);
		dfsVisit(startCell);		
	}
	
	// DFS visit method to be used in the DFS solution
	public void dfsVisit(Cell currentCell)
	{
		//System.out.println("-------- In DFS visit --------");
		//time++;
		Cell lastCell = getCell(n,n);
		if(currentCell == lastCell)
		{
			currentCell.setColor(Cell.BLACK);
			currentCell.setOnDfsPath(true);
			dfsPathDone = true; 
			return;
		}
		if(lastCell.getColor() == Cell.BLACK)
			return;
		
		currentCell.setColor(Cell.GREY);
		currentCell.setDfsOrder(time);
		time++;
		ArrayList<Cell> adjList = getAdjCell(currentCell);
		//System.out.println(" Adj list size" + adjList.size());
		/*System.out.println("---- Current cell details before while loop ----");
		System.out.println(" x --->" + currentCell.getX());
		System.out.println(" y --->" + currentCell.getY());
		System.out.println(" color --->" + currentCell.getColor());
		System.out.println(" time -->" + time);*/
		for(int i = 0; i < 4; i++)
		{
			Cell adjCell = adjList.get(i);
			if( adjCell != null)
			{
				if(adjCell.getColor() == Cell.WHITE && !currentCell.checkWall(i))
				{
					dfsVisit(adjCell);
				}
					
			}
				
		}	
		currentCell.setColor(Cell.BLACK);
		if(dfsPathDone)
			currentCell.setOnDfsPath(true);
		
	
	}
	
	/*public void dfsVisit(Cell currentCell)
	{
		//System.out.println("-------- In DFS visit --------");
		time++;
		currentCell.setColor(Cell.GREY);
		currentCell.setDfsStartTime(time);
		ArrayList<Cell> adjList = getAdjCell(currentCell);
		//System.out.println(" Adj list size" + adjList.size());
		Iterator<Cell> i = adjList.iterator();
		System.out.println("---- Current cell details before while loop ----");
		System.out.println(" x --->" + currentCell.getX());
		System.out.println(" y --->" + currentCell.getY());
		System.out.println(" color --->" + currentCell.getColor());
		System.out.println(" time -->" + time);
		while(i.hasNext())
		{
			//System.out.println(" Adjacent cell list details");
			Cell adjCell = i.next();
			if( adjCell != null)
			{
				if(adjCell.getColor() == Cell.WHITE)
				{
					//System.out.println("--------- Adj cell for which dfs is called -------");
					//System.out.println(" x --->" + adjCell.getX());
					//System.out.println(" y --->" + adjCell.getY());
					//System.out.println(" color --->" + adjCell.getColor());
					dfsVisit(adjCell);
				}
					
			}
				
		}
		currentCell.setColor(Cell.BLACK);
		time++;
		System.out.println("---- Current cell details after while loop ----");
		System.out.println(" x --->" + currentCell.getX());
		System.out.println(" y --->" + currentCell.getY());
		System.out.println(" color --->" + currentCell.getColor());
		System.out.println(" time -->" + time);
		currentCell.setDfsEndTime(time);
	}*/
	
	// Prints the DFS solution
	public void printDFSsolution(){
		Cell currentCell;
		for(int i =1; i <= n; i++)
		{
			for(int j =1; j <= n; j++)
			{
				System.out.print("+");
				currentCell = getCell(i,j);
				
				if(currentCell.isNorth())
					System.out.print("-");
				else
					System.out.print(" ");
			}
			System.out.print("+");
			System.out.println();
			for(int k =1; k <= n; k++)
			{
				currentCell = getCell(i,k);
				int dfsOrder = currentCell.getDfsOrder();
				
				if(currentCell.isWest())
					System.out.print("|");
				else
					System.out.print(" ");
				
				if( dfsOrder != 0)
					System.out.print(String.valueOf(Math.abs((long)dfsOrder)).charAt(0));
				else
				{
					if( ((i == 1) && (k == 1)) ||  ((i == n) && (k == n)))
						System.out.print(String.valueOf(Math.abs((long)dfsOrder)).charAt(0));
					else
						System.out.print(" ");
				}
					
				if(k == n)
					System.out.print("|");
			}
			System.out.println("");
			if(i == n)
			{
				for(int l = 1; l <= n; l++)
				{
					currentCell = getCell(i,l);
					System.out.print("+");
					if(currentCell.isSouth())
						System.out.print("-");
					else
						System.out.print(" ");
				}
				System.out.print("+");
			}
			
		}
			
		System.out.println("");
	}
	
	// Prints the DFS path
	public void printDFSPath(){
		Cell currentCell;
		for(int i =1; i <= n; i++)
		{
			for(int j =1; j <= n; j++)
			{
				System.out.print("+");
				currentCell = getCell(i,j);
				
				if(currentCell.isNorth())
					System.out.print("-");
				else
					System.out.print(" ");
			}
			System.out.print("+");
			System.out.println();
			for(int k =1; k <= n; k++)
			{
				currentCell = getCell(i,k);
				//int dfsOrder = currentCell.getDfsOrder();
				
				if(currentCell.isWest())
					System.out.print("|");
				else
					System.out.print(" ");
				
				if( currentCell.isOnDfsPath())
					System.out.print("#");
				else
					System.out.print(" ");
					
				if(k == n)
					System.out.print("|");
			}
			System.out.println("");
			if(i == n)
			{
				for(int l = 1; l <= n; l++)
				{
					currentCell = getCell(i,l);
					System.out.print("+");
					if(currentCell.isSouth())
						System.out.print("-");
					else
						System.out.print(" ");
				}
				System.out.print("+");
			}
			
		}
			
		System.out.println("");
	}
	
	// Solves the maze using BFS
		public void solveMazeBFS()
		{	
			//System.out.println(" In DFS solver");
			time = 0;
			Cell lastCell = getCell(n,n);
			for(int i = 1; i <= n; i ++)
				for(int j = 1; j <= n; j++)
				{
					Cell currentCell = getCell(i,j);
					currentCell.setColor(Cell.WHITE);
					currentCell.setOnBfsPath(false);
				}
					
			bfsPathDone = false;
			Cell startCell = getCell(1,1);
			startCell.setOnDfsPath(true);
			startCell.setBfsOrder(time);
			startCell.setColor(Cell.GREY);
			LinkedList<Cell> queue = new LinkedList<Cell>();
			queue.add(startCell);
			while( !queue.isEmpty())
			{
				Cell currentCell = queue.poll();
				if(currentCell == lastCell)
				{
					currentCell.setColor(Cell.BLACK);
					currentCell.setOnBfsPath(true);
					bfsPathDone = true;
					setBfsPath();
					return;
				}
				ArrayList<Cell> adjList = getAdjCell(currentCell);
				for(int i = 0; i < 4; i++)
				{
					Cell adjCell = adjList.get(i);
					if( adjCell != null)
					{
						if(adjCell.getColor() == Cell.WHITE && !currentCell.checkWall(i))
						{
							adjCell.setColor(Cell.GREY);
							time++;
							adjCell.setBfsOrder(time);
							adjCell.setBfsParentx(currentCell.getX());
							adjCell.setBfsParenty(currentCell.getY());
							queue.add(adjCell);
							
						}
							
					}	
				}	
				currentCell.setColor(Cell.BLACK);	
			}
		}

		public void setBfsPath()
		{
			Cell currentCell = getCell(n,n);
			Cell parent = getCell(currentCell.getBfsParentx(),currentCell.getBfsParenty());
			
			while (parent !=  null)
			{
				parent.setOnBfsPath(true);
				currentCell = parent;
				parent = getCell(currentCell.getBfsParentx(),currentCell.getBfsParenty());

			}
				
		}
		
		// Prints the BFS solution
		public void printBFSsolution(){
			Cell currentCell;
			for(int i =1; i <= n; i++)
			{
				for(int j =1; j <= n; j++)
				{
					System.out.print("+");
					currentCell = getCell(i,j);
					
					if(currentCell.isNorth())
						System.out.print("-");
					else
						System.out.print(" ");
				}
				System.out.print("+");
				System.out.println();
				for(int k =1; k <= n; k++)
				{
					currentCell = getCell(i,k);
					int bfsOrder = currentCell.getBfsOrder();
					
					if(currentCell.isWest())
						System.out.print("|");
					else
						System.out.print(" ");
					
					if( bfsOrder != 0)
						System.out.print(String.valueOf(Math.abs((long)bfsOrder)).charAt(0));
					else
					{
						if( ((i == 1) && (k == 1)) ||  ((i == n) && (k == n)))
							System.out.print(String.valueOf(Math.abs((long)bfsOrder)).charAt(0));
						else
							System.out.print(" ");
					}
						
					if(k == n)
						System.out.print("|");
				}
				System.out.println("");
				if(i == n)
				{
					for(int l = 1; l <= n; l++)
					{
						currentCell = getCell(i,l);
						System.out.print("+");
						if(currentCell.isSouth())
							System.out.print("-");
						else
							System.out.print(" ");
					}
					System.out.print("+");
				}
				
			}
				
			System.out.println("");
		}
		
		// Prints the BFS path
		public void printBFSPath(){
			Cell currentCell;
			for(int i =1; i <= n; i++)
			{
				for(int j =1; j <= n; j++)
				{
					System.out.print("+");
					currentCell = getCell(i,j);
					
					if(currentCell.isNorth())
						System.out.print("-");
					else
						System.out.print(" ");
				}
				System.out.print("+");
				System.out.println();
				for(int k =1; k <= n; k++)
				{
					currentCell = getCell(i,k);
					//int dfsOrder = currentCell.getDfsOrder();
					
					if(currentCell.isWest())
						System.out.print("|");
					else
						System.out.print(" ");
					
					if( currentCell.isOnBfsPath())
						System.out.print("#");
					else
						System.out.print(" ");
						
					if(k == n)
						System.out.print("|");
				}
				System.out.println("");
				if(i == n)
				{
					for(int l = 1; l <= n; l++)
					{
						currentCell = getCell(i,l);
						System.out.print("+");
						if(currentCell.isSouth())
							System.out.print("-");
						else
							System.out.print(" ");
					}
					System.out.print("+");
				}
				
			}
				
			System.out.println("");
		}

}
