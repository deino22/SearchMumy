package searchmethods;

import agent.Heuristic;
import agent.Problem;
import agent.Solution;
import utils.NodePriorityQueue;


public abstract class InformedSearch extends GraphSearch<NodePriorityQueue> {

    protected Heuristic heuristic;

    public InformedSearch() {
        frontier = new NodePriorityQueue();
    }

    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        this.heuristic = problem.getHeuristic();
        return graphSearch(problem);
    }
}