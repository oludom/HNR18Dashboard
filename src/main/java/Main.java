import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;

public class Main extends Application implements CarModelListener{


  CarModel carModel = new CarModel();

  private static final double TILE_SIZE = 150;

  private Tile engineSpeed;

  @Override
  public void init() throws Exception {
    super.init();

    carModel.addCarModelListener(this);

    ReceiverThread receiverThread = new ReceiverThread(carModel);
    receiverThread.start();

    engineSpeed = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.GAUGE)
        .title("Engine Speed")
        .unit("rpm")
        .threshold(11000)
        .minValue(0)
        .maxValue(11000d)
        .build();

    engineSpeed.setValue(5000);

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FlowGridPane pane = new FlowGridPane(7, 6, engineSpeed);
    pane.setHgap(5);
    pane.setVgap(5);
    pane.setPadding(new Insets(5));
    pane.setBackground(new Background(new BackgroundFill(Tile.BACKGROUND.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

    Scene scene = new Scene(pane);

    primaryStage.setTitle("HNN18 Dashboard");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  @Override public void stop() {
    System.exit(0);
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void onCarModelUpdate() {
    engineSpeed.setValue(carModel.getEngineSpeed());
  }
}
