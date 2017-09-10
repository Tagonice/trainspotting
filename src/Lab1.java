import TSim.*;


public class Lab1 {

  private TSimInterface tsi;

  public Lab1(Integer speed1, Integer speed2) {
    tsi = TSimInterface.getInstance();

    Thread train1 = new Train(1, speed1);
    Thread train2 = new Train(2, speed2);

    train1.start();
    train2.start();
  }

  public class Train extends Thread{
    int id;
    int speed;

    public Train(int id, int speed){
      this.id = id;
      this.speed = speed;
    }

    @Override
    public void run(){
      try{
        tsi.setSpeed(id, speed);
        while(true){
          SensorEvent se = tsi.getSensor(id);

          if((se.getXpos()==15) && (se.getYpos() == 13)){
            if(se.getStatus() == SensorEvent.ACTIVE){
              tsi.setSpeed(se.getTrainId(), 0);
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
