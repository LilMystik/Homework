import exception.WorkspaceUnavailableException;
import models.Reservation;
import models.Workspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

  private Storage storage;

  @BeforeEach
  void setUp() {
    storage = new Storage();
  }

  @Test
  void testAddWorkspace() {
    storage.addWorkspace("Office Space", 100);
    Map<Integer, Workspace> workspaces = storage.getWorkspaces();
    assertEquals(1, workspaces.size());
    assertTrue(workspaces.containsKey(1));
  }

  @Test
  void testRemoveWorkspace() {
    storage.addWorkspace("Office Space", 100);
    storage.removeWorkspace(1);
    assertFalse(storage.getWorkspaces().containsKey(1));
  }

  @Test
  void testFindAvailableWorkspace() {
    storage.addWorkspace("Conference Room", 200);
    assertTrue(storage.findAvailableWorkspace(1).isPresent());
    storage.getWorkspaces().get(1).setAvailability(false);
    assertFalse(storage.findAvailableWorkspace(1).isPresent());
  }

  @Test
  void testAddReservation() throws WorkspaceUnavailableException {
    storage.addWorkspace("Meeting Room", 150);
    storage.addReservation(1, "John Doe", "2025-03-04", "10:00", "12:00");
    assertEquals(1, storage.getReservations().size());
    assertFalse(storage.getWorkspaces().get(1).isAvailable());
  }

  @Test
  void testAddReservationThrowsExceptionWhenWorkspaceUnavailable() {
    assertThrows(WorkspaceUnavailableException.class, () ->
            storage.addReservation(1, "Jane Doe", "2025-03-04", "14:00", "16:00")
    );
  }

  @Test
  void testCancelReservation() throws WorkspaceUnavailableException {
    storage.addWorkspace("Private Office", 300);
    storage.addReservation(1, "Alice", "2025-03-04", "09:00", "11:00");
    storage.cancelReservation(1);
    assertTrue(storage.getReservations().isEmpty());
    assertTrue(storage.getWorkspaces().get(1).isAvailable());
  }

  @Test
  void testSaveAndLoadState() {
    storage.addWorkspace("Shared Desk", 50);
    storage.addWorkspace("Quiet Room", 75);
    storage.saveState();

    Storage newStorage = new Storage();
    newStorage.loadState();

    assertEquals(2, newStorage.getWorkspaces().size());
    assertTrue(new File("workspaces").exists());
    assertTrue(new File("reservations").exists());
  }
}
