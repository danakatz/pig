package com.danakatz.pig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PigFrame extends JFrame {

    private final int FRAME_WIDTH = 275;
    private final int FRAME_HEIGHT = 230;

    private final int HUMAN = 1;
    private final int COMPUTER = -1;

    private final int MAX_COMPUTER_ROLLS = 10;
    private final int DELAY = 1500;

    private final int WINNING_SCORE = 100;

    private int humanTurnTotal;
    private int humanGrandTotal;

    private int compTurnTotal;
    private int compGrandTotal;

    private JButton roll;
    private JButton hold;

    private int numCompRolls;
    private int compRollsSoFar;

    private JLabel humanTurnLabel;
    private JLabel humanTotalLabel;
    private JLabel compTurnLabel;
    private JLabel compTotalLabel;
    private JLabel whoseTurnLabel;

    private DiceComponent dice;

    private int whoseTurn;
    private int thisRoll;

    Timer t;
    Random generate;

    public PigFrame() {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        humanTurnTotal = 0;
        humanGrandTotal = 0;
        compGrandTotal = 0;
        compTurnTotal = 0;
        whoseTurn = HUMAN;
        thisRoll = 0;

        layoutElements();

        generate = new Random();
        t = new Timer(DELAY, new CompTurnListener());
        t.start();
    }

    private void layoutElements() {

        // create roll and hold buttons
        roll = new JButton("Roll");
        roll.addActionListener(new RollButtonListener());
        hold = new JButton("Hold");
        hold.addActionListener(new HoldButtonListener());

        humanTotalLabel = new JLabel("Grand total : " + humanGrandTotal, SwingConstants.CENTER);
        humanTurnLabel = new JLabel("Turn total : " + humanTurnTotal, SwingConstants.CENTER);
        compTotalLabel = new JLabel("Grand total : " + compGrandTotal, SwingConstants.CENTER);
        compTurnLabel = new JLabel("Turn total : " + compTurnTotal, SwingConstants.CENTER);
        whoseTurnLabel = new JLabel("YOUR TURN", SwingConstants.CENTER);

        // create score information labels
        Container labels = Box.createHorizontalBox();
        labels.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel humanScore = new JPanel();
        humanScore.setLayout(new GridLayout(3, 1));
        humanScore.add(new JLabel("HUMAN SCORE", SwingConstants.CENTER));
        humanScore.add(humanTurnLabel);
        humanScore.add(humanTotalLabel);

        JPanel computerScore = new JPanel();
        computerScore.setLayout(new GridLayout(3, 1));
        computerScore.add(new JLabel("COMPUTER SCORE", SwingConstants.CENTER));
        computerScore.add(compTurnLabel);
        computerScore.add(compTotalLabel);

        labels.add(humanScore);
        labels.add(Box.createHorizontalGlue());
        labels.add(computerScore);
        labels.add(Box.createRigidArea(new Dimension(15, 0)));

        // add whose turn label
        Container turn = Box.createHorizontalBox();
        turn.add(Box.createHorizontalGlue());
        turn.add(whoseTurnLabel);
        turn.add(Box.createHorizontalGlue());

        Container north = Box.createVerticalBox();
        north.add(Box.createRigidArea(new Dimension(0, 20)));
        north.add(turn);

        // create dice and buttons
        dice = new DiceComponent();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(1, 2));
        innerPanel.add(dice);

        Container buttons = Box.createVerticalBox();
        buttons.add(Box.createRigidArea(new Dimension(0, 25)));
        buttons.add(roll);
        buttons.add(Box.createVerticalGlue());
        buttons.add(hold);
        buttons.add(Box.createRigidArea(new Dimension(0, 140)));

        Container south = Box.createVerticalBox();
        south.add(labels);
        south.add(Box.createRigidArea(new Dimension(0, 20)));

        innerPanel.add(buttons);

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());

        outerPanel.add(innerPanel, BorderLayout.CENTER);
        outerPanel.add(south, BorderLayout.SOUTH);
        outerPanel.add(north, BorderLayout.NORTH);

        add(outerPanel);
    }

    class RollButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            thisRoll = generate.nextInt(6) + 1;
            dice.setNumDots(thisRoll);
            if(thisRoll != 1 && whoseTurn == HUMAN) {
                humanTurnTotal += thisRoll;
                humanTurnLabel.setText("Turn total : " + humanTurnTotal);
            } else if(thisRoll == 1 && whoseTurn == HUMAN) {
                humanTurnTotal = 0;
                humanTurnLabel.setText("Turn total : " + humanTurnTotal);
                switchTurn();
            }
        }
    }

    class HoldButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            humanGrandTotal += humanTurnTotal;
            humanTurnTotal = 0;
            humanTurnLabel.setText("Round total : " + humanTurnTotal);
            humanTotalLabel.setText("Grand total : " + humanGrandTotal);

            if(humanGrandTotal >= WINNING_SCORE) {
                humanTurnLabel.setText("YOU WIN!");
                roll.setEnabled(false);
                hold.setEnabled(false);
            } else {
                switchTurn();
            }
        }
    }


    class CompTurnListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(whoseTurn == COMPUTER && compRollsSoFar < numCompRolls) {
                thisRoll = generate.nextInt(6) + 1;
                dice.setNumDots(thisRoll);
                if(thisRoll != 1 && whoseTurn == COMPUTER) {
                    compTurnTotal += thisRoll;
                    compTurnLabel.setText("Round total : " + compTurnTotal);
                    compRollsSoFar++;
                    if(compTurnTotal + compGrandTotal >= WINNING_SCORE) {           // computer stops rolling if it's
                        compRollsSoFar = numCompRolls;                              // reached 100
                    }
                } else if(thisRoll == 1 && whoseTurn == COMPUTER) {
                    compTurnTotal = 0;
                    compTurnLabel.setText("Round total : " + compTurnTotal);
                    switchTurn();
                }
            } else if(whoseTurn == COMPUTER && compRollsSoFar == numCompRolls) {
                compGrandTotal += compTurnTotal;
                compTurnTotal = 0;
                compTotalLabel.setText("Grand total : " + compGrandTotal);
                if(compGrandTotal >= WINNING_SCORE) {
                    compTurnLabel.setText("COMPUTER WINS.");
                } else {
                    compTurnLabel.setText("Round total : " + compTurnTotal);
                    switchTurn();
                }
            }
        }
    }

    private void switchTurn() {
        if(whoseTurn == HUMAN) {
            numCompRolls = generate.nextInt(MAX_COMPUTER_ROLLS) + 1;
            compRollsSoFar = 0;
            roll.setEnabled(false);
            hold.setEnabled(false);

            whoseTurnLabel.setText("COMPUTER'S TURN");
            whoseTurn = COMPUTER;
        } else {
            compRollsSoFar = 0;
            numCompRolls = 0;
            roll.setEnabled(true);
            hold.setEnabled(true);
            whoseTurnLabel.setText("YOUR TURN");
            whoseTurn = HUMAN;
        }
    }
}
