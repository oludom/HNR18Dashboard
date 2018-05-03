
public class WorkerThread extends Thread implements Runnable{

  DataQueue dataQueue;
  CarModel carModel;

  public WorkerThread(DataQueue dq, CarModel cm){
    dataQueue = dq;
    carModel = cm;
  }


  @Override
  public void run() {
    while(true){
      if(dataQueue.getLength() > 5){
        if(dataQueue.getFirst() == 3){
          int[] data = dataQueue.pop(8);
          for(int d : data){
            System.out.print(d + " ");
          }
          System.out.println();
          ParseThread parseThread = new ParseThread(data, carModel);
          parseThread.start();
        }else{
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
}
