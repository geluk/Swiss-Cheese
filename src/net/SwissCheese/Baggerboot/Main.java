package net.SwissCheese.Baggerboot;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
  private static final String version = "pre_0.7";
  private static boolean released = true;
  private static boolean debugging = false;
  private static boolean flying = false;
  private static int vDistance = 1;
  private static long seed = 19L;
  private static int hMax = 34;

  private float mouseSensitivity = 0.15F;
  private float movementSpeed = 4.0F;

  private static void testOS()
  {
    switch (OSValidator.getOS()) {
    case 0:
      System.out.println("Auto-detected OS: Windows");
      System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "\\native\\windows");
      break;
    case 2:
      System.out.println("Auto-detected OS: GNU/Linux");
      System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/linux");
      break;
    case 1:
      System.out.println("Auto-detected OS: Mac OS");
      System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/macosx");
      break;
    case 3:
      System.out.println("Auto-detected OS: Solaris (Really?)");
      System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/solaris");
      break;
    default:
      System.out.println("An error has occured, or perhaps your OS is not supported?");
      System.out.println("  If you are using Windows, Linux, Mac OS or Solaris, it should be supported.");
      System.out.println("  In that case, contact Baggerboot.");
      System.out.println("  Actually, contact Baggerboot anyway. This is not supposed to happen."); }  } 
  private static void testInput() { Scanner input = new Scanner(System.in);
    System.out.println("========================================");
    System.out.println("\tSwiss Cheese pre_0.7");
    System.out.println("========================================");

    testOS();
    System.out.println("Please input the desired render distance:");
    int var;
    try { var = input.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Invalid input! Only whole numbers and no letters are accepted.");
      System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
      testInput();
      return;
    }

    vDistance = var;
    System.out.println("Please input the world seed:");
    try {
      var = input.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Invalid input! Only whole numbers and no letters are accepted.");
      System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
      testInput();
      return;
    }
    seed = var;
  }

  private static void setBagger()
  {
    System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "\\native\\windows");
    vDistance = 2;
    seed = 2L;
  }

  private static void debug()
  {
  }

  public float toFloat(int x)
  {
    if (x > 255)
      return 1.0F;
    if (x == 0) {
      return 0.0F;
    }

    double y = 392.0D;
    y *= x;
    return (float)y / 100000.0F;
  }

  boolean getFlying()
  {
    return flying;
  }

  public long getSeed() {
    return seed;
  }

  public int getHMax() {
    return hMax;
  }

  public int getVDistance() {
    return vDistance;
  }

  public float getMouseSensitivity() {
    return this.mouseSensitivity;
  }

  public float getMovementSpeed() {
    return this.movementSpeed;
  }

  public static void main(String[] args) {
    if (!debugging) {
      if (args.length == 2) {
        try {
          vDistance = Integer.parseInt(args[0]);
          seed = Integer.parseInt(args[1]);
          System.out.println("Trying to load arguments");
        } catch (NumberFormatException e) {
          System.out.println("Argument error detected. Arguments will be ignored.");
          System.out.println("Both arguments may not contain any letters.");
          System.out.println("Argument 1 is the render distance, argument 2 is the world seed");
          testInput();
        }
        testOS();
      } else if (args.length != 0) {
        System.out.println("Please use a maximum of two arguments.");
        System.out.println("Argument 1 will be the render distance, argument 2 will be the world seed");
      }
      else if (released) {
        testInput();
      } else {
        setBagger();
      }

      generateChunks();
      Render3D.run();
    } else {
      debug();
    }
  }

  private static void generateChunks() {
    System.out.println("Generating chunks. Please be patient as this may take a while...");
    int k = 0;
    for (int i = 0; i < vDistance; i++) {
      for (int j = 0; j < vDistance; j++) {
        Render3D.chunkData[i][j] = new Chunk(i, j);
        if (k % 5 == 0) {
          System.out.println("Generating chunk " + k + " out of " + vDistance * vDistance);
        }
        k++;
      }
    }
    System.out.println("Saving chunks...");
    for (int i = 0; i < vDistance; i++) {
      for (int j = 0; j < vDistance; j++) {
        Render3D.chunkData[i][j].save();
      }
    }
    System.out.println("========================================");
  }
}