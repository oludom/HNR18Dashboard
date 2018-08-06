
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import jssc.SerialPortList;

import java.awt.*;

public class MainCharts extends Application implements CarModelListener {

  // get screen width
  private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
  private static final int width = gd.getDisplayMode().getWidth();
  private static final int height = gd.getDisplayMode().getHeight()-50;

  public static final double TILE_WIDTH = ( width - 200 ) / 4;
  public static final double TILE_HEIGHT = height/2;

  CarModel carModel = new CarModel();
  private boolean firstSelection = true;
  private ReceiverThread receiverThread;
  private long startTime;
  private long lastTime;

  private CustomChart engineSpeedCustomChart;
  private CustomChart engineOilAndFuelPressureCustomChart;
  private CustomChart engineOilAndCoolantTempCustomChart;
  private CustomChart exhaustTempCustomChart;
  private CustomChart intakeAirPressureCustomChart;
  private CustomChart intakeAirAndFuelTempCustomChart;
  private CustomChart throttleValvePositionCustomChart;
  private CustomChart velocityCustomChart;
  private Slider steerAngleSlider;

  private Label engineSpeedLabel = new Label();
  private Label engineOilPressureLabel = new Label();
  private Label fuelPressureLabel = new Label();
  private Label engineOilTempLabel = new Label();
  private Label engineCoolantTempLabel = new Label();
  private Label exhaustTempLabel = new Label();
  private Label intakeAirPressureLabel = new Label();
  private Label intakeAirTempLabel = new Label();
  private Label fuelTempLabel = new Label();
  private Label throttleValvePositionLabel = new Label();
  private Label velocityLabel = new Label();

  private Label batteryVoltageLabel;
  private Label engineMapLabel;
  private Label gearLabel;
  private Label lambdaLabel;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    carModel.addCarModelListener(this);

    GridPane grid = new GridPane();

    // Create the Scene
    Scene scene = new Scene(grid);
    // Add the Stylesheet to the Scene
    scene.getStylesheets().add(getClass().getResource("linechart.css").toExternalForm());
    // Add the Scene to the Stage
    primaryStage.setScene(scene);
    // Set the Title of the Stage
    primaryStage.setTitle("HHN18 telemetry");
    primaryStage.setResizable(false);
    primaryStage.setWidth(width);
    primaryStage.setHeight(height);
    // Display the Stage
    primaryStage.show();

    engineSpeedCustomChart = new CustomChart(0, 100, 1, 1000, "rpm", "engine speed");

    engineOilAndFuelPressureCustomChart = new CustomChart(0, 100, 1, 10, "bar", "engine oil pressure");
    engineOilAndFuelPressureCustomChart.createSeriesTwo("fuel pressure");

    engineOilAndCoolantTempCustomChart = new CustomChart(0, 100, 1, 120, "°C", "engine oil temp");
    engineOilAndCoolantTempCustomChart.createSeriesTwo("engine coolant temp");

    exhaustTempCustomChart = new CustomChart(0, 100, 1, 100, "°C", "exhaust temp");

    intakeAirPressureCustomChart = new CustomChart(0, 100, 1, 100, "mbar", "intake air pressure");

    intakeAirAndFuelTempCustomChart = new CustomChart(0, 100, 1, 60, "°C", "intake air temp");
    intakeAirAndFuelTempCustomChart.createSeriesTwo("fuel temp");

    throttleValvePositionCustomChart = new CustomChart(0, 100, 1, 10, "%", "throttle valve position");

    velocityCustomChart = new CustomChart(0, 100, 1, 10, "km/h", "velocity");

    steerAngleSlider = new Slider();
    steerAngleSlider.setMin(-50);
    steerAngleSlider.setMax(50);
    steerAngleSlider.setValue(0);
    steerAngleSlider.setShowTickLabels(true);
    steerAngleSlider.setShowTickMarks(true);
    steerAngleSlider.setMajorTickUnit(50);
    steerAngleSlider.setMinorTickCount(5);
    steerAngleSlider.setBlockIncrement(10);
    steerAngleSlider.setMouseTransparent(true);

