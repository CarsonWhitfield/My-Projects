/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package drawingapp;

/**
 *
 * @author Cwhit
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

public class DrawingApp extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    private BufferedImage image;
    private Graphics2D g2d;
    private JButton clearButton, filledRectButton, filledOvalButton, emptyRectButton, emptyOvalButton, freeHandButton;
    private JPanel paintPanel;
    private Color currentColor;
    private int strokeThickness;

    public DrawingApp() {
        setTitle("Drawing App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();

        clearButton = createButton("Clear", null);
        filledRectButton = createButton("Filled Rectangle", new Rectangle2D.Double(10, 10, 100, 50));
        filledOvalButton = createButton("Filled Oval", new Ellipse2D.Double(10, 10, 100, 50));
        emptyRectButton = createButton("Empty Rectangle", new Rectangle2D.Double(10, 10, 100, 50));
        emptyOvalButton = createButton("Empty Oval", new Ellipse2D.Double(10, 10, 100, 50));
        freeHandButton = createButton("Free Hand", null);

        paintPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        paintPanel.setPreferredSize(new Dimension(800, 600));
        paintPanel.addMouseListener(this);
        paintPanel.addMouseMotionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(filledRectButton);
        buttonPanel.add(filledOvalButton);
        buttonPanel.add(emptyRectButton);
        buttonPanel.add(emptyOvalButton);
        buttonPanel.add(freeHandButton);

        JSlider strokeSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        strokeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            strokeThickness = source.getValue();
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(strokeSlider);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(paintPanel, BorderLayout.CENTER);
        contentPane.add(controlPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private JButton createButton(String text, Shape shape) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (shape != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(currentColor);
                    g2.setStroke(new BasicStroke(strokeThickness));
                    g2.draw(shape);
                }
            }
        };
        button.setPreferredSize(new Dimension(120, 30));
        button.addActionListener(this);
        button.setText(text);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            clearImage();
        } else if (e.getSource() == filledRectButton) {
            drawShape(new Rectangle2D.Double(10, 10, 100, 50));
        } else if (e.getSource() == filledOvalButton) {
            drawShape(new Ellipse2D.Double(10, 10, 100, 50));
        } else if (e.getSource() == emptyRectButton) {
            drawShape(new Rectangle2D.Double(10, 10, 100, 50), true);
        } else if (e.getSource() == emptyOvalButton) {
            drawShape(new Ellipse2D.Double(10, 10, 100, 50), true);
        } else if (e.getSource() == freeHandButton) {
            paintPanel.addMouseMotionListener(this);
        }
    }

    private void clearImage() {
        g2d.clearRect(0, 0, image.getWidth(), image.getHeight());
        paintPanel.repaint();
    }

    private void drawShape(Shape shape) {
        g2d.setColor(currentColor);
        g2d.setStroke(new BasicStroke(strokeThickness));
        g2d.draw(shape);
        paintPanel.repaint();
    }

    private void drawShape(Shape shape, boolean isFilled) {
        g2d.setColor(currentColor);
        if (isFilled) {
            g2d.fill(shape);
        } else {
            g2d.setStroke(new BasicStroke(strokeThickness));
            g2d.draw(shape);
        }
        paintPanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        // Capture the initial mouse press coordinates for freehand drawing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Implement the logic to finish drawing based on the mouse release coordinates
        if (e.getSource() != freeHandButton) {
            drawShape(new Rectangle2D.Double(10, 10, 100, 50)); // Replace with appropriate shape
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        // Implement the logic to draw freehand based on the mouse drag coordinates
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DrawingApp::new);
    }
}