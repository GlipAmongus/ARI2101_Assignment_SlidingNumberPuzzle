import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.Arrays;
import java.util.HashSet;

public class SearchAlgorithms {

    // ================= Breadth First Search ============================
    public static State FindBreadthFirstPlan(State initialState) {
        int[] goalStateStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        LinkedList<State> edgeStates = new LinkedList<>();
        HashSet<State> closedStates = new HashSet<>();

        edgeStates.add(initialState);

        while (!edgeStates.isEmpty()) {
            State headState = edgeStates.removeFirst();

            if (Arrays.equals(headState.puzzleStructure, goalStateStructure)) {
                int unique = closedStates.size() + edgeStates.size();
                System.out.println("unique: " + unique);
                return headState;
            }

            if (!closedStates.contains(headState)) {
                closedStates.add(headState);
                edgeStates.addAll(headState.children());
            }
        }
        return null;
    }

    // ================= Greedy Search ============================
    public static State FindGreedyPlan(State initialState, BiFunction<State, State, Integer> DistanceFunction) {
        State goalState = new State(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 }, null);

        PriorityQueue<State> edgeStates = new PriorityQueue<>(
                (state1, state2) -> Integer.compare(state1.hCost, state2.hCost));

        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(initialState);
        inEdgeStates.add(initialState);

        while (!edgeStates.isEmpty()) { // traverse all edge States
            State headState = edgeStates.poll();

            inEdgeStates.remove(headState);
            closedStates.add(headState);

            if (Arrays.equals(headState.puzzleStructure, goalState.puzzleStructure)) {
                int unique = closedStates.size() + edgeStates.size();
                System.out.println("unique: " + unique);
                return headState;
            }

            for (State child : headState.children()) {
                if (closedStates.contains(child) || inEdgeStates.contains(child))
                    continue;

                child.hCost = DistanceFunction.apply(child, goalState);
                edgeStates.add(child);
                inEdgeStates.add(child);
            }
        }
        return null;
    }


    // ================= A* Search ============================
    public static State FindAstarPlan(State initialState, BiFunction<State, State, Integer> DistanceFunction) {
        State goalState = new State(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 }, null);
        
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
            State headState = edgeStates.poll();

            inEdgeStates.remove(headState);
            closedStates.add(headState);

            if (Arrays.equals(headState.puzzleStructure, goalState.puzzleStructure)) {
                int unique = closedStates.size() + edgeStates.size();
                System.out.println("unique: " + unique);
                return headState;
            }

            for (State child : headState.children()) {

                if (closedStates.contains(child))
                    continue;

                int CostToChild = headState.gCost + DistanceFunction.apply(headState, child);
                if (CostToChild < child.gCost || !inEdgeStates.contains(child)) {
                    child.gCost = CostToChild;
                    child.hCost = DistanceFunction.apply(child, goalState);

                    if (!inEdgeStates.contains(child)) {
                        edgeStates.add(child);
                        inEdgeStates.add(child);
                    }
                }
            }
        }
        return null;
    }

    // ================= Enforced Hill Climb Search ============================
    public static State FindEnforcedPlan(State bestState, BiFunction<State, State, Integer> DistanceFunction) {
        State goalState = new State(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 }, null);

        Queue<State> edgeStates = new LinkedList<>();
        HashSet<State> closedStates = new HashSet<>();
        HashSet<State> inEdgeStates = new HashSet<>();

        edgeStates.add(bestState);
        bestState.hCost = DistanceFunction.apply(bestState, goalState);

        while (!edgeStates.isEmpty()) { // traverse all edge States
            State currenState = edgeStates.poll();
            inEdgeStates.remove(currenState);
            closedStates.add(currenState);

            if (Arrays.equals(currenState.puzzleStructure, goalState.puzzleStructure)) {
                int unique = closedStates.size() + edgeStates.size();
                System.out.println("unique: " + unique);
                return currenState;
            }

            for (State child : currenState.children()) { // traverse all edge States
                if (closedStates.contains(child) && inEdgeStates.contains(child))
                    continue;                

                child.hCost = DistanceFunction.apply(child, goalState);
                if (child.hCost < bestState.hCost) {
                    bestState = child;
                    edgeStates.clear();
                    edgeStates.add(child);
                    inEdgeStates.add(child);
                    break;
                } else {
                    edgeStates.add(child);
                    inEdgeStates.add(child);
                }
            }
        }
        return bestState;
    }

    // ================= Hueristic Functions ============================
    public static Integer ManhattanFunction(State state, State destination) {
        int cost = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Find distance between specific state and destination tiles
                if (state.puzzleStructure[i] == destination.puzzleStructure[j] && state.puzzleStructure[i] != 0) {
                    // co ords in array
                    int stateX = j % 3, stateY = j / 3;
                    int destX = i % 3, destY = i / 3;

                    int distX = Math.abs(stateX - destX);
                    int distY = Math.abs(stateY - destY);

                    cost += distX + distY;
                }
            }
        }
        return cost;
    }

    public static Integer MisplacedTiles(State state, State destination) {
        int cost = 0;
        for (int i = 0; i < state.puzzleStructure.length; i++) {
            // If the value at position i in state is not the same as in destination
            if (state.puzzleStructure[i] != destination.puzzleStructure[i] && state.puzzleStructure[i] != 0) {
                cost++;
            }
        }
        return cost;
    }
}