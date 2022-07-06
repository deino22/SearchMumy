package mummymaze.elements;

import mummymaze.MummyMazeState;
import mummymaze.Position;
import mummymaze.helpers.CalculateNextEnemyPosition;

public class RedMummy extends Base {
    public RedMummy(Position position) {
        super(position);
    }

    public Position getNextPosition(
            MummyMazeState state,
            Position heroPosition
    ) {
        return CalculateNextEnemyPosition.lineFirst(
                state,
                heroPosition,
                this.position
        );
    }

}
