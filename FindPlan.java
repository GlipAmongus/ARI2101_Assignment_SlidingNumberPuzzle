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
        State goalState = new State((new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0}),null);

        FindPlan insBreadth = new FindPlan();

        FindPlan insGreedyManhattan = new FindPlan();
        FindPlan insGreedyMisplaced = new FindPlan();

        FindPlan insAstarManhattan = new FindPlan();
        FindPlan insAstarMisplaced = new FindPlan();

        FindPlan insEHCManhattan = new FindPlan();
        FindPlan insEHCMisplaced = new FindPlan();

        State initialState = new State(new int[] { 6, 4, 7, 8, 5, 0, 3, 2, 1 },
                null);
        // State initialState = new State(new int[] {1,2,3,0,4,5,6,7,8}, null);

        long startTime, endTime;

        System.out.println("======== Breadth First Search ========\n");
        startTime = System.currentTimeMillis();
        insBreadth.goalFound = SearchAlgorithms.FindBreadthFirstPlan(initialState);
        endTime = System.currentTimeMillis(); // Record end time
        insBreadth.duration = endTime - startTime;

        if (insBreadth.goalFound.equals(goalState)){
            insBreadth.validity = true;
            insBreadth.RetraceAndDiagnosePlan(insBreadth.goalFound);
        }else{
            insBreadth.validity = false;
            insBreadth.RetraceAndDiagnosePlan(insBreadth.goalFound);
        }
        System.out.println("\n=======================================\n");

        System.out.println("====== Greedy Search (Manhattan) =====\n");
        startTime = System.currentTimeMillis();
        insGreedyManhattan.goalFound = SearchAlgorithms.FindGreedyPlan(initialState,
                SearchAlgorithms::ManhattanFunction);
        endTime = System.currentTimeMillis(); // Record end time
        insGreedyManhattan.duration = endTime - startTime;

        if (insGreedyManhattan.goalFound.equals(goalState)){
            insGreedyManhattan.validity = true;
            insGreedyManhattan.RetraceAndDiagnosePlan(insGreedyManhattan.goalFound);
        }else{
            insGreedyManhattan.validity = false;
            insGreedyManhattan.RetraceAndDiagnosePlan(insGreedyManhattan.goalFound);
        }

        System.out.println("\n=======================================\n");

        System.out.println("====== Greedy Search (Misplaced) =====\n");
        startTime = System.currentTimeMillis();
        insGreedyMisplaced.goalFound = SearchAlgorithms.FindGreedyPlan(initialState,
                SearchAlgorithms::MisplacedTiles);
        endTime = System.currentTimeMillis(); // Record end time
        insGreedyMisplaced.duration = endTime - startTime;

        if (insGreedyMisplaced.goalFound.equals(goalState)){
            insGreedyMisplaced.validity = true;
            insGreedyMisplaced.RetraceAndDiagnosePlan(insGreedyMisplaced.goalFound);
        }else{
            insGreedyMisplaced.validity = false;
            insGreedyMisplaced.RetraceAndDiagnosePlan(insGreedyMisplaced.goalFound);
        }

        System.out.println("\n=======================================\n");

        System.out.println("======== A* Search (Manhattan) =======\n");
        startTime = System.currentTimeMillis();
        insAstarManhattan.goalFound = SearchAlgorithms.FindAstarPlan(initialState,
                SearchAlgorithms::ManhattanFunction);
        endTime = System.currentTimeMillis(); // Record end time
        insAstarManhattan.duration = endTime - startTime;

        if (insAstarManhattan.goalFound.equals(goalState)){
            insAstarManhattan.validity = true;
            insAstarManhattan.RetraceAndDiagnosePlan(insAstarManhattan.goalFound);
        }else{
            insAstarManhattan.validity = false;
            insAstarManhattan.RetraceAndDiagnosePlan(insAstarManhattan.goalFound);
        }

        System.out.println("\n=======================================\n");

        System.out.println("======== A* Search (Misplaced) =======\n");
        startTime = System.currentTimeMillis();
        insAstarMisplaced.goalFound = SearchAlgorithms.FindAstarPlan(initialState,
                SearchAlgorithms::MisplacedTiles);
        endTime = System.currentTimeMillis(); // Record end time
        insAstarMisplaced.duration = endTime - startTime;

        if (insAstarMisplaced.goalFound.equals(goalState)){
            insAstarMisplaced.validity = true;
            insAstarMisplaced.RetraceAndDiagnosePlan(insAstarMisplaced.goalFound);
        }else{
            insAstarMisplaced.validity = false;
            insAstarMisplaced.RetraceAndDiagnosePlan(insAstarMisplaced.goalFound);
        }

        System.out.println("\n=======================================\n");

        System.out.println("=========== EHC (Manhattan) ==========\n");
        startTime = System.currentTimeMillis();
        insEHCManhattan.goalFound = SearchAlgorithms.FindEnforcedPlan(initialState,
                SearchAlgorithms::ManhattanFunction);
        endTime = System.currentTimeMillis(); // Record end time
        insEHCManhattan.duration = endTime - startTime;

        if (insEHCManhattan.goalFound.equals(goalState)){
            insEHCManhattan.validity = true;
            insEHCManhattan.RetraceAndDiagnosePlan(insEHCManhattan.goalFound);
        }else{
            insEHCManhattan.validity = false;
            insEHCManhattan.RetraceAndDiagnosePlan(insEHCManhattan.goalFound);
        }

        System.out.println("\n=======================================\n");

        System.out.println("=========== EHC (Misplaced) ==========\n");
        startTime = System.currentTimeMillis();
        insEHCMisplaced.goalFound = SearchAlgorithms.FindEnforcedPlan(initialState,
                SearchAlgorithms::MisplacedTiles);
        endTime = System.currentTimeMillis(); // Record end time
        insEHCMisplaced.duration = endTime - startTime;

        if (insEHCMisplaced.goalFound.equals(goalState)){
            insEHCMisplaced.validity = true;
            insEHCMisplaced.RetraceAndDiagnosePlan(insEHCMisplaced.goalFound);
        }else{
            insEHCMisplaced.validity = false;
            insEHCMisplaced.RetraceAndDiagnosePlan(insEHCMisplaced.goalFound);
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
            ;
            plan.add(ancestor.move);
            ancestor = ancestor.parent;
        }

        // Print plan receipt
        System.out.println("Duration: " + duration + "ms");
        System.out.println("Validity: " + validity);
        System.out.println("Actions: " + actions);
        System.out.println(plan);

        // while(!puzzleInstances.isEmpty())
        // PrintPuzzlePosition(puzzleInstances.pop());

    }

    public void PrintPuzzlePosition(int[] statePuzzlePostion) {
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
