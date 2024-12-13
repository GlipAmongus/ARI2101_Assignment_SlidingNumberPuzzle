import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.Arrays;
import java.util.HashSet;

public class SearchAlgorithms {
    public Result result;
    int[] goalStateStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

    // ================= Breadth First Search ============================
    public Result FindBreadthFirstPlan(State initialState) {
        long startTime = System.currentTimeMillis();
        State currentState = null;
        result = new Result();

        LinkedList<State> edgeStates = new LinkedList<>();
        HashSet<State> closedStates = new HashSet<>();

        edgeStates.add(initialState);

        while (!edgeStates.isEmpty()) {
            currentState = edgeStates.removeFirst();

            if (Arrays.equals(currentState.board, goalStateStructure)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            if (!closedStates.contains(currentState)) {
                closedStates.add(currentState);
                edgeStates.addAll(currentState.children());
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

            if (Arrays.equals(currentState.board, goalStateStructure)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            for (State child : currentState.children()) {
                result.generatedStatesCount++;
                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                child.hCost = DistanceFunction.apply(child.board, goalStateStructure);
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

            if (Arrays.equals(currentState.board, goalStateStructure)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            for (State child : currentState.children()) {
                result.generatedStatesCount++;

                if (closedStates.contains(child))
                    continue;

                int CostToChild = currentState.gCost
                        + DistanceFunction.apply(currentState.board, child.board);
                if (CostToChild < child.gCost || !inEdgeStates.contains(child)) {
                    child.gCost = CostToChild;
                    child.hCost = DistanceFunction.apply(child.board, goalStateStructure);

                    if (!inEdgeStates.contains(child)) {
                        edgeStates.add(child);
                        inEdgeStates.add(child);
                    } else {
                        edgeStates.remove(child);
                        edgeStates.add(child);
                    }
                }
            }
        }
        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
        return result;
    }

    // ================= Enforced Hill Climb Search ============================
    public Result FindEnforcedPlan(State initialState, BiFunction<int[], int[], Integer> DistanceFunction) {
        long startTime = System.currentTimeMillis();
        State bestState = initialState;
        result = new Result();

        Queue<State> edgeStates = new LinkedList<>();
        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(bestState);
        bestState.hCost = DistanceFunction.apply(bestState.board, goalStateStructure);

        while (!edgeStates.isEmpty()) { // traverse all edge States
            State currentState = edgeStates.poll();
            inEdgeStates.remove(currentState);
            closedStates.add(currentState);

            if (Arrays.equals(currentState.board, goalStateStructure)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), bestState);
                return result;
            }

            for (State child : currentState.children()) { // traverse all edge States
                result.generatedStatesCount++;
                if (closedStates.contains(child) && inEdgeStates.contains(child))
                    continue;

                child.hCost = DistanceFunction.apply(child.board, goalStateStructure);
                if (child.hCost < bestState.hCost) {
                    bestState = child;
                    edgeStates.clear();
                    edgeStates.add(child);
                    inEdgeStates.add(child);
                    break;
                } else {
                    edgeStates.remove(child);
                    edgeStates.add(child);
                }
            }
        }
        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), bestState);
        return result;
    }
    public void diagnosticHelper(long startTime, int closedSize, int edgeSize, State finalState){
        long endTime = System.currentTimeMillis();
        result.duration = endTime - startTime;
        result.uniqueStatesCount = closedSize + edgeSize;
        result.retracePlan(finalState);
    }
}