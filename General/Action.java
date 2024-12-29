package General;

public class Action {
  // id of action, time for action to progress
  private int id, timer;
  // optional extra data for action e.g. rotation, speed
  private double[] data;

  public Action(int i, int t) {
    id = i;
    timer = t;
  }
  public Action(int i, int t, double[] da) {
    id = i;
    timer = t;
    data = da;
  }
  
  // true = action can still execute, false = action has ended
  public boolean tick() {
    if (timer > 0) {
      timer--;
      return true;
    }
    return false;
  }

  public int getId() {
	  return id;
  }
  public int getTimer() {
	  return timer;
  }
  public double[] getData() {
	  return data;
  }
  public void setId(int id) {
	  this.id = id;
  }
  public void setTimer(int timer) {
	  this.timer = timer;
  }
  public void setData(double[] data) {
	  this.data = data;
  }

}