/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package maze;

/**
 *
 * @author Cwhit
 */
public class Maze {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        char[][] mazeArray = new char[12][12];

        char[][] mazeArray1 = { {'#','#','#','#','#','#','#','#','#','#','#','#'},
                                {'#','.','.','.','#','.','.','.','.','.','.','#'},
                                {'O','.','#','.','#','.','#','#','#','#','.','#'},
                                {'#','#','#','.','#','.','.','.','.','#','.','#'},
                                {'#','.','.','.','.','#','#','#','.','#','.','#'},
                                {'#','#','#','#','.','#','F','#','.','#','.','#'},
                                {'#','.','.','#','.','#','.','#','.','#','.','#'},
                                {'#','#','.','#','.','#','.','#','.','#','.','#'},
                                {'#','.','.','.','.','.','.','.','.','#','.','#'},
                                {'#','#','#','#','#','#','.','#','#','#','.','#'},
                                {'#','.','.','.','.','.','.','#','.','.','.','#'},
                                {'#','#','#','#','#','#','#','#','#','#','#','#'}
                              };
        CreatMazeSetup myMaze = new CreatMazeSetup(mazeArray1);
        myMaze.print(mazeArray1);
        myMaze.run(mazeArray1, 0, 2, 0, 3);
        System.out.println(myMaze.run(mazeArray1, 0, 2, 0, 3));
    }
}
    

