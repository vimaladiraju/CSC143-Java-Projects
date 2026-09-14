import java.awt.*;

public class PrettyPicture {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(220, 150);
        panel.setBackground(Color.YELLOW);

        Graphics g = panel.getGraphics();
        g.setColor(Color.BLUE);
        g.drawRect(50, 25, 10, 10);
        g.setColor(Color.RED);
        g.fillOval(130, 25, 42, 40);
    }
}