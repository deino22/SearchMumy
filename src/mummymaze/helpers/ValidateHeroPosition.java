package mummymaze.helpers;

import mummymaze.MummyMazeState;
import mummymaze.Position;
import mummymaze.elements.RedMummy;
import mummymaze.elements.Scorpion;
import mummymaze.elements.Trap;
import mummymaze.elements.WhiteMummy;

public class ValidateHeroPosition {
    public static boolean isValid(
            MummyMazeState state, Position newHeroPosition
    ) {
        for (Trap trap : state.getTraps()) {
            if (newHeroPosition.equals(trap.getPosition())) return false;
        }
        // TODO CHANGE FOR A FOR WITH MOVES SIZE
        for (WhiteMummy whiteMummy : state.getWhiteMummies()) {
            Position whiteMummyPosition = whiteMummy.getPosition();
            Position nextPosition = CalculateNextEnemyPosition.columnFirst(state,
                    newHeroPosition,
                    whiteMummyPosition
            );
            if (!nextPosition.equals(newHeroPosition)) {
                if (CalculateNextEnemyPosition.columnFirst(state,
                                newHeroPosition,
                                nextPosition
                        )
                        .equals(newHeroPosition)) return false;
            } else {
                return false;
            }
        }
        for (RedMummy redMummy : state.getRedMummies()) {
            Position redMummyPosition = redMummy.getPosition();
            Position nextPosition = CalculateNextEnemyPosition.lineFirst(state,
                    newHeroPosition,
                    redMummyPosition
            );
            if (!nextPosition.equals(newHeroPosition)) {
                if (CalculateNextEnemyPosition.lineFirst(state,
                                newHeroPosition,
                                nextPosition
                        )
                        .equals(newHeroPosition)) return false;
            } else {
                return false;
            }
        }
        for (Scorpion scorpion : state.getScorpions()) {
            Position nextPosition = scorpion.getNextPosition(state,
                    newHeroPosition
            );
            if (nextPosition.equals(newHeroPosition)) return false;
        }
        return true;
    }
}
