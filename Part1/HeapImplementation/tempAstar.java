import java.util.function.BiFunction;
import java.util.Arrays;
import java.util.HashSet;

public class tempAstar {

    public static void main(String[] args) {
        // Create a comparator for max-heap (highest value first)
        State goalState = new State(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 }, null);
        State goalState2 = new State(new int[] { 1, 2, 3, 5, 0, 6, 4, 7, 8 }, null);
        State goalState3 = new State(new int[] { 1, 2, 3, 4, 5, 6, 0, 7, 8 }, null);
        State goalState4 = new State(new int[] { 1, 2, 3, 0, 5, 6, 4, 7, 8 }, null);

        goalState.hCost = MisplacedTiles(goalState, goalState);
        goalState2.hCost = MisplacedTiles(goalState2, goalState);
        goalState3.hCost = MisplacedTiles(goalState3, goalState);
        goalState4.hCost = MisplacedTiles(goalState4, goalState);

        // Create a heap with initial size 10
        Heap<State> heap = new Heap<>(10, StateComparators.byAstar);

        // Add some items to the heap
        heap.add(goalState);
        heap.add(goalState2);
        heap.add(goalState3);
        heap.add(goalState4);


        System.out.println("Heap size after additions: " + heap.size());

        // Print items in order of removal
        System.out.println("Items removed from the heap:");
        while (heap.size() > 0) {
            State tempstate = heap.removeFirst();
            System.out.println(tempstate.hCost);
        }

        heap.add(goalState);
        heap.add(goalState2);
        heap.add(goalState3);
        heap.add(goalState4);
        System.out.println(goalState2.getHeapIndex());

        goalState2.puzzleStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 0, 8 };
        goalState2.hCost = MisplacedTiles(goalState2, goalState);

        System.out.println(goalState2.getHeapIndex());
        heap.updateItem(goalState2);
        // Print items in order of removal
        System.out.println("Items removed from the heap:");
        while (heap.size() > 0) {
            State tempstate = heap.removeFirst();
            System.out.println(tempstate.hCost);
        }

    }


    // ================= A* Search ============================
    public static State FindAstarPlan(State initialState, BiFunction<State, State, Integer> DistanceFunction) {
        State goalState = new State(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 }, null);

        Heap<State> edgeStates = new Heap<>(100, StateComparators.byAstar);
        HashSet<State> closedStates = new HashSet<>();

        edgeStates.add(initialState);

        while (edgeStates.size() > 0) { // traverse all edge States
            State headState = edgeStates.removeFirst();
            System.out.println(headState.hCost);
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
                if (CostToChild < child.gCost || !edgeStates.contains(child)) {
                    child.gCost = CostToChild;
                    child.hCost = DistanceFunction.apply(child, goalState);
                    
                    if (!edgeStates.contains(child)) {
                        edgeStates.add(child);
                    } else {
                        edgeStates.updateItem(child);
                    }
                }
            }
        }
        return null;
    }

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