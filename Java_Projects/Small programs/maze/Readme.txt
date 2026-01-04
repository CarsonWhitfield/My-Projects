🧩 Java Maze Solver (Right-Hand Rule, Recursive)

This project is a Java maze-solving simulation that navigates a 2D character maze using a recursive right-hand wall-following algorithm. The maze is represented as a char[][] grid containing walls (#), open paths (.), a starting position (O), and a finish tile (F). As the solver moves, it marks visited locations with X and continuously prints the maze to show progress step-by-step.

   How It Works

The maze is stored as a 12×12 char[][]

The solver starts at a given (x, y) coordinate and tracks a “hand” position to determine which direction it is facing

Each step:

Determines the current facing direction (North, South, East, West)

Checks which adjacent tiles are open (., X, or F)

Moves forward or turns based on the right-hand rule logic

Marks the previous tile with X and the current tile with O

Prints the maze after each move

The recursion stops when the solver reaches F, returning "Finished!"

   Features

Maze stored as a 2D grid using characters

Recursive navigation until the finish is reached

Tracks direction and available openings dynamically

Visual output: prints the maze after each move

Marks visited paths to prevent confusion during output (X)

   Concepts Demonstrated

Recursion

2D arrays

Coordinate-based movement

State tracking (direction + openings)

Basic pathfinding strategy (wall-following / right-hand rule)

Console visualization of algorithms
