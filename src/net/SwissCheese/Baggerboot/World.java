package net.SwissCheese.Baggerboot;
public class World {
    private static Chunk chunks[][] = new Chunk[Main.getVDistance()*2][Main.getVDistance()*2];
    public static void addChunk(Chunk chunk, int x, int z){
        chunks[x][z] = chunk;
        chunks[x][z].init();
    }
}
