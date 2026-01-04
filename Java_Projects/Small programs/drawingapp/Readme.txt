🎨 Java Swing Drawing Application

This project is a Java Swing–based drawing application that allows users to draw shapes and freehand strokes on a canvas using mouse input. The application demonstrates event-driven programming, custom painting, and graphics handling with Graphics2D.

  Overview

The DrawingApp class extends JFrame and implements multiple event listener interfaces to handle button actions, mouse clicks, and mouse movement. A BufferedImage is used as an off-screen canvas to persist drawings between repaints.

  Features

Draw filled and outlined rectangles

Draw filled and outlined ovals

Freehand drawing using mouse drag events

Adjustable stroke thickness via a slider

Clear canvas functionality

Custom-drawn buttons with visual shape previews

  Technical Highlights

Swing GUI Components

JFrame, JPanel, JButton, JSlider

Custom Painting

Overrides paintComponent to render a BufferedImage

Graphics2D API

Uses strokes, shapes, and colors for rendering

Event Handling

Implements ActionListener, MouseListener, and MouseMotionListener

Double Buffering

Drawing is performed on a BufferedImage to avoid flicker

  Program Flow

User selects a drawing mode using toolbar buttons

Mouse events capture user interaction

Shapes or freehand strokes are rendered onto the image buffer

The canvas repaints to reflect updates

The stroke slider controls line thickness dynamically
