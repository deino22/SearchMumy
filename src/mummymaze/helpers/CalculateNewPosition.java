package mummymaze.helpers;

import mummymaze.Constants;
import mummymaze.Position;

public class CalculateNewPosition {
    public static Position down(Position currentPosition) {
        return new Position(
                currentPosition.getLine() + Constants.STEP_SIZE,
                currentPosition.getColumn()
        );
    }
    public static Position intermediateDown(Position currentPosition) {
        return new Position(
                currentPosition.getLine() + Constants.STEP_INTERMEDIATE_SIZE,
                currentPosition.getColumn()
        );
    }

    public static Position left(Position currentPosition) {
        return new Position(
                currentPosition.getLine(),
                currentPosition.getColumn() - Constants.STEP_SIZE
        );
    }

    public static Position intermediateLeft(Position currentPosition) {
        return new Position(
                currentPosition.getLine(),
                currentPosition.getColumn() - Constants.STEP_INTERMEDIATE_SIZE
        );
    }

    public static Position right(Position currentPosition) {
        return new Position(
                currentPosition.getLine(),
                currentPosition.getColumn() + Constants.STEP_SIZE
        );
    }

    public static Position intermediateRight(Position currentPosition) {
        return new Position(
                currentPosition.getLine(),
                currentPosition.getColumn() + Constants.STEP_INTERMEDIATE_SIZE
        );
    }

    public static Position up(Position currentPosition) {
        return new Position(
                currentPosition.getLine() - Constants.STEP_SIZE,
                currentPosition.getColumn()
        );
    }

    public static Position intermediateUp(Position currentPosition) {
        return new Position(
                currentPosition.getLine() - Constants.STEP_INTERMEDIATE_SIZE,
                currentPosition.getColumn()
        );
    }
}
