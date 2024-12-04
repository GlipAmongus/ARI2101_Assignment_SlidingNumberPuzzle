import java.util.ArrayList;

public class FindPlan {
    ArrayList<State> plan;

    public static void main(String[] args) {
        int[] puzzle1 = {8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] puzzle2 = {8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] goal = {1,2,3,4,5,6,7,8,0};

        State initialState1 = new State(puzzle1);
        State initialState2 = new State(puzzle2);
        State goalState = new State(goal);
        initialState1.puzzleStructure = new int[] {8,6,7,2,5,4,3,0,1}; // Test initial state 1
        initialState2.puzzleStructure = new int[] {8,6,7,2,5,4,3,0,1}; // Test initial state 1
        goalState.puzzleStructure = new int[] {1,2,3,4,5,6,7,8,0};

        FindAstarPlan(initialState2);
    }
    // Plan finding logic goes here (main)
    // Specific search algorithms go in seprate files
}
