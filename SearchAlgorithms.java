import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.Arrays;
import java.util.HashSet;

public class SearchAlgorithms {

    // ================= Breadth First Search ============================
    public static State FindBreadthFirstPlan(State initialState) {
        int[] goalStateStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        LinkedList<State> edgeStates = new LinkedList<>();
        HashSet<List<Integer>> closedStates = new HashSet<>();

        edgeStates.add(initialState);

        while (!edgeStates.isEmpty()) {
            State headState = edgeStates.removeFirst();
            List<Integer> puzzleList = Arrays.stream(headState.puzzleStructure).boxed().toList();

            if (Arrays.equals(headState.puzzleStructure, goalStateStructure)){
                int unique = closedStates.size() + edgeStates.size();
                System.out.println("unique: "+ unique);
                return headState;
            }
                

            if (!closedStates.contains(puzzleList)) {
                closedStates.add(puzzleList);
                edgeStates.addAll(headState.children());
            }
        }
        return null;
    }

    // ================= A* Search ============================
    public static State FindAstarPlan(State initialState, BiFunction<State, State, Integer> Hueristic) {
        State goalState = new State(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 }, null);

        PriorityQueue<State> edgeStates = new PriorityQueue<>(
                (state1, state2) -> {
                    if (state1.fCost() != state2.fCost()) { // Compare Fcosts
                        return Integer.compare(state1.fCost(), state2.fCost()); // set to lowest
                    } else { // Compare hcosts
                        return Integer.compare(state1.hCost, state2.hCost); // set to lowest
                    }
                });

        HashSet<List<Integer>> closedStates = new HashSet<>();
        HashSet<List<Integer>> inEdgeStates = new HashSet<>();

        edgeStates.add(initialState);

        while (!edgeStates.isEmpty()) { // traverse all edge States
            State headState = edgeStates.poll();
            List<Integer> headList = Arrays.stream(headState.puzzleStructure).boxed().toList();

            inEdgeStates.remove(headList);
            closedStates.add(headList);

            if (Arrays.equals(headState.puzzleStructure, goalState.puzzleStructure)){
                int unique2 = inEdgeStates.size();
                System.out.println("unique: "+ unique2);
                return headState;
            }

            for (State child : headState.children()) {
                List<Integer> childList = Arrays.stream(child.puzzleStructure).boxed().toList();

                if (closedStates.contains(childList))
                    continue;

                int CostToChild = headState.gCost + Hueristic.apply(headState, child);
                if (CostToChild < child.gCost || !inEdgeStates.contains(childList)) {
                    child.gCost = CostToChild;
                    child.hCost = Hueristic.apply(child, goalState);

                    if (!inEdgeStates.contains(childList))
                        edgeStates.add(child);
                    inEdgeStates.add(headList);
                }

            }
        }
        return null;
    }

    // ================= Hueristic Functions ============================
    public static Integer DistanceFunction(State state, State destination) {
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
        for (int i = 1; i < 9; i++) {
            if (state.puzzleStructure[i - 1] == i &&
                    state.puzzleStructure[i - 1] != destination.puzzleStructure[i - 1])
                cost++;
        }
        return cost;
    }

}