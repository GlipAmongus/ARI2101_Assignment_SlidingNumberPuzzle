import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.lang.Math;

public class SearchAlgorithms {

    public static boolean FindBreadthFirstPlan(State initialState) {
        int[] goalStateStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        LinkedList<State> edgeStates = new LinkedList<>();
        HashSet<List<Integer>> closedStates = new HashSet<>();

        edgeStates.add(initialState);

        while (!edgeStates.isEmpty()) {
            State headState = edgeStates.removeFirst();
            List<Integer> puzzleList = Arrays.stream(headState.puzzleStructure).boxed().toList();

            if (Arrays.equals(headState.puzzleStructure, goalStateStructure)) {
                // retrace
                return true;
            }

            if (!closedStates.contains(puzzleList)) {
                closedStates.add(puzzleList);
                edgeStates.addAll(headState.children());
            }
        }
        return false;
    }

    public boolean FindAstarPlan(State initialState) {
        int[] goalStateStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        LinkedList<State> edgeStates = new LinkedList<>();
        edgeStates.add(initialState);

        while (!edgeStates.isEmpty()) {
            State headState = edgeStates.removeFirst();
            if (Arrays.equals(headState.puzzleStructure, goalStateStructure)) {
                // retrace
                return true;
            }

            edgeStates.addAll(headState.children());
        }
        return false;
    }

    public int DistanceFunction(State state) {
        int cost = 0;
        for (int i = 1; i < 9; i++) { // find all tile positions
            for (int j = 0; j < 9; j++) {
                if (state.puzzleStructure[j] == i) {
                    int arrayX = j % 3, arrayY = j / 3; // co ords in array
                    int tileX = (i - 1) % 3, tileY = (i - 1) / 3;

                    int distX = Math.abs(arrayX - tileX);
                    int distY = Math.abs(arrayY - tileY);

                    cost += distX + distY;
                }
            }
        }
        return cost;
    }
}