

public class ParseThread extends Thread implements Runnable {

  private int[] data;
  private CarModel carModel;

  public ParseThread(int[] data, CarModel carModel){
    this.data = data;
    this.carModel = carModel;
  }

  @Override
  public void run() {

    if(data[0] == 1){
      messageOne();
    }else if(data[0] == 2){
      messageTwo();
    }

  }

  private void messageOne(){

    int engineSpeed = ((data[1] << 8) | data[2]) >> 2;
    System.out.println("engine speed: " + engineSpeed);
    carModel.setEngineSpeed(engineSpeed);

    int gear = (data[3]>>5);
    System.out.println("gear: " +gear);
    carModel.setGear(gear);

    int intake = ((0b111 & data[3]) << 8) | data[4];
    System.out.println("intake air pressure: " + intake);
    carModel.setIntakeAirPressure(intake);

    int throttle = 0b01111111 & data[5];
    System.out.println("throttle valve position: " + throttle);
    carModel.setThrottleValvePosition(throttle);

  }

  private void messageTwo(){

    int oil = (data[1] << 1) | (data[2] >> 7);
    System.out.println("engine oil pressure: " + oil);
    carModel.setEngineOilPressure(oil);

    int speed = data[2] & 0b01111111;
    System.out.println("speed: " + speed);
    carModel.setVelocity(speed);

    int exhaust = (data[3] << 3) | (data[4] >> 5);
    System.out.println("exhaust: " + exhaust);
    carModel.setLambdaExhaust(exhaust);

    int steerangle = data[5] & 0b01111111;
    steerangle -= 40;
    System.out.println("steerangle: " + steerangle);
    carModel.setSteerAngle(steerangle);

  }

}
