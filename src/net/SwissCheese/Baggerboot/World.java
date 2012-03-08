package net.SwissCheese.Baggerboot;
public class World
{
  private static Main main = new Main();
  private static int vDistance = main.getVDistance();
  private static int hMax = main.getHMax();
  private static int[][][] blocks = new int[vDistance * 16 * 2][hMax][vDistance * 2];
}