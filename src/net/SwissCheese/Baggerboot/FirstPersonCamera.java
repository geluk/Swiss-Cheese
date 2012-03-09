package net.SwissCheese.Baggerboot;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class FirstPersonCamera
{
  private static Vector3f position = null;

  private static float yaw = 0.0F;

  private static float pitch = 0.0F;

  public static void setCam(float x, float y, float z)
  {
    position = new Vector3f(x, y, z);
  }

  public static void yaw(float amount)
  {
    yaw += amount;
    if (yaw > 360.0F)
      yaw -= 360.0F;
    else if (yaw < -360.0F)
      yaw += 360.0F;
  }

  public static float getX()
  {
    return position.x;
  }
  public static float getY() {
    return position.y;
  }
  public static float getZ() {
    return position.z;
  }

  public static void pitch(float amount)
  {
    pitch += amount;
    if (pitch > 360.0F)
      pitch -= 360.0F;
    else if (pitch < -360.0F)
      pitch += 360.0F;
  }

  public static void moveUp(float distance) {
    position.y -= distance;
  }
  public static void moveDown(float distance) {
    position.y += distance;
  }

  public static void walkForward(float distance) {
    position.x -= distance * (float)Math.sin(Math.toRadians(yaw));
    position.z += distance * (float)Math.cos(Math.toRadians(yaw));
  }

  public static void walkBackwards(float distance)
  {
    position.x += distance * (float)Math.sin(Math.toRadians(yaw));
    position.z -= distance * (float)Math.cos(Math.toRadians(yaw));
  }

  public static void strafeLeft(float distance)
  {
    position.x -= distance * (float)Math.sin(Math.toRadians(yaw - 90.0F));
    position.z += distance * (float)Math.cos(Math.toRadians(yaw - 90.0F));
  }

  public static void strafeRight(float distance)
  {
    position.x -= distance * (float)Math.sin(Math.toRadians(yaw + 90.0F));
    position.z += distance * (float)Math.cos(Math.toRadians(yaw + 90.0F));
  }

  public static void lookThrough()
  {
    GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);

    GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);

    GL11.glTranslatef(position.x, position.y, position.z);
  }

  public static void setPos(Vector3f newpos) {
    position.setX(newpos.x);
    position.setY(newpos.y);
    position.setZ(newpos.z);
  }
  public static void setX(float x) {
    position.setX(x);
  }
  public static void setY(float y) {
    position.setY(y);
  }
  public static void setZ(float z) {
    position.setZ(z);
  }

  public static float getYaw() {
    return yaw;
  }

  public static void setYaw(float yawn)
  {
    yaw = yawn;
  }

  public static float getPitch() {
    return pitch;
  }
  public static void setPitch(float pitch) {
    FirstPersonCamera.pitch = pitch;
  }
}