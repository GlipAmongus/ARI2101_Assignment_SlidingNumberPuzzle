import java.util.LinkedList;
import java.util.Arrays;

public class SearchAlgorithms {
    public boolean FindBreadthFirstPlan(State initialState) {
        int[] goalStateStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        LinkedList<State> edgeNodes = new LinkedList<>();
        edgeNodes.add(initialState);

        while (!edgeNodes.isEmpty()) {
            State headState = edgeNodes.removeFirst();
            if (Arrays.equals(headState.puzzleStructure, goalStateStructure)) {
                // retrace
                return true;
            }

            edgeNodes.addAll(headState.children());
        }
        return false;
    }

    public boolean FindAstarPlan(State initialState) {
        int[] goalStateStructure = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        LinkedList<State> edgeNodes = new LinkedList<>();
        edgeNodes.add(initialState);

        while (!edgeNodes.isEmpty()) {
            State headState = edgeNodes.removeFirst();
            if (Arrays.equals(headState.puzzleStructure, goalStateStructure)) {
                // retrace
                return true;
            }

            edgeNodes.addAll(headState.children());
        }
        return false;
    }

    public int DistanceFunction(State state) {
        int cost = 0;
        for (int i = 1; i < 9; i++) {       // find all tile positions
            for (int j = 0; j < 9; j++) {
                if (state.puzzleStructure[j] == i) {
                    int arrayX = j % 3, arrayY = j / 3; // co ords in array
                    int tileX = (i-1)%3, tileY = (i-1)/3;

                    int distX = arrayX - tileX;
                    int distY = arrayY - tileY;

                    cost += distX+distY;
                }
            }
        }
        return cost;
    }
}