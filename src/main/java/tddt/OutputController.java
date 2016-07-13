package tddt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class OutputController implements Initializable {
	
	@FXML 
	private PieChart piechart;
	
	@FXML 
	private Button buttonPieChart;
	
	@FXML
	private void loadPieChart(ActionEvent event) {
		buttonPieChart.setVisible(false);
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        		new PieChart.Data("Time for Testwriting", TDDTTimer.test),
        		new PieChart.Data("Time for Coding", TDDTTimer.code),
        		new PieChart.Data("Time for Refactoring", TDDTTimer.refactor));
		piechart.setTitle("Time used for Phases");
		piechart.setData(pieChartData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}