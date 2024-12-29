package Game;

import java.util.HashSet;

public abstract class Wave {
  private GameState game;
  private double baseCD; // wave cooldown after this wave is sent

  public Wave(GameState g) {
    game = g;
    baseCD = 180;
  }
  
  public Wave(GameState g, double cd) {
    game = g;
    baseCD = cd;
  }

  public abstract HashSet<Entity> getWave();

  public abstract double getWeight(int level);
  
  public GameState getGame() {
    return game;
  }
  public double getCD() {
    return baseCD;
  }
}