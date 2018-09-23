/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package booking;

import hotel.booking.BookingCTL;
import hotel.credit.CreditCardType;
import hotel.entities.Hotel;
import hotel.entities.RoomType;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dilan
 */
public class BookingCTLTest {
    
    public BookingCTLTest() {
    }
    
    /**
     * Test of creditDetailsEntered method, of class BookingCTL.
     */
    
 @Test
    public void testCreditDetailsEntered() {
        System.out.println("creditDetailsEntered");
        Hotel hotel = new Hotel();//create hotel object for booking
        //create variables for test data
        CreditCardType type = CreditCardType.MASTERCARD ;
        int number = 30202;
        int ccv = 615;
        BookingCTL instance = new BookingCTL(hotel);
        
        instance.creditDetailsEntered(type, number, ccv);//passing card information to approve
        //create variables for test options
        String state = "COMPLETED";//create variable for expected result
        String currentState = String.valueOf(instance.getState());//create variable for actual result
        long expectConfirmation = Long.parseLong("2053918120");
        long currentConfirmation = instance.getConfirmationNumber();
        
        // testing each operation
        assertEquals(expectConfirmation, instance.getConfirmationNumber());
        assertEquals("COMPLETED", state, currentState);//checking whether state is credit
        
    }
    
    
    
}
