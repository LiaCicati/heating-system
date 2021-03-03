package view;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.Region;
import viewmodel.ThermometerViewModel;

import java.util.Optional;

public class ThermometerViewController
{
  @FXML private Label fistThermometerLabel;
  @FXML private Label secondThermometerLabel;
  @FXML private Label outsideThermometerLabel;
  @FXML private Label heaterLabel;
  @FXML public Label secondThermometerWaringLabel;
  @FXML public Label firstThermometerWarningLabel;
  @FXML public Label criticalLowLabel;
  @FXML public Label criticalHighLabel;
  @FXML private TextField highValue;
  @FXML private TextField lowValue;

  private Region root;
  private ThermometerViewModel viewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, ThermometerViewModel viewModel,
      Region root)
  {
    this.viewModel = viewModel;
    this.viewHandler = viewHandler;
    this.root = root;

    heaterLabel.textProperty().bind(viewModel.getPowerProperty());
    firstThermometerWarningLabel.textProperty()
        .bind(viewModel.firstThermometerWarningProperty());
    secondThermometerWaringLabel.textProperty()
        .bind(viewModel.secondThermometerWarningProperty());

    Bindings.bindBidirectional(fistThermometerLabel.textProperty(),
        viewModel.getFirstIndoorProperty(), new StringIntegerConverter(0));
    Bindings.bindBidirectional(secondThermometerLabel.textProperty(),
        viewModel.getSecondIndoorProperty(), new StringIntegerConverter(0));
    Bindings.bindBidirectional(outsideThermometerLabel.textProperty(),
        viewModel.getOutdoorProperty(), new StringIntegerConverter(0));

    Bindings.bindBidirectional(criticalHighLabel.textProperty(),
        viewModel.getHighValueProperty(), new StringIntegerConverter(0));
    Bindings.bindBidirectional(criticalLowLabel.textProperty(),
        viewModel.getLowValueProperty(), new StringIntegerConverter(0));

    Bindings.bindBidirectional(lowValue.textProperty(),
        viewModel.setCriticalLowTemperature(), new StringIntegerConverter(0));
    Bindings.bindBidirectional(highValue.textProperty(),
        viewModel.setCriticalHighTemperature(), new StringIntegerConverter(0));

  }

  public void reset()
  {
    viewModel.getAll();
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML public void upClicked()
  {
    viewModel.turnUp();
  }

  @FXML public void downClicked()
  {
    viewModel.turnDown();
  }

  @FXML public void openLogs()
  {
    viewHandler.openView("logs");
  }

  public void setValue()
  {
    confirmation();
    viewModel.submit();
  }

  private boolean confirmation()
  {
    if (Double.parseDouble(highValue.getText()) <= 40
        && Double.parseDouble(lowValue.getText()) >= 10)
    {
      return false;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Warning");
    if (Double.parseDouble(highValue.getText()) <= 40)
    {
      lowValue.setText("10");
      alert.setHeaderText("Minimum temperature can not be lower than 10");
    }
    else if (Double.parseDouble(lowValue.getText()) >= 10)
    {
      highValue.setText("40");
      alert.setHeaderText("Maximum temperature can not be higher than 40");
    }
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}
