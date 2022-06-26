package gui;

import javax.swing.*;

public abstract class MenuOption extends JMenuItem {

    public MenuOption(String title, String toolTip) {
        super(title);
        this.setToolTipText(toolTip);
        this.addActionListener((event)->action());
    }

    public abstract void action();
}
