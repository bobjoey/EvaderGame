package Projectiles;
import General.*;
import Game.*;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;

public class Particle extends Projectile {
  private BufferedImage sprite;
  private double duration;
  private double grav;

  public Particle(GameState g, double x, double y) {
    super(g, 0, 0, 1, 0, 0, x, y);
    setupInfo();
  }

  public void setupInfo() {
    // setup speed, rotation, size, duration, grav, sprite
    double speed = 1+Math.random()*2;
    double rotation = Math.random()*Math.PI*2;
    int size = (int) (3+4*Math.random());
    double dur = 30+Math.random()*30;
    int color = (int) (20+Math.random()*150);
    setSpeed(speed);
    setRotation(rotation);
    setSize(size);
    duration = dur;
    grav = 0;

    setupSprite(size, color);
  }

  public void setupSprite(int s, int c) {
    sprite = new BufferedImage(s, s, BufferedImage.TYPE_INT_RGB);
    Graphics g = sprite.getGraphics();
    g.setColor(new Color(c, c, c));
    g.fillRect(0, 0, s, s);
    g.dispose();
  }

  @Override
  public void update() {
    moveInDir(getRotation(), getSpeed());
    setY(getY() + grav);
    grav += 0.15;
    
    duration--;
    if (duration < 0) destroy();
  }

  @Override
  public void onCollision(Entity e) {}

  @Override
  public Sprite getSprite() {
    return new Sprite(sprite, getX(), getY(), 4);
  }
  
}