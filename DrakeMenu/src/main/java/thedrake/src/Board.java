package thedrake.src;

import java.io.PrintWriter;

public class Board implements JSONSerializable{

    private final BoardTile[][] tiles;
    private final int dimension;
    // Konstruktor. Vytvoří čtvercovou hrací desku zadaného rozměru, kde všechny dlaždice jsou prázdné, tedy BoardTile.EMPTY
    public Board(int dimension)
    {
        this.tiles = new BoardTile[dimension][dimension];

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                tiles[i][j] = BoardTile.EMPTY;

        this.dimension = dimension;
    }

    // Rozměr hrací desky
    public int dimension() { return this.dimension;}

    // Vrací dlaždici na zvolené pozici.
    public BoardTile at(TilePos pos)
    {
        return tiles[pos.i()][pos.j()];
    }

    // Vytváří novou hrací desku s novými dlaždicemi. Všechny ostatní dlaždice zůstávají stejné
    public Board withTiles(TileAt... ats)
    {
        Board newBoard = new Board(dimension);

        for(int i = 0; i < dimension; i++)
            newBoard.tiles[i] = this.tiles[i].clone();

        for(TileAt at: ats)
        {
            if(at.tile == BoardTile.EMPTY)
                newBoard.tiles[at.pos.i()][at.pos.j()] = BoardTile.EMPTY;
            else
                newBoard.tiles[at.pos.i()][at.pos.j()] = BoardTile.MOUNTAIN;
        }

        return newBoard;
    }

    // Vytvoří instanci PositionFactory pro výrobu pozic na tomto hracím plánu
    public PositionFactory positionFactory()
    {
        return new PositionFactory(this.dimension);
    }

    @Override
    public void toJSON(PrintWriter writer)
    {
        // print dimension
        writer.print("{\"dimension\":" + dimension + ",\"tiles\":[");

        for (int i = 0; i < dimension; i ++)
            for(int j = 0; j < dimension; j++)
            {
                tiles[j][i].toJSON(writer);
                if(i * dimension + j < dimension * dimension -1)
                    writer.print(",");
            }
        writer.print("]}");
    }

    public static class TileAt {
        public final BoardPos pos;
        public final BoardTile tile;

        public TileAt(BoardPos pos, BoardTile tile) {
            this.pos = pos;
            this.tile = tile;
        }
    }
}


