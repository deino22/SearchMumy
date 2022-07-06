package utils;

import java.util.Queue;

import agent.State;
import searchmethods.Node;

public interface NodeCollection extends Queue<Node> {
    public boolean containsState(State e);
}
