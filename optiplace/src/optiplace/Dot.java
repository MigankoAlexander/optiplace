package optiplace;

import java.awt.*;

/**
 */
public class Dot extends Figure2D {

    public Dot(int centerX, int centerY, Color color) {
        super(centerX, centerY, color);
    }
        
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getCenterX()-3, getCenterY()-3, 6, 6); //6 - dot's size
    }
}
