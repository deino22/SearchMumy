package mummymaze.helpers;

import mummymaze.MummyMazeState;
import mummymaze.Position;

public class CalculateNextEnemyPosition {

    private static Position calculateByColumn(
            MummyMazeState state,
            Position heroPosition,
            Position currentPosition
    ) {
        if (!heroPosition.isOnSameColumn(currentPosition.getColumn())) {
            if (heroPosition.getColumn() < currentPosition.getColumn() &&
                    CanMove.left(state, currentPosition))
                return CalculateNewPosition.left(currentPosition);
            if (heroPosition.getColumn() > currentPosition.getColumn() &&
                    CanMove.right(state, currentPosition))
                return CalculateNewPosition.right(currentPosition);
        }
        return null;
    }

    private static Position calculateByLine(
            MummyMazeState state,
            Position heroPosition,
            Position currentPosition
    ) {
        if (!heroPosition.isOnSameLine(currentPosition.getLine())) {
            if (heroPosition.getLine() < currentPosition.getLine() &&
                    CanMove.up(state, currentPosition))
                return CalculateNewPosition.up(currentPosition);
            if (heroPosition.getLine() > currentPosition.getLine() &&
                    CanMove.down(state, currentPosition))
                return CalculateNewPosition.down(currentPosition);
        }
        return null;
    }

    public static Position columnFirst(
            MummyMazeState state,
            Position heroPosition,
            Position currentPosition
    ) {
        Position positionCalculated;
        positionCalculated = calculateByColumn(
                state,
                heroPosition,
                currentPosition
        );
        if (positionCalculated != null) return positionCalculated;
        positionCalculated = calculateByLine(
                state,
                heroPosition,
                currentPosition
        );
        return positionCalculated !=
                null ? positionCalculated : currentPosition;
    }

    public static Position lineFirst(
            MummyMazeState state,
            Position heroPosition,
            Position currentPosition
    ) {
        Position positionCalculated;
        positionCalculated = calculateByLine(
                state,
                heroPosition,
                currentPosition
        );
        if (positionCalculated != null) return positionCalculated;
        positionCalculated = calculateByColumn(
                state,
                heroPosition,
                currentPosition
        );
        return positionCalculated !=
                null ? positionCalculated : currentPosition;
    }
}
