package net.SwissCheese.Baggerboot;

import java.util.Scanner;

public class Main {

    static final int major = 1;
    static final int minor = 0;
    static final int debug = 0;
    static final String version = "Alpha " + major + "." + minor + "." + debug;
    static final int chunkDims = 16;
    static final int chunkHeight = 32;
    private static long seed;
    private static int vDistance;

    private static void setLibrary(){
        System.out.print("Auto-detected OS: ");
        switch(OSValidator.getOS()) {
            case 0:
                System.out.print("Windows\n");
                System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "\\native\\windows");
                break;
            case 1:
                System.out.print("Mac OS\n");
                System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/wmacosx");
                break;
            case 2:
                System.out.print("GNU/Linux");
                System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/linux");
                break;
            case 3:
                System.out.print("Solaris");
                System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/solaris");
                break;
            default:
                System.out.print("None found, defaulting to Windows. If this is not your OS, please contact Baggerboot.");
                System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "\\native\\windows");
        }
    }

    public static void main(String[] args) {
        System.out.println(toFloat(-1));
        System.out.println("==============================================");
        System.out.println("\tSwiss Cheese " + version);
        System.out.println("==============================================");
        setLibrary();
        testInputVariables();
        loadChunks();
        Render3D.run();
    }

    private static void testInputVariables() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the desired render distance:");
        vDistance = input.nextInt();
        System.out.println("Please input the world seed.");
        seed = input.nextLong();
    }
    static long getSeed(){
        return seed;
    }
    static int getVDistance(){
        return vDistance;
    }
    private static void loadChunks() {
        for(int i = 0; i< vDistance; i++){
            for(int j = 0; j< vDistance; j++){
                World.addChunk(new Chunk(), i, j);
            }
        }
    }
    public static float toFloat(int rgb){
        if(rgb>255){
            return 1.0f;
        }else if(rgb<=0){
            return 0.0f;
        }
        return ((float)rgb/255);
    }
}
