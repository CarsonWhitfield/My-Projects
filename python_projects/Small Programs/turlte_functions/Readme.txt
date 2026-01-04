🐢 Turtle Drawing Color Changer (Python)

This Python program uses the turtle graphics library to create a simple interactive drawing setup. The user can click and drag a circular turtle cursor to draw on the screen, and clicking a designated “Click the Box” area changes the drawing color to a random RGB color.

🔹 What It Does

Creates a turtle graphics window with three turtles:

picasso: the drawing cursor (shaped like a circle)

clickbox: draws a square “button” area on the screen

instruction: displays instructions on the canvas

Draws a clickable square box in the top-left corner

Displays instructions for the user

Listens for mouse clicks:

If the user clicks inside the box, a random color is generated

The drawing turtle (picasso) changes to that color

The instruction text is redrawn using the same color

🔹 Interaction

Click and drag the circle to draw

Click the box to change the drawing color randomly

🔹 Concepts Demonstrated

Turtle graphics and multiple turtle objects

Mouse event handling (window.onclick)

Simple UI elements using drawn shapes and text

Random RGB color generation (random.random)

Coordinate-based click detection
