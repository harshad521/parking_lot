package com.step.new_bootcamp;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class Attendant {

  private List<ParkingLot> lots;

  Attendant() {
    this.lots = new ArrayList<>();
  }

  public Object park(Vehicle vehicle) throws UnableToParkException {
    if (lots.isEmpty()) throw new UnableToParkException( "No lot available" );
    sort(lots);
    for (ParkingLot lot : lots) {
      if (lot.hasAlreadyParked( vehicle )) throw new UnableToParkException( "Already Parked" );
    }
    return lots.get(0).park( vehicle );
  }

  public void add(ParkingLot lot) {
    lots.add( lot );
  }

  public Vehicle checkout(Object token) throws InvalidCheckoutException {
    for (ParkingLot lot : lots)
      if (lot.isVehicleParkedOf( token )) return lot.checkout( token );
    throw new InvalidCheckoutException( "Invalid Checkout Request" );
  }
}
