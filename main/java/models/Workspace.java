package models;


import java.io.Serializable;

public class Workspace implements Serializable {
  private int id;
  private String details;
  private int price;

  public void setId(int id) {
    this.id = id;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public boolean isAvailability() {
    return availability;
  }

  private boolean availability;



  public int getId() {
    return id;
  }

  public String getDetails() {
    return details;
  }

  public int getPrice() {
    return price;
  }

  public boolean isAvailable() {
    return availability;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  public Workspace(int id, String details, int price) {
    this.id = id;
    this.details = details;
    this.price = price;
    this.availability = true;
  }

  @Override
  public String toString() {
    return "ID: " + id + " | Details: " + details + " | Price: " + price + " | Available: " + availability;
  }
}

