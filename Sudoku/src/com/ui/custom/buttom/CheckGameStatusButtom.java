package com.ui.custom.buttom;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckGameStatusButtom extends JButton {
    public CheckGameStatusButtom(final ActionListener actionListener) {
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }

}
