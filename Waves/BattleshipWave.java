package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class BattleshipWave extends Wave {

  public BattleshipWave(GameState g) {
    super(g, 240);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    Entity spawn = new EnemyBattleship(getGame(), 0, 0);
    Entity carrier = new EnemyCarrier(getGame(), spawn, Math.random()*getGame().getWidth(), -60, 60);
    send.add(carrier);
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    double d = 0.4;
    if (level < 60) d = 0;
    return d;
  }
  
}