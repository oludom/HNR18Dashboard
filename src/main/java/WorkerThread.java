
public class WorkerThread extends Thread implements Runnable{

  private DataQueue dataQueue;
  private CarModel carModel;
  private boolean run = false;

  public WorkerThread(DataQueue dq, CarModel cm){
    dataQueue = dq;
    carModel = cm;
  }


  @Override
  public void run() {
    run = true;
    while(run){
      // mindestens eine volle Nachricht
      if(dataQueue.getLength() > 5){
        if(dataQueue.getFirst() == 3 && dataQueue.getLength() > 7){
          int[] data = dataQueue.pop(8);
          for(int d : data){
            System.out.print(d + " ");
          }
          System.out.println();
          ParseThread parseThread = new ParseThread(data, carModel);
          parseThread.start();
        }else if(dataQueue.getFirst() == 1 || dataQueue.getFirst() == 2){
          int[] data = dataQueue.pop(6);
          for(int d : data){
            System.out.print(d + " ");
          }
          System.out.println();
          ParseThread parseThread = new ParseThread(data, carModel);
          parseThread.start();
        }
      }
    }
  }

  public void end(){
    run = false;
  }

}
