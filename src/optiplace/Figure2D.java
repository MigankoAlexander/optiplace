package optiplace;

import java.awt.Color;

/**
 */
public abstract class Figure2D implements Point {
    private int centerX;
    private int centerY;
    private Color color;

    public Figure2D(int centerX, int centerY, Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public Color getColor() {
        return color;
    }
}
