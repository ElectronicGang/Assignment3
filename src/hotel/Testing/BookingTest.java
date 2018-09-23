   package entities;

import hotel.credit.CreditCard;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Room;
import hotel.entities.ServiceCharge;
import hotel.entities.ServiceType;
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
 * @author Dilan
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
        Booking instance = new Booking();
        instance.checkIn();
        String state = "CHECKED_IN";//create variable for expected result
        String currentState = String.valueOf(instance.getState());//create variable for actual result
        // Testing the output
         assertEquals("CHECKED_IN", state, currentState);
    }

    /**
     * Test of addServiceCharge method, of class Booking.
     */
    @Test
    public void testAddServiceCharge() {
        System.out.println("addServiceCharge");
        
        ServiceType serviceType = null;
        serviceType = ServiceType.valueOf("BAR_FRIDGE");
        
        double cost = 0.0;
        Booking instance = new Booking();
        instance.addServiceCharge(serviceType, cost);//passing values of services
        ServiceCharge expected = new ServiceCharge(serviceType, cost);
        ServiceCharge servCharge = instance.getCharges().get(0);
        
        // Testing the output
       // assertEquals(expected, servCharge);
        assertTrue(instance.getCharges().size()>0);
    }

    /**
     * Test of checkOut method, of class Booking.
     */
    @Test
    public void testCheckOut() {
        System.out.println("checkOut");
        Booking instance = new Booking();
        instance.checkOut();
        String state = "CHECKED_OUT";//create variable for expected result
        String currentState = String.valueOf(instance.getState());//create variable for actual result
        // TODO review the generated test code and remove the default call to fail.
        //assertTrue("CHECKED_OUT", state.equalsIgnoreCase(currentState));
        assertEquals("CHECKED_OUT", state, currentState);
    }
    
}
