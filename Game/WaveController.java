package Game;
import Waves.*;

import java.util.ArrayList;

public class WaveController {
  private GameState game;
  private int waveNum; // wave number
  private double waveCD; // wave cooldown - time until next wave
  private double scaling; // difficulty scale
  private double skipTimer;
  private ArrayList<Wave> waves;

  public WaveController(GameState g) {
    game = g;
    waveNum = 0;
    waveCD = 30;
    scaling = 0;
    skipTimer = 60;

    setupWaves();
  }

  public void update() {
    updateDifficulty();
    waveCD -= 1;
    skipWave();
    
    if (waveCD <= 0) {
      sendWave(waveNum);
    }
    
  }

  public Wave getWave(int level) {
    Wave scriptedWave = getScriptedWave(level);
    if (scriptedWave != null) {
      return scriptedWave;
    }
    
    ArrayList<Double> weights = new ArrayList<Double>();
    double weightSum = 0;
    
    for (Wave w : waves) {
      weightSum += w.getWeight(level);
      weights.add(weightSum);
    }
    
    double index = Math.random() * weightSum;
    for (int i = 0; i < weights.size(); i++) {
      if (index < weights.get(i)) {
        return waves.get(i);
      }
    }
    
    System.out.println("no valid waves");
    return new RandomWave(game);
  }

  public Wave getScriptedWave(int level) {
    if (level >= 50 && level % 50 == 0) {
      return new BossBattleshipWave(game, level/50);
    }
    
    return null;
  }

  public void sendWave(int level) {
    waveNum++;
    Wave toSend = getWave(level);
    for (Entity e : toSend.getWave()) {
      game.addEntity(e);
    }
    waveCD = toSend.getCD();
    skipTimer = 60;
  }

  public void setupWaves() {
    waves = new ArrayList<Wave>();
    waves.add(new TestWave(game));
    waves.add(new DroneWave(game));
    waves.add(new ChaserWave(game));
    waves.add(new ShooterWave(game));
    waves.add(new RandomWave(game));
    waves.add(new DroneWaveElite(game));
    waves.add(new ChaserWaveElite(game));
    waves.add(new ShooterWaveElite(game));
    waves.add(new RandomWaveElite(game));
    waves.add(new GatlingWave(game));
    waves.add(new BattleshipWave(game));
  }
  
  public void updateDifficulty() {
    waveCD -= 4-4/(1+scaling*0.25);
    scaling += 0.0005;
  }

  public void skipWave() {
    boolean x = true;
    for (Entity e : game.getEntities()) {
      if (e.getTeam() == 0) continue;
      x = false;
    }
    if (x) skipTimer--;
    else skipTimer = 60;
    
    if (skipTimer <= 0) {
      skipTimer = 60;
      waveCD = 5;
      System.out.println(waveNum + ": wave delay skip triggered");
    }
  }

  public int getWaveNum() {
	  return waveNum;
  }
  
}