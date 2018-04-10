package com.step.new_bootcamp;
import java.util.HashMap;


class ParkingLot implements Comparable{
  private final int capacity;
  private HashMap<Object, Vehicle> vehicles;
  private Object o;
  ParkingLot(int capacity) {
    this.capacity = capacity;
    this.vehicles = new HashMap<>();
  }

  protected Object park(Vehicle vehicle) throws UnableToParkException {
    if(hasAlreadyParked( vehicle )){
      throw new UnableToParkException("Already Parked");
    }else if(isFull()){
      throw new UnableToParkException( "No Space Available" );
    }
    Object token = new Object();
    vehicles.put( token, vehicle );
    return token;
  }

  protected boolean hasAlreadyParked(Vehicle vehicle) {
    return vehicles.containsValue( vehicle );
  }

  protected Vehicle checkout(Object token) throws InvalidCheckoutException {
    if (!isVehicleParkedOf( token )) {
      throw new InvalidCheckoutException( "Invalid Checkout Request" );
    }
    return vehicles.remove( token );
  }

  protected boolean isVehicleParkedOf(Object token) {
    return vehicles.containsKey( token );
  }

  protected boolean isFull() {
    return vehicles.size() == capacity;
  }

  @Override
  public int compareTo(Object o) {
    ParkingLot lot = (ParkingLot)o;
    if (isFull() && !lot.isFull()) return 1;
    if(!lot.isFull() && lot.capacity-capacity>0) return 1;
    return -1;
  }
}
