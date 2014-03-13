package database;

import java.io.*;

  /**
   * http://www.javaranch.com/journal/200607/Plugins.html
   * In order to impose tight security restrictions on untrusted classes but
   * not on trusted system classes, we have to be able to distinguish between
   * those types of classes. This is done by keeping track of how the classes
   * are loaded into the system. By definition, any class that the interpreter
   * loads directly from the CLASSPATH is trusted. This means that we can't
   * load untrusted code in that way--we can't load it with Class.forName().
   * Instead, we create a ClassLoader subclass to load the untrusted code.
   * This one loads classes from a specified directory (which should not
   * be part of the CLASSPATH).
   */

public class AnimationClassLoader extends ClassLoader {
    /** This is the directory from which the classes will be loaded */
    File directory;

    /** The constructor. Just initialize the directory */
    public AnimationClassLoader (File dir) {
		directory = dir;
	}

    /** A convenience method that calls the 2-argument form of this method */
    public Class loadClass (String name) throws ClassNotFoundException { 
      return loadClass(name, true); 
    }

    /**
     * This is one abstract method of ClassLoader that all subclasses must
     * define. Its job is to load an array of bytes from somewhere and to
     * pass them to defineClass(). If the resolve argument is true, it must
     * also call resolveClass(), which will do things like verify the presence
     * of the superclass. Because of this second step, this method may be called to
     * load superclasses that are system classes, and it must take this into account.
     */
    @Override
    public Class loadClass (String dotClassFile, boolean resolve) throws ClassNotFoundException {
      try {
        // Our ClassLoader superclass has a built-in cache of classes it has
        // already loaded. So, first check the cache.
        Class c = findLoadedClass(dotClassFile);

        // After this method loads a class, it will be called again to
        // load the superclasses. Since these may be system classes, we've
        // got to be able to load those too. So try to load the class as
        // a system class (i.e. from the CLASSPATH) and ignore any errors
        if (c == null) {
          try { c = findSystemClass(dotClassFile); }
          catch (Exception ex) {}
        }

        // If the class wasn't found by either of the above attempts, then
        // try to load it from a file in (or beneath) the directory
        // specified when this ClassLoader object was created. Form the
        // filename by replacing all dots in the class name with
        // (platform-independent) file separators and by adding the ".class" extension.
        if (c == null) {
          // Figure out the filename

          // Create a File object. Interpret the filename relative to the
          // directory specified for this ClassLoader.
          File f = new File(directory, dotClassFile);

          // Get the length of the class file, allocate an array of bytes for
          // it, and read it in all at once.
          int length = (int) f.length();
          byte[] classbytes = new byte[length];
          DataInputStream in = new DataInputStream(new FileInputStream(f));
          in.readFully(classbytes);
          in.close();

          // Now call an inherited method to convert those bytes into a Class
          c = defineClass(dotClassFile, classbytes, 0, length);
        }

        // If the resolve argument is true, call the inherited resolveClass method.
        if (resolve) resolveClass(c);

        // And we're done. Return the Class object we've loaded.
        return c;
      }
      // If anything goes wrong, throw a ClassNotFoundException error
      catch (Exception ex) { throw new ClassNotFoundException(ex.toString()); }
    }
}