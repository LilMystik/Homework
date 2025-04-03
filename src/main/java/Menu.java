

import exception.WorkspaceUnavailableException;
import models.Reservation;
import models.Workspace;

import java.util.Optional;
import java.util.Scanner;

public class Menu {
  private final Scanner scanner = new Scanner(System.in);
  private final Storage storage = new Storage();
  private static final String WRONG = "Wrong choice";

  public void start() {
    while (true) {
      System.out.println("Select Option:\n1 - Admin\n2 - Customer\n3 - Exit");
      int option = scanner.nextInt();
      scanner.nextLine();
      switch (option) {
        case 1 : {
          adminMenu();
          break;
        }
        case 2 : {
          customerMenu();
          break;
        }
        case 3 : {
          storage.closeState();
          return;
        }
        default : {
          System.out.println(WRONG);
          break;
        }
      }
    }
  }

  private void adminMenu() {
    while (true) {
      System.out.println("Workspace management:\n1 - View workspaces\n2 - Add workspace\n3 - Remove workspace\n4 - View reservations\n5 - Return");
      int option = scanner.nextInt();
      scanner.nextLine();
      switch (option) {
        case 1 : {
          showWorkspaces();
          break;
        }
        case 2 :{
          addWorkspace();
          break;
        }
        case 3 :{
          removeWorkspace();
          break;
        }
        case 4 :{
          showReservations();
          break;
        }
        case 5 : {
          return;
        }
        default : {
          System.out.println(WRONG);
          break;
        }
      }
    }
  }

  private void customerMenu() {
    while (true) {
      System.out.println("Customer menu:\n1 - View workspaces\n2 - Make reservation\n3 - See reservations\n4 - Cancel reservation\n5 - Return");
      int option = scanner.nextInt();
      scanner.nextLine();
      switch (option) {
        case 1 : {
          showWorkspaces();
          break;
        }
        case 2 :{
          addReservation();
          break;
        }
        case 3 :{
          showReservations();
          break;
        }
        case 4 :{
          cancelReservation();
          break;
        }
        case 5 : {
          return;
        }
        default :{
          System.out.println(WRONG);
          break;
        }
      }
    }
  }

  private void showWorkspaces() {
    System.out.println("Workspaces:");
    for (Workspace ws : storage.getWorkspaces()) {
      System.out.println(ws);
    }
  }

  private void addWorkspace() {
    System.out.println("Enter workspace description:");
    String description = scanner.nextLine();
    System.out.println("Enter price:");
    int price = scanner.nextInt();
    scanner.nextLine();
    storage.addWorkspace(description, price);
  }

  private void removeWorkspace() {
    showWorkspaces();
    System.out.println("Enter workspace ID to remove:");
    int id = scanner.nextInt();
    scanner.nextLine();
    storage.removeWorkspace(id);
  }

  private void addReservation() {
    System.out.println("Enter your name:");
    String name = scanner.nextLine();
    showWorkspaces();
    System.out.println("Choose workspace ID:");
    int wsId = scanner.nextInt();
    scanner.nextLine();
    System.out.println("Enter date:");
    String date = scanner.nextLine();
    System.out.println("Enter start time:");
    String startTime = scanner.nextLine();
    System.out.println("Enter end time:");
    String endTime = scanner.nextLine();
    try {
      storage.addReservation(wsId, name, date, startTime, endTime);
    }catch (WorkspaceUnavailableException e) {
      System.out.println(e.getMessage());
    }
  }

  private void showReservations() {
    System.out.println("Reservations:");
    for (Reservation res : storage.getReservations()) {
      System.out.println(res);
    }
  }

  private void cancelReservation() {
    showReservations();
    System.out.println("Enter reservation ID to cancel:");
    int resId = scanner.nextInt();
    scanner.nextLine();
    storage.cancelReservation(resId);
  }
}

