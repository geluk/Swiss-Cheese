package net.SwissCheese.Baggerboot;

public class Coordinate
{
  private int x;
  private int z;

  public Coordinate(int x, int z)
  {
    this.x = x;
    this.z = z;
  }
  public int getX() {
    return this.x;
  }
  public int getZ() {
    return this.z;
  }
  public void setX(int x) {
    this.x = x;
  }
  public void setZ(int z) {
    this.z = z;
  }
  public void set(int x, int z) {
    this.x = x;
    this.z = z;
  }
}