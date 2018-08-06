import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CustomChart {

  private XYChart.Series<Number, Number> seriesOne;
  private XYChart.Series<Number, Number> seriesTwo;
  private NumberAxis xAxis;
  private NumberAxis yAxis;
  private LineChart<Number,Number> chart;
  private ObservableList<XYChart.Series<Number, Number>> data;

  public CustomChart(int xLower, int xUpper, int xTick, int yTick, String yLabel, String seriesOneName){

    // Create the X-Axis
    xAxis = new NumberAxis();
    xAxis.setAutoRanging(false);
    xAxis.setLowerBound(xLower);
    xAxis.setUpperBound(xUpper);
    xAxis.setTickUnit(xTick);

    // Create the Y-Axis
    yAxis = new NumberAxis();
    yAxis.setLabel(yLabel);
    yAxis.setTickUnit(yTick);

    // Create the LineChart
    chart = new LineChart<>(xAxis, yAxis);

    seriesOne = new XYChart.Series<Number, Number>();
    seriesOne.setName(seriesOneName);
    data = FXCollections.<XYChart.Series<Number, Number>>observableArrayList();
    data.add(seriesOne);

    chart.setData(data);
    chart.setMaxHeight(MainCharts.TILE_HEIGHT-50);

  }

  public void update(double x, float value, int maxX, int maxDataSize){
    seriesOne.getData().add(new XYChart.Data<Number, Number>(x, value));
    if (x > maxX) {
      xAxis.setLowerBound(x - maxX);
    }
    if (seriesOne.getData().size() > maxDataSize) {
      seriesOne.getData().remove(0, 1);
    }
    xAxis.setUpperBound(x);
  }

  public LineChart<Number, Number> getChart() {
    return chart;
  }

  public void createSeriesTwo(String name){
    seriesTwo = new XYChart.Series<Number, Number>();
    seriesTwo.setName(name);
    data.add(seriesTwo);
  }

  public void update(double x, float value){
    seriesTwo.getData().add(new XYChart.Data<Number, Number>(x, value));
  }
}
