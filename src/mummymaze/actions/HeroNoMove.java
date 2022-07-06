package mummymaze.actions;

import agent.Action;
import mummymaze.MummyMazeState;
import mummymaze.helpers.ValidateHeroPosition;

public class HeroNoMove extends Action<MummyMazeState> {
    public HeroNoMove() {
        super(1);
    }

    @Override
    public void execute(MummyMazeState state) {
        state.moveEnemies();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MummyMazeState state) {
        return ValidateHeroPosition.isValid(state, state.getHeroPosition());
    }
}
