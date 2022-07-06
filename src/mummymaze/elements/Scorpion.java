package mummymaze.elements;

import mummymaze.MummyMazeState;
import mummymaze.Position;
import mummymaze.helpers.CalculateNextEnemyPosition;

public class Scorpion extends Base {
    public Scorpion(Position position) {
        super(position);
    }

    public Position getNextPosition(
            MummyMazeState state,
            Position heroPosition
    ) {
        return CalculateNextEnemyPosition.columnFirst(
                state,
                heroPosition,
                this.position
        );
    }

}