    grid.add(engineSpeedCustomChart.getChart(), 1, 0);
    grid.add(engineOilAndFuelPressureCustomChart.getChart(), 2, 0);
    grid.add(engineOilAndCoolantTempCustomChart.getChart(), 3, 0);
    grid.add(exhaustTempCustomChart.getChart(), 4, 0);

    grid.add(intakeAirPressureCustomChart.getChart(), 1,1);
    grid.add(intakeAirAndFuelTempCustomChart.getChart(), 2, 1);
    grid.add(throttleValvePositionCustomChart.getChart(), 3,1);
    grid.add(velocityCustomChart.getChart(), 4, 1);


    ListView<String> serialList = new ListView<>();

    String[] ports = SerialPortList.getPortNames();
    ObservableList<String> devices = FXCollections.observableArrayList();
    devices.addAll(ports);
    ListView<String> deviceView = new ListView<>(devices);
    serialList.setItems(devices);

    serialList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> observable,
                              String oldValue, String newValue) {
            if (firstSelection) {
              firstSelection = false;
              receiverThread = new ReceiverThread(carModel, newValue);
              receiverThread.start();
              serialList.setMouseTransparent(true);
              serialList.setFocusTraversable(false);
              startTime = System.currentTimeMillis();
            }
          }
        });

//    grid.add(serialList, 0, 0);

    GridPane topLeftGrid = new GridPane();
    topLeftGrid.add(serialList, 0, 0);
