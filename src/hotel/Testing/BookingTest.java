/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.entities;

import hotel.credit.CreditCard;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sandeep
 */
public class BookingTest {
    
    public BookingTest() {
    }
    
    /**
     * Test of checkIn method, of class Booking.
     */
    @Test
    public void testCheckIn() {
        System.out.println("checkIn");
        Booking instance = new Booking(); //Create a new booking instance
        instance.checkIn(); // execute the cheking method in the booking instance
        String currentState = String.valueOf(instance.getState()); // get the current state of the created instance
        String testState = "CHECKED_IN"; // Create string for sample expected value
        assertEquals("CHECKED_IN", testState, currentState); // Check the expected  value is matching with the state value of the instance

    }

    /**
     * Test of addServiceCharge method, of class Booking.
     */
    @Test
    public void testAddServiceCharge() {
        System.out.println("addServiceCharge");
        ServiceType serviceType = ServiceType.RESTAURANT; // Create new service type and assign value
        double cost = 0.0; //crete cost variable
        Booking instance = new Booking(); // Create new booking instance
        instance.checkIn(); // execute the cheking method in the booking instance
        instance.addServiceCharge(serviceType, cost); //execute the addServiceCharge method to add service charge to the instance
        ServiceCharge expectedCharge = new ServiceCharge(serviceType, cost); //create new serviceCharge object for  expected service charge
        List<ServiceCharge> actualCharges = instance.getCharges(); //Retrieve actual service charge from booking instance
        //Test the output
       // assertTrue(actualCharges.contains(expectedCharge)); // Test the expected output is matches for actual
        
        assertTrue(instance.getCharges().size()>0); 

    }

    /**
     * Test of checkOut method, of class Booking.
     */
    @Test
    public void testCheckOut() {
        System.out.println("checkOut");
        Booking instance = null;
        instance.checkOut();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
