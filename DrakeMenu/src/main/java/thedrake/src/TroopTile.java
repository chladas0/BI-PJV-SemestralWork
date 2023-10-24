package thedrake.src;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TroopTile implements Tile, JSONSerializable
{
    public TroopTile(Troop troop, PlayingSide side, TroopFace face)
    {
        this.troop = troop;
        this.side = side;
        this.face = face;
    }
    public boolean canStepOn()
    {
        return false;
    }
    public boolean hasTroop()
    {
        return true;
    }

    @Override
    public List<Move> movesFrom(BoardPos pos, GameState state)
    {
        List<Move> res = new ArrayList<>();
        for( TroopAction x : troop.actions(face))
            res.addAll(x.movesFrom(pos, side, state));

        return res;
    }

    public TroopTile flipped()
    {
        if (face == TroopFace.AVERS)
            return new TroopTile(troop, side, TroopFace.REVERS);
        else
            return new TroopTile(troop, side, TroopFace.AVERS);
    }
    public PlayingSide side(){return side;}

    public TroopFace face(){return face;}

    public Troop troop(){return troop;}
    private PlayingSide side;
    private TroopFace face;
    private Troop troop;


    @Override
    public void toJSON(PrintWriter writer)
    {
        writer.print("{\"troop\":\"" + troop.name() + "\",");
        writer.print("\"side\":\""   + side         + "\",");
        writer.print("\"face\":\""   + face         + "\"}");
    }
}
