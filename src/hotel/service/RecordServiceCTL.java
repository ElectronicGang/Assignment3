package hotel.service;

import hotel.entities.Booking;
import hotel.entities.Hotel;
import hotel.entities.ServiceType;
import hotel.utils.IOUtils;

public class RecordServiceCTL {
	/* Class global variables declaration */
	private static enum State {ROOM, SERVICE, CHARGE, CANCELLED, COMPLETED};	
	private Hotel hotel;
	private RecordServiceUI recordServiceUI;
	private State state;	
	private Booking booking;
	private int roomNumber;

        /* Booking overloaded constructor  */
	public RecordServiceCTL(Hotel hotel) {
		this.recordServiceUI = new RecordServiceUI(this);
		state = State.ROOM;
		this.hotel = hotel;
	}

	/* Method to run the class */
	public void run() {		
		IOUtils.trace("PayForServiceCTL: run");
		recordServiceUI.run();
	}

        /* Method to enter room number */
	public void roomNumberEntered(int roomNumber) {
		if (state != State.ROOM) { //Chack the state and throw error message if state is not ROOM
			String mesg = String.format("PayForServiceCTL: roomNumberEntered : bad state : %s", state);
			throw new RuntimeException(mesg);
		}
		booking = hotel.findActiveBookingByRoomId(roomNumber); // Find the booking by room id
		if (booking == null) {
			String mesg = String.format("No active booking for room id: %d", roomNumber);
			recordServiceUI.displayMessage(mesg);
		}
		else {
			this.roomNumber = roomNumber;
			state = State.SERVICE;
			recordServiceUI.setState(RecordServiceUI.State.SERVICE);
		}
	}
	
	/* Method to genter service details */
	public void serviceDetailsEntered(ServiceType serviceType, double cost) {
            if (state != State.SERVICE) { //Chack the state and throw error message if state is not ROOM
                String mesg = String.format("PayForServiceCTL: serviceDetailsEntered : bad state : %s", new Object[] { state });
                throw new RuntimeException(mesg);
            }
            hotel.addServiceCharge(roomNumber, serviceType, cost);    // Add service charges
            recordServiceUI.displayServiceChargeMessage(roomNumber, cost, serviceType.getDescription());
            state = State.COMPLETED;
            recordServiceUI.setState(RecordServiceUI.State.COMPLETED); // set the state as complete
	}

        /* Method to display cancel message */
	public void cancel() {
		recordServiceUI.displayMessage("Pay for service cancelled");
		state = State.CANCELLED;
		recordServiceUI.setState(RecordServiceUI.State.CANCELLED); //set the state as cancelled
	}

        /* Method to display completer message */
	public void completed() {
		recordServiceUI.displayMessage("Pay for service completed");
	}


}
