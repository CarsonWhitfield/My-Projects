/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maze;

import java.util.Scanner;

/**
 *
 * @author Cwhit
 */
public class CreatMazeSetup {
String hfacing;
boolean northisOpen, southisOpen, westisOpen, eastisOpen = false;
Scanner user_input = new Scanner( System.in );

public CreatMazeSetup(char[][] array){
}

    public void print(char[][] myMaze)
    {
        String output="";
        for(int y=0; y< myMaze.length; y++)
        {
            output+="\n";
            for(int x=0; x<myMaze[y].length; x++){
            output+= myMaze[y][x];
        }
    }
        System.out.println(output);
}

    public String run(char [][]maze, int xLoc, int yLoc, int handLocationX, int handLocationY)//recursive method
    {
        if(maze[yLoc][xLoc]=='F'){
        return "Finished!";
    }
    else {
           hfacing = findDirection(xLoc, yLoc, handLocationX, handLocationY); //determines the direction that we are facing
           findOpening(maze, xLoc, yLoc, handLocationX, handLocationY);//determines if the direction we are facing is open
           if (hfacing.equals("East"))//FACING EAST
           {
               if(eastisOpen){//moves and faces east
               placeX(maze, xLoc, yLoc);//places an X where I was
               xLoc++; //moves forward 1
               handLocationX++;
               if(maze[yLoc][xLoc]!='F')
               {
                    placeO(maze, xLoc, yLoc);//places an O where I am now
               }
            }
            else if(southisOpen)
            {
                placeX(maze, xLoc, yLoc);
                yLoc++;
                handLocationX--;
                if(maze[yLoc][xLoc]!='F')
                {
                    placeO(maze, xLoc, yLoc);//places an O where I am now
                }
            }
            else{//rotates to the north
                    handLocationY--;
                    handLocationX++;
                }
                    print(maze);
                }
                    else if(hfacing.equals("North"))//facing north
                    {
                        if(eastisOpen)//moves and rotates to face east
                        {
                            placeX(maze, xLoc, yLoc);
                            xLoc++;
                            handLocationY++;
                            if(maze[yLoc][xLoc]!='F')
                            {
                                placeO(maze, xLoc, yLoc);//places an O where I am now
                            }
                        }
                        else if(northisOpen)//MOVE NORTH
                        {
                          placeX(maze, xLoc, yLoc);
                          yLoc--;
                          handLocationY--;
                          if(maze[yLoc][xLoc]!='F')
                          {
                            placeO(maze, xLoc, yLoc);//places an O where I am now
                          }
                        }
                        else{//rotates to the west
                                handLocationX--;
                                handLocationY--;
                            }
                            print(maze);
                    }
                    else if (hfacing.equals("South"))//facing south
                    {
                        if(southisOpen)//moves south
                        {
                          placeX(maze, xLoc, yLoc);//places an X where I was
                          yLoc++; //moves forward 1
                          handLocationY++;
                          if(maze[yLoc][xLoc]!='F')
                          {
                            placeO(maze, xLoc, yLoc);//places an O where I am now
                          }
                        }
                        else if(westisOpen)//moves and rotates to face west
                        {
                            placeX(maze, xLoc, yLoc);
                            xLoc--;
                            handLocationY--;
                            if(maze[yLoc][xLoc]!='F')
                            {
                               placeO(maze, xLoc, yLoc);//places an O where I am now
                            }
                        }
                        else{//rotates to the east
                             handLocationX++;
                             handLocationY++;
                            }
                            print(maze);
                            }
                            else if (hfacing.equals("West"))//facing west
                            {
                                if(westisOpen)//MOVES WEST
                                {
                                    placeX(maze, xLoc, yLoc);//places an X where I was
                                    xLoc--; //moves forward 1
                                    handLocationX--;
                                    if(maze[yLoc][xLoc]!='F')
                                    {
                                       placeO(maze, xLoc, yLoc);//places an O where I am now
                                    }
                                }
                                else if(northisOpen)//moves and rotates to face north
                                {
                                    placeX(maze, xLoc, yLoc);
                                    yLoc--;
                                    handLocationX++;
                                    if(maze[yLoc][xLoc]!='F')
                                    {
                                        placeO(maze, xLoc, yLoc);//places an O where I am now
                                    }
                                }
                                else{//rotates to the SOUTH
                                        handLocationX--;
                                        handLocationY++;
                                    }
                                    print(maze);
                            }
        }
                            return run(maze, xLoc, yLoc, handLocationX, handLocationY);
    }
    public char getData(char data)//returns the value in a location of the maze
    {
        return data;
    }
    public char[][] placeX(char[][] array, int x, int y)//places an X value
    {
        array[y][x] = 'X';
        return array;
    }
    public char[][] placeO(char[][] array, int x, int y)//places an O value
    {
        array[y][x] = 'O';
        return array;
    }

