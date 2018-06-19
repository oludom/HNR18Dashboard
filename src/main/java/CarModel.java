import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Micha Heiss
 *
 * model class representing car
 */
public class CarModel {

  ArrayList<CarModelListener> carModelListeners = new ArrayList<>();

  // 0 to 11000, res: 1
  private int engineSpeed;

  // 0 to 4, res: 1
  private int gear;

  // 0 to 127, res: 1
  private int velocity;

  // 0 to 160, res: 1
  private int engineOilTemp;

  // 0 to 5, res: 0.01
  private float engineOilPressure;

  // 0 to 1200, res: 1
  private int intakeAirPressure;

  // 0 to 50, res: 1
  private int intakeAirTemp;

  // 0 to 15, res: 0.1
  private float batteryVoltage;

  // 0.5 to 1.8, res: 0.001
  private float lambdaExhaust;

  // 0 to 100, res: 1
  private int throttleValvePosition;

  // 0 to 140, res: 1
  private int engineCoolantTemp;

  // 0 to 50, res: 1
  private int fuelTemp;

  // -40 to 40, res: 1
  private int steerAngle;

  // 1 to 3, res: 1
  private int engineMap;

  // 0 to 1100, res: 1
  private int exhaustTemp;

  private float fuelPressure;




  public synchronized int getEngineSpeed() {
    return engineSpeed;
  }

  public synchronized void setEngineSpeed(int engineSpeed) {
    this.engineSpeed = engineSpeed;
    onCarModelUpdate();
  }

  public synchronized int getGear() {
    return gear;
  }

  public synchronized void setGear(int gear) {
    this.gear = gear;
    onCarModelUpdate();
  }

  public synchronized int getVelocity() {
    return velocity;
  }

  public synchronized void setVelocity(int velocity) {
    this.velocity = velocity;
    onCarModelUpdate();
  }

  public synchronized int getEngineOilTemp() {
    return engineOilTemp;
  }

  public synchronized void setEngineOilTemp(int engineOilTemp) {
    this.engineOilTemp = engineOilTemp;
    onCarModelUpdate();
  }

  public synchronized float getEngineOilPressure() {
    return engineOilPressure;
  }

  public synchronized void setEngineOilPressure(float engineOilPressure) {
    this.engineOilPressure = engineOilPressure;
    onCarModelUpdate();
  }

  public synchronized int getIntakeAirPressure() {
    return intakeAirPressure;
  }

  public synchronized void setIntakeAirPressure(int intakeAirPressure) {
    this.intakeAirPressure = intakeAirPressure;
    onCarModelUpdate();
  }

  public synchronized int getIntakeAirTemp() {
    return intakeAirTemp;
  }

  public synchronized void setIntakeAirTemp(int intakeAirTemp) {
    this.intakeAirTemp = intakeAirTemp;
    onCarModelUpdate();
  }

  public synchronized float getBatteryVoltage() {
    return batteryVoltage;
  }

  public synchronized void setBatteryVoltage(float batteryVoltage) {
    this.batteryVoltage = batteryVoltage;
    onCarModelUpdate();
  }

  public synchronized float getLambdaExhaust() {
    return lambdaExhaust;
  }

  public synchronized void setLambdaExhaust(float lambdaExhaust) {
    this.lambdaExhaust = lambdaExhaust;
    onCarModelUpdate();
  }

  public synchronized int getThrottleValvePosition() {
    return throttleValvePosition;
  }

  public synchronized void setThrottleValvePosition(int throttleValvePosition) {
    this.throttleValvePosition = throttleValvePosition;
    onCarModelUpdate();
  }

  public synchronized int getEngineCoolantTemp() {
    return engineCoolantTemp;
  }

  public synchronized void setEngineCoolantTemp(int engineCoolantTemp) {
    this.engineCoolantTemp = engineCoolantTemp;
    onCarModelUpdate();
  }

  public synchronized int getFuelTemp() {
    return fuelTemp;
  }

  public synchronized void setFuelTemp(int fuelTemp) {
    this.fuelTemp = fuelTemp;
    onCarModelUpdate();
  }

  public synchronized int getSteerAngle() {
    return steerAngle;
  }

  public synchronized void setSteerAngle(int steerAngle) {
    this.steerAngle = steerAngle;
    onCarModelUpdate();
  }

  public synchronized int getEngineMap() {
    return engineMap;
  }

  public synchronized void setEngineMap(int engineMap) {
    this.engineMap = engineMap;
    onCarModelUpdate();
  }

  public int getExhaustTemp() {
    return exhaustTemp;
  }

  public synchronized void setExhaustTemp(int exhaustTemp) {
    this.exhaustTemp = exhaustTemp;
    onCarModelUpdate();
  }

  public synchronized float getFuelPressure() {
    return fuelPressure;
  }

  public synchronized void setFuelPressure(float fuelPressure) {
    this.fuelPressure = fuelPressure;
  }

  public void addCarModelListener(CarModelListener carModelListener){
    carModelListeners.add(carModelListener);
  }

  private void onCarModelUpdate(){
    for(CarModelListener cml : carModelListeners){
      cml.onCarModelUpdate();
    }
  }

}
