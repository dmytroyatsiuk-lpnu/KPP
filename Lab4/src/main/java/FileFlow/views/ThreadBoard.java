package main.java.FileFlow.views;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ThreadBoard extends JPanel {
    private Map<String, ThreadPanel> ThreadPanels;
    public ThreadBoard() {
        ThreadPanels = new HashMap<>();
        this.setLayout(new GridLayout(0, 4, 10, 10));
    }

    public void setThreadsPanels(Map<String, ThreadPanel> threadsPanels) {
        ThreadPanels = threadsPanels;
        ThreadPanels.forEach((k, v) -> {
            this.add(v);
        });
        this.revalidate();
        this.repaint();

    }

    public void updateState(String nameThread) {
        System.out.println(this.getComponents());

    }
}
