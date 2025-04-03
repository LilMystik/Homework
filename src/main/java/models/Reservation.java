package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reservation", schema = "homework")
public class Reservation implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToOne
  @JoinColumn(name = "workspace_id", nullable = false)
  private Workspace workspace;

  private String name;
  private String date;
  private String startTime;
  private String endTime;

  public Reservation() {
  }

  public Reservation(Workspace workspace, String name, String date, String startTime, String endTime) {
    this.workspace = workspace;
    this.name = name;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public int getId() {
    return id;
  }

  public Workspace getWorkspace() {
    return workspace;
  }

  public void setWorkspace(Workspace workspace) {
    this.workspace = workspace;
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


  @Override
  public String toString() {
    return "ID: " + id + " | " + "Workspace ID: " + (workspace != null ? workspace.getId() : null)
            + " | " + "Name: " + name + " | " + "Date: " + date + " | " + "Start Time: "
            + startTime + " | " + "End Time: " + endTime;
  }

}
