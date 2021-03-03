package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.TemperatureModel;
import model.Temperature;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class DetailsViewModel implements PropertyChangeListener
{
  private TemperatureModel model;
  private ObservableList<DataTableView> list;

  public DetailsViewModel(TemperatureModel model)
  {
    this.model = model;
    this.list = FXCollections.observableArrayList();
    loadFromModel();
  }

  private void loadFromModel()
  {

    ArrayList<Temperature> temperatures = model.getAllTemp();
    for (int i = 0; i < temperatures.size(); i++)
    {
      this.list.add(new DataTableView(temperatures.get(i)));
    }
    this.model.addListener( this);
  }

public ObservableList<DataTableView> getList()
{
  return list;
}


  @Override public void propertyChange(PropertyChangeEvent event)
  {
    if (event.getPropertyName().equals("ThermometerTemperature") || event.getPropertyName().equals("outsideTemperature")){
      list.add(new DataTableView((Temperature) event.getNewValue()));
    }
  }
}