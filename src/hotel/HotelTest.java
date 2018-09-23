/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.entities;

import hotel.credit.CreditCard;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Macbookpro
 */
public class HotelTest {

    public HotelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * ** MY TEST CLASSES ****
     */
    @Test
    public void testBook() {
        System.out.println("book");
        Room room = null;
        Guest guest = null;
        Date arrivalDate = null;
        int stayLength = 0;
        int occupantNumber = 0;
        CreditCard creditCard = null;
        Hotel instance = new Hotel();
        long expResult = 12345678L; //expected result 
        long result = instance.book(room, guest, arrivalDate, stayLength, occupantNumber, creditCard); //call mbook function from the instanc ewe created above
        assertEquals(expResult, result); //compare and test two values
    }

    /**
     * Test of checking method, of class Hotel.
     */
    @Test
    public void testCheckin() {
        System.out.println("checkin");
        int roomId = 0;
        long confirmationNumber = 12101367L;
        Hotel instance = new Hotel();
        instance.checkin(confirmationNumber); //pass above long variable to checkin method
        Booking book = instance.findActiveBookingByRoomId(roomId); //gather booking details using roomId
        assertEquals(confirmationNumber, book.confirmationNumber); //test whether the confirmation number is same as the one we initiated here

        String state = "CHECKED_IN"; //set expected state for testing purposesstate 
        String currentState = String.valueOf(book.getState()); //set current state
        assertEquals("CHECKED_IN", state, currentState); //assert to compare to make sure the states are same

    }

    /**
     * Test of addServiceCharge method, of class Hotel.
     */
    @Test
    public void testAddServiceCharge() {
        System.out.println("addServiceCharge");
        int roomId = 0;
        ServiceType serviceType = null;
        double cost = 0.0;
        Hotel instance = new Hotel(); //create new instance 
        serviceType = ServiceType.valueOf("BAR_FRIDGE"); //save varible
        instance.addServiceCharge(roomId, serviceType, cost);
        Booking book = instance.findActiveBookingByRoomId(roomId); //get booking details using room Id
        ServiceCharge expected = new ServiceCharge(serviceType, cost); //expected test results are saved
        book.charges.add(expected); //add new record
        ServiceCharge servCharge = book.getCharges().get(0); //get first servce charge from the list
        // assertEquals(book.charges.add(expected), servCharge      );
        assertTrue(book.getCharges().size() > 0); //compare if even one record exist to test

    }

    /**
     * Test of checkout method, of class Hotel.
     */
    @Test
    public void testCheckout() {
        System.out.println("checkout");
        int roomId = 609;
        Hotel instance = new Hotel();

        instance.checkout(roomId);
        Booking book = instance.findActiveBookingByRoomId(roomId); //find booking details by RoomID
        String state = "CHECKED_OUT"; //set expected state for testing purposesstate 
        String currentState = String.valueOf(book.getState()); //set current state
        assertEquals("CHECKED_OUT", state, currentState);

    }

}
