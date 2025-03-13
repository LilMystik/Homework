import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyClassLoader extends ClassLoader{

  private String lastResolve = "classes";

  @Override
  protected Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException{
    Class<?> loadedClass = findLoadedClass(name);
    if (loadedClass == null){
      try {
        loadedClass = super.loadClass(name,false);
      }catch(ClassNotFoundException ex){
        loadedClass = load(name);
      }
    }
    return  loadedClass;
  }

  public Class<?> load(String name){
    String filePath = lastResolve + File.separator  +name + ".class";
    byte[] binaryData;
    try (FileInputStream fileInputStream = new FileInputStream(filePath)){
      binaryData = fileInputStream.readAllBytes();
      return defineClass(name,binaryData,0,binaryData.length);
    }catch(IOException ex){
      System.err.println("Error while reading class data");
    }
    return null;
  }
}

