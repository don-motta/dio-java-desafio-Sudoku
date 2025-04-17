package com.ui.custom.buttom;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FinishGameButtom extends JButton {
    public FinishGameButtom (final ActionListener actionListener) {
        this.setText("Concluir jogo");
        this.addActionListener(actionListener);
    }
}
