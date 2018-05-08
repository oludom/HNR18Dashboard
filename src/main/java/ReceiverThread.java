import jssc.SerialPort;
import jssc.SerialPortException;

public class ReceiverThread extends Thread {

  private DataQueue dataQueue;
  private boolean run = false;
  private WorkerThread workerThread;
  private SerialPort serialPort;
  private String selection;

  public ReceiverThread(CarModel carModel, String selection){
    this.selection = selection;
    dataQueue = new DataQueue();
    workerThread = new WorkerThread(dataQueue, carModel);
  }

  @Override
  public void run() {
    run = true;

    workerThread.start();

    try {
      //Open serial port
      serialPort = new SerialPort(selection);
      serialPort.openPort();
      serialPort.setParams(9600, 8, 1, 0);//Set params.

      // read byte by byte and add to queue
      while (run) {
        byte[] buffer = serialPort.readBytes(1);
        if (buffer != null) {
            int b = buffer[0] < 0 ? buffer[0] +256 : buffer[0];
            dataQueue.push(b);

        }
      }
    } catch (SerialPortException e) {
      e.printStackTrace();
    }

  }

  public void end(){
    if(run){
      run = false;
      workerThread.end();
      try {
        workerThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      try {
        if(serialPort != null)
          serialPort.closePort();
      } catch (SerialPortException e) {
        System.out.println("could not close serial port.");
      }
    }
  }

}
