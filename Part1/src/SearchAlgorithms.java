import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
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

        // Data structures storing explored and unexplored states
        Queue<State> edgeStates = new LinkedList<>();
        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(initialState);

        // Traverse all edge states until none are left
        while (!edgeStates.isEmpty()) {
            // First in queue goes from unexplored to explored
            currentState = edgeStates.poll();
            inEdgeStates.remove(currentState);
            closedStates.add(currentState);

            // Goal found, terminate and construct result
            if (currentState.equals(goalState)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            // Expand state in layer n and enqueue its children in layer n+1
            for (State child : currentState.children()) {
                // If not a redundant state enqueue child
                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                edgeStates.offer(child);
                inEdgeStates.add(child);
            }
        }
        // Goal not found, terminate and construct result
        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
        return result;
    }

    // ================= Greedy Search ============================
    public Result FindGreedyPlan(State initialState, BiFunction<int[], int[], Integer> DistanceFunction) {
        long startTime = System.currentTimeMillis();
        State currentState = null;
        result = new Result();

        // Data structures storing explored and unexplored states
        PriorityQueue<State> edgeStates = new PriorityQueue<>(
                (state1, state2) -> Integer.compare(state1.hCost, state2.hCost));
        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(initialState);

        // Traverse all edge states until none are left
        while (!edgeStates.isEmpty()) {
            // First in queue goes from unexplored to explored
            currentState = edgeStates.poll();
            inEdgeStates.remove(currentState);
            closedStates.add(currentState);

            // Goal found, terminate and construct result
            if (currentState.equals(goalState)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            // Expand state and loop over children
            for (State child : currentState.children()) {
                // Redundancy Check
                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                child.hCost = DistanceFunction.apply(child.board, goalState.board);
                // Enqueue in order of hcost
                edgeStates.add(child);
                inEdgeStates.add(child);
            }
        }
        // Goal not found, terminate and construct result
        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
        return result;
    }

    // ================= A* Search ============================
    public Result FindAstarPlan(State initialState, BiFunction<int[], int[], Integer> DistanceFunction) {
        long startTime = System.currentTimeMillis();
        State currentState = null;
        result = new Result();

        // Data structures storing explored and unexplored states
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

        initialState.gCost = 0;
        edgeStates.add(initialState);

        // Traverse all edge states until none are left
        while (!edgeStates.isEmpty()) {
            // First in queue goes from unexplored to explored
            currentState = edgeStates.poll();
            inEdgeStates.remove(currentState);
            closedStates.add(currentState);

            // Goal found, terminate and construct result
            if (currentState.equals(goalState)) {
                diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
                return result;
            }

            // Expand state and loop over children
            for (State child : currentState.children()) {
                // Redundancy Check
                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                /*
                 * Since moves are cardinal,
                 * distance from a state and its child is always 1
                 */
                child.gCost = currentState.gCost + 1;
                child.hCost = DistanceFunction.apply(child.board, goalState.board);

                // Enqueue in order of fcost, then hcost
                edgeStates.add(child);
                inEdgeStates.add(child);
            }
        }
        // Goal not found, terminate and construct result
        diagnosticHelper(startTime, closedStates.size(), edgeStates.size(), currentState);
        return result;
    }

    // ================= EHC (HC + BFS) ============================
    public Result FindEnforcedPlan(State initialState, BiFunction<int[], int[], Integer> DistanceFunction) {
        long startTime = System.currentTimeMillis();
        State bestState = initialState;
        result = new Result();

        HashSet<State> closedStates = new HashSet<>(); 

        bestState.hCost = DistanceFunction.apply(bestState.board, goalState.board);
        closedStates.add(bestState);

        int BFSedgeStates = 0;
        int BFSCounter = -1; // track times BFS was used

        // Traverse all edge states until none are left
        while (bestState != bestState.parent) {

            BFSCounter++;

            // ============================ HC ================================
            boolean improved = true;
            while (improved) {
                improved = false;

                // Goal found, terminate and construct result
                if (bestState.equals(goalState)) {
                    System.out.println("BFS counter: " + BFSCounter);
                    diagnosticHelper(startTime, closedStates.size(), BFSedgeStates, bestState);
                    return result;
                }

                for (State child : bestState.children()) {

                    if (closedStates.contains(child))
                        continue; // Skip already explored states

                    child.hCost = DistanceFunction.apply(child.board, goalState.board);

                    // Continue ehc only if immediately better 
                    if (child.hCost < bestState.hCost) {
                        bestState = child;
                        improved = true;
                        break;
                    }
                }
            }

            /* Apply Breadth first search when reach plateau
             * ======== Breadth First Search ==========
             */
            // Data structures storing explored and unexplored states
            Queue<State> edgeStates = new LinkedList<>();
            HashSet<State> inEdgeStates = new HashSet<>();

            edgeStates.add(bestState);
            // Traverse all edge states until none are left
            while (!edgeStates.isEmpty()) {
                // First in queue goes from unexplored to explored
                State currentState = edgeStates.poll();
                inEdgeStates.remove(currentState);
                closedStates.add(currentState);

                // Goal found with improvement
                if (DistanceFunction.apply(currentState.board, goalState.board) == bestState.hCost - 1) {
                    currentState.hCost = DistanceFunction.apply(currentState.board, goalState.board);
                    bestState = currentState;
                    break;
                }

                // Expand state in layer n and enqueue its children in layer n+1
                for (State child : currentState.children()) {
                    // If not a redundant state enqueue child
                    if (closedStates.contains(child) || inEdgeStates.contains(child))
                        continue;

                    edgeStates.offer(child);
                    inEdgeStates.add(child);
                }
            }

            BFSedgeStates += inEdgeStates.size();
        }

        // Goal not found, terminate and construct result
        diagnosticHelper(startTime, closedStates.size(), BFSedgeStates, bestState);
        return result;
    }

    private void diagnosticHelper(long startTime, int closedSize, int edgeSize, State finalState) {
        long endTime = System.currentTimeMillis();
        result.duration = endTime - startTime;
        result.uniqueStatesCount = closedSize + edgeSize;
        result.retracePlan(finalState);
    }
}