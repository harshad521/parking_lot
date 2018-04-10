package com.step.new_bootcamp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttendantTest {

  private Attendant attendant;
  private TestCar firstCar;
  private ParkingLot parkingLotOfCapacityOne;
  private ParkingLot parkingLotOfCapacityTwo;

  private class TestCar implements Vehicle {
  }

  @Before
  public void setUp() {
    attendant = new Attendant();
    parkingLotOfCapacityTwo = new ParkingLot( 2 );
    attendant.add( parkingLotOfCapacityTwo );
    parkingLotOfCapacityOne = new ParkingLot( 1 );
    attendant.add( parkingLotOfCapacityOne );
    firstCar = new TestCar();
  }

  @Test
  public void shouldBeAbleToPark() throws UnableToParkException {
    Object token = attendant.park( new TestCar() );
    assertNotNull( token );
  }

  @Test
  public void shouldBeAbleToParkInSecondLotWhenFirstLotIsFull() throws UnableToParkException {
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    Object token = attendant.park( firstCar );
    assertNotNull( token );
  }

  @Test(expected = UnableToParkException.class)
  public void shouldNotBeAbleToParkSameCarTwice() throws UnableToParkException {
    attendant.park( firstCar );
    attendant.park( firstCar );
  }

  @Test(expected = UnableToParkException.class)
  public void shouldNotBeAbleToParkSameCarInDifferentLots() throws UnableToParkException {
    attendant.park( firstCar );
    attendant.park( new TestCar() );
    attendant.park( firstCar );
  }

  @Test(expected = UnableToParkException.class)
  public void shouldNotBeAbleToParkWhenLotRunsOutOfSpace() throws UnableToParkException {
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
  }

  @Test
  public void shouldBeAbleToCheckOutTheCarProvidedToken() throws UnableToParkException, InvalidCheckoutException {
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    Object token = attendant.park( firstCar );
    assertNotNull( token );
    assertEquals( attendant.checkout( token ), firstCar );
  }

  @Test(expected = InvalidCheckoutException.class)
  public void shouldNotBeAbleToCheckoutCarIfNotParked() throws UnableToParkException, InvalidCheckoutException {
    Object token = attendant.park( firstCar );
    assertNotNull( token );
    attendant.checkout( new Object() );
  }

  @Test
  public void shouldBeAbleToParkInTheVehicleAgainOnceItIsCheckedOut() throws UnableToParkException, InvalidCheckoutException {
    Object token = attendant.park( firstCar );
    assertNotNull( token );
    assertEquals( attendant.checkout( token ), firstCar );
    token = attendant.park( firstCar );
    assertNotNull( token );
    assertEquals( attendant.checkout( token ), firstCar );
  }

  @Test
  public void shouldBeAbleToParkInTheVehicleWhenSpaceGetsAvailable() throws UnableToParkException, InvalidCheckoutException {
    attendant.park( new TestCar() );
    Object firstToken = attendant.park( new TestCar() );
    Object secondToken = attendant.park( new TestCar() );
    attendant.checkout( firstToken );
    attendant.checkout( secondToken );
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
  }

  @Test
  public void ShouldParkInHighestCapacityLotFromAvailableLots() throws UnableToParkException {
    ParkingLot parkingLotOfCapacityThree = new ParkingLot( 3 );
    attendant.add( parkingLotOfCapacityThree );
    Object token = attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    assertTrue( parkingLotOfCapacityThree.isVehicleParkedOf(  token));
    attendant.park( new TestCar() );
    Object anotherToken = attendant.park( new TestCar() );
    assertTrue(parkingLotOfCapacityTwo.isVehicleParkedOf( anotherToken ));
    Object oneMoreCar = attendant.park( new TestCar() );
    assertTrue(parkingLotOfCapacityOne.isVehicleParkedOf( oneMoreCar ));
  }

  @Test
  public void ShouldParkInLotOfHighestCapacityWhenSpaceIsAvailable() throws InvalidCheckoutException, UnableToParkException {
    ParkingLot parkingLotOfCapacityThree = new ParkingLot( 3 );
    attendant.add( parkingLotOfCapacityThree );
    Object token = attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    assertTrue( parkingLotOfCapacityThree.isVehicleParkedOf(  token));
    attendant.park( new TestCar() );
    attendant.checkout( token );
    Object newToken = attendant.park( new TestCar() );
    assertTrue( parkingLotOfCapacityThree.isVehicleParkedOf(newToken));
  }

  @Test
  public void shouldParkInLotOfSecondHighestCapacityWhenHighestGetFull() throws UnableToParkException {
    attendant.park( new TestCar() );
    attendant.park( new TestCar() );
    assertTrue( parkingLotOfCapacityTwo.isFull());
    attendant.park( new TestCar() );
    assertTrue( parkingLotOfCapacityOne.isFull());
  }

  @Test
  public void should() {
  }
}


