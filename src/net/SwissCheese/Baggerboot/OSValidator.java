package net.SwissCheese.Baggerboot;

public class OSValidator
{
  public static int getOS()
  {
    if (isWindows())
      return 0;
    if (isUnix())
      return 2;
    if (isMac())
      return 1;
    if (isSolaris()) {
      return 3;
    }
    return -1;
  }

  public static boolean isWindows()
  {
    String os = System.getProperty("os.name").toLowerCase();

    return os.indexOf("win") >= 0;
  }

  public static boolean isMac()
  {
    String os = System.getProperty("os.name").toLowerCase();

    return os.indexOf("mac") >= 0;
  }

  public static boolean isUnix()
  {
    String os = System.getProperty("os.name").toLowerCase();

    return (os.indexOf("nix") >= 0) || (os.indexOf("nux") >= 0);
  }

  public static boolean isSolaris()
  {
    String os = System.getProperty("os.name").toLowerCase();

    return os.indexOf("sunos") >= 0;
  }
}