package General;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

public class Tools {
  // get the angle in radians from (x1, y1) to (x2, y2)
  public static double angleTo(double x1, double y1, double x2, double y2) {
    double xd = x2 - x1;
    double yd = y2 - y1;
    double a = Math.atan2(yd, xd);
    //if (a < 0) a += Math.PI*2;
    return a;
  }

  public static double dist(double x1, double y1, double x2, double y2) {
    double a = x2 - x1;
    double b = y2 - y1;
    return Math.sqrt(a*a+b*b);
  }

  public static BufferedImage resize(BufferedImage original, double scale) {
    int w = original.getWidth();
    int h = original.getHeight();
    BufferedImage bimg = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
    Graphics g = bimg.getGraphics();
    g.drawImage(original.getScaledInstance((int) (w*scale), (int)(h*scale), Image.SCALE_DEFAULT), 0, 0, null);
    g.dispose();

    return bimg;
  }

  public static BufferedImage rotate(BufferedImage bimg, double angle) {
    double sin = Math.abs(Math.sin(-angle));
    double cos = Math.abs(Math.cos(-angle));
    int w = bimg.getWidth();
    int h = bimg.getHeight();
    int w2 = (int) (w*cos + h*sin);
    int h2 = (int) (h*cos + w*sin);
    BufferedImage newImg = new BufferedImage(w2, h2, bimg.getType());
    Graphics2D g = newImg.createGraphics();
    g.translate((w2-w)/2, (h2-h)/2);
    g.rotate(-angle, w/2, h/2);
    g.drawRenderedImage(bimg, null);
    g.dispose();
    return newImg;
  }

  public static void drawCenteredText(Graphics g, String s, int x, int y) {
    FontMetrics fm = g.getFontMetrics();
    x -= fm.stringWidth(s)/2;
    g.drawString(s, x, y);
  }
  
}