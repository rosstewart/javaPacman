package lab12;

public class PacmanTester {

    public static void main(String[] arg)
    {
    	Pacman pacman = new Pacman("src/lab12/mazes/bigMaze.txt","src/lab12/output.txt");
    	Pacman pacmanBFS = new Pacman("src/lab12/mazes/bigMazeBFSSol.txt","src/lab12/dontwrite.txt");
    	Pacman pacmanDFS = new Pacman("src/lab12/mazes/bigMazeDFSSol.txt","src/lab12/dontwrite.txt");
    	pacman.solveBFS();
    	pacman.writeOutput();
    	System.out.println(pacman.toString().equals(pacmanBFS.toString()));
    	Pacman pacman2 = new Pacman("src/lab12/mazes/bigMaze.txt","src/lab12/output2.txt");
    	pacman2.solveDFS();
    	pacman2.writeOutput();
    	System.out.println(pacman2.toString().equals(pacmanDFS.toString()));
    	
    }
}