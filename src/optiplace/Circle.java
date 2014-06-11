package optiplace;

import java.awt.*;

/**
 * @author: Ilya Sadykov (mailto: smecsia@yandex-team.ru)
 */
public class Circle extends Figure2D {
        
    private int radius;
        
    public Circle(int centerX, int centerY, int radius, Color color) {
        super(centerX, centerY, color);
        this.radius = radius;
        
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawOval(getCenterX()-radius, getCenterY()-radius,
                2*radius, 2*radius);
    }
}
