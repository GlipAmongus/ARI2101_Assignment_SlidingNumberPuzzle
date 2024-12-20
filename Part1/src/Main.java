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

    public static void PrintBoard(int[] board) {
        for (int row = 0; row < 3; row++) {
            System.out.print("[ ");
            for (int col = 0; col < 3; col++) {
                System.out.print(board[row * 3 + col] + " ");
            }
            System.out.println("]");
        }
    }

    // ================= Main Method ============================
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int searchOption = 0;
        int hueristicOption = 0;
        int testStatesCheck = 0;
        int displayBoardCheck = -1;

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

        if (searchOption != 5) {
            System.out.println("\n--------- PRINT BOARD -----------");
            System.out.println("Display board after each plan action: [1/0]");
            while (displayBoardCheck < 0 || displayBoardCheck > 1) {
                System.out.print("Enter Option: ");
                displayBoardCheck = scn.nextInt();
            }
        }

        System.out.println("\n------ INITIAL TEST STATE -------");
        System.out.println("[[8,6,7],[2,5,4],[3,0,1]]:    [1]");
        System.out.println("[[6,4,7],[8,5,0],[3,2,1]]:    [2]");
        System.out.println("[[1,2,3],[4,5,6],[8,7,0]]:    [3]");
        System.out.println("[Custom Board]:               [4]");
        while (testStatesCheck < 1 || testStatesCheck > 4) {
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
            case 4:
                int[] customBoard = new int[9];
                int emptyTileIndex = 0;

                System.out.println("\n------ CUSTOM BOARD INPUT -------");
                for (int i = 0; i < 9; i++) {
                    boolean invalid = false;

                    System.out.print("Enter Tile [" + i + "]: ");
                    customBoard[i] = scn.nextInt();

                    if (customBoard[i] == 0)
                        emptyTileIndex = i;

                    // Out of bounds check
                    if (customBoard[i] < 0 || customBoard[i] > 8) {
                        System.out.println("Error: "+customBoard[i]+" out of bounds.");
                        invalid = true;
                    }

                    // Already exists check
                    for (int j = 0; j < i; j++) {
                        if (customBoard[j] == customBoard[i]) {
                            System.out.println("Error: "+customBoard[i]+" already exists.");
                            invalid = true;
                            break;
                        }
                    }

                    if (invalid)
                        i--;
                }
                System.out.println();
                initialState = new State(customBoard, emptyTileIndex, null);
                break;
        }

        System.out.println("\nINITIAL BOARD:");
        PrintBoard(initialState.board);

        switch (searchOption) {
            case 1:
                searchInstance = new Main();
                searchType = "BreadthFirst";
                distanceFunction = null;
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState, displayBoardCheck);
                break;
            case 2:
                searchInstance = new Main();
                if (hueristicOption == 1) {
                    searchType = "Greedy (Manhattan)";
                    distanceFunction = DistanceFunctions::Manhattan;
                } else {
                    searchType = "Greedy (Misplaced)";
                    distanceFunction = DistanceFunctions::MisplacedTiles;
                }
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState, displayBoardCheck);
                break;
            case 3:
                searchInstance = new Main();
                if (hueristicOption == 1) {
                    searchType = "A* (Manhattan)";
                    distanceFunction = DistanceFunctions::Manhattan;
                } else {
                    searchType = "A* (Misplaced)";
                    distanceFunction = DistanceFunctions::MisplacedTiles;
                }
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState, displayBoardCheck);
                break;
            case 4:
                searchInstance = new Main();
                if (hueristicOption == 1) {
                    searchType = "EHC (Manhattan)";
                    distanceFunction = DistanceFunctions::Manhattan;
                } else {
                    searchType = "EHC (Misplaced)";
                    distanceFunction = DistanceFunctions::MisplacedTiles;
                }
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState, displayBoardCheck);
                break;
            case 5:
                Main[] searchInstances = new Main[7];
                String[] searchTypes = new String[] { "BreadthFirst", "Greedy (Manhattan)", "Greedy (Misplaced)",
                        "A* (Manhattan)",
                        "A* (Misplaced)", "EHC (Manhattan)", "EHC (Misplaced)" };
                List<BiFunction<int[], int[], Integer>> distanceFunctions = new ArrayList<>();
                distanceFunctions.add(null); // Breadth-First doesn't need a heuristic function
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                forAllDiagnostics(searchInstances, searchTypes, distanceFunctions,
                        initialState);
                break;
        }
        scn.close();
    }

    public static void specificDiagnostic(Main searchInstance, String searchType,
            BiFunction<int[], int[], Integer> distanceFunction, State initialState, int display) {

        System.out.println("======== " + searchType + " ========\n");
        searchInstance.executeSearch(initialState, distanceFunction, searchType);
        // Print plan receipt
        System.out.println("Duration: " + searchInstance.result.duration + "ms");
        System.out.println("Validity: " + searchInstance.result.validity);
        System.out.println("Unique States: " + searchInstance.result.uniqueStatesCount);
        System.out.println("Actions: " + searchInstance.result.actions);
        System.out.println("Plan: " + searchInstance.result.plan);
        System.out.println("\n=======================================\n");

        if (display == 1) {
            int[] current = searchInstance.result.boards.pop();
            int[] target = searchInstance.result.boards.pop();

            for (int step = 0; step < searchInstance.result.actions; step++) {
                System.out.println("Move: " + searchInstance.result.plan.get(step)
                        + "   | Action: " + (step + 1));

                for (int row = 0; row < 3; row++) {
                    // Print the corresponding row from the first array
                    System.out.print("[ ");
                    for (int col = 0; col < 3; col++) {
                        System.out.print(current[row * 3 + col] + " ");
                    }
                    if (row == 1) {
                        System.out.print("] -> [ ");
                    } else {
                        System.out.print("]    [ ");
                    }

                    // Print the corresponding row from the second array
                    for (int col = 0; col < 3; col++) {
                        System.out.print(target[row * 3 + col] + " ");
                    }
                    System.out.print("]");

                    // Move to the next line
                    System.out.println();
                }

                current = target;
                if (!searchInstance.result.boards.empty()) {
                    target = searchInstance.result.boards.pop();
                }
            }
        }
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
            System.out.println("Actions: " + searchInstances[i].result.actions);
            System.out.println("Plan: " + searchInstances[i].result.plan);
            System.out.println("\n=======================================\n");
        }

        System.out.println("\n============== Summary ==============\n");
        System.out.printf("%-20s | %-8s | %-8s | %-13s%n", "Algorithm", "Duration", "Actions", "Unique States");
        System.out.println("-------------------------------------");

        for (int i = 0; i < searchInstances.length; i++) {
            System.out.printf("%-20s | %-8s | %-8d | %-13d%n", searchTypes[i],
                    searchInstances[i].result.duration + "ms",
                    searchInstances[i].result.actions, searchInstances[i].result.uniqueStatesCount);
        }
        System.out.println("\n=====================================\n");
    }
}