package mummymaze;

import agent.Heuristic;

public class HeuristicDistanceToGoalAndEnemiesAndNumberOfEnemies extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state) {
        return state.computeNumberOfEnemies() + state.computeDistanceToGoal() +
                state.computeDistanceToEnemies();
    }

    @Override
    public String toString() {
        return "Distance to Goal And Enemies and Number of Enemies";
    }
}