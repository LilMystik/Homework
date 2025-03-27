

import exception.WorkspaceUnavailableException;
import models.Reservation;
import models.Workspace;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Storage {

  private static final String WORKSPACES_FILE = "workspaces";
  private static final String RESERVATIONS_FILE = "reservations";

  private final Map<Integer, Workspace> workspaces = new HashMap<>();
  private final Map<Integer, Reservation> reservations = new HashMap<>();
  private int reservationId = 1;
  private int workspaceId = 1;

  public Map<Integer, Workspace> getWorkspaces() {
    return workspaces;
  }

  public Map<Integer, Reservation> getReservations() {
    return reservations;
  }

  public void addWorkspace(String details, int price) {
    workspaces.put(workspaceId, new Workspace(workspaceId, details, price));
    workspaceId++;
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

    Workspace workspace = workspaceOpt.get();
    workspace.setAvailability(false);
    Reservation reservation = new Reservation(reservationId, workspaceId, name, date, startTime, endTime);
    reservations.put(reservationId, reservation);
    reservationId++;
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
    saveToFile(WORKSPACES_FILE, workspaces);
    saveToFile(RESERVATIONS_FILE, reservations);
  }

  private <T> void saveToFile(String filename, Map<Integer, T> map) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
      oos.writeObject(map);
    } catch (IOException e) {
      System.out.println("Error saving data: " + e.getMessage());
    }
  }

  public void loadState() {
    Map<Integer, Workspace> loadedWorkspaces = loadFromFile(WORKSPACES_FILE);
    if (loadedWorkspaces != null) workspaces.putAll(loadedWorkspaces);
    workspaceId = workspaces.isEmpty() ? 1 : (workspaces.keySet().stream().max(Integer::compareTo).orElse(0) + 1);

    Map<Integer, Reservation> loadedReservations = loadFromFile(RESERVATIONS_FILE);
    if (loadedReservations != null) reservations.putAll(loadedReservations);
    reservationId = reservations.isEmpty() ? 1 : (reservations.keySet().stream().max(Integer::compareTo).orElse(0) + 1);
  }

  private <T> Map<Integer, T> loadFromFile(String filename) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      return (Map<Integer, T>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      return new HashMap<>();
    }
  }
}
