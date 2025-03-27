package models;

import  java.io.Serializable;

public class Reservation implements Serializable {
  public void setReservationID(int reservationID) {
    this.reservationID = reservationID;
  }

  public void setWorkspaceId(int workspaceId) {
    this.workspaceId = workspaceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  private int reservationID;
  private int workspaceId;
  private String name;
  private String date;
  private String startTime;
  private String endTime;

  public int getReservationID() {
    return reservationID;
  }

  public int getWorkspaceId() {
    return workspaceId;
  }

  public Reservation(int reservationID, int workspaceId, String name, String date, String startTime, String endTime) {
    this.reservationID = reservationID;
    this.workspaceId = workspaceId;
    this.name = name;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return "ID: " + reservationID + " | Workspace ID: " + workspaceId + " | Name: " + name +
            " | Date: " + date + " | Start Time: " + startTime + " | End Time: " + endTime;
  }
}
