public static void pumpkinPie() {
    
    DrawingPanel panel = new DrawingPanel(300, 200);
    Graphics g = panel.getGraphics();
    
    int x = 100;
    int y = 50; // top left corner at (100, 50)
    int width = 100;
    int height = 100;

    g.setColor(new Color(255, 128, 0));
    g.fillOval(x, y, width, height);

    g.setColor(Color.BLACK);
    g.drawOval(x, y, width, height);

    g.drawLine(x, y + height / 2, x + width, y + height / 2);
    g.drawLine(x + width / 2, y, x + width / 2, y + height);

}

