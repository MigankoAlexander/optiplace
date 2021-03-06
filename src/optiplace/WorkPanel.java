/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optiplace;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Miganko
 */
public class WorkPanel extends JPanel {
    
    
    List<Point> figures = new ArrayList<>();
    
      private BufferedImage image;

    public WorkPanel() {
       try {                
          image = ImageIO.read(this.getClass().getResource("images/map2.png"));
       } catch (IOException ex) {
            System.out.println("Can't load");
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);        
    }

    public void addFigure(Point figure) {
        figures.add(figure);
        repaint();
    }
    
    public List<Point> getFigures(){
        return figures;
        
    }
    
    public void cleanFigures(){
        figures = new ArrayList<>();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Point figure : figures) {
            figure.draw(g);
        }
    }
}
