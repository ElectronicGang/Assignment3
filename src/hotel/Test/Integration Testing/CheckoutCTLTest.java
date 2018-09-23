/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.entities;

import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @author Kasunka Kithmini
 */
public class CheckoutCTLTest {

    public CheckoutCTLTest() {
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
     * Test of creditDetailsEntered method, of class CheckoutCTL.
     */
    @Test
    public void testCreditDetailsEntered() throws ParseException {

        double total = 150;
        int roomId = 1;
        RoomType roomType = RoomType.SINGLE;
        String guestName = "Saranga";
        String guestAddress = "Glenroy";
        int phoneNo = 0412435467;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
        Date arrivalDate = simpleDateFormat.parse("02-10-2018");
        int stayLength = 10;
        int numberOfOccupants = 1;
        ServiceType serviceType = ServiceType.ROOM_SERVICE;
        double cost = 200;

        Room room = new Room(roomId, roomType);
        Guest guest = new Guest(guestName, guestAddress, phoneNo);
        CreditCard creditCard = new CreditCard(CreditCardType.VISA, 12342, 321);

        Hotel hotel = new Hotel();

        long bookConfirmationNumber = hotel.book(room, guest, arrivalDate, stayLength, numberOfOccupants, creditCard);
        hotel.checkin(bookConfirmationNumber);
        hotel.addServiceCharge(roomId, serviceType, cost);

        CheckoutCTL checkOut = new CheckoutCTL(hotel);

        CheckoutCTL.creditDetailsEntered(CreditCardType.VISA, 12342, 321);

        long testVal = 0;
        if (CheckoutCTL.getState() == State.COMPLETED) {
            testVal = 1;
        }
        assertEquals(1, testVal);
    }
}