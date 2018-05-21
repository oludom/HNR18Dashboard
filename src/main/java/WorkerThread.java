
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

      int[] queue = dataQueue.getUntilFF();

      if(queue.length <1) continue;

      // short message
      if(queue.length == 8 && (queue[2] == 17 || queue[2] == 34)){
        System.out.println();
        System.out.println("message num: " + queue[2]);
        System.arraycopy(queue, 2, queue, 0, 6);
        ParseThread parseThread = new ParseThread(queue, carModel);
        parseThread.start();
      }
      // long message
      else if(queue.length == 10 && queue[2] == 51){
        System.out.println();
        System.out.println("message num: " + queue[2]);
        System.arraycopy(queue, 2, queue, 0, 8);
        ParseThread parseThread = new ParseThread(queue, carModel);
        parseThread.start();
      }else{
        // message might be corrupt
      }



//
//      if(dataQueue.getLength() > 5){
//        if(dataQueue.getFirst() == 3 && dataQueue.getLength() > 7){
//          int[] data = dataQueue.pop(8);
//          for(int d : data){
//            System.out.print(d + " ");
//          }
//          System.out.println();
//          ParseThread parseThread = new ParseThread(data, carModel);
//          parseThread.start();
//        }else if(dataQueue.getFirst() == 1 || dataQueue.getFirst() == 2){
//          int[] data = dataQueue.pop(6);
//          for(int d : data){
//            System.out.print(d + " ");
//          }
//          System.out.println();
//          ParseThread parseThread = new ParseThread(data, carModel);
//          parseThread.start();
//        }
//      }
    }
  }

  public void end(){
    run = false;
  }

}
