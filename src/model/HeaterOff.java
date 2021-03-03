package model;

public class HeaterOff extends HeaterState
{
  public HeaterOff(Heater heater)
  {
    System.out.println("Heater Off");
    heater.setPower(0);
  }
  @Override public void clickUp(Heater heater)
  {
    heater.setState(new HeaterLow(heater));
  }

  @Override public void clickDown(Heater heater)
  {
    System.out.println("Min state reached");
  }
  
}
