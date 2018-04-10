package com.step.new_bootcamp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingLotTest {

  private ParkingLot parkingLot;
  private Vehicle firstCar;

  private class TestCar implements Vehicle {}

  @Before
  public void setUp() {
    parkingLot = new ParkingLot(2);
    firstCar = new TestCar();
  }

  @Test
  public void shouldBeAbleToParkTheVehicle() throws UnableToParkException{
    Object token = parkingLot.park( firstCar );
    assertNotNull( token );

  }

  @Test
  public void shouldBeAbleToCheckedOutTheVehicle() throws UnableToParkException, InvalidCheckoutException{
    Object token = parkingLot.park( firstCar );
    Vehicle checkedOutCar = parkingLot.checkout( token );
    assertEquals( firstCar, checkedOutCar );
  }

  @Test(expected = UnableToParkException.class)
  public void shouldNotBeAbleToParkTheSameCarWithoutBeingCheckedOut() throws UnableToParkException{
    parkingLot.park( firstCar );
    parkingLot.park( firstCar );
  }

  @Test(expected = InvalidCheckoutException.class)
  public void shouldBeAbleToCheckoutTheParkedCarOnly() throws UnableToParkException, InvalidCheckoutException{
    Object token = parkingLot.park( firstCar );
    parkingLot.checkout( token );
    parkingLot.checkout( token );
  }

  @Test
  public void shouldAssertFalseIfParkingLotIsNotFull() {
    assertFalse(parkingLot.isFull());
  }

  @Test
  public void shouldAssertTrueWhenParkingLotIsFull() throws UnableToParkException{
    parkingLot.park(new TestCar());
    parkingLot.park(new TestCar());
    assertTrue(parkingLot.isFull());
  }

  @Test(expected = UnableToParkException.class)
  public void shouldNotAllowToParkTheCarIfParkingLotIsFull() throws UnableToParkException{
    parkingLot.park(new TestCar());
    parkingLot.park(new TestCar());
    parkingLot.park(new TestCar());
  }

  @Test
  public void shouldAssertTrueAfterSpaceGetsAvailableInParkingLot() throws UnableToParkException, InvalidCheckoutException {
    parkingLot.park( new TestCar() );
    Object token = parkingLot.park( new TestCar() );
    assertTrue( parkingLot.isFull() );
    parkingLot.checkout( token );
    assertFalse(parkingLot.isFull());
  }
}