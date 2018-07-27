import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;
import jssc.SerialPortList;

public class Main2  extends Application implements CarModelListener {


  @Override
  public void init() throws Exception {
    super.init();



  }


  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

    Scene scene = new Scene(root, 300, 275);

    primaryStage.setScene(scene);
//    pane.setHgap(5);
//    pane.setVgap(5);
//    pane.setPadding(new Insets(5));
//    pane.setBackground(new Background(new BackgroundFill(Tile.BACKGROUND.darker(), CornerRadii.EMPTY, Insets.EMPTY)));
//
//    Scene scene = new Scene(pane);

    primaryStage.setTitle("HNN18 Dashboard");
    primaryStage.show();

  }

  @Override
  public void stop() {
//    if (receiverThread != null) {
//      receiverThread.end();
//      try {
//        receiverThread.join();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }
    System.exit(0);
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void onCarModelUpdate() {
//    engineSpeed.setValue(carModel.getEngineSpeed());
//    velocityGauge.setValue(carModel.getVelocity());
//    SAindicatorGauge.setValue(carModel.getSteerAngle());
//    throttleValvePosition.setValue(carModel.getThrottleValvePosition());
//    gear.setValue(carModel.getGear());
//    engineOilTemp.setValue(carModel.getEngineOilTemp());
//    engineOilPressureGauge.setValue(carModel.getEngineOilPressure());
//    intakeAirPressureGauge.setValue(carModel.getIntakeAirPressure());
//    intakeAirTempGauge.setValue(carModel.getIntakeAirTemp());
//    batteryVoltage.setValue(carModel.getBatteryVoltage());
//    engineCoolantTempGauge.setValue(carModel.getEngineCoolantTemp());
//    fuelTempGauge.setValue(carModel.getFuelTemp());
//    engineMap.setValue(carModel.getEngineMap());
//    exhaustTempGauge.setValue(carModel.getExhaustTemp());
//    lambda.setValue(carModel.getLambdaExhaust());
//    fuelPressureGauge.setValue(carModel.getFuelPressure());
  }

}
