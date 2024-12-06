import java.util.ArrayList;
import java.util.Stack;
import java.lang.System;

public class FindPlan {
    // Plan Output
    public ArrayList<Character> plan; // list of moves done (l,u,r,d)
    public boolean validity; // plan validity
    public int actions; // length of the plan
    public int uniqueStates; // number of unique states gen. unexpanded included

    public long duration; // time taken to execute
    public State goalFound; // final state returned by search

    public static void main(String[] args) {
        FindPlan insBreadth = new FindPlan();

        FindPlan insGreedyManhattan = new FindPlan();
        FindPlan insGreedyMisplaced = new FindPlan();

        FindPlan insAstarManhattan = new FindPlan();
        FindPlan insAstarMissing = new FindPlan();

        State initialState = new State(new int[] { 6, 4, 7, 8, 5, 0, 3, 2, 1 },
                null);
        // State initialState = new State(new int[] {1,2,3,0,4,5,6,7,8}, null);

        long startTime, endTime;

        System.out.println("======== Breadth First Search ========\n");
        startTime = System.currentTimeMillis();
        insBreadth.goalFound = SearchAlgorithms.FindBreadthFirstPlan(initialState);
        endTime = System.currentTimeMillis(); // Record end time
        insBreadth.duration = endTime - startTime;

        if (insBreadth.goalFound != null) {
            System.out.println("Plan found: " + insBreadth.duration + "ms");
            insBreadth.RetraceAndDiagnosePlan(insBreadth.goalFound);
        } else {
            System.out.println("Plan not found!");
        }
        System.out.println("\n=======================================\n");


        System.out.println("====== Greedy Search (Manhattan) =====\n");
        startTime = System.currentTimeMillis();
        insGreedyManhattan.goalFound = SearchAlgorithms.FindAstarPlan(initialState,
                SearchAlgorithms::ManhattanFunction);
        endTime = System.currentTimeMillis(); // Record end time
        insGreedyManhattan.duration = endTime - startTime;

        if (insGreedyManhattan.goalFound != null) {
            System.out.println("Plan found: " + insGreedyManhattan.duration + "ms");
            insGreedyManhattan.RetraceAndDiagnosePlan(insGreedyManhattan.goalFound);
        } else {
            System.out.println("Plan not found!");
        }
        System.out.println("\n=======================================\n");


        System.out.println("====== Greedy Search (Misplaced) =====\n");
        startTime = System.currentTimeMillis();
        insGreedyMisplaced.goalFound = SearchAlgorithms.FindAstarPlan(initialState,
                SearchAlgorithms::MisplacedTiles);
        endTime = System.currentTimeMillis(); // Record end time
        insGreedyMisplaced.duration = endTime - startTime;

        if (insGreedyMisplaced.goalFound != null) {
            System.out.println("Plan found: " + insGreedyMisplaced.duration + "ms");
            insGreedyMisplaced.RetraceAndDiagnosePlan(insGreedyMisplaced.goalFound);
        } else {
            System.out.println("Plan not found!");
        }
        System.out.println("\n=======================================\n");


        System.out.println("======== A* Search (Manhattan) =======\n");
        startTime = System.currentTimeMillis();
        insAstarManhattan.goalFound = SearchAlgorithms.FindAstarPlan(initialState,
                SearchAlgorithms::MisplacedTiles);
        endTime = System.currentTimeMillis(); // Record end time
        insAstarManhattan.duration = endTime - startTime;

        if (insAstarManhattan.goalFound != null) {
            System.out.println("Plan found: " + insAstarManhattan.duration + "ms");
            insAstarManhattan.RetraceAndDiagnosePlan(insAstarManhattan.goalFound);
        } else {
            System.out.println("Plan not found!");
        }
        System.out.println("\n=======================================\n");


        System.out.println("======== A* Search (Missing) =======\n");
        startTime = System.currentTimeMillis();
        insAstarMissing.goalFound = SearchAlgorithms.FindAstarPlan(initialState,
                SearchAlgorithms::MisplacedTiles);
        endTime = System.currentTimeMillis(); // Record end time
        insAstarMissing.duration = endTime - startTime;

        if (insAstarMissing.goalFound != null) {
            System.out.println("Plan found: " + insAstarMissing.duration + "ms");
            insAstarMissing.RetraceAndDiagnosePlan(insAstarMissing.goalFound);
        } else {
            System.out.println("Plan not found!");
        }
        System.out.println("\n=======================================\n");
        
    }

    // ================= Diagnostic Functions ============================
    // ================= Retrace receipt ============================
    public void RetraceAndDiagnosePlan(State state) {
        // Flip first then print
        Stack<int[]> puzzleInstances = new Stack<>(); // stack to hold plan in correct order

        plan = new ArrayList<>();

        State ancestor = state.parent;
        puzzleInstances.add(state.puzzleStructure);
        plan.add(state.move);

        while (ancestor != null) {
            puzzleInstances.add(ancestor.puzzleStructure);
            actions++;
            validity = true;
            plan.add(ancestor.move);
            ancestor = ancestor.parent;
        }

        // Print plan receipt
        System.out.println("Actions: " + actions);
        System.out.println("Validity: " + validity);
        System.out.println(plan);

        // while(!puzzleInstances.isEmpty())
        // PrintPuzzlePosition(puzzleInstances.pop());

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
                    break;
                default:
                    System.out.print(statePuzzlePostion[i] + ",");
                    break;
            }
        }
        System.out.println("]");
    }
}
