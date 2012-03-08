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
        System.out.println("==============================================");
        System.out.println("\tSwiss Cheese " + version);
        System.out.println("==============================================");
        setLibrary();
        testInputVariables();
        Render3D.run();
    }

    private static void testInputVariables() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the desired render distance:");
        int var;
        var  = input.nextInt();
        seed = var;
    }
    static long getSeed(){
        return seed;
    }
}
