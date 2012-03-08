package net.SwissCheese.Baggerboot;

import org.lwjgl.input.Keyboard;

public class KeyInput
{
  Main main = new Main();

  private float movementSpeed = this.main.getMovementSpeed();
  private float dt;

  public void listen()
  {
    this.dt = Render3D.dt;
    if (Keyboard.isKeyDown(17)) {
      FirstPersonCamera.walkForward(this.movementSpeed * this.dt);
    }
    if (Keyboard.isKeyDown(31)) {
      FirstPersonCamera.walkBackwards(this.movementSpeed * this.dt);
    }
    if (Keyboard.isKeyDown(30)) {
      FirstPersonCamera.strafeLeft(this.movementSpeed * this.dt);
    }
    if (Keyboard.isKeyDown(32)) {
      FirstPersonCamera.strafeRight(this.movementSpeed * this.dt);
    }
    if (Keyboard.isKeyDown(46)) {
      FirstPersonCamera.moveUp(this.movementSpeed * this.dt);
    }
    if (Keyboard.isKeyDown(44)) {
      FirstPersonCamera.moveDown(this.movementSpeed * this.dt);
    }
    if (Keyboard.isKeyDown(1)) {
      Render3D.closeRequested = true;
    }
    while (Keyboard.next())
    {
      if ((Keyboard.getEventKey() == 33) && (Keyboard.getEventKeyState())) {
        Render3D.toggleFullScreen();
      }
      if ((Keyboard.getEventKey() == 57) && (Keyboard.getEventKeyState()))
        Render3D.toggleFalling();
    }
  }
}