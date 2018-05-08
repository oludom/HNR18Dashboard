import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import eu.hansolo.medusa.Gauge;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class Main extends Application implements CarModelListener{


  CarModel carModel = new CarModel();

  private static final double TILE_SIZE = 150;

  private Tile engineSpeed;
  private Tile velocity;
  private Gauge SAindicatorGauge;
  private Tile steerAngle;
  private Tile throttleValvePosition;
  private Tile gear;
  private Tile engineOilTemp;
  private Gauge engineOilPressureGauge;
  private Tile engineOilPressure;
  private Gauge intakeAirPressureGauge;
  private Tile intakeAirPressure;
  private Gauge intakeAirTempGauge;
  private Tile intakeAirTemp;
  private Tile batteryVoltage;
  private Gauge engineCoolantTempGauge;
  private Tile engineCoolantTemp;
  private Gauge fuelTempGauge;
  private Tile fuelTemp;
  private Tile engineMap;
  private Gauge exhaustTempGauge;
  private Tile exhaustTemp;
  private Gauge lambdaGauge;
  private Tile lambda;

  private ReceiverThread receiverThread;
  private long animationDuration = 200;

  @Override
  public void init() throws Exception {
    super.init();

    carModel.addCarModelListener(this);

    receiverThread = new ReceiverThread(carModel);
    receiverThread.start();

    engineSpeed = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.GAUGE)
        .title("engine speed")
        .unit("rpm")
        .threshold(11000)
        .minValue(0)
        .maxValue(11000d)
        .decimals(0)
        .animationDuration(animationDuration)
        .build();

    velocity = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.GAUGE)
        .title("velocity")
        .unit("kph")
        .threshold(100)
        .minValue(0d)
        .maxValue(127d)
        .decimals(0)
        .animationDuration(animationDuration)
        .build();

    SAindicatorGauge = createGauge(Gauge.SkinType.INDICATOR, -40, 40, "\u00B0C", 0);
    steerAngle = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("steer angle")
        .text("")
        .graphic(SAindicatorGauge)
        .build();
    steerAngle.textProperty().bind(SAindicatorGauge.currentValueProperty().asString("%.0f"));


    throttleValvePosition = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.PERCENTAGE)
        .title("throttle valve position")
        .unit("\u0025")
        .description("")
        .maxValue(100)
        .animated(false)
        .decimals(0)
        .build();

    gear = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.NUMBER)
        .title("gear")
        .text("gear")
        .value(0)
        .unit("")
        .decimals(0)
        .animationDuration(animationDuration)
        .description("")
        .textVisible(false)
        .build();

    engineOilTemp = TileBuilder.create()
        .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
        .prefSize(TILE_SIZE, TILE_SIZE)
        .title("engine oil temperature")
        .animated(false)
        .textVisible(false)
        .averagingPeriod(25)
        .autoReferenceValue(true)
        .barColor(Tile.YELLOW_ORANGE)
        .barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
        .sections(new eu.hansolo.tilesfx.Section(0, 80, Tile.BLUE),
            new eu.hansolo.tilesfx.Section(80, 140, Tile.YELLOW),
            new eu.hansolo.tilesfx.Section(140, 160, Tile.LIGHT_RED))
        .sectionsVisible(true)
        .highlightSections(true)
        .strokeWithGradient(true)
        .gradientStops(new Stop(0.0, Tile.BLUE),
            new Stop(0.33, Tile.BLUE),
            new Stop(0.33,Tile.YELLOW),
            new Stop(0.67, Tile.YELLOW),
            new Stop(0.67, Tile.LIGHT_RED),
            new Stop(1.0, Tile.LIGHT_RED))
        .build();

    engineOilPressureGauge = createGauge(Gauge.SkinType.SIMPLE_DIGITAL, 0, 5, "bar", 2);
    engineOilPressure  = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("engine oil pressure")
        .text("")
        .decimals(2)
        .graphic(engineOilPressureGauge)
        .build();

    intakeAirPressureGauge = createGauge(Gauge.SkinType.SIMPLE_DIGITAL, 0, 1200, "mbar", 0);
    intakeAirPressure  = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("intake air pressure")
        .text("")
        .decimals(2)
        .graphic(intakeAirPressureGauge)
        .build();

    intakeAirTempGauge = createGauge(Gauge.SkinType.SIMPLE_DIGITAL, 0, 50, "\u00B0C", 0);
    intakeAirTemp  = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("intake air temperature")
        .text("")
        .graphic(intakeAirTempGauge)
        .build();

    batteryVoltage = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.NUMBER)
        .title("batery voltage")
        .text("")
        .value(0)
        .unit("V")
        .textVisible(false)
        .build();

    engineCoolantTempGauge = createGauge(Gauge.SkinType.SIMPLE_DIGITAL, 0, 140, "\u00B0C", 0);
    engineCoolantTemp  = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("engine coolant temperature")
        .text("")
        .graphic(engineCoolantTempGauge)
        .build();

    fuelTempGauge = createGauge(Gauge.SkinType.SIMPLE_DIGITAL, 0, 50, "\u00B0C", 0);
    fuelTemp  = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("fuel temperature")
        .text("")
        .graphic(fuelTempGauge)
        .build();

    engineMap = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.NUMBER)
        .title("engine map")
        .text("engine map")
        .unit("")
        .decimals(0)
        .animationDuration(animationDuration)
        .description("")
        .textVisible(false)
        .build();

    exhaustTempGauge = createGauge(Gauge.SkinType.SIMPLE_DIGITAL, 0, 1100, "\u00B0C", 0);
    exhaustTemp  = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("exhaust temperature")
        .text("")
        .graphic(exhaustTempGauge)
        .build();

    lambdaGauge = createGauge(Gauge.SkinType.DIGITAL, .5, 1.8, "", 3);
    lambda  = TileBuilder.create()
        .prefSize(TILE_SIZE, TILE_SIZE)
        .skinType(Tile.SkinType.CUSTOM)
        .title("Lambda exhaust")
        .text("")
        .graphic(lambdaGauge)
        .build();

  }





  @Override
  public void start(Stage primaryStage) throws Exception {
    FlowGridPane pane = new FlowGridPane(4, 6, engineSpeed, velocity, steerAngle, throttleValvePosition, gear,
        engineOilTemp, engineOilPressure, intakeAirPressure, intakeAirTemp, batteryVoltage, engineCoolantTemp, fuelTemp, engineMap, exhaustTemp, lambda);
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
    // TODO stop all threads
    receiverThread.end();
    try {
      receiverThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.exit(0);
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void onCarModelUpdate() {
    engineSpeed.setValue(carModel.getEngineSpeed());
    velocity.setValue(carModel.getVelocity());
    SAindicatorGauge.setValue(carModel.getSteerAngle());
    throttleValvePosition.setValue(carModel.getThrottleValvePosition());
    gear.setValue(carModel.getGear());
    engineOilTemp.setValue(carModel.getEngineOilTemp());
    engineOilPressureGauge.setValue(carModel.getEngineOilPressure());
    intakeAirPressureGauge.setValue(carModel.getIntakeAirPressure());
    intakeAirTempGauge.setValue(carModel.getIntakeAirTemp());
    batteryVoltage.setValue(carModel.getBatteryVoltage());
    engineCoolantTempGauge.setValue(carModel.getEngineCoolantTemp());
    fuelTempGauge.setValue(carModel.getFuelTemp());
    engineMap.setValue(carModel.getEngineMap());
    exhaustTempGauge.setValue(carModel.getExhaustTemp());
    lambdaGauge.setValue(carModel.getLambdaExhaust());
  }

  private Gauge createGauge(final Gauge.SkinType TYPE, final double min, final double max, String unit, int decimals) {
    return GaugeBuilder.create()
        .skinType(TYPE)
        .prefSize(TILE_SIZE, TILE_SIZE)
        .animated(true)
        //.title("")
        .unit(unit)
        .valueColor(Tile.FOREGROUND)
        .titleColor(Tile.FOREGROUND)
        .unitColor(Tile.FOREGROUND)
        .barColor(Tile.BLUE)
        .needleColor(Tile.FOREGROUND)
        .barColor(Tile.BLUE)
        .barBackgroundColor(Tile.BACKGROUND.darker())
        .tickLabelColor(Tile.FOREGROUND)
        .majorTickMarkColor(Tile.FOREGROUND)
        .minorTickMarkColor(Tile.FOREGROUND)
        .mediumTickMarkColor(Tile.FOREGROUND)
        .minValue(min)
        .maxValue(max)
        .decimals(decimals)
        .animationDuration(animationDuration)
        .build();
  }

}
