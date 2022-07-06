package mummymaze;

import agent.Heuristic;

public class HeuristicDistanceToGoal extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state) {
        return state.computeDistanceToGoal();
    }

    @Override
    public String toString() {
        return "Distance to Goal";
    }
}