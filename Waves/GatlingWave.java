package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class GatlingWave extends Wave {

  public GatlingWave(GameState g) {
    super(g);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();

    double centerPoint = Math.random()*240-120+getGame().getWidth()/2;
    
    Entity spawn = new EnemyGatling(getGame(), 0, 0);
    Entity carrier = new EnemyCarrier(getGame(), spawn, centerPoint, -120, 40);
    send.add(carrier);
    
    Entity spawn1 = new EnemyDrone(getGame(), 0, 0);
    Entity carrier1 = new EnemyCarrier(getGame(), spawn1, centerPoint+80, -60, 60);
    send.add(carrier1);

    Entity spawn2 = new EnemyDrone(getGame(), 0, 0);
    Entity carrier2 = new EnemyCarrier(getGame(), spawn2, centerPoint-80, -60, 60);
    send.add(carrier2);

    Entity spawn3 = new EnemyDrone(getGame(), 0, 0);
    Entity carrier3 = new EnemyCarrier(getGame(), spawn3, centerPoint+35, -20, 100);
    send.add(carrier3);

    Entity spawn4 = new EnemyDrone(getGame(), 0, 0);
    Entity carrier4 = new EnemyCarrier(getGame(), spawn4, centerPoint-35, -20, 100);
    send.add(carrier4);
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    double d = 0.8;
    if (level < 25) d = 0;
    return d;
  }
  
}