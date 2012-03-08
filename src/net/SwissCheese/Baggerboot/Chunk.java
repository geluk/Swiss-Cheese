package net.SwissCheese.Baggerboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chunk {

    Main main = new Main();
    long seed = this.main.getSeed();
    int hMax = this.main.getHMax();
    int x;
    int z;
    File chunkData;
    private int[][][] blockData = new int[16][this.hMax][16];
    Random rand = new Random();
    Perlin octave0;
    Perlin octave1;
    Perlin octave2;
    Perlin octave3;
    Perlin octave4;

    public Chunk(int x, int z) {
        octave0 = new Perlin(seed, 64);
        octave1 = new Perlin((long) (10.0 * Math.sqrt(seed)), 32);
        octave2 = new Perlin((long) (this.seed / Math.sqrt(this.seed)), 16);
        octave3 = new Perlin(this.seed * this.seed, 8);
        octave4 = new Perlin((long) Math.pow(Math.sin(this.seed) + 3.0, this.seed / 2), 4);
        this.x = x;
        this.z = z;
        testExisting(x, z);
    }

    private void testExisting(int x, int z) {
        /*
         * this.chunkData = new File("I:\\chunks\\c_" + x + "_" + z); if
         * (!this.chunkData.exists()) generate(); else { load();
    }
         */

        generate();
    }

    private double checkOctave(int i, int k, Perlin octave) {
        return octave.getNoiseLevelAtPosition(i + this.x * 16, k + this.z * 16);
    }

    private void generate() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < this.hMax; j++) {
                for (int k = 0; k < 16; k++) {
                    if (5.0D * checkOctave(i, k, this.octave0) + 15.0D * checkOctave(i, k, this.octave1) + 7.0D * checkOctave(i, k, this.octave2) + 1.0D * checkOctave(i, k, this.octave3) + 1.0D * checkOctave(i, k, this.octave4) >= j) {
                        this.blockData[i][j][k] = 1;
                    }

                    if ((checkOctave(i, k, this.octave4) > 0.9D) && (10.0D * checkOctave(i, k, this.octave4) >= j)) {
                        this.blockData[i][j][k] = 1;
                    }
                }
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < this.hMax; j++) {
                for (int k = 0; k < 16; k++) {
                    if ((this.blockData[i][j][k] == 1) && (this.blockData[i][(j + 1)][k] == 0)) {
                        this.blockData[i][j][k] = 2;
                        if (j > 1) {
                            this.blockData[i][(j - 1)][k] = 3;
                            this.blockData[i][(j - 2)][k] = 3;
                        }

                    }

                }

            }

        }

        renderThis();
    }

    private void load() {
        System.out.printf("Loading chunk at x: %s; z: %s\n", new Object[]{Integer.valueOf(this.x), Integer.valueOf(this.z)});
        FileInputStream fis;
        try {
            fis = new FileInputStream("chunks/" + this.x + "_" + this.z + ".chunk");
        } catch (FileNotFoundException ex) {
            System.out.println("Warning - Could not load chunk: chunks/" + this.x + "_" + this.z);
            ex.printStackTrace();
            return;
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < this.hMax; j++) {
                for (int k = 0; k < 16; k++) {
                    try {
                        int v = fis.read();
                        this.blockData[i][j][k] = fis.read();
                    } catch (IOException ex) {
                        Logger.getLogger(Chunk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void save() {
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream("chunks/" + this.x + "_" + this.z + ".chunk");
//        } catch (FileNotFoundException ex) {
//            System.out.println("Warning - Could not save chunk: chunks/" + this.x + "_" + this.z);
//            ex.printStackTrace();
//            return;
//        }
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < this.hMax; j++) {
//                for (int k = 0; k < 16; k++) {
//                    try {
//                        fos.write(this.blockData[i][j][k]);
//                    } catch (IOException ex) {
//                        System.out.println("Warning - Failed to write data to the chunk file");
//                    }
//                }
//            }
//        }
    }

    private void renderThis() {
        Render3D.addChunkToRenderEngine(this.blockData, this.x * 16, this.z * 16);
    }

    private void genTree(int x, int y, int z) {
        if (y == this.hMax - 1) {
            return;
        }
        if (this.blockData[x][(y + 1)][z] != 0) {
            return;
        }
        if ((y > this.hMax - 6) || (y == 0) || (x >= 14) || (x <= 2) || (z >= 14) || (z <= 2)) {
            return;
        }
        if (this.blockData[x][(y - 1)][z] != 2) {
            return;
        }
        int tH = 4;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (this.blockData[(x + i - 2)][(y + j)][(z + k - 2)] == 4) {
                        return;
                    }
                }
            }
        }
        for (int j = 0; j <= tH; j++) {
            this.blockData[x][(y + j)][z] = 4;
        }
    }
}