//    topLeftGrid.add(new Label("steer angle: "), 0, 1);
//    topLeftGrid.add(steerAngleSlider, 0, 2);

    topLeftGrid.getRowConstraints().add(new RowConstraints());
    topLeftGrid.getRowConstraints().add(new RowConstraints(50));

    grid.add(topLeftGrid, 0, 0);


    /*
     * LABEL BASED VALUES
     */
    GridPane labelStack = new GridPane();
    labelStack.setMinHeight(TILE_HEIGHT);
    labelStack.getColumnConstraints().add(new ColumnConstraints(140));
    labelStack.getColumnConstraints().add(new ColumnConstraints(60));
    for(int i = 0; i<15; i++)
      labelStack.getRowConstraints().add(new RowConstraints(30));

    batteryVoltageLabel = new Label();
    engineMapLabel = new Label();
    gearLabel = new Label();
    lambdaLabel = new Label();

    labelStack.add(new Label("engine speed: "), 0,0);
    labelStack.add(new Label("engine oil pressure: "), 0,1);
    labelStack.add(new Label("fuel pressure: "), 0,2);
    labelStack.add(new Label("engine oil temp: "), 0,3);
    labelStack.add(new Label("engine coolant temp: "), 0,4);
    labelStack.add(new Label("exhaust temp: "), 0,5);
    labelStack.add(new Label("intake air pressure: "), 0,6);
    labelStack.add(new Label("intake air temp: "), 0,7);
    labelStack.add(new Label("fuel temp: "), 0,8);
    labelStack.add(new Label("throttle valve position: "), 0,9);
    labelStack.add(new Label("velocity: "), 0,10);

    labelStack.add(new Label("battery voltage: "), 0, 11);
    labelStack.add(new Label("engine map: "), 0, 12);
    labelStack.add(new Label("gear: "), 0, 13);
    labelStack.add(new Label("lambda: "), 0, 14);

    labelStack.add(engineSpeedLabel, 1, 0);
    labelStack.add(engineOilPressureLabel, 1, 1);
    labelStack.add(fuelPressureLabel, 1, 2);
    labelStack.add(engineOilTempLabel, 1, 3);
    labelStack.add(engineCoolantTempLabel, 1, 4);
    labelStack.add(exhaustTempLabel, 1, 5);
    labelStack.add(intakeAirPressureLabel, 1, 6);
    labelStack.add(intakeAirTempLabel, 1, 7);
    labelStack.add(fuelTempLabel, 1, 8);
    labelStack.add(throttleValvePositionLabel, 1, 9);
    labelStack.add(velocityLabel, 1, 10);

    labelStack.add(batteryVoltageLabel, 1,11);
    labelStack.add(engineMapLabel,1,12);
    labelStack.add(gearLabel,1,13);
    labelStack.add(lambdaLabel,1,14);

    grid.add(labelStack, 0,1);

    // first column width
    grid.getColumnConstraints().add(new ColumnConstraints(200));
    // second column...
    grid.getColumnConstraints().add(new ColumnConstraints(TILE_WIDTH));
    grid.getColumnConstraints().add(new ColumnConstraints(TILE_WIDTH));
    grid.getColumnConstraints().add(new ColumnConstraints(TILE_WIDTH));

    grid.getRowConstraints().add(new RowConstraints(TILE_HEIGHT));
    grid.getRowConstraints().add(new RowConstraints(TILE_HEIGHT));


  }

  @Override
  public void stop() {
    if (receiverThread != null) {
      receiverThread.end();
      try {
        receiverThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.exit(0);
  }

  @Override
  public void onCarModelUpdate() {
    long currentTime = System.currentTimeMillis();
    double x = (double) (((long)((currentTime - startTime) / 100))/10);
    if ((currentTime - lastTime) > 500) { // refresh every second
      Platform.runLater(() ->{
          engineSpeedCustomChart.update(x, carModel.getEngineSpeed(), 30, 100);
          engineOilAndFuelPressureCustomChart.update(x, carModel.getEngineOilPressure(), 30, 100);
          engineOilAndFuelPressureCustomChart.update(x, carModel.getFuelPressure());
          engineOilAndCoolantTempCustomChart.update(x, carModel.getEngineOilTemp(), 30,100);
          engineOilAndCoolantTempCustomChart.update(x, carModel.getEngineCoolantTemp());
          exhaustTempCustomChart.update(x,carModel.getExhaustTemp(), 30, 100);

          intakeAirPressureCustomChart.update(x, carModel.getIntakeAirPressure(), 30, 100);
          intakeAirAndFuelTempCustomChart.update(x, carModel.getIntakeAirTemp(), 30, 100);
          intakeAirAndFuelTempCustomChart.update(x, carModel.getFuelTemp());
          throttleValvePositionCustomChart.update(x, carModel.getThrottleValvePosition(), 30, 100);
          velocityCustomChart.update(x, carModel.getVelocity(), 30, 100);

          steerAngleSlider.setValue(carModel.getSteerAngle());
      });

      lastTime = currentTime;
    }

    // refresh on every update
    Platform.runLater(() -> {
      engineSpeedLabel.setText(carModel.getEngineSpeed() + "rpm");
      engineOilPressureLabel.setText(carModel.getEngineOilPressure() + "bar");
      fuelPressureLabel.setText(carModel.getFuelPressure() + "bar");
      engineOilTempLabel.setText(carModel.getEngineOilTemp() + "°C");
      engineCoolantTempLabel.setText(carModel.getEngineCoolantTemp() + "°C");
      exhaustTempLabel.setText(carModel.getExhaustTemp() + "°C");
      intakeAirPressureLabel.setText(carModel.getIntakeAirPressure() + "mbar");
      intakeAirTempLabel.setText(carModel.getIntakeAirTemp() + "°C");
      fuelTempLabel.setText(carModel.getFuelTemp() + "°C");
      throttleValvePositionLabel.setText(carModel.getThrottleValvePosition() + "%");
      velocityLabel.setText(carModel.getVelocity() + "km/h");
      batteryVoltageLabel.setText("" + carModel.getBatteryVoltage() + "V");
      engineMapLabel.setText("" + carModel.getEngineMap());
      gearLabel.setText("" + carModel.getGear());
      lambdaLabel.setText("" + carModel.getLambdaExhaust());
    });

  }

}
