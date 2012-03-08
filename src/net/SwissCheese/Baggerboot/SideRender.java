package net.SwissCheese.Baggerboot;

public class SideRender
{
  private boolean top;
  private boolean bottom;
  private boolean front;
  private boolean back;
  private boolean left;
  private boolean right;

  public SideRender(boolean top, boolean bottom, boolean front, boolean back, boolean left, boolean right)
  {
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
    this.back = back;
    this.front = front;
  }

  public boolean[] getAll() {
    boolean[] var = { this.top, this.bottom, this.front, this.back, this.left, this.right };
    return var;
  }

  public void setAll(boolean[] newSettings) {
    this.top = newSettings[0];
    this.bottom = newSettings[1];
    this.front = newSettings[2];
    this.back = newSettings[3];
    this.left = newSettings[4];
    this.right = newSettings[5];
  }

  public boolean getTop() {
    return this.top;
  }

  public boolean getBottom() {
    return this.bottom;
  }

  public boolean getFront() {
    return this.front;
  }

  public boolean getBack() {
    return this.back;
  }

  public boolean getLeft() {
    return this.left;
  }

  public boolean getRight() {
    return this.right;
  }
}