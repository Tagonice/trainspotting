import TSim.*;

import java.util.concurrent.Semaphore;

public class Lab1 {

  private TSimInterface tsi;

  private AddingArrayList<Semaphore> semaphores;
  private AddingArrayList<int[]> sensors;

  public Lab1(Integer speed1, Integer speed2) {
    tsi = TSimInterface.getInstance();

    semaphores = new AddingArrayList<>();
    sensors = new AddingArrayList<>();

    try {
      tsi.setSpeed(1,speed1);
      tsi.setSpeed(2, speed2);
      tsi.setSwitch(17,7, TSimInterface.SWITCH_RIGHT);
      tsi.setSwitch(15,9,TSimInterface.SWITCH_RIGHT);
      //SensorEvent se = new SensorEvent(1,12,3,SensorEvent.ACTIVE );
      //tsi.setSpeed(1,0);
    }
    catch (CommandException e) {
      e.printStackTrace();    // or only e.getMessage() for the error
      System.exit(1);
    }
  }
}