    public String findDirection(int xLoc, int yLoc, int handLocationX, int handLocationY)
    {
        String facing;
        if(xLoc == handLocationX && handLocationY > yLoc )
        {
        facing = "East";
        }else if(xLoc ==handLocationX && handLocationY < yLoc)
        {
            facing = "West";
        }else if(yLoc == handLocationY && xLoc > handLocationX)
        {
            facing = "South";
        }else{// facing North
             facing = "North";
             }
                return facing;
             }

    public void findOpening(char [][] maze, int xLoc, int yLoc, int handLocationX, int handLocationY)
    {
        northisOpen = false;
        southisOpen = false;
        westisOpen = false;
        eastisOpen = false;
        if (hfacing.equals("East"))//facing east
        {
        if(maze[handLocationY][handLocationX]=='.'||maze[handLocationY][handLocationX]=='X'||maze[handLocationY][handLocationX]=='F')//open to South
        {
            southisOpen=true;
        }
        else if(maze[yLoc][xLoc+1]=='.'||maze[yLoc][xLoc+1]=='X'||maze[yLoc][xLoc+1]=='F')//east path in front of us is clear
        {
            eastisOpen= true;
        }
        else{//must turn to the North
              northisOpen = true;
            }
        }
        else if(hfacing.equals("North"))//facing north
        {
            if(maze[handLocationY][handLocationX]=='.'||maze[handLocationY][handLocationX]=='X'||maze[handLocationY][handLocationX]=='F')//open to the EAST
            {
                eastisOpen = true;
            }
            else if(maze[yLoc-1][xLoc]=='.'||maze[yLoc-1][xLoc]=='X'||maze[yLoc-1][xLoc]=='F')//north is clear to take
            {
                northisOpen = true;
            }
            else{//must turn west
                westisOpen = true;
                }           
        }
        else if(hfacing.equals("South"))//facing south
        {
        if(maze[handLocationY][handLocationX]=='.'||maze[handLocationY][handLocationX]=='X'||maze[handLocationY][handLocationX]=='F')//open to the WEST
        {
          westisOpen = true;
        }
        else if(maze[yLoc+1][xLoc]=='.'||maze[yLoc+1][xLoc]=='X'||maze[yLoc+1][xLoc]=='F')//south is clear to take
        {
          southisOpen = true;
        }
        else{//must turn EAST
                eastisOpen = true;
            }
        }
        else{//facing WEST
            if(maze[handLocationY][handLocationX]=='.'||maze[handLocationY][handLocationX]=='X'||maze[handLocationY][handLocationX]=='F')//open to the north
            {
                northisOpen = true;
            }
            else if(maze[yLoc][xLoc-1]=='.'||maze[yLoc][xLoc-1]=='X'||maze[yLoc][xLoc-1]=='F')//west path in front of us is clear
            {
                westisOpen= true;
            }
            else{ //must turn to the south
                    southisOpen = true;
                }
            }
    }
}


