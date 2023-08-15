public class PacmanTester {

    public static void main(String[] arg)
    {
    	Pacman pacman = new Pacman("mazes/bigMaze.txt","output.txt");
    	Pacman pacmanBFS = new Pacman("mazes/bigMazeBFSSol.txt","dontwrite.txt");
    	Pacman pacmanDFS = new Pacman("mazes/bigMazeDFSSol.txt","dontwrite.txt");
    	pacman.solveBFS();
    	pacman.writeOutput();
    	System.out.println(pacman.toString().equals(pacmanBFS.toString()));
    	Pacman pacman2 = new Pacman("mazes/bigMaze.txt","output2.txt");
    	pacman2.solveDFS();
    	pacman2.writeOutput();
    	System.out.println(pacman2.toString().equals(pacmanDFS.toString()));
    	
    }
}
