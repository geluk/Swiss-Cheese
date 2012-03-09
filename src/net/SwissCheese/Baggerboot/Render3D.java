package net.SwissCheese.Baggerboot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Render3D {

    private static boolean fullscreen = false;
    private static long lastFrame = 0;
    public static boolean closeRequested = false;
    private static Texture t_blocks;
    private static final Main main = new Main();
    private static KeyInput keyInput = new KeyInput();
    public static float dx = 0.0f;
    public static float dy = 0.0f;
    public static float dt = 0.0f;
    private static float lastTime = 0.0f;
    private static float time = 0.0f;
    private static float mouseSensitivity = main.getMouseSensitivity();
    private static boolean falling;
    private static final int vDistance = main.getVDistance();
    private static final int hMax = main.getHMax();
    public static boolean top;
    public static boolean bottom;
    public static boolean front;
    public static boolean back;
    public static boolean left;
    public static boolean right;
    private static int[][][] worldData = new int[16 * vDistance + 1][hMax + 1][16 * vDistance + 1];
    public static Chunk[][] chunkData = new Chunk[vDistance][vDistance];
    static float speed = 0.0F;
    private static long lastFPS;
    private static int fps;

    static void toggleFalling() {
        if (falling) {
            falling = false;
            System.out.println("You are not falling.");
            speed = 0.0F;
            return;
        }
        falling = true;
        System.out.println("You are falling.");
    }

    private static void loadTextures() {
        System.out.println("Loading textures...");
        boolean failed = false;
        try {
            t_blocks = TextureLoader.getTexture("PNG", new FileInputStream("tex/blocks.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("An error has occured. The generated wall of text may be helpful to the developers.");
            System.out.println("If this is not a known issue, please consider sending it. Thank you.");
            failed = true;
        }

        if (!failed) {
            System.out.println("All textures loaded successfully");
        }
    }

    public boolean checkBlock(int x, int y, int z) {
        return false;
    }

    public static void run() {
        lastFPS = getTime();
        createWindow();
        initGL();
        loadTextures();

        Mouse.setGrabbed(true);
        FirstPersonCamera.setCam(-10.0f, -14.0f, -10.0f);
        FirstPersonCamera.setYaw(-45.0f);
        FirstPersonCamera.setPitch(-315.0f);

        while (!closeRequested) {
            if (falling) {
                FirstPersonCamera.setY(FirstPersonCamera.getY() + speed);

                speed += 0.001F;
            }
            input();
            renderChunks();
            updateFPS();

            Display.update();
            time = (float) getTime();
            dt = (time - lastTime) / 1000.0F;
            lastTime = time;

            dx = Mouse.getDX();
            dy = -1.0F * Mouse.getDY();

            FirstPersonCamera.pitch(dy * mouseSensitivity);
            FirstPersonCamera.yaw(dx * mouseSensitivity);
        }
        cleanup();
    }

    public static void addChunkToRenderEngine(int[][][] adding, int x, int z) {
        int counter = 0;
        int vI = 0;
        int vJ = 0;
        int vK = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < hMax; j++) {
                System.arraycopy(adding[i][j], 0, worldData[(i + x)][j], z, 16);
            }
        }
    }

    public static long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    public static int getDelta() {
        long _time = getTime();
        int delta = (int) (_time - lastFrame);
        lastFrame = _time;

        return delta;
    }

    public static void updateFPS() {
        if (getTime() - lastFPS > 1000L) {
            Display.setTitle("Swiss Cheese - FPS: " + fps);
            fps = 0;
            lastFPS += 1000L;
        }
        fps += 1;
    }

    private static void createWindow() {
        try {
            Display.setDisplayMode(new DisplayMode(1920, 1080));
            Display.setVSyncEnabled(true);
            Display.setTitle("LWJGL 3D application test 3");
            Display.create();
        } catch (LWJGLException e) {
            Sys.alert("Error", "Initialization failed!\n\n" + e.getMessage());
            System.exit(0);
        }
    }

    private static void initGL() {
        System.out.println("Setting up OpenGL...");
        
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();

        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f, (float)width / (float)height, 0.1f, 100.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, main.toFloat(226), main.toFloat(255), 0.0f);
        GL11.glClearDepth(1.0f);
        
        //GL11.glEnable(2929);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        //GL11.glDepthFunc(515);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glOrtho(0, width, height, 0, 1, -1);

        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    public static void drawCube(float x, float y, float z, float[] ftexX, float[] ftexY) {
        GL11.glLoadIdentity();
        FirstPersonCamera.lookThrough();
        
        GL11.glTranslatef(x / 2.0F, y / 2.0F, z / 2.0F);
        
//        GL11.glTexParameteri(3553, 10240, 9728);
//        GL11.glTexParameteri(3553, 10242, 10496);
//        GL11.glTexParameteri(3553, 10243, 10496);
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

        for (int i = 0; i < 6; i++) {
            ftexX[i] /= 4;
        }
        for (int i = 0; i < 6; i++) {
            ftexY[i] /= 4;
        }

        float delta = 0.25f;

        if (front) {
            GL11.glBegin(7);
            t_blocks.bind();

            GL11.glTexCoord2f(ftexX[2], ftexY[2]);
            GL11.glVertex3f(0.5F, 0.5F, 0.5F);

            GL11.glTexCoord2f(ftexX[2] + delta, ftexY[2]);
            GL11.glVertex3f(0.0F, 0.5F, 0.5F);

            GL11.glTexCoord2f(ftexX[2] + delta, ftexY[2] + delta);
            GL11.glVertex3f(0.0F, 0.0F, 0.5F);

            GL11.glTexCoord2f(ftexX[2], ftexY[2] + delta);
            GL11.glVertex3f(0.5F, 0.0F, 0.5F);
            t_blocks.release();
            GL11.glEnd();
        }

        if (top) {
            GL11.glBegin(7);
            t_blocks.bind();
            GL11.glTexCoord2f(ftexX[0], ftexY[0]);
            GL11.glVertex3f(0.5F, 0.5F, 0.0F);

            GL11.glTexCoord2f(ftexX[0] + delta, ftexY[0]);
            GL11.glVertex3f(0.0F, 0.5F, 0.0F);

            GL11.glTexCoord2f(ftexX[0] + delta, ftexY[0] + delta);
            GL11.glVertex3f(0.0F, 0.5F, 0.5F);

            GL11.glTexCoord2f(ftexX[0], ftexY[0] + delta);
            GL11.glVertex3f(0.5F, 0.5F, 0.5F);
            t_blocks.release();
            GL11.glEnd();
        }

        if (bottom) {
            GL11.glBegin(7);
            t_blocks.bind();
            GL11.glTexCoord2f(ftexX[1], ftexY[1]);
            GL11.glVertex3f(0.5F, 0.0F, 0.5F);

            GL11.glTexCoord2f(ftexX[1] + delta, ftexY[1]);
            GL11.glVertex3f(0.0F, 0.0F, 0.5F);

            GL11.glTexCoord2f(ftexX[1] + delta, ftexY[1] + delta);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);

            GL11.glTexCoord2f(ftexX[1], ftexY[1] + delta);
            GL11.glVertex3f(0.5F, 0.0F, 0.0F);
            t_blocks.release();
            GL11.glEnd();
        }

        if (back) {
            GL11.glBegin(7);
            t_blocks.bind();
            GL11.glTexCoord2f(ftexX[3], ftexY[3]);
            GL11.glVertex3f(0.5F, 0.0F, 0.0F);

            GL11.glTexCoord2f(ftexX[3] + delta, ftexY[3]);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);

            GL11.glTexCoord2f(ftexX[3] + delta, ftexY[3] + delta);
            GL11.glVertex3f(0.0F, 0.5F, 0.0F);

            GL11.glTexCoord2f(ftexX[3], ftexY[3] + delta);
            GL11.glVertex3f(0.5F, 0.5F, 0.0F);
            t_blocks.release();
            GL11.glEnd();
        }
        if (left) {
            GL11.glBegin(7);
            t_blocks.bind();
            GL11.glTexCoord2f(ftexX[4], ftexY[4]);
            GL11.glVertex3f(0.0F, 0.5F, 0.5F);

            GL11.glTexCoord2f(ftexX[4] + delta, ftexY[4]);
            GL11.glVertex3f(0.0F, 0.5F, 0.0F);

            GL11.glTexCoord2f(ftexX[4] + delta, ftexY[4] + delta);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);

            GL11.glTexCoord2f(ftexX[4], ftexY[4] + delta);
            GL11.glVertex3f(0.0F, 0.0F, 0.5F);
            t_blocks.release();
            GL11.glEnd();
        }
        if (right) {
            GL11.glBegin(7);
            t_blocks.bind();
            GL11.glTexCoord2f(ftexX[5], ftexY[5]);
            GL11.glVertex3f(0.5F, 0.5F, 0.0F);

            GL11.glTexCoord2f(ftexX[5] + delta, ftexY[5]);
            GL11.glVertex3f(0.5F, 0.5F, 0.5F);

            GL11.glTexCoord2f(ftexX[5] + delta, ftexY[5] + delta);
            GL11.glVertex3f(0.5F, 0.0F, 0.5F);

            GL11.glTexCoord2f(ftexX[5], ftexY[5] + delta);
            GL11.glVertex3f(0.5F, 0.0F, 0.0F);
            t_blocks.release();
            GL11.glEnd();
        }
    }

    public static void renderChunks() {
        GL11.glClear(16640);

        for (int i = 0; i < 16 * vDistance; i++) {
            for (int j = 0; j < hMax; j++) {
                for (int k = 0; k < 16 * vDistance; k++) {
                    if (worldData[i][j][k] != 0) {
                        top = true;
                        bottom = true;
                        front = true;
                        back = true;
                        left = true;
                        right = true;
                        if (j == 0) {
                            bottom = false;
                        }
                        if ((i != 15)
                                && (worldData[(i + 1)][j][k] != 0)) {
                            right = false;
                        }

                        if ((i != 0)
                                && (worldData[(i - 1)][j][k] != 0)) {
                            left = false;
                        }

                        if ((j != hMax - 1)
                                && (worldData[i][(j + 1)][k] != 0)) {
                            top = false;
                        }

                        if ((bottom) && (j != 0)
                                && (worldData[i][(j - 1)][k] != 0)) {
                            bottom = false;
                        }

                        if ((k != 15)
                                && (worldData[i][j][(k + 1)] != 0)) {
                            front = false;
                        }

                        if ((k != 0)
                                && (worldData[i][j][(k - 1)] != 0)) {
                            back = false;
                        }

                        if (worldData[i][j][k] == 1) {
                            float[] var0 = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
                            float[] var1 = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
                            drawCube(i, j, k, var0, var1);
                        } else if (worldData[i][j][k] == 2) {
                            float[] var0 = {1.0F, 1.0F, 1.0F, 2.0F, 1.0F, 1.0F};
                            float[] var1 = {0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F};
                            drawCube(i, j, k, var0, var1);
                        } else if (worldData[i][j][k] == 3) {
                            float[] var0 = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
                            float[] var1 = {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
                            drawCube(i, j, k, var0, var1);
                        }
                    }
                }
            }
        }
    }

    public static void input() {
        keyInput.listen();
        if (Display.isCloseRequested()) {
            closeRequested = true;
        }
    }

    private static void cleanup() {
        Display.destroy();
    }

    public static void toggleFullScreen() {
        try {
            if (!fullscreen) {
                fullscreen = true;
                Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
            } else {
                fullscreen = false;
                Display.setDisplayMode(new DisplayMode(1920, 1080));
            }
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
    }
}