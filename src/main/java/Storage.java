import exception.WorkspaceUnavailableException;
import models.Reservation;
import models.Workspace;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Storage {
  private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
  private static final String DB_USER = "postgres";
  private static final String DB_PASSWORD = "postgres";

  private final Map<Integer, Workspace> workspaces = new HashMap<>();
  private final Map<Integer, Reservation> reservations = new HashMap<>();

  public Storage() {
    loadState();
  }

  public Map<Integer, Workspace> getWorkspaces() {
    return workspaces;
  }

  public Map<Integer, Reservation> getReservations() {
    return reservations;
  }

  public void addWorkspace(String details, int price) {
    int id = workspaces.size() + 1;
    Workspace workspace = new Workspace(id, details, price, true);
    workspaces.put(id, workspace);
  }

  public void removeWorkspace(int id) {
    workspaces.remove(id);
  }

  public Optional<Workspace> findAvailableWorkspace(int id) {
    Workspace workspace = workspaces.get(id);
    return (workspace != null && workspace.isAvailable()) ? Optional.of(workspace) : Optional.empty();
  }

  public void addReservation(int workspaceId, String name, String date, String startTime, String endTime) throws WorkspaceUnavailableException {
    Optional<Workspace> workspaceOpt = findAvailableWorkspace(workspaceId);
    if (workspaceOpt.isEmpty()) {
      throw new WorkspaceUnavailableException("Workspace is unavailable.");
    }
    int id = reservations.size() + 1;
    Reservation reservation = new Reservation(id, workspaceId, name, date, startTime, endTime);
    reservations.put(id, reservation);
    workspaceOpt.get().setAvailability(false);
  }

  public void cancelReservation(int reservationId) {
    Reservation reservation = reservations.remove(reservationId);
    if (reservation != null) {
      Workspace workspace = workspaces.get(reservation.getWorkspaceId());
      if (workspace != null) {
        workspace.setAvailability(true);
      }
      System.out.println("Reservation cancelled.");
    } else {
      System.out.println("Reservation not found.");
    }
  }

  public void saveState() {
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      conn.setAutoCommit(false);
      try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate("DELETE FROM homework.workspace");
        stmt.executeUpdate("DELETE FROM homework.reservation");

        for (Workspace workspace : workspaces.values()) {
          String query = "INSERT INTO homework.workspace (id, details, price, available) VALUES (?, ?, ?, ?)";
          try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, workspace.getId());
            pstmt.setString(2, workspace.getDetails());
            pstmt.setInt(3, workspace.getPrice());
            pstmt.setBoolean(4, workspace.isAvailable());
            pstmt.executeUpdate();
          }
        }

        for (Reservation reservation : reservations.values()) {
          String query = "INSERT INTO homework.reservation (id, workspace_id, name, date, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?)";
          try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reservation.getReservationID());
            pstmt.setInt(2, reservation.getWorkspaceId());
            pstmt.setString(3, reservation.getName());
            pstmt.setString(4, reservation.getDate());
            pstmt.setString(5, reservation.getStartTime());
            pstmt.setString(6, reservation.getEndTime());
            pstmt.executeUpdate();
          }
        }
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void loadState() {
    String workspaceQuery = "SELECT * FROM homework.workspace";
    String reservationQuery = "SELECT * FROM homework.reservation";
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet wsRs = stmt.executeQuery(workspaceQuery)) {

      while (wsRs.next()) {
        Workspace workspace = new Workspace(
                wsRs.getInt("id"),
                wsRs.getString("details"),
                wsRs.getInt("price"),
                wsRs.getBoolean("available")
        );
        workspaces.put(workspace.getId(), workspace);
      }

      try (ResultSet resRs = stmt.executeQuery(reservationQuery)) {
        while (resRs.next()) {
          Reservation reservation = new Reservation(
                  resRs.getInt("id"),
                  resRs.getInt("workspace_id"),
                  resRs.getString("name"),
                  resRs.getString("date"),
                  resRs.getString("start_time"),
                  resRs.getString("end_time")
          );
          reservations.put(reservation.getReservationID(), reservation);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
