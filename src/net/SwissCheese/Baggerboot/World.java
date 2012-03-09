package net.SwissCheese.Baggerboot;
public class World
{
  private static final Main main = new Main();
  private static final int vDistance = main.getVDistance();
  private static final int hMax = main.getHMax();
  private static int[][][] blocks = new int[vDistance * 16 * 2][hMax][vDistance * 2];
  
}