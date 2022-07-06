package mummymaze.helpers;

import mummymaze.Constants;
import mummymaze.MummyMazeState;
import mummymaze.Position;
import mummymaze.elements.RedMummy;
import mummymaze.elements.Scorpion;
import mummymaze.elements.WhiteMummy;

public class EnemiesFight {
    public static boolean checkIfPositionHasEnemy(
            MummyMazeState state,
            Position newPosition
    ) {
        return state.getWhiteMummies().contains(new WhiteMummy(newPosition))
                || state.getRedMummies().contains(new RedMummy(newPosition))
                || state.getScorpions().contains(new Scorpion(newPosition));
    }

    private static void removeEnemy(MummyMazeState state, Position position) {
        Character positionChar = state.getChar(position);
        switch (positionChar) {
            case Constants.whiteMummy:
                state.getWhiteMummies().remove(new WhiteMummy(position));
                break;
            case Constants.redMummy:
                state.getRedMummies().remove(new RedMummy(position));
                break;
            case Constants.scorpion:
                state.getScorpions().remove(new Scorpion(position));
                break;
            default:
                throw new IllegalStateException(
                        "Unexpected value: " + positionChar);
        }
    }

    // this function returns boolean (enemy that's moving dies or not)
    public static boolean updateStateAndCheckIsKilled(
            MummyMazeState state,
            Position oldPosition,
            Position newPosition,
            Class enemyClass
    ) {
        if (WhiteMummy.class.equals(enemyClass)) {
            if (state.getChar(newPosition) == Constants.whiteMummy) {
                removeEnemy(state, oldPosition);
                return true;
            }
            removeEnemy(state, newPosition);
        } else if (RedMummy.class.equals(enemyClass)) {
            if (state.getChar(newPosition) == Constants.whiteMummy ||
                    state.getChar(newPosition) == Constants.redMummy) {
                removeEnemy(state, oldPosition);
                return true;
            }
            removeEnemy(state, newPosition);
        } else if (Scorpion.class.equals(enemyClass)) {
            removeEnemy(state, oldPosition);
            return true;
            /*

            Character newPositionChar = state.getChar(newPosition);
            switch (newPositionChar) {
                case Constants.whiteMummy:
                case Constants.redMummy:
                    removeEnemy(state, oldPosition);
                    return true;

                case Constants.scorpion:
                    removeEnemy(state, newPosition);
                    break;

                default:
                    throw new IllegalStateException(
                            "Unexpected value: " + newPositionChar);
            }*/
        }
        return false;
    }
}
