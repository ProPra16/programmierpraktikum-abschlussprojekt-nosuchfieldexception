package tddt;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class OutputController implements Initializable {
	
	@FXML 
	private PieChart chart;
	
	@FXML
	private BarChart<String, Number> barChart;
	
	@FXML 
	private Button buttonPieChart;
	
	@FXML 
	private Button buttonBarChart;
	
	@FXML 
	private CategoryAxis categoryAxis;
	
	@FXML
	private NumberAxis numberAxis;
	
	@FXML
	private void loadPieChart(ActionEvent event) {
		buttonPieChart.setVisible(false);
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        		new PieChart.Data("Time for Testwriting", TDDTTimer.test),
        		new PieChart.Data("Time for Coding", TDDTTimer.code),
        		new PieChart.Data("Time for Refactoring", TDDTTimer.refactor));
		chart.setTitle("Time used for Phases");
		chart.setData(pieChartData);
	}
	
	@FXML
	private void loadBarChart(ActionEvent event) {
		buttonBarChart.setVisible(false);
		XYChart.Series series = new XYChart.Series();
		HashMap<String, Integer> errors = MenuController.compileErrors;
		errors.forEach((key, value) -> {
			series.getData().add(new XYChart.Data(key, value));
		} );
		barChart.getData().addAll(series);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}