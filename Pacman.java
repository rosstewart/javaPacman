import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class Pacman {

	/** representation of the graph as a 2D array */
	private Node[][] maze;
	/** input file name */
	private String inputFileName;
	/** output file name */
	private String outputFileName;
	/** height and width of the maze */
	private int height;
	private int width;
	/** starting (X,Y) position of Pacman */
	private int startX;
	private int startY;

	/** A Node is the building block for a Graph. */
	private static class Node {
		/** the content at this location */
	    private char content;
	    /** the row where this location occurs */
	    private int row;
	    /** the column where this location occurs */
	    private int col;
		private boolean visited;
		private Node parent;

	    public Node(int x, int y, char c) {
	        visited = false;
	        content = c;
	        parent =  null;
	        this.row = x;
	        this.col = y;
	    }

	    public boolean isWall() { return content == 'X'; }
	    public boolean isVisited() { return visited; }
	}

	/** constructor */
	public Pacman(String inputFileName, String outputFileName) {
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		buildGraph();
	}

	private boolean inMaze(int index, int bound){
		return index < bound && index >= 0;
	}

	/** helper method to construct the solution path from S to G
	 *  NOTE this path is built in reverse order, starting with the goal G.
	*/
	private void backtrack(Node end){
		end = end.parent; //Don't overwrite goal with "."
		while (end.content != 'S') {
			end.content = '.';
			end = end.parent;
		}
	}

	/** writes the solution to file */
	public void writeOutput() {
		// TODO for lab12
		try {
			PrintWriter output = new PrintWriter(new FileWriter(outputFileName));
			output.write(this.height+" "+this.width);
			for (int row = 0; row < this.height; row++) {
				output.write("\n");
				for (int col = 0; col < this.width; col++) {
					output.write(this.maze[row][col].content);
				}
			}
			output.write("\n");
			output.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		String s = "";
		s += height + " " + width + "\n";
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				s += maze[i][j].content + " ";
			}
			s += "\n";
		}
		return s;
	}

	/** helper method to construct a graph from a given input file;
	 *  all member variables (e.g. maze, startX, startY) should be
	 *  initialized by the end of this method
	 */
	private void buildGraph() {
		// TODO for lab12
		try {
			// don't forget to close input when you're done
			BufferedReader input = new BufferedReader(
				new FileReader(inputFileName));
			String[] dimensions = input.readLine().split(" ");
			this.width = Integer.parseInt(dimensions[1]);
			this.height = Integer.parseInt(dimensions[0]);
			this.maze = new Node[37][37];
			for (int row = 0; row < this.height; row++) {
				String mazeRow = input.readLine();
				if (mazeRow != null) {
					for (int col = 0; col < this.width; col++) {
						//System.out.println("col: "+col+", row: "+row);
						this.maze[row][col] = new Node(row,col,mazeRow.charAt(col));
						if (mazeRow.charAt(col) == 'S') {
							this.startX = col;
							this.startY = row;
						}
					}
				}
				
			}
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** obtains all neighboring nodes that have *not* been
	 *  visited yet from a given node when looking at the four
	 *  cardinal directions: North, South, West, East (IN THIS ORDER!)
	 *
	 * @param currentNode the given node
	 * @return an ArrayList of the neighbors (i.e., successors)
	 */
	public ArrayList<Node> getNeighbors(Node currentNode) {
		// 0. Initialize an ArrayList of nodes
		Node north,south,west,east;
		ArrayList<Node> neighbors = new ArrayList<Node>();
		// 1. Inspect the north neighbor
		if (!currentNode.isWall() && currentNode.row != 0) {
			north = this.maze[currentNode.row-1][currentNode.col]; //col][row
			if (!north.isWall() && !north.isVisited()) {
				//north.visited = true;
				//north.parent = currentNode;
				neighbors.add(north);
			}
		}
		// 2. Inspect the south neighbor
		if (!currentNode.isWall() && currentNode.row != this.height-1) {
			south = this.maze[currentNode.row+1][currentNode.col];
			if (!south.isWall() && !south.isVisited()) {
				//south.visited = true;
				//south.parent = currentNode;
				neighbors.add(south);
			}
		}
		// 3. Inspect the west neighbor
		if (!currentNode.isWall() && currentNode.col != 0) {
			west = this.maze[currentNode.row][currentNode.col-1];
			if (!west.isWall() && !west.isVisited()) {
				//west.visited = true;
				//west.parent = currentNode;
				neighbors.add(west);
			}
		}
		// 4. Inspect the east neighbor
		if (!currentNode.isWall() && currentNode.col != this.width-1) {
			east = this.maze[currentNode.row][currentNode.col+1];
			if (!east.isWall() && !east.isVisited()) {
				//east.visited = true;
				//east.parent = currentNode;
				neighbors.add(east);
			}
		}
		return neighbors;
	}

	/** Pacman uses BFS strategy to solve the maze */
	public void solveBFS() {
		Queue<Node> q = new LinkedList<Node>();
		Node curr = this.maze[this.startY][this.startX];
		q.add(curr);
		curr.visited = true;
		while (!q.isEmpty()) {
			curr = q.remove();
			if (curr.content == 'G') { //solved
				backtrack(curr);
				writeOutput();
				return;
			}
			for (Node n : getNeighbors(curr)) {
				n.visited = true;
				n.parent = curr;
				q.add(n);
			}
		}
		
		// TODO for assignment12
	}

	/** Pacman uses DFS strategy to solve the maze */
	public void solveDFS() {
		Stack<Node> s = new Stack<Node>();
		Node curr = this.maze[this.startY][this.startX];
		s.push(curr);
		curr.visited = true;
		while (!s.isEmpty()) {
			curr = s.pop();
			if (curr.content == 'G') { //solved
				backtrack(curr);
				writeOutput();
				return;
			}
			for (Node n : getNeighbors(curr)) {
				n.visited = true;
				n.parent = curr;
				s.push(n);
			}
		}
	}

}
