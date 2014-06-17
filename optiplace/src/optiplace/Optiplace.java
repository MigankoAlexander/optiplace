/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optiplace;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Miganko
 */
public class Optiplace {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    new WorkFrame(900, 600,
                        "СППР по оптимальному размещению патрульных "
                        + "машин служб экстренного реагирования");
            }
        });
    }
}
