package sjsu.dalal.cs146.project2;

// Class cell. represent a vertex on the graph. In the case of maze instead of a single id/ label it's easier to identify it with x, y coordinates

public class Cell {
	
	private int x;          // x - coordinate of the cell
	private int y;          // y - coordinate of the cell
	private boolean north;  // true if there is a wall to the north of the cell. By definition if the variable is false implies there is an edge with its northern cell
	private boolean south;	// true if there is a wall to the south of the cell, false implies there is an edge with its southern cell
	private boolean east;	// true if there is a wall to the east of the cell, false implies there is an edge with its eastern cell
	private boolean west;	// true if there is a wall to the west of the cell, false implies there is an edge with its western cell
	private boolean visited; // For maze generation
	public static final int WHITE = 1; // Represents a node that is not discovered in the search
	public static final int GREY =  2; // Represents a node that is discovered but not fully explored ( all neighbors not discovered) in the search
	public static final int BLACK = 3; // Represents a node that is fully discovered in the search
	private int color;				   // Color of the node to be used during BFS or DFS	
	private int bfsDist;			   // Distance of the node from the start node for BFS	
	private int dfsStartTime;		   // Time when the node is made grey in dfs
	private int dfsEndTime;			   // Time when the node is made black in dfs
	private int dfsOrder;			   // Order(rank) in which the node is discovered in the dfs
	private int bfsOrder;			   // Order(rank) in which the node is discovered in the bfs
	private boolean onDfsPath;		   // Is the node on the shortest DFS path
	private boolean onBfsPath;		   // Is the node on the shortest BFS path
	private int bfsParentx;
	private int bfsParenty;

	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;
		north = true;
		south = true;
		east = true;
		west = true;
		visited  = false;
		//this.color = WHITE;
	}
	
	// Getters and setters for the member fields of class Cell
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isNorth() {
		return north;
	}

	public void setNorth(boolean north) {
		this.north = north;
	}

	public boolean isSouth() {
		return south;
	}

	public void setSouth(boolean south) {
		this.south = south;
	}

	public boolean isEast() {
		return east;
	}

	public void setEast(boolean east) {
		this.east = east;
	}

	public boolean isWest() {
		return west;
	}

	public void setWest(boolean west) {
		this.west = west;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public int getBfsDist() {
		return bfsDist;
	}



	public void setBfsDist(int bfsDist) {
		this.bfsDist = bfsDist;
	}



	public int getDfsStartTime() {
		return dfsStartTime;
	}



	public void setDfsStartTime(int dfsStartTime) {
		this.dfsStartTime = dfsStartTime;
	}



	public int getDfsEndTime() {
		return dfsEndTime;
	}



	public void setDfsEndTime(int dfsEndTime) {
		this.dfsEndTime = dfsEndTime;
	}

	public int getDfsOrder() {
		return dfsOrder;
	}



	public void setDfsOrder(int dfsOrder) {
		this.dfsOrder = dfsOrder;
	}



	public int getBfsOrder() {
		return bfsOrder;
	}



	public void setBfsOrder(int bfsOrder) {
		this.bfsOrder = bfsOrder;
	}

	public boolean checkWall(int i)
	{
		if(i == 0)
			return north;
		else if(i == 1)
			return east;
		else if(i == 2)
			return south;
		else if(i == 3)
			return west;
		else
			return true;
		
	}
	public boolean isOnDfsPath() {
		return onDfsPath;
	}

	public void setOnDfsPath(boolean onDfsPath) {
		this.onDfsPath = onDfsPath;
	}
	
	public boolean isOnBfsPath() {
		return onBfsPath;
	}

	public void setOnBfsPath(boolean onBfsPath) {
		this.onBfsPath = onBfsPath;
	}
	public int getBfsParentx() {
		return bfsParentx;
	}

	public void setBfsParentx(int bfsParentx) {
		this.bfsParentx = bfsParentx;
	}

	public int getBfsParenty() {
		return bfsParenty;
	}

	public void setBfsParenty(int bfsParenty) {
		this.bfsParenty = bfsParenty;
	}

	
}
