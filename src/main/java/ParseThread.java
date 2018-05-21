

public class ParseThread extends Thread implements Runnable {

  private int[] data;
  private CarModel carModel;

  public ParseThread(int[] data, CarModel carModel){
    this.data = data;
    this.carModel = carModel;
  }

  @Override
  public void run() {

    if(data[0] == 17){
      messageOne();
    }else if(data[0] == 34){
      messageTwo();
    }else if(data[0] == 51){
      messageThree();
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
    System.out.println("engine oil pressure: " + oil/100);
    carModel.setEngineOilPressure(oil/100);

    int speed = data[2] & 0b01111111;
    System.out.println("speed: " + speed);
    carModel.setVelocity(speed);

    int exhaust = (data[3] << 3) | (data[4] >> 5);
    float ex = exhaust + 500;
    ex /= 1000;
    System.out.println("exhaust: " + ex);
    carModel.setLambdaExhaust(ex);

    int steerangle = (data[5] & 0b01111111);
    steerangle = steerangle - 40;
    System.out.println("steerangle: " + steerangle);
    carModel.setSteerAngle(steerangle);

  }

  private void messageThree(){

    int batteryVoltage = data[1];
    float bV = batteryVoltage;
    bV /= 10;
    System.out.println("battery voltage: " + bV);
    carModel.setBatteryVoltage(bV);

    int coolantTemp = data[2];
    System.out.println("engine coolant temperature: " + coolantTemp);
    carModel.setEngineCoolantTemp(coolantTemp);

    int oilTemp = data[3];
    System.out.println("engine oil temperature: " + oilTemp);
    carModel.setEngineOilTemp(oilTemp);

    int intakeAirTemp = data[4] & 0b00111111;
    System.out.println("intake air temperature: " + intakeAirTemp);
    carModel.setIntakeAirTemp(intakeAirTemp);

    int fuelTemp = data[5] & 0b00111111;
    System.out.println("fuel temperature: " + fuelTemp);
    carModel.setFuelTemp(fuelTemp);

    int engineMap = data[6] >> 6;
    System.out.println("engine Map:" + engineMap);
    carModel.setEngineMap(engineMap);

    int exhaustTemp = ((data[6] & 0b00000111) << 8) | data[7];
    System.out.println("exhaust temperature: " + exhaustTemp);
    carModel.setExhaustTemp(exhaustTemp);

  }

}
