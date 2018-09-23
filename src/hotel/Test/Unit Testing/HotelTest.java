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
public class HotelTest {

    int roomId;
    RoomType roomType;
    String guestName;
    String guestAddress;
    int phoneNo;
    Date arrivalDate;
    int stayLength;
    int numberOfOccupants;
    ServiceType serviceType;
    double cost;

    public HotelTest() throws ParseException {

        roomId = 1;
        roomType = RoomType.SINGLE;
        guestName = "Saranga";
        guestAddress = "Glenroy";
        phoneNo = 0412435467;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
        arrivalDate = simpleDateFormat.parse("02-10-2018");
        stayLength = 10;
        numberOfOccupants = 1;
        serviceType = ServiceType.ROOM_SERVICE;
        cost = 200;
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
     * Test of book method, of class Hotel.
     */
    @Test
    public void testBook() throws ParseException {


        Room room = new Room(roomId, roomType);
        Guest guest = new Guest(guestName, guestAddress, phoneNo);
        CreditCard creditCard = new CreditCard(CreditCardType.VISA, 1234234534, 123);

        Hotel hotel = new Hotel();
        long bookConfirmationNumber = hotel.book(room, guest, arrivalDate, stayLength, numberOfOccupants, creditCard);
        assertEquals(02102018001, bookConfirmationNumber);

        long testVal = 0;
        if (room.isAvailable(arrivalDate, stayLength)) {
            testVal = 1;
        }
        assertEquals(0, testVal);

        Booking booking = findBookingByConfirmationNumber(02102018001);
        assertEquals(02102018001, book.getConfirmationNumber());

        assertEquals(roomId, Long.valueOf(booking.getRoomId()));

    }

    /**
     * Test of checkin method, of class Hotel.
     */
    @Test
    public void testCheckin() {
        hotel.checkin(02102018001);
        Book book = findActiveBookingByRoomId(roomId);
        assertEquals(02102018001, book.getConfirmationNumber());

        long testVal = 0;
        if (book.isCheckedIn()) {
            testVal = 1;
        }
        assertEquals(1, testVal);
    }

    /**
     * Test of addServiceCharge method, of class Hotel.
     */
    @Test
    public void testAddServiceCharge() {
        hotel.addServiceCharge(roomId, serviceType, cost);
        Book book = findActiveBookingByRoomId(roomId);
        List<ServiceCharge> charges = book.getCharges();
        ServiceCharge serviceCharge = charges.get(0);
        long testVal = 0;
        if ((serviceCharge.getType() == serviceType) && (serviceCharge.getCost() == cost)) {
            testVal = 1;
        }
        assertEquals(1, testVal);
    }

    /**
     * Test of checkout method, of class Hotel.
     */
    @Test
    public void testCheckout() {
        hotel.checkout(roomId);
        Book book = hotel.findActiveBookingByRoomId(roomId);
        long testVal = 0;
        if (book.isCheckedOut()) {
            testVal = 1;
        }
        assertEquals(1, testVal);
    }
}