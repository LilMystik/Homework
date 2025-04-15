package identifier.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "workspace", schema = "homework")
public class Workspace implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String details;
  private int price;
  private boolean available;

  public Workspace() {
  }

  public Workspace(String details, int price, boolean available) {
    this.details = details;
    this.price = price;
    this.available = available;
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
    return available;
  }

  public void setAvailability(boolean availability) {
    this.available= availability;
  }

  @Override
  public String toString() {
    return "ID: " + id + " | Details: " + details + " | Price: " + price + " | Available: " + available;

  }
}
