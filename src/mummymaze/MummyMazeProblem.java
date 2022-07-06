package mummymaze;

import java.util.LinkedList;
import java.util.List;

import agent.Action;
import agent.Problem;
import mummymaze.actions.HeroMoveDown;
import mummymaze.actions.HeroMoveLeft;
import mummymaze.actions.HeroMoveRight;
import mummymaze.actions.HeroMoveUp;
import mummymaze.actions.HeroNoMove;

public class MummyMazeProblem extends Problem<MummyMazeState> {

    protected List<Action> actions;

    public MummyMazeProblem(MummyMazeState initialState) {
        super(initialState);
        actions = new LinkedList<Action>() {{
            add(new HeroNoMove());
            add(new HeroMoveDown());
            add(new HeroMoveUp());
            add(new HeroMoveRight());
            add(new HeroMoveLeft());
        }};
    }

    @Override
    public List<Action<MummyMazeState>> getActions(MummyMazeState state) {
        List<Action<MummyMazeState>> possibleActions = new LinkedList<>();

        for (Action action : actions) {
            if (action.isValid(state)) {
                possibleActions.add(action);
            }
        }
        return possibleActions;
    }

    @Override
    public MummyMazeState getSuccessor(MummyMazeState state, Action action) {
        MummyMazeState successor = state.clone();
        action.execute(successor);
        return successor;
    }

    @Override
    public boolean isGoal(MummyMazeState state) {
        return state.isGoal();
    }


    @Override
    public double computePathCost(List<Action> path) {
        return path.size();
    }

}
