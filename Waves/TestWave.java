package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class TestWave extends Wave {

  public TestWave(GameState g) {
    super(g, 9900);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    Entity spawn = new EnemyBattleship(getGame(), 250, -50);
    Entity carrier = new EnemyCarrier(getGame(), spawn, 250, -60, 60);
    send.add(carrier);
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    return 0;
  }
  
}