package com.danakatz.pig;

import javax.swing.*;
import java.awt.*;

public class DiceComponent extends JComponent {
    
    private final int DICE_EDGE = 50;
    private final int DICE_CORNER_X = 51;
    private final int DICE_CORNER_Y = 26;
    private final int DOT_DIAM = 6;
    private final int LEFT_X = 60;
    private final int RIGHT_X = 86;
    private final int TOP_Y = 35;
    private final int BOTTOM_Y = 61;
    private final int CENTER_X = 73;
    private final int CENTER_Y = 48;
    
    int numDots;

    public void paintComponent(Graphics g) {
        g.drawRect(DICE_CORNER_X, DICE_CORNER_Y, DICE_EDGE, DICE_EDGE);
        if(numDots == 1) {
            g.fillOval(CENTER_X, CENTER_Y, DOT_DIAM, DOT_DIAM);
        } else if(numDots == 2) {
            g.fillOval(LEFT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
        } else if (numDots == 3) {
            g.fillOval(LEFT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(CENTER_X, CENTER_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
        } else if(numDots == 4) {
            g.fillOval(LEFT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(LEFT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
        } else if(numDots == 5) {
            g.fillOval(LEFT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(LEFT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(CENTER_X, CENTER_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
        } else if(numDots == 6) {
            g.fillOval(LEFT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(LEFT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(LEFT_X, CENTER_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, TOP_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, BOTTOM_Y, DOT_DIAM, DOT_DIAM);
            g.fillOval(RIGHT_X, CENTER_Y, DOT_DIAM, DOT_DIAM);
        }
    }

    public void setNumDots(int dots) {
        this.numDots = dots;
        repaint();
    }
}
