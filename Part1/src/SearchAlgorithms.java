import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.HashSet;

public class SearchAlgorithms {
    public Result result;
    State goalState = new State(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 }, 8, null);

    // ================= Breadth First Search ============================
    public Result FindBreadthFirstPlan(State initialState) {
        long startTime = System.currentTimeMillis();
        State currentState = null;
        result = new Result();

        Queue<State> edgeStates = new LinkedList<>();
        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(initialState);

        while (!edgeStates.isEmpty()) {
            currentState = edgeStates.poll();
            inEdgeStates.remove(currentState);
            closedStates.add(currentState);

            if (currentState.equals(goalState)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            for (State child : currentState.children()) {
                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                edgeStates.offer(child);
                inEdgeStates.add(child);
            }
        }

        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
        return result;
    }

    // ================= Greedy Search ============================
    public Result FindGreedyPlan(State initialState, BiFunction<int[], int[], Integer> DistanceFunction) {
        long startTime = System.currentTimeMillis();
        State currentState = null;
        result = new Result();

        PriorityQueue<State> edgeStates = new PriorityQueue<>(
                (state1, state2) -> Integer.compare(state1.hCost, state2.hCost));

        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(initialState);
        inEdgeStates.add(initialState);

        while (!edgeStates.isEmpty()) { // traverse all edge States
            currentState = edgeStates.poll();

            inEdgeStates.remove(currentState);
            closedStates.add(currentState);

            if (currentState.equals(goalState)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            for (State child : currentState.children()) {
                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                child.hCost = DistanceFunction.apply(child.board, goalState.board);
                edgeStates.add(child);
                inEdgeStates.add(child);
            }
        }

        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
        return result;
    }

    // ================= A* Search ============================
    public Result FindAstarPlan(State initialState, BiFunction<int[], int[], Integer> DistanceFunction) {
        long startTime = System.currentTimeMillis();
        State currentState = null;
        result = new Result();

        PriorityQueue<State> edgeStates = new PriorityQueue<>(
                (state1, state2) -> {
                    if (state1.fCost() != state2.fCost()) { // Compare Fcosts
                        return Integer.compare(state1.fCost(), state2.fCost()); // set to lowest
                    } else { // Compare hcosts
                        return Integer.compare(state1.hCost, state2.hCost); // set to lowest
                    }
                });

        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(initialState);
        inEdgeStates.add(initialState);

        while (!edgeStates.isEmpty()) { // traverse all edge States
            currentState = edgeStates.poll();

            inEdgeStates.remove(currentState);
            closedStates.add(currentState);

            if (currentState.equals(goalState)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            for (State child : currentState.children()) {

                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                child.gCost = currentState.gCost
                        + DistanceFunction.apply(currentState.board, child.board);
                child.hCost = DistanceFunction.apply(child.board, goalState.board);

                edgeStates.add(child);
                inEdgeStates.add(child);
            }
        }
        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
        return result;
    }

    // ================= Enforced Hill Climb Search with Backtracking
    // ============================
    public Result FindEnforcedPlan(State initialState,
            BiFunction<int[], int[], Integer> DistanceFunction) {
        long startTime = System.currentTimeMillis();
        State bestState = initialState;
        result = new Result();

        Stack<State> stateStack = new Stack<>(); // Stack for backtracking
        HashSet<State> closedStates = new HashSet<>(); // Closed set

        bestState.hCost = DistanceFunction.apply(bestState.board, goalState.board);

        stateStack.push(bestState);

        while (!stateStack.isEmpty()) {
            State currentState = stateStack.pop();
            closedStates.add(currentState);

            if (currentState.equals(goalState)) {
                diagnosticHelper(startTime, closedStates.size(), stateStack.size(), currentState);
                return result;
            }

            boolean improved = false;
            for (State child : currentState.children()) {

                if (closedStates.contains(child))
                    continue; // Skip already explored states

                child.hCost = DistanceFunction.apply(child.board, goalState.board);

                if (child.hCost < bestState.hCost) {
                    bestState = child;
                    stateStack.push(child); // Push the better state onto the stack
                    improved = true;
                    break; // Only explore the first improvement
                } else if (child.hCost == bestState.hCost) {
                    bestState = child;
                    stateStack.push(child); // Push non worse state onto the stack
                    improved = true;
                }
            }

            // If no improvement was made, backtrack by revisiting previous states
            if (!improved) {
                for (State child : currentState.children()) {
                    if (!closedStates.contains(child)) {
                        stateStack.push(child); // Revisit unexplored children
                    }
                }
            }
        }

        diagnosticHelper(startTime, closedStates.size(), stateStack.size(), bestState);
        return result;
    }

    public void diagnosticHelper(long startTime, int closedSize, int edgeSize, State finalState) {
        long endTime = System.currentTimeMillis();
        result.duration = endTime - startTime;
        result.uniqueStatesCount = closedSize + edgeSize;
        result.retracePlan(finalState);
    }
}