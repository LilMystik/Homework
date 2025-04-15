import identifier.config.SpringConfig;
import identifier.menu.Menu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    Menu menu = context.getBean(Menu.class);
    menu.start();
  }
}
