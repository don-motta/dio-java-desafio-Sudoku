package com.ui.custom.panel;

import com.ui.custom.input.NumberText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static java.awt.Color.black;
import static java.awt.Color.darkGray;

import java.util.List;

public class SudokuSector extends JPanel {
    public SudokuSector(final List<NumberText> textFields) {
        var dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        //this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
        this.setBackground(darkGray);
        textFields.forEach(this::add);
    }
}
