package mummymaze.helpers;

import mummymaze.Constants;
import mummymaze.MummyMazeState;
import mummymaze.Position;

public class CanMove {
    public static boolean up(
            MummyMazeState state,
            Position currentPosition
    ) {
        Position newPosition = CalculateNewPosition.up(currentPosition);
        Position intermediatePosition =
                CalculateNewPosition.intermediateUp(currentPosition);
        return newPosition.getLine() > 0 &&
                !state.getObstaclesPositions().contains(intermediatePosition);
    }

    public static boolean down(
            MummyMazeState state,
            Position currentPosition
    ) {
        Position newPosition = CalculateNewPosition.down(currentPosition);
        Position intermediatePosition =
                CalculateNewPosition.intermediateDown(currentPosition);
        return newPosition.getLine() < Constants.SIZE &&
                !state.getObstaclesPositions().contains(intermediatePosition);
    }

    public static boolean left(
            MummyMazeState state,
            Position currentPosition
    ) {
        Position newPosition = CalculateNewPosition.left(currentPosition);
        Position intermediatePosition =
                CalculateNewPosition.intermediateLeft(currentPosition);
        return newPosition.getColumn() > 0 &&
                !state.getObstaclesPositions().contains(intermediatePosition);
    }

    public static boolean right(
            MummyMazeState state,
            Position currentPosition
    ) {
        Position newPosition = CalculateNewPosition.right(currentPosition);
        Position intermediatePosition =
                CalculateNewPosition.intermediateRight(currentPosition);
        return newPosition.getColumn() < Constants.SIZE &&
                !state.getObstaclesPositions().contains(intermediatePosition);
    }
}
