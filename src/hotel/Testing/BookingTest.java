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
        ServiceType serviceType = null;
        double cost = 0.0;
        Booking instance = null;
        instance.addServiceCharge(serviceType, cost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

