package pack;
import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private Image backgroundImage;

    public MyPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}