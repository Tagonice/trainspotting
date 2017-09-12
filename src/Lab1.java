import TSim.*;

import java.util.concurrent.Semaphore;


public class Lab1 {

  private TSimInterface tsi;
  private AddingArrayList<Semaphore> semaphores;

  public Lab1(Integer speed1, Integer speed2) {
    tsi = TSimInterface.getInstance();
    semaphores = new AddingArrayList<>();

    Thread train1 = new Train(1, speed1, true);
    Thread train2 = new Train(2, speed2, false);

    train1.start();
    train2.start();
  }

  public class Train extends Thread{
    int id;
    int speed;
    boolean downward;

    public Train(int id, int speed, boolean downward){
      this.id = id;
      this.speed = speed;
      this.downward = downward;
    }

    @Override
    public void run(){
      try{

        tsi.setSpeed(id, speed);
        while(true){
          SensorEvent se = tsi.getSensor(id);
          //Coming from north
          if((se.getXpos()==14) && (se.getYpos()==7) && downward){
            tsi.setSwitch(17,7,TSimInterface.SWITCH_RIGHT);
          }

          if((se.getXpos()==19) && (se.getYpos()==9) && downward){
            tsi.setSwitch(15,9,TSimInterface.SWITCH_RIGHT);
          }

          if((se.getXpos()==6) && (se.getYpos()==9) && downward) {
            tsi.setSwitch(4, 9, TSimInterface.SWITCH_LEFT);
            /*if(TSimInterface.switchDir != 0x01) {
              tsi.setSwitch(4, 9, TSimInterface.SWITCH_LEFT);
            }*/
          }   //Trying to turn a switch that is already turned the right way
          //Or so I thought, looks like passing the same switch twice makes it throw exception...

          /*if((se.getXpos()==1) && (se.getYpos()==10) && downward) {
            tsi.setSwitch(3, 11, TSimInterface.SWITCH_LEFT); //<--- Proves that you actually can turn an
          }*/                                                //already turned right switch

          if((se.getXpos()==1) && (se.getYpos()==10) && downward) {
            tsi.setSwitch(3, 11, TSimInterface.SWITCH_LEFT);
          }

          //Coming from south
          if((se.getXpos()==1) && (se.getYpos()==10) && !downward){
            tsi.setSwitch(4, 9, TSimInterface.SWITCH_RIGHT);
          }

          //North station
          if((se.getXpos()==15) && (se.getYpos()==5)){
            if(se.getStatus() == SensorEvent.ACTIVE){
              tsi.setSpeed(se.getTrainId(), 0);
            }
          }

          //South station
          if((se.getXpos()==15) && (se.getYpos()==13) || ((se.getXpos()==15) && (se.getYpos()==3))){
            if(se.getStatus() == SensorEvent.ACTIVE){
              tsi.setSpeed(se.getTrainId(), 0);
              sleep(1000 + Math.abs(speed) * 15);
              speed = -speed;
              tsi.setSpeed(se.getTrainId(), speed);
            }
          }
        }
      }
      catch (CommandException e){
        e.printStackTrace();
        System.exit(1);
      }
      catch (InterruptedException e){
        e.printStackTrace();
        System.exit(1);
      }
    }
  }
}
