package mummymaze;

import agent.Heuristic;

public class HeuristicNumberOfEnemies extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state) {
        return state.computeNumberOfEnemies();
    }

    @Override
    public String toString() {
        return "Number of Enemies";
    }
}