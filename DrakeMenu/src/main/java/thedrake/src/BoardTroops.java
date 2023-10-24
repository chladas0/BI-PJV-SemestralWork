package thedrake.src;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable{

    private final PlayingSide playingSide;
    private final Map<BoardPos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private final int guards;

    public BoardTroops(PlayingSide playingSide)
    {
        this.leaderPosition = TilePos.OFF_BOARD;
        this.playingSide = playingSide;
        this.troopMap = Collections.emptyMap();
        this.guards = 0;
    }

    public BoardTroops(
            PlayingSide playingSide,
            Map<BoardPos, TroopTile> troopMap,
            TilePos leaderPosition,
            int guards)
    {
        this.playingSide = playingSide;
        this.troopMap = troopMap;
        this.leaderPosition = leaderPosition;
        this.guards = guards;
    }

    public Optional<TroopTile> at(TilePos pos)
    {
        if(troopMap.containsKey(pos))
        {
            return Optional.ofNullable(troopMap.get(pos));
        }
        return Optional.empty();
    }

    public PlayingSide playingSide()
    {
        return playingSide;
    }

    public TilePos leaderPosition()
    {
        return leaderPosition;
    }

    public int guards()
    {
        return guards;
    }

    public boolean isLeaderPlaced()
    {
        return leaderPosition != TilePos.OFF_BOARD;
    }

    public boolean isPlacingGuards()
    {
        return isLeaderPlaced() && guards < 2;
    }

    public Set<BoardPos> troopPositions()
    {
        Set<BoardPos> result = new HashSet<BoardPos>();

        for(Map.Entry<BoardPos, TroopTile> entry: this.troopMap.entrySet())
        {
            if(entry.getValue().hasTroop())
                result.add(entry.getKey());
        }

        return result;
    }

    public BoardTroops placeTroop(Troop troop, BoardPos target)
    {
        if(troopMap.containsKey(target))
            throw new IllegalArgumentException("Can not place Troop, tile is occupied");

        Map<BoardPos, TroopTile> resMap = new HashMap<>(troopMap);
        resMap.put(target, new TroopTile(troop, playingSide, TroopFace.AVERS));

        if(!isLeaderPlaced())
            return new BoardTroops(playingSide, resMap, target, guards);
        else if (isPlacingGuards())
            return new BoardTroops(playingSide, resMap, leaderPosition, guards + 1);
        else
            return new BoardTroops(playingSide, resMap, leaderPosition, guards);
    }

    public BoardTroops troopStep(BoardPos origin, BoardPos target)
    {
        if (!isLeaderPlaced() || isPlacingGuards())
            throw new IllegalStateException("Wrong operation in this state of game");

        if (!at(origin).isPresent() || at(target).isPresent())
            throw new IllegalArgumentException("Origin is empty or target is occupied");

        Map<BoardPos, TroopTile> resMap = new HashMap<>(troopMap);
        BoardTroops resBoard;

        if(leaderPosition.equals(origin))
            resBoard = new BoardTroops(playingSide, resMap, TilePos.OFF_BOARD, guards);
        else
            resBoard = new BoardTroops(playingSide, resMap, leaderPosition, guards);

        resBoard = resBoard.placeTroop(resBoard.troopMap.get(origin).troop(), target);

        if( resBoard.troopMap.get(target).face() == resBoard.troopMap.get(origin).face())
            resBoard = resBoard.troopFlip(target);

        resBoard = resBoard.removeTroop(origin);

        return resBoard;
    }

    public BoardTroops troopFlip(BoardPos origin) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if (!at(origin).isPresent())
            throw new IllegalArgumentException();

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops removeTroop(BoardPos target)
    {
        if(isPlacingGuards() || !isLeaderPlaced())
            throw new IllegalStateException ("Can not remove trip in current state of game");

        if(!at(target).isPresent())
            throw new IllegalArgumentException("The target is empty, can not remove troop");

        Map<BoardPos, TroopTile> resMap = new HashMap<>(troopMap);

        if(leaderPosition.equals(target))
        {
            resMap.remove(target);
            return new BoardTroops(playingSide, resMap, TilePos.OFF_BOARD, guards);
        }
        else
        {
            resMap.remove(target);
            return new BoardTroops(playingSide, resMap, leaderPosition, guards);
        }
    }

    @Override
    public void toJSON(PrintWriter writer)
    {
        writer.print("{\"side\":");
            playingSide.toJSON(writer);
        writer.print(",\"leaderPosition\":");
            leaderPosition.toJSON(writer);
        writer.print(",\"guards\":" + guards);
            writer.print(",\"troopMap\":{");

        SortedSet <BoardPos> sortedPositions = new TreeSet<>(Comparator.comparing(BoardPos::toString));
        sortedPositions.addAll(troopMap.keySet());

        int position = 0;
        for(BoardPos pos: sortedPositions)
        {
            pos.toJSON(writer);
            writer.print(':');
            troopMap.get(pos).toJSON(writer);

            if(position != sortedPositions.size() - 1)
                writer.print(",");

            position += 1;
        }
        writer.print("}}");
    }
}
