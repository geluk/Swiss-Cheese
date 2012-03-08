package net.SwissCheese.Baggerboot;

public class OSValidator {
 
        public static int getOS() {
                if (isWindows()) {
                        return 0;     
                } else if (isUnix()) {
                        return 2;
                } else if (isMac()) {
                        return 1;
                } else if (isSolaris()) {
                        return 3;
                } else {
                        return -1;
                }
        }
 
        public static boolean isWindows() {
 
                String os = System.getProperty("os.name").toLowerCase();
                // Windows
                return (os.indexOf("win") >= 0);
 
        }
 
        public static boolean isMac() {
 
                String os = System.getProperty("os.name").toLowerCase();
                // Mac
                return (os.indexOf("mac") >= 0);
 
        }
 
        public static boolean isUnix() {
 
                String os = System.getProperty("os.name").toLowerCase();
                // Linux or Unix
                return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
 
        }
 
        public static boolean isSolaris() {
 
                String os = System.getProperty("os.name").toLowerCase();
                // Solaris
                return (os.indexOf("sunos") >= 0);
 
        }
 
}