package main.java.FileFlow.views;

import javax.swing.*;
import java.awt.*;

public class ThreadPanel extends JPanel {
    private final JLabel nameLabel;
    private final JLabel statusLabel;
    private final JLabel timeLabel;
    private long totalTime;

    public ThreadPanel(String threadName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        nameLabel = new JLabel("Потік: " + threadName);
        statusLabel = new JLabel("Стан: Очікує");
        timeLabel = new JLabel("Час виконання: -");

        add(nameLabel);
        add(statusLabel);
        add(timeLabel);
    }

    public void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> statusLabel.setText("Стан: " + status));
    }

    public void updateTime(long time) {
        totalTime += time;
        SwingUtilities.invokeLater(() -> timeLabel.setText("Час: " + totalTime));
    }

    public void updateExecutionTime(long time) {
        totalTime += time;
        SwingUtilities.invokeLater(() -> timeLabel.setText("Час виконання: " + totalTime + " ms"));
    }
}
