package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class BossBattleshipWave extends Wave {
  private int level;

  public BossBattleshipWave(GameState g) {
    super(g, 2400);
    level = 0;
  }
  public BossBattleshipWave(GameState g, int l) {
    this(g);
    level = l;
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    Entity boss = new EnemyBossBattleship(getGame(), getGame().getWidth()/2, -400);
    send.add(boss);

    if (level > 1) {
      for (int i = 0; i < 4; i++) {
        Entity e = new EnemyDrone(getGame(), 0, 0, level>2);
        send.add(new EnemyCarrier(getGame(), e, Math.random()*getGame().getWidth(), -20, 12));
      }
    }
    if (level > 2) {
      for (int i = 0; i < 2; i++) {
        Entity e = new EnemyShooter(getGame(), 0, 0, level>3);
        send.add(new EnemyCarrier(getGame(), e, Math.random()*getGame().getWidth(), -40, 10));
      }
    }
    if (level > 3) {
      Entity e = new EnemyGatling(getGame(), 0, 0);
      send.add(new EnemyCarrier(getGame(), e, Math.random()*getGame().getWidth(), -60, 8));
    }
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    return 0;
  }
  
}