import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.lang.System;

public class Main {
    public Result result; // final state returned by search

    public void executeSearch(State initialState, BiFunction<int[], int[], Integer> distanceFunction,
            String searchType) {
        SearchAlgorithms searchAlgorithms = new SearchAlgorithms();
        switch(searchType) {
            case "BreadthFirst":
                result = searchAlgorithms.FindBreadthFirstPlan(initialState);
                break;
            case "Greedy (Manhattan)":
                result = searchAlgorithms.FindGreedyPlan(initialState, distanceFunction);
                break;
            case "Greedy (Misplaced)":
                result = searchAlgorithms.FindGreedyPlan(initialState, distanceFunction);
                break;
            case "A* (Manhattan)":
                result = searchAlgorithms.FindAstarPlan(initialState, distanceFunction);
                break;
            case "A* (Misplaced)":
                result = searchAlgorithms.FindAstarPlan(initialState, distanceFunction);
                break;
            case "EHC (Manhattan)":
                result = searchAlgorithms.FindEnforcedPlan(initialState, distanceFunction);
                break;
            case "EHC (Misplaced)":
                result = searchAlgorithms.FindEnforcedPlan(initialState, distanceFunction);
                break;
        }
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

    // ================= Main Method ============================
    public static void main(String[] args) {
        State initialState1 = new State(new int[] { 8, 6, 7, 2, 5, 4, 3, 0, 1 }, 7, null);
        State initialState2 = new State(new int[] { 6, 4, 7, 8, 5, 0, 3, 2, 1 }, 5, null);
        // State unsolvableState = new State(new int[] { 1, 2, 3, 4, 5, 6, 8, 7, 0 }, 8,
        // null);

        // Set up search algorithms
        Main[] searchInstances = new Main[7];
        String[] searchTypes = { "BreadthFirst", "Greedy (Manhattan)", "Greedy (Misplaced)", "A* (Manhattan)",
                "A* (Misplaced)", "EHC (Manhattan)", "EHC (Misplaced)" };
        List<BiFunction<int[], int[], Integer>> distanceFunctions = new ArrayList<>();
        distanceFunctions.add(null); // Breadth-First doesn't need a heuristic function
        distanceFunctions.add(Hueristics::ManhattanFunction);
        distanceFunctions.add(Hueristics::MisplacedTiles);
        distanceFunctions.add(Hueristics::ManhattanFunction);
        distanceFunctions.add(Hueristics::MisplacedTiles);
        distanceFunctions.add(Hueristics::ManhattanFunction);
        distanceFunctions.add(Hueristics::MisplacedTiles);

        // Execute and print results for each algorithm
        mainDiagnostics(searchInstances, searchTypes, distanceFunctions, initialState1);
        mainDiagnostics(searchInstances, searchTypes, distanceFunctions,
        initialState2);
        // mainDiagnostics(searchInstances, searchTypes, distanceFunctions,
        // unsolvableState);

    }

    public static void mainDiagnostics(Main[] searchInstances, String[] searchTypes,
            List<BiFunction<int[], int[], Integer>> distanceFunctions, State initialState) {
        for (int i = 0; i < searchInstances.length; i++) {
            searchInstances[i] = new Main();
            System.out.println("======== " + searchTypes[i] + " ========\n");
            searchInstances[i].executeSearch(initialState, distanceFunctions.get(i), searchTypes[i]);
            // // Print plan receipt
            System.out.println("Duration: " + searchInstances[i].result.duration + "ms");
            System.out.println("Validity: " + searchInstances[i].result.validity);
            System.out.println("Unique States: " + searchInstances[i].result.uniqueStatesCount);
            System.out.println("Generated States: " + searchInstances[i].result.generatedStatesCount);
            System.out.println("Actions: " + searchInstances[i].result.actions);
            System.out.println("Plan: " + searchInstances[i].result.plan);
            System.out.println("\n=======================================\n");
        }

        System.out.println("\n============== Summary ==============\n");
        System.out.printf("%-20s | %-8s | %-8s | %-13s | %-13s%n", "Algorithm", "Duration", "Actions", "Unique States",
                "Generated States");
        System.out.println("-------------------------------------");

        for (int i = 0; i < searchInstances.length; i++) {
            System.out.printf("%-20s | %-8s | %-8d | %-13d | %-13d%n", searchTypes[i],
                    searchInstances[i].result.duration + "ms",
                    searchInstances[i].result.actions, searchInstances[i].result.uniqueStatesCount, searchInstances[i].result.generatedStatesCount);
        }
        System.out.println("\n=====================================\n");
    }

}
