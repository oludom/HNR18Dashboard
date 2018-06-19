

public class ParseThread extends Thread implements Runnable {

  private int[] data;
  private CarModel carModel;

  public ParseThread(int[] data, CarModel carModel){
    this.data = data;
    this.carModel = carModel;
  }

  @Override
  public void run() {

    if(data[0] == 17){        // 0x11
      messageOne();
    }else if(data[0] == 51){  // 0x33
      messageTwo();
    }else if(data[0] == 85){  // 0x55
      messageThree();
    }else if(data[0] == 102){ // 0x66
      messageFour();
    }

  }

  private void messageOne(){

    int engineSpeed = ((data[1] << 8) | data[2]) ;
    System.out.println("engine speed: " + engineSpeed);
    carModel.setEngineSpeed(engineSpeed);

    int gear = data[3];
    System.out.println("gear: " +gear);
    carModel.setGear(gear);

    int intake = (data[4] << 8) | data[5];
    System.out.println("intake air pressure: " + intake/10);
    carModel.setIntakeAirPressure(intake/10);

    int throttle = (data[6] << 8) | data[5];
    System.out.println("throttle valve position: " + throttle/100);
    carModel.setThrottleValvePosition(throttle/100);

  }

  private void messageTwo(){

    int speed = (data[1] << 8) | data[2];
    System.out.println("speed: " + speed/100);
    carModel.setVelocity(speed/100);

    int oil = data[3];
    System.out.println("engine oil pressure: " + oil*.05);
    carModel.setEngineOilPressure(oil*.05F);



    int exhaust = (data[4] << 8) | data[5];
//    float ex = exhaust + 500;
//    ex /= 1000;
    System.out.println("exhaust: " + exhaust*.01);
    carModel.setLambdaExhaust(exhaust*.01F);

    int steerangle = (data[6] << 8) | data[7];
    if(steerangle>=0x8000){
      steerangle -= 0x8000;
      steerangle *= -1;
    }
//    steerangle = steerangle - 40;
    System.out.println("steerangle: " + steerangle/100);
    carModel.setSteerAngle(steerangle/100);

  }

  private void messageThree(){

    int oilTemp = data[1];
    System.out.println("engine oil temperature: " + (oilTemp-40));
    carModel.setEngineOilTemp(oilTemp-40);

    int intakeAirTemp = data[2];
    System.out.println("intake air temperature: " + (intakeAirTemp-40));
    carModel.setIntakeAirTemp(intakeAirTemp-40);

    int coolantTemp = data[3];
    System.out.println("engine coolant temperature: " + (coolantTemp-40));
    carModel.setEngineCoolantTemp(coolantTemp-40);

    int engineMap = data[4];
    System.out.println("engine Map:" + engineMap);
    carModel.setEngineMap(engineMap);

    int fuelTemp = data[5];
    System.out.println("fuel temperature: " + (fuelTemp-40));
    carModel.setFuelTemp(fuelTemp-40);

  }

  private void messageFour(){

    int batteryVoltage = (data[1] << 8 ) | data[2];
    float bV = batteryVoltage;
    bV /= 1000;
    System.out.println("battery voltage: " + bV);
    carModel.setBatteryVoltage(bV);

    int exhaustTemp = (data[3] << 8) | data[4];
    System.out.println("exhaust temperature: " + exhaustTemp/10);
    carModel.setExhaustTemp(exhaustTemp/10);

    int fuelPressure = data[5];
    System.out.println("fuel pressure: " + fuelPressure*.05F);
    carModel.setFuelPressure(fuelPressure*.05F);

  }

}
