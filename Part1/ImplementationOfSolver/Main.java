import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.lang.System;

public class Main {
    public Result result; // final state returned by search

    public void executeSearch(State initialState, BiFunction<int[], int[], Integer> distanceFunction,
            String searchType) {
        SearchAlgorithms searchAlgorithms = new SearchAlgorithms();
        switch (searchType) {
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
        Scanner scn = new Scanner(System.in);
        int searchOption = 0, hueristicOption = 0, testStatesCheck = 0;

        Main searchInstance;
        String searchType;

        BiFunction<int[], int[], Integer> distanceFunction;

        State initialState = null;

        System.out.println("-------- SEARCH OPTIONS ---------");
        System.out.println("Breadth First Search:         [1]");
        System.out.println("Greedy Best First Search:     [2]");
        System.out.println("A* Search:                    [3]");
        System.out.println("Enforced Hill Climb Search:   [4]");
        System.out.println("Perform All:                  [5]");

        while (searchOption < 1 || searchOption > 5) {
            System.out.print("Enter Option: ");
            searchOption = scn.nextInt();
        }

        if (searchOption > 1 && searchOption < 5) {
            System.out.println("\n------- Hueristic Options -------");
            System.out.println("Manhattan Hueristic:          [1]");
            System.out.println("Misplaced Tiles Hueristic:    [2]");

            while (hueristicOption < 1 || hueristicOption > 2) {
                System.out.print("Enter Option: ");
                hueristicOption = scn.nextInt();
            }
        }

        System.out.println("\n------ INITIAL TEST STATE -------");
        System.out.println("[[8,6,7],[2,5,4],[3,0,1]]:    [1]");
        System.out.println("[[6,4,7],[8,5,0],[3,2,1]]:    [2]");
        System.out.println("[[1,2,3],[4,5,6],[8,7,0]]:    [3]");
        while (testStatesCheck < 1 || testStatesCheck > 3) {
            System.out.print("Enter Option: ");
            testStatesCheck = scn.nextInt();
        }
        System.out.println();


        switch (testStatesCheck) {
            case 1:
                initialState = new State(new int[] { 8, 6, 7, 2, 5, 4, 3, 0, 1 }, 7, null);
                break;
            case 2:
                initialState = new State(new int[] { 6, 4, 7, 8, 5, 0, 3, 2, 1 }, 5, null);
                break;
            case 3:
                initialState = new State(new int[] { 1, 2, 3, 4, 5, 6, 8, 7, 0 }, 8, null);
                break;
        }
        switch (searchOption) {
            case 1:
                searchInstance = new Main();
                searchType = "BreadthFirst";
                distanceFunction = null;
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState);
                break;
            case 2:
                searchInstance = new Main();
                if (hueristicOption == 1) {
                    searchType = "Greedy (Manhattan)";
                    distanceFunction = Hueristics::ManhattanFunction;
                } else {
                    searchType = "Greedy (Misplaced)";
                    distanceFunction = Hueristics::MisplacedTiles;
                }
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState);
                break;
            case 3:
                searchInstance = new Main();
                if (hueristicOption == 1) {
                    searchType = "A* (Manhattan)";
                    distanceFunction = Hueristics::ManhattanFunction;
                } else {
                    searchType = "A* (Misplaced)";
                    distanceFunction = Hueristics::MisplacedTiles;
                }
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState);
                break;
            case 4:
                searchInstance = new Main();
                if (hueristicOption == 1) {
                    searchType = "EHC (Manhattan)";
                    distanceFunction = Hueristics::ManhattanFunction;
                } else {
                    searchType = "EHC (Misplaced)";
                    distanceFunction = Hueristics::MisplacedTiles;
                }
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState);
                break;
            case 5:
                Main[] searchInstances = new Main[7];
                String[] searchTypes = new String[] { "BreadthFirst", "Greedy (Manhattan)", "Greedy (Misplaced)",
                        "A* (Manhattan)",
                        "A* (Misplaced)", "EHC (Manhattan)", "EHC (Misplaced)" };
                List<BiFunction<int[], int[], Integer>> distanceFunctions = new ArrayList<>();
                distanceFunctions.add(null); // Breadth-First doesn't need a heuristic function
                distanceFunctions.add(Hueristics::ManhattanFunction);
                distanceFunctions.add(Hueristics::MisplacedTiles);
                distanceFunctions.add(Hueristics::ManhattanFunction);
                distanceFunctions.add(Hueristics::MisplacedTiles);
                distanceFunctions.add(Hueristics::ManhattanFunction);
                distanceFunctions.add(Hueristics::MisplacedTiles);
                forAllDiagnostics(searchInstances, searchTypes, distanceFunctions,
                        initialState);
                break;
        }
        scn.close();
    }

    public static void specificDiagnostic(Main searchInstance, String searchType,
            BiFunction<int[], int[], Integer> distanceFunction, State initialState) {

        System.out.println("======== " + searchType + " ========\n");
        searchInstance.executeSearch(initialState, distanceFunction, searchType);
        // Print plan receipt
        System.out.println("Duration: " + searchInstance.result.duration + "ms");
        System.out.println("Validity: " + searchInstance.result.validity);
        System.out.println("Unique States: " + searchInstance.result.uniqueStatesCount);
        System.out.println("Generated States: " + searchInstance.result.generatedStatesCount);
        System.out.println("Actions: " + searchInstance.result.actions);
        System.out.println("Plan: " + searchInstance.result.plan);
        System.out.println("\n=======================================\n");
    }

    public static void forAllDiagnostics(Main[] searchInstances, String[] searchTypes,
            List<BiFunction<int[], int[], Integer>> distanceFunctions, State initialState) {
        for (int i = 0; i < searchInstances.length; i++) {
            searchInstances[i] = new Main();
            System.out.println("======== " + searchTypes[i] + " ========\n");
            searchInstances[i].executeSearch(initialState, distanceFunctions.get(i), searchTypes[i]);
            // Print plan receipt
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
                    searchInstances[i].result.actions, searchInstances[i].result.uniqueStatesCount,
                    searchInstances[i].result.generatedStatesCount);
        }
        System.out.println("\n=====================================\n");
    }

}
