package hotel.booking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hotel.credit.CreditAuthorizer;
import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;
import hotel.utils.IOUtils;

public class BookingCTL {
        /* Class global variables declaration */
	private static enum State {PHONE, ROOM, REGISTER, TIMES, CREDIT, APPROVED, CANCELLED, COMPLETED}	
	
	private BookingUI bookingUI;
	private Hotel hotel;
	private Guest guest;
	private Room room;
	private double cost;
        private long confirmationNumber;	
	private State state;
	private int phoneNumber;
	private RoomType selectedRoomType;
	private int occupantNumber;
	private Date arrivalDate;
	private int stayLength;
        
        /* Default constructor */
	public BookingCTL(Hotel hotel) {
		this.bookingUI = new BookingUI(this);
		this.hotel = hotel;
		state = State.CREDIT;
                this.room = new Room(120,RoomType.DOUBLE);
                this.guest = new Guest("Sandeep", "Crown", 451142876);
	}
        /* Method to run */
	public void run() {		
		IOUtils.trace("BookingCTL: run");
		getBookingUI().run();
	}
        
        /**
     * @return the confirmationNumber
     */
    public long getConfirmationNumber() {
        return confirmationNumber;
    }

    /**
     * @param confirmationNumber the confirmationNumber to set
     */
    public void setConfirmationNumber(long confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }
    
    /**
     * @return the bookingUI
     */
    public BookingUI getBookingUI() {
        return bookingUI;
    }

    /**
     * @param bookingUI the bookingUI to set
     */
    public void setBookingUI(BookingUI bookingUI) {
        this.bookingUI = bookingUI;
    }
        
         /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }
	
	/* Method to Enter phone number */
	public void phoneNumberEntered(int phoneNumber) {
		if (getState() != State.PHONE) { //Chack the state and throw error message if state is not PHONE
			String mesg = String.format("BookingCTL: phoneNumberEntered : bad state : %s", getState());
			throw new RuntimeException(mesg);
		}
		this.phoneNumber = phoneNumber; // Assign phone number
		
		boolean isRegistered = hotel.isRegistered(phoneNumber); // Check registraion
		
		if (isRegistered) {
			guest = hotel.findGuestByPhoneNumber(phoneNumber);
			getBookingUI().displayGuestDetails(guest.getName(), guest.getAddress(), guest.getPhoneNumber());
                        this.setState(State.ROOM);
			getBookingUI().setState(BookingUI.State.ROOM);
		}
		else {
                    this.setState(State.REGISTER);
			getBookingUI().setState(BookingUI.State.REGISTER);
		}
	}
        
        /* Method to entered guest details */
	public void guestDetailsEntered(String name, String address) {
		if (getState() != State.REGISTER) { //Chack the state and throw error message if state is not REGISTER
			String mesg = String.format("BookingCTL: guestDetailsEntered : bad state : %s", getState());
			throw new RuntimeException(mesg);
		}
		guest = hotel.registerGuest(name, address, phoneNumber);// register guest
		//Guest details
		getBookingUI().displayGuestDetails(guest.getName(), guest.getAddress(), guest.getPhoneNumber());
		setState(State.ROOM);
		getBookingUI().setState(BookingUI.State.ROOM);
	}
        
        /* Method to Room type and occupant entered */
	public void roomTypeAndOccupantsEntered(RoomType selectedRoomType, int occupantNumber) {
		if (getState() != State.ROOM) { //Chack the state and throw error message if state is not ROOM
			String mesg = String.format("BookingCTL: roomTypeAndOccupantsEntered : bad state : %s", getState());
			throw new RuntimeException(mesg);
		}
		this.selectedRoomType = selectedRoomType;
		this.occupantNumber = occupantNumber;
		
		boolean suitable = selectedRoomType.isSuitable(occupantNumber);
		
		if (!suitable) {			
			String notSuitableMessage = "\nRoom type unsuitable, please select another room type\n";
			getBookingUI().displayMessage(notSuitableMessage);
		}
		else {
			setState(State.TIMES);
			getBookingUI().setState(BookingUI.State.TIMES);
		}
	}

        /* Method to booking time entered */
	public void bookingTimesEntered(Date arrivalDate, int stayLength) {
		if (getState() != State.TIMES) { //Chack the state and throw error message if state is not TIEMS
			String mesg = String.format("BookingCTL: bookingTimesEntered : bad state : %s", getState());
			throw new RuntimeException(mesg);
		}
		this.arrivalDate = arrivalDate;
		this.stayLength = stayLength;
		
		room = hotel.findAvailableRoom(selectedRoomType, arrivalDate, stayLength);
		
		if (room == null) {				
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(arrivalDate);
			calendar.add(Calendar.DATE, stayLength);
			Date departureDate = calendar.getTime();
			
			String notAvailableStr = String.format("\n%s is not available between %s and %s\n",
					selectedRoomType.getDescription(),
					format.format(arrivalDate),
					format.format(departureDate));
			
			getBookingUI().displayMessage(notAvailableStr);
		}
		else {
			cost = selectedRoomType.calculateCost(arrivalDate, stayLength);
			String description = selectedRoomType.getDescription();
			getBookingUI().displayBookingDetails(description, arrivalDate, stayLength, cost);
			setState(State.CREDIT);
			getBookingUI().setState(BookingUI.State.CREDIT);
		}
	}

	public void creditDetailsEntered(CreditCardType type, int number, int ccv) {
            //checking the state whether it is credit
           if (getState() != State.CREDIT) {
                String message = String.format("BookingCTL: BookingTimesEntered : bad state : %s", new Object[] { getState()});
                throw new RuntimeException(message);
            }
           //creating an instance of credit card with passing values to constructor
            CreditCard creditCard = new CreditCard(type, number, ccv);
            // getting return value as true or false
            boolean approved = CreditAuthorizer.getInstance().authorize(creditCard, cost);
    
            if (!approved) {
                 String creditNotAuthorizedMessage = String.format(
                "\n%s credit card number %d was not authorized for $%.2f\n", new Object[] {
                creditCard.getType().getVendor(), Integer.valueOf(creditCard.getNumber()), Double.valueOf(cost) });
      
                getBookingUI().displayMessage(creditNotAuthorizedMessage); //display error message if error occurs
            }
            else {
                //assigning return value to long variable from book method of hotel class
                confirmationNumber = hotel.book(room,guest,new Date(2018, 05, 20),10,15,creditCard);
                //assign values to local variables
                String roomDecription = room.getDescription();
                int roomNumber = room.getId();
                 String guestName = guest.getName();
                 String creditCardVendor = creditCard.getVendor();
                 int cardNumber = creditCard.getNumber();
      
                //passing variable values to booking user interface
                 getBookingUI().displayConfirmedBooking(roomDecription, roomNumber, 
                new Date(2018, 05, 20), 04, "Sandeep", 
                creditCardVendor, cardNumber, cost, 
                confirmationNumber);
      
            //set state to completed
            setState(State.COMPLETED);
            getBookingUI().setState(BookingUI.State.COMPLETED);
         }
        }

	public void cancel() {
		IOUtils.trace("BookingCTL: cancel");
		getBookingUI().displayMessage("Booking cancelled");
		setState(State.CANCELLED);
		getBookingUI().setState(BookingUI.State.CANCELLED);
	}
	
	public void completed() {
		IOUtils.trace("BookingCTL: completed");
		getBookingUI().displayMessage("Booking completed");
	}

}