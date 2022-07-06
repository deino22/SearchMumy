package mummymaze;

import java.io.File;
import java.io.IOException;

import agent.Agent;

public class MummyMazeAgent extends Agent<MummyMazeState> {
    protected MummyMazeState initialEnvironment;

    public MummyMazeAgent(MummyMazeState environment) {
        super(environment);
        initialEnvironment = environment.clone();
        heuristics.add(new HeuristicDistanceToGoal());
        heuristics.add(new HeuristicDistanceToEnemies());
        heuristics.add(new HeuristicNumberOfEnemies());
        heuristics.add(new HeuristicDistanceToGoalAndEnemiesAndNumberOfEnemies());
        heuristic = heuristics.get(0);
    }

    public MummyMazeState resetEnvironment() {
        environment = initialEnvironment.clone();
        return environment;
    }

    public MummyMazeState readInitialStateFromFile(File file) throws IOException {
        java.util.Scanner scanner = new java.util.Scanner(file);

        char[][] matrix = new char[Constants.SIZE][Constants.SIZE];

        String fileLine;
        for (int i = 0; i < Constants.SIZE; i++) {
            fileLine = scanner.nextLine();
            for (int j = 0; j < Constants.SIZE; j++) {
                matrix[i][j] = fileLine.charAt(j);
            }
        }
        initialEnvironment = new MummyMazeState(matrix);
        resetEnvironment();
        return environment;
    }

}
