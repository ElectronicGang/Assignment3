package hotel.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hotel.credit.CreditCard;
import hotel.utils.IOUtils;

public class Booking {
	/* Class global variables declaration */
	private enum State {PENDING, CHECKED_IN, CHECKED_OUT};	
	private Guest guest;
	private Room room;
	private Date guestArrival; // variable "bookedArrival" renamed as "guestArrival"
	private int stayLength;
	int numberOfOccupants;
	long confirmationNumber;
	CreditCard creditCard;
	private List<ServiceCharge> charges;	
	private State state;

        /* Default Booking Constructor */
        public Booking(){
            this.state = State.PENDING; // Add default value for State to avaoid null output of default constructor
            
        }

	/* Booking constructor */
	public Booking(Guest guest, Room room, 
			Date arrivalDate, int stayLength, 
			int numberOfOccupants, 
			CreditCard creditCard) {
		
		this.guest = guest;
		this.room = room;
		this.guestArrival = arrivalDate;// variable "bookedArrival" renamed as "guestArrival"
		this.stayLength = stayLength;
		this.numberOfOccupants = numberOfOccupants;
		this.confirmationNumber = generateConfirmationNumber(room.getId(), arrivalDate);
		this.creditCard = creditCard;
		this.charges = new ArrayList<>();
		this.state = State.PENDING;
	}

    
	/* Method to generate booking confirmation number */
	private long generateConfirmationNumber(int roomId, Date arrivalDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(arrivalDate);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		String numberString = String.format("%d%d%d%d", day, month, year, roomId);
		
		return Long.parseLong(numberString);
	}

        /* Method to check any time conflicts in the booking */
	public boolean doTimesConflict(Date requestedArrival, int stayLength) {
		IOUtils.trace("Booking: timesConflict");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(guestArrival); // variable "bookedArrival" renamed as "guestArrival"
		calendar.add(Calendar.DATE, stayLength);
		Date bookedDeparture = calendar.getTime();
		
		calendar.setTime(requestedArrival);
		calendar.add(Calendar.DATE, stayLength);
		Date requestedDeparture = calendar.getTime();
		
		boolean doesConflict = requestedArrival.before(bookedDeparture) && 
				requestedDeparture.after(guestArrival);// variable "bookedArrival" renamed as "guestArrival"

		return doesConflict;
	}
        
        /* get method for State */
        public State getState() {
            return state;//returns the current state 
        }


        /* get method for confirmation number */
	public long getConfirmationNumber() {
		return confirmationNumber;
	}

        /* get method for room Id */
	public int getRoomId() {
		return room.getId();
	}
	
	/* get method for croom */
	public Room getRoom() {
		return room;
	}

        /* get method for arival date */
	public Date getArrivalDate() {
		return guestArrival;// variable "bookedArrival" renamed as "guestArrival"
	}

        /* get method for length of the stay */
	public int getStayLength() {
		return stayLength;
	}

        /* get method for guest */
	public Guest getGuest() {
		return guest;
	}

        /* get method for card details */
	public CreditCard getCreditCard() {
		return creditCard;
	}

        /* check method for pending status */
	public boolean isPending() {
		return state == State.PENDING;
	}

        /* check method for checked in status */
	public boolean isCheckedIn() {
		return state == State.CHECKED_IN;
	}

        /* check method for pending status */
	public boolean isCheckedOut() {
		return state == State.CHECKED_OUT;
	}

        /* get method for list of service charges */
	public List<ServiceCharge> getCharges() {
		return Collections.unmodifiableList(charges);
	}

        /* method check in */
	public void checkIn() {
            this.room = new Room();//intialize room instance 
            if (state != State.PENDING) { //Chack the state and throw error message if state is nor PENDING
                String mesg = String.format("Booking: checkIn : bad state : %s", new Object[] { state });
            throw new RuntimeException(mesg);
            }
            room.checkin(); // call checking method to Occupy the room
            state = State.CHECKED_IN; // Put the booking state as CHECKED_IN
	}

        /* Method for adding service chargers */
	public void addServiceCharge(ServiceType serviceType, double cost) {
            if (state != State.CHECKED_IN) { //Chack the state and throw error message if state is nor PENDING
                String mesg = String.format("Booking: addServiceCharge : bad state : %s", new Object[] { state });
            throw new RuntimeException(mesg);
            }
            ServiceCharge charge = new ServiceCharge(serviceType, cost);//Create new Service Charge object by constructor overloading 
            charges.add(charge);// Add the service charge into booking charges list
        }
  
        /* Method for checking out */
	public void checkOut() {
            if (state != State.CHECKED_IN) { //Chack the state and throw error message if state is nor PENDING
                String mesg = String.format("Booking: checkOut : bad state : %s", new Object[] { state });
            throw new RuntimeException(mesg);
            }
            room.checkout(this);  // call checkout method to vacant the room
            state = State.CHECKED_OUT; // Put the booking state as CHECKED_OUT
	}

}
