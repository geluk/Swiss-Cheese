package net.SwissCheese.Baggerboot;


import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

class Render3D {

    private static boolean closeRequested = false;

    //The main method for all 3D rendering.
    static void run() {
        createWindow();
        System.out.println("Setting up OpenGL");
        initGL();

        //The main loop. As long as the game runs, this loop
        //will keep going, at a maximum of 60 times per second.
        while (!closeRequested) {
            input();
            render();
            Display.update();

        }
        cleanup();

    }
    //Create an LWJGL window. 
    private static void createWindow() {
        try {
            Display.setDisplayMode(new DisplayMode(1920, 1080));
            Display.setVSyncEnabled(true);
            Display.setTitle("Test");
            Display.create();
        } catch (LWJGLException e) {
            //If something goes wrong, alert the user.
            Sys.alert("Error", "Initialization failed!\n\n" + e.getMessage());
            System.exit(0);
        }
    }

    //Initialize openGL. These are just some default settings.
    //If you want to know what they do exactly, please find a tutorial about LWJGL and/or OpenGL
    private static void initGL() {
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();

        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        //GL11.glEnable(GL11.GL_TEXTURE_2D); //Required later
    }

    private static void input() {
        KeyInput.listen();
        if (Display.isCloseRequested()) {
            closeRequested = true;
        }
    }

    static void requestClose() {
        closeRequested = true;
    }

    private static void render() {
        
    }

    private static void cleanup() {
        Display.destroy();
    }
    private static void drawBlock(Block block){
        
    }
}
