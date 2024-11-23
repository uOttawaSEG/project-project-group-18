package com.example.seg_project_d11;

public class RequestEvent {

    private int requestID; //auto-incremented ID
    private String attendeeEmail; //email of the attendee that made the request
    private int eventID; //ID of the event requested
    private String status; //status of teh request ("Pending", "Approved" or "Rejected")

    public RequestEvent(int requestID, String attendeeEmail, int eventID, String status) {
        this.requestID = requestID;
        this.attendeeEmail = attendeeEmail;
        this.eventID = eventID;
        this.status = status;
    }

    public RequestEvent(String attendeeEmail, int eventID, String status) {
        this.attendeeEmail = attendeeEmail;
        this.eventID = eventID;
        this.status = status;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getAttendeeEmail() {
        return attendeeEmail;
    }

    public void setAttendeeEmail(String attendeeEmail) {
        this.attendeeEmail = attendeeEmail;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
