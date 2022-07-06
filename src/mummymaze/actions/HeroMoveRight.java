package mummymaze.actions;

import agent.Action;
import mummymaze.MummyMazeState;
import mummymaze.Position;
import mummymaze.helpers.CalculateNewPosition;
import mummymaze.helpers.CanMove;
import mummymaze.helpers.ValidateHeroPosition;

public class HeroMoveRight extends Action<MummyMazeState> {
    public HeroMoveRight() {
        super(1);
    }

    @Override
    public void execute(MummyMazeState state) {
        Position heroPosition = state.getHeroPosition();
        Position nextPosition = CalculateNewPosition.right(heroPosition);
        state.moveElement(heroPosition, nextPosition);
        state.setHeroPosition(nextPosition);
        state.moveEnemies();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MummyMazeState state) {
        Position newPosition = CalculateNewPosition.right(state.getHeroPosition());
        return CanMove.right(state, state.getHeroPosition()) &&
                ValidateHeroPosition.isValid(state, newPosition);
    }
}
