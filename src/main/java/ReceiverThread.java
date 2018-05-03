import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReceiverThread extends Thread {

  private CarModel carModel;

  public ReceiverThread(CarModel carModel){
    this.carModel = carModel;
  }

  @Override
  public void run() {

    // all available serial ports
    String[] ports = SerialPortList.getPortNames();

    DataQueue dataQueue = new DataQueue();

    WorkerThread workerThread = new WorkerThread(dataQueue, carModel);
    workerThread.start();

    System.out.println("available Serial Ports: ");

    int i = 0;
    for (String port : ports){
      System.out.println(i++ + ": " + port);
    }

    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader keyb = new BufferedReader(isr);

    try {
      // get selection
      String text = keyb.readLine();
      int selection = Integer.parseInt(text);

      try {
        //Open serial port
        SerialPort serialPort = new SerialPort(ports[selection]);
        serialPort.openPort();
        serialPort.setParams(115200, 8, 1, 0);//Set params.

        // read byte by byte and add to queue
        while (true) {
          byte[] buffer = serialPort.readBytes(1);
          if (buffer != null) {
              int b = buffer[0] < 0 ? buffer[0] +256 : buffer[0];
              dataQueue.push(b);

          }
        }
      } catch (SerialPortException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
