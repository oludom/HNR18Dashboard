

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jssc.SerialPortList;

public class MainController implements CarModelListener{


  CarModel carModel = new CarModel();
  private boolean firstSelection = true;
  private ReceiverThread receiverThread;

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML
  private Label l1;

  @FXML
  private Label l2;

  @FXML
  private Label l3;

  @FXML
  private Label l4;

  @FXML
  private Label l5;

  @FXML // fx:id="serialList"
  private ListView<String> serialList; // Value injected by FXMLLoader

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert serialList != null : "fx:id=\"serialList\" was not injected: check your FXML file 'main.fxml'.";

    carModel.addCarModelListener(this);

    String[] ports = SerialPortList.getPortNames();
    ObservableList<String> devices = FXCollections.observableArrayList();
    devices.addAll(ports);
//    ListView<String> deviceView = new ListView<>(devices);
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
            }
          }
        });

  }

  @Override
  public void onCarModelUpdate() {
    Platform.runLater(new Runnable() {
      @Override public void run() {
        l1.setText("Gear: " + carModel.getGear());
        l2.setText("engine coolant temp: " + carModel.getEngineCoolantTemp());
        l3.setText("engine speed: " + carModel.getEngineSpeed());
        l4.setText("intake air pressure: " + carModel.getIntakeAirPressure());
        l5.setText("exhaust temp: " + carModel.getExhaustTemp());
      }
    });

  }
}
