package mummymaze.actions;

import agent.Action;
import mummymaze.MummyMazeState;
import mummymaze.Position;
import mummymaze.helpers.CalculateNewPosition;
import mummymaze.helpers.CanMove;
import mummymaze.helpers.ValidateHeroPosition;

public class HeroMoveDown extends Action<MummyMazeState> {
    public HeroMoveDown() {
        super(1);
    }

    @Override
    public void execute(MummyMazeState state) {
        Position heroPosition = state.getHeroPosition();
        Position heroNextPosition = CalculateNewPosition.down(heroPosition);
        state.moveElement(heroPosition, heroNextPosition);
        state.setHeroPosition(heroNextPosition);
        state.moveEnemies();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MummyMazeState state) {
        Position newPosition = CalculateNewPosition.down(state.getHeroPosition());
        return CanMove.down(state, state.getHeroPosition()) &&
                ValidateHeroPosition.isValid(state, newPosition);
    }
}
