package identifier.service;

import identifier.exception.WorkspaceUnavailableException;
import identifier.models.Reservation;
import identifier.models.Workspace;
import identifier.repository.ReservationRepository;
import identifier.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StorageService {

  private final WorkspaceRepository workspaceRepository;
  private final ReservationRepository reservationRepository;

  public StorageService(WorkspaceRepository workspaceRepository, ReservationRepository reservationRepository) {
    this.workspaceRepository = workspaceRepository;
    this.reservationRepository = reservationRepository;
  }

  public List<Workspace> getWorkspaces() {
    return workspaceRepository.findAll();
  }

  public List<Reservation> getReservations() {
    return reservationRepository.findAll();
  }

  @Transactional
  public void addWorkspace(String details, int price) {
    Workspace workspace = new Workspace(details, price, true);
    workspaceRepository.save(workspace);
  }

  @Transactional
  public void removeWorkspace(int id) {
    workspaceRepository.deleteById(id);
  }

  @Transactional
  public void addReservation(int workspaceId, String name, String date, String startTime, String endTime) throws WorkspaceUnavailableException {
    Workspace workspace = workspaceRepository.findByIdAndAvailableTrue(workspaceId)
            .orElseThrow(() -> new WorkspaceUnavailableException("Workspace is unavailable."));

    workspace.setAvailability(false);
    Reservation reservation = new Reservation(workspace, name, date, startTime, endTime);
    reservationRepository.save(reservation);
    workspaceRepository.save(workspace);
  }

  @Transactional
  public void cancelReservation(int reservationId) {
    reservationRepository.findById(reservationId).ifPresentOrElse(reservation -> {
      Workspace workspace = reservation.getWorkspace();
      workspace.setAvailability(true);
      reservationRepository.delete(reservation);
      workspaceRepository.save(workspace);
    }, () -> System.out.println("Reservation not found."));
  }
}
