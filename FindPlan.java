import java.util.ArrayList;
import java.util.Stack;
import java.lang.System;

public class FindPlan {
    // Plan Output
    public ArrayList<Character> plan; // list of moves done (l,u,r,d)
    public int actions; // length of the plan
    public int uniqueStates; // number of unique states gen. unexpanded included
    public boolean validity; // plan validity

    public static void main(String[] args) {

        // State initialState = new State(new int[] { 6, 4, 7, 8, 5, 0, 3, 2, 1 }, null);
        State initialState = new State(new int[] {1,2,3,0,4,5,6,7,8}, null);

        long startTime, endTime, duration;
        // Test time for breadth first
        startTime = System.currentTimeMillis();
        if (SearchAlgorithms.FindBreadthFirstPlan(initialState)) {
            endTime = System.currentTimeMillis(); // Record end time
            duration = endTime - startTime;
            System.out.println("Plan found: " + duration + "ms");
        } else {
            System.out.println("Plan not found!");
        }

        // Test time for astar
        startTime = System.currentTimeMillis();
        if (SearchAlgorithms.FindAstarPlan(initialState, SearchAlgorithms::DistanceFunction)) {
            endTime = System.currentTimeMillis(); // Record end time
            duration = endTime - startTime;
            System.out.println("Plan found: " + duration + "ms");
        } else {
            System.out.println("Plan not found!");
        }
    }

    // ================= Diagnostic Functions ============================
    // ================= Retrace Function ============================
    public static void RetracePlan(State state) {
        // Flip first then print
        Stack<int[]> puzzleInstances = new Stack<>();       // stack to hold plan in correct order

        State ancestor = state.parent;
        puzzleInstances.add(state.puzzleStructure);
        while(ancestor != null){                            
            puzzleInstances.add(ancestor.puzzleStructure);  
            ancestor = ancestor.parent;
        }

        while(!puzzleInstances.isEmpty())
            PrintPuzzlePosition(puzzleInstances.pop());
    }

    public static void PrintPuzzlePosition(int[] statePuzzlePostion) {
        System.out.print("[");
        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 2, 5:
                    System.out.println(statePuzzlePostion[i] + ",");
                    break;
                case 8:
                    System.out.print(statePuzzlePostion[i]);
                default:
                    System.out.print(statePuzzlePostion[i] + ",");
                    break;
            }
        }
        System.out.println("]");
    }
}
