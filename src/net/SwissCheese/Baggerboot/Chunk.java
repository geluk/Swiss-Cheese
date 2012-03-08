package net.SwissCheese.Baggerboot;

public class Chunk {

    Perlin octave0, octave1, octave2, octave3;
    private Block[][][] blockData = new Block[Main.chunkDims][Main.chunkHeight][Main.chunkDims];

    public void init() {
        if (testExisting()) {
            load();
        } else {
            generate();
        }
    }

    private boolean testExisting() {
        return false;
    }

    private void load() {
    }
    
    public void update(int x, int y, int z){
        
    }

    private void generate() {
        octave0 = new Perlin(Main.getSeed(), 64);
        octave1 = new Perlin(Main.getSeed(), 32);
        octave2 = new Perlin(Main.getSeed(), 16);
        octave3 = new Perlin(Main.getSeed(), 8);

        for (int i = 0; i < Main.chunkDims; i++) {
            for (int j = 0; j < Main.chunkHeight; j++) {
                for (int k = 0; k < Main.chunkDims; k++) {
                    if(j <= 
                            2*octave0.getNoiseLevelAtPosition(i, k) 
                            + 4*octave1.getNoiseLevelAtPosition(i, k) 
                            + 8*octave2.getNoiseLevelAtPosition(i, k) 
                            + 16*octave3.getNoiseLevelAtPosition(i, k)){
                        blockData[i][j][k] = new Block(1);
                        
                    }
                }
            }
        }
    }
    Block getBlock(int x, int y, int z){
        return blockData[x][y][z];
    }
}
