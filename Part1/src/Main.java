import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.lang.System;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public Result result;

    public void executeSearch(State initialState, BiFunction<int[], int[], Integer> distanceFunction,
            String searchType) {

        SearchAlgorithms searchAlgorithms = new SearchAlgorithms();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            Callable<Result> search = () -> {
                switch (searchType) {
                    case "BreadthFirst":
                        return searchAlgorithms.FindBreadthFirstPlan(initialState);

                    case "Greedy (Manhattan)":
                    case "Greedy (Misplaced)":
                        return searchAlgorithms.FindGreedyPlan(initialState, distanceFunction);

                    case "A* (Manhattan)":
                    case "A* (Misplaced)":
                        return searchAlgorithms.FindAstarPlan(initialState, distanceFunction);

                    case "EHC (Manhattan)":
                    case "EHC (Misplaced)":
                        return searchAlgorithms.FindEnforcedPlan(initialState, distanceFunction);

                    default:
                        throw new IllegalArgumentException("Unknown search type: " + searchType);
                }
            };

            // Run the search with a timeout of 15 minutes
            Future<Result> future = executor.submit(search);
            try {
                result = future.get(15, TimeUnit.MINUTES);
            } catch (TimeoutException e) {
                System.out.println("Search type \"" + searchType + "\" did not find a solution in 15 minutes.");
                result = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    // ====================== Print Board Functions =========================
    public static void PrintBoard(int[] board) {
        for (int row = 0; row < 3; row++) {
            System.out.print("[ ");
            for (int col = 0; col < 3; col++) {
                System.out.print(board[row * 3 + col] + " ");
            }
            System.out.println("]");
        }
    }

    public static void PrintStep(int[] current, int[] target) {
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
    }

    // ================= Main Method ============================
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int searchOption = 0;
        int distanceOption = 0;
        int testStatesCheck = 0;
        int displayBoardCheck = -1;

        Main searchInstance;
        String searchType;
        BiFunction<int[], int[], Integer> distanceFunction;

        Main[] searchInstances = new Main[7];
        String[] searchTypes = new String[] { "BreadthFirst", "Greedy (Manhattan)", "Greedy (Misplaced)",
                        "A* (Manhattan)",
                        "A* (Misplaced)", "EHC (Manhattan)", "EHC (Misplaced)" };
        List<BiFunction<int[], int[], Integer>> distanceFunctions = new ArrayList<>();

        State initialState = null;


        // ======================== MENU ============================

        // ====================== Search Algorithms =========================
        System.out.println("-------- SEARCH OPTIONS ---------");
        System.out.println("Breadth First Search:         [1]");
        System.out.println("Greedy Best First Search:     [2]");
        System.out.println("A* Search:                    [3]");
        System.out.println("Enforced Hill Climb Search:   [4]");
        System.out.println("Perform All:                  [5]");
        System.out.println("Evaluation:                   [6]");

        while (searchOption < 1 || searchOption > 6) {
            System.out.print("Enter Option: ");
            searchOption = scn.nextInt();
        }

        // ====================== Distance Functions =========================
        if (searchOption > 1 && searchOption < 5) {
            System.out.println("\n------- DISTANCE OPTIONS --------");
            System.out.println("Manhattan Distance:           [1]");
            System.out.println("Misplaced Tiles Distance:     [2]");

            while (distanceOption < 1 || distanceOption > 2) {
                System.out.print("Enter Option: ");
                distanceOption = scn.nextInt();
            }
        }

        // ========================= Print Board =============================
        if (searchOption > 0 && searchOption < 5) {
            System.out.println("\n--------- PRINT BOARD -----------");
            System.out.println("Display board after each plan action: [1/0]");
            while (displayBoardCheck < 0 || displayBoardCheck > 1) {
                System.out.print("Enter Option: ");
                displayBoardCheck = scn.nextInt();
            }
        }

        // ====================== Initial Test State =========================
        if (searchOption != 6) {
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
        }


        // ====================== Board Initialiazation =========================
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
            case 4:         // ====================== Custom Board =========================
                int[] customBoard = new int[9];
                int customEmptyTile = -1;
                boolean valid;

                do {
                    valid = true;
                    System.out.println("------ CUSTOM BOARD INPUT ------- \n(e.g: 1,2,3,4,5,6,7,8,0)\n");
                    String boardString = scn.next();

                    String[] tokens = boardString.split(",");

                    // Process each token and print it
                    if (tokens.length != 9) {
                        System.out.println("Invalid input. You must provide exactly 9 integers.");
                        valid = false;
                    } else {
                        try {
                            int index = 0;
                            for (String token : tokens) {
                                customBoard[index] = Integer.parseInt(token.trim()); // Convert token to integer

                                // Check tile range
                                if (customBoard[index] < 0 || customBoard[index] > 8) {
                                    valid = false;
                                    System.out.println("Invalid input. Tile " + customBoard[index] + " out of range");
                                    break;
                                }

                                // Check for empty tile
                                if (customBoard[index] == 0) {
                                    customEmptyTile = index;
                                }
                                
                                index++;
                            }
                            // Validate duplicates
                            if (valid) {
                                HashSet<Integer> uniqueTiles = new HashSet<>();
                                for (int tile : customBoard) {
                                    if (!uniqueTiles.add(tile)) {
                                        valid = false;
                                        System.out.println("Invalid board. Duplicate tiles are not allowed.");
                                        break;
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please ensure all values are integers.");
                        }
                    }
                } while (valid == false);

                // Result
                if (valid) {
                    System.out.println("Custom board is valid.");
                } 

                initialState = new State(customBoard, customEmptyTile, null);
                break;
            default:
                break;
        }

        // ========================= Execute Selection ============================
        switch (searchOption) {
            case 1:
                searchInstance = new Main();
                searchType = "BreadthFirst";
                distanceFunction = null;
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState, displayBoardCheck);
                break;
            case 2:
                searchInstance = new Main();
                if (distanceOption == 1) {
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
                if (distanceOption == 1) {
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
                if (distanceOption == 1) {
                    searchType = "EHC (Manhattan)";
                    distanceFunction = DistanceFunctions::Manhattan;
                } else {
                    searchType = "EHC (Misplaced)";
                    distanceFunction = DistanceFunctions::MisplacedTiles;
                }
                specificDiagnostic(searchInstance, searchType, distanceFunction, initialState, displayBoardCheck);
                break;
            case 5:
                distanceFunctions.add(null); // Breadth-First doesn't need a heuristic function
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                forAllDiagnostics(searchInstances, searchTypes, distanceFunctions, initialState);
                break;
            case 6:
                distanceFunctions.add(null); // Breadth-First doesn't need a heuristic function
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);
                distanceFunctions.add(DistanceFunctions::Manhattan);
                distanceFunctions.add(DistanceFunctions::MisplacedTiles);

                try (FileWriter writer = new FileWriter("results.csv")) {
                    writer.append("Strategy,InitialState,PlanLength,StatesDiscovered,TimeTaken\n");

                    forAllDiagnostics(searchInstances, searchTypes, distanceFunctions,
                            new State(new int[] { 8, 6, 7, 2, 5, 4, 3, 0, 1 }, 7, null));

                    System.out.println("\nWriting to csv file...");
                    // Write the data rows
                    for (int i = 0; i < searchInstances.length; i++) {
                        writer.append(searchTypes[i]) // Strategy
                                .append(",")
                                .append(String.valueOf("State 1"))
                                .append(",")
                                .append(String.valueOf(searchInstances[i].result.actions)) // PlanLength
                                .append(",")
                                .append(String.valueOf(searchInstances[i].result.uniqueStatesCount)) // StatesDiscovered
                                .append(",")
                                .append(String.valueOf(searchInstances[i].result.duration)) // TimeTaken
                                .append("\n");
                    }

                    System.out.println("Done.\n");

                    forAllDiagnostics(searchInstances, searchTypes, distanceFunctions,
                            new State(new int[] { 6, 4, 7, 8, 5, 0, 3, 2, 1 }, 5, null));

                    System.out.println("\nWriting to csv file...");
                    // Write the data rows
                    for (int i = 0; i < searchInstances.length; i++) {
                        writer.append(searchTypes[i]) // Strategy
                                .append(",")
                                .append(String.valueOf("State 2"))
                                .append(",")
                                .append(String.valueOf(searchInstances[i].result.actions)) // PlanLength
                                .append(",")
                                .append(String.valueOf(searchInstances[i].result.uniqueStatesCount)) // StatesDiscovered
                                .append(",")
                                .append(String.valueOf(searchInstances[i].result.duration)) // TimeTaken
                                .append("\n");
                    }

                    System.out.println("Done.\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
        scn.close();
    }

    public static void specificDiagnostic(Main searchInstance, String searchType,
            BiFunction<int[], int[], Integer> distanceFunction, State initialState, int display) {

        System.out.println("\nINITIAL BOARD:");
        PrintBoard(initialState.board);
        System.out.println();
        // format search algorithm title
        String formattedLine = "=".repeat((38 - searchType.length() - 1) / 2) +
                " " + searchType + " " +
                "=".repeat((38 - searchType.length() - 1) / 2);

        System.out.println(formattedLine + "\n");

        searchInstance.executeSearch(initialState, distanceFunction, searchType);
        // Print plan receipt
        System.out.println("Duration: " + searchInstance.result.duration + "ms");
        System.out.println("Validity: " + searchInstance.result.validity);
        System.out.println("Unique States: " + searchInstance.result.uniqueStatesCount);
        System.out.println("Actions: " + searchInstance.result.actions);
        System.out.println("Plan: " + searchInstance.result.plan);
        System.out.println("\n=======================================\n");

        if (display == 1 && searchInstance.result.actions > 0) {
            int[] current = searchInstance.result.boards.pop();
            int[] target = searchInstance.result.boards.pop();

            for (int step = 0; step < searchInstance.result.actions; step++) {
                System.out.println("Move: " + searchInstance.result.plan.get(step)
                        + "   | Action: " + (step + 1));

                PrintStep(current, target);

                current = target;
                if (!searchInstance.result.boards.empty()) {
                    target = searchInstance.result.boards.pop();
                }
            }
        }
    }

    public static void forAllDiagnostics(Main[] searchInstances, String[] searchTypes,
            List<BiFunction<int[], int[], Integer>> distanceFunctions, State initialState) {

        System.out.println("\nINITIAL BOARD:");
        PrintBoard(initialState.board);
        System.out.println();

        for (int i = 0; i < searchInstances.length; i++) {
            searchInstances[i] = new Main();
            String formattedLine = "=".repeat((38 - searchTypes[i].length() - 1) / 2) +
                " " + searchTypes[i] + " " +
                "=".repeat((38 - searchTypes[i].length() - 1) / 2);
            System.out.println(formattedLine + "\n");
            searchInstances[i].executeSearch(initialState, distanceFunctions.get(i), searchTypes[i]);
            // Print plan receipt
            System.out.println("Duration: " + searchInstances[i].result.duration + "ms");
            System.out.println("Validity: " + searchInstances[i].result.validity);
            System.out.println("Unique States: " + searchInstances[i].result.uniqueStatesCount);
            System.out.println("Actions: " + searchInstances[i].result.actions);
            System.out.println("Plan: " + searchInstances[i].result.plan);
            System.out.println("\n=======================================\n");
        }
        
        System.out.println("\nINITIAL BOARD:");
        PrintBoard(initialState.board);
        System.out.println();

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