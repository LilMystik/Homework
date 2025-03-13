public class Main {
  public static void main(String[] args) {
    try {
      MyClassLoader myClassLoader = new MyClassLoader();
      Class<?> printerClass = myClassLoader.loadClass("Printer");
      Object printerInstance = printerClass.getDeclaredConstructor().newInstance();
      System.out.println(printerInstance.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}