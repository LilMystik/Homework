package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workspace", schema = "homework")
public class Workspace implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String details;
  private int price;
  private boolean availability;

  public Workspace() {
  }

  public Workspace(String details, int price, boolean availability) {
    this.details = details;
    this.price = price;
    this.availability = availability;
  }

  public int getId() {
    return id;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public boolean isAvailable() {
    return availability;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  @Override
  public String toString() {
    return "ID: " + id + " | Details: " + details + " | Price: " + price + " | Available: " + availability;

  }
}
