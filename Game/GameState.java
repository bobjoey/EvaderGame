package Game;
import General.*;
import Entities.*;
import Projectiles.*;
import GUI.*;

import java.util.HashSet;
import java.util.ArrayList;

public class GameState {
  private PanelControl controller;
  private SpriteSheet sheet;

  private int width, height;

  private Player player;
  private ArrayList<Entity> entities;
  private ArrayList<Projectile> projectiles;
  private HashSet<GameObj> toAdd, toRemove;

  private WaveController waveControl;

  public GameState(PanelControl p, SpriteSheet s) {
    controller = p;
    sheet = s;
    width = 500;
    height = 600;
    setup();
  }

  public void setup() {
    entities = new ArrayList<Entity>();
    projectiles = new ArrayList<Projectile>();
    
    toAdd = new HashSet<GameObj>();
    toRemove = new HashSet<GameObj>();

    waveControl = new WaveController(this);
    
    player = new Player(this);
    player.setX(width/2);
    player.setY(500);
  }

  public void updateGame() {
    updateObjs();
    updateLists();
    waveControl.update();
  }

  public void updateObjs() {
    player.update();
    for (Entity e: entities) {
      e.update();
    }
    for (Projectile p: projectiles) {
      p.update();
    }
  }

  public void updateLists() {
    for (GameObj o: toRemove) {
      if (o instanceof Entity && entities.contains((Entity)o)) {
        for (int i = 0; i < entities.size(); i++) {
          if ((Entity)o == entities.get(i)) entities.remove(i);
        }
      }
      if (o instanceof Projectile && projectiles.contains((Projectile)o)) {
        for (int i = 0; i < projectiles.size(); i++) {
          if ((Projectile)o == projectiles.get(i)) projectiles.remove(i);
        }
      }
    }
    toRemove.clear();
    
    for (GameObj o: toAdd) {
      if (o instanceof Entity) {
        entities.add((Entity)o);
      }
      if (o instanceof Projectile) {
        projectiles.add((Projectile)o);
      }
    }
    toAdd.clear();
  }

  public void gameOver() {
    ((LosePanel)controller.getPanel("LOSE")).setWave(waveControl.getWaveNum());
    controller.setScreen("LOSE");
    
    setup();
  }

  public void pause() {
    for (int i = 0; i <= 6; i++) player.keyR(i);
    controller.setScreen("PAUSE");
  }

  public void addEntity(Entity e) {
    int team = e.getTeam();
    if (team != 0 && team != 1) e.setTeam(-1);
    toAdd.add(e);
  }

  public void addProjectile(Projectile p) {
    toAdd.add(p);
  }

  public void remove(GameObj o) {
    toRemove.add(o);
  }

  public int getWidth() {
    return width;
  }
  public int getHeight() {
	  return height;
  }
  public PanelControl getController() {
	  return controller;
  }
  public WaveController getWaveControl() {
    return waveControl;
  }
  public SpriteSheet getSheet() {
	  return sheet;
  }
  public Player getPlayer() {
	  return player;
  }
  public ArrayList<Entity> getEntities() {
	  return entities;
  }
  public ArrayList<Projectile> getProjectiles() {
	  return projectiles;
  }
  
}