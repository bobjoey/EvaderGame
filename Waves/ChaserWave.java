package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class ChaserWave extends Wave {

  public ChaserWave(GameState g) {
    super(g);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    int w = getGame().getWidth();
    for (int i = 0; i < 3; i++) {
      Entity spawn = new EnemyChaser(getGame(), 0, 0);
      Entity carrier = new EnemyCarrier(getGame(), spawn, Math.random()*w, -60-120*i, 50+Math.random()*30);
      send.add(carrier);
    }
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    double d = 1;
    if (level < 1 || level > 15) d = 0;
    return d;
  }
  
}