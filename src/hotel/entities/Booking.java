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
	
	 public Booking(){
           setState(State.PENDING);
           this.charges = new ArrayList<>();
       }
	/* Default Booking constructor */
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
	public State getState() {
        return state;
        } 
	
	public void setState(State state) {
        this.state = state;
        }

        /* method check in */
	public void checkIn() {
             this.room = new Room();//intialize room instance for the method
		//checking state befor the checking
    if (getState() != State.PENDING) {
        //if state not equal to pending error message will display with current stste
      String message = String.format("Booking: checkIn : bad state : %s", new Object[] { getState()});
      throw new RuntimeException(message);//run time exception will be thrown.
    }
    //execute checkin method of room class to make room occupied
       room.checkin();
    //make state as checked in
        setState(State.CHECKED_IN);
	}

        /* Method for adding service chargers */
	public void addServiceCharge(ServiceType serviceType, double cost) {
           checkIn();
            //checking the state first
		if (getState() != State.CHECKED_IN) {
      String message = String.format("Booking: addServiceCharge : bad state : %s", new Object[] { getState()});
      throw new RuntimeException(message);
    }
                //creating an instance of service charge with passed parameters
    ServiceCharge servCharge = new ServiceCharge(serviceType, cost);
    charges.add(servCharge); // add charge to the charges list
        }
  
        /* Method for checking out */
	public void checkOut() {
             checkIn();
		//checking the state first
    if (getState() != State.CHECKED_IN) {
      String message = String.format("Booking: checkOut : bad state : %s", new Object[] { getState()});
      throw new RuntimeException(message);
    }
    //passing object of the class to checkout method of room class
    room.checkout(this);
    // change state as checked out.
        setState(State.CHECKED_OUT);
	}

}
