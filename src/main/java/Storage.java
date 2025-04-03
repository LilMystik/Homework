import exception.WorkspaceUnavailableException;
import models.Reservation;
import models.Workspace;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class Storage {
  private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("homeworkDB");
  private final EntityManager em = emf.createEntityManager();

  public List<Workspace> getWorkspaces() {
    return em.createQuery("SELECT w FROM Workspace w", Workspace.class).getResultList();
  }

  public List<Reservation> getReservations() {
    return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
  }

  public void addWorkspace(String details, int price) {
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Workspace workspace = new Workspace(details, price, true);
    em.persist(workspace);
    transaction.commit();
  }

  public void removeWorkspace(int id) {
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Workspace workspace = em.find(Workspace.class, id);
    if (workspace != null) {
      em.remove(workspace);
    }
    transaction.commit();
  }

  public Optional<Workspace> findAvailableWorkspace(int id) {
    Workspace workspace = em.find(Workspace.class, id);
    return (workspace != null && workspace.isAvailable()) ? Optional.of(workspace) : Optional.empty();
  }

  public void addReservation(int workspaceId, String name, String date, String startTime, String endTime) throws WorkspaceUnavailableException {
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Optional<Workspace> workspaceOpt = findAvailableWorkspace(workspaceId);
    if (workspaceOpt.isEmpty()) {
      transaction.rollback();
      throw new WorkspaceUnavailableException("Workspace is unavailable.");
    }
    Workspace workspace = workspaceOpt.get();
    workspace.setAvailability(false);
    Reservation reservation = new Reservation(workspace, name, date, startTime, endTime);
    em.persist(reservation);
    transaction.commit();
  }

  public void cancelReservation(int reservationId) {
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Reservation reservation = em.find(Reservation.class, reservationId);
    if (reservation != null) {
      Workspace workspace = reservation.getWorkspace();
      workspace.setAvailability(true);
      em.remove(reservation);
      System.out.println("Reservation cancelled.");
    } else {
      System.out.println("Reservation not found.");
    }
    transaction.commit();
  }

  public void closeState(){
    em.close();
    emf.close();
  }

}
