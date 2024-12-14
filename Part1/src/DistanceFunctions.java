public class DistanceFunctions {

    // =============== Test Distance Functions ==========================
    public static void main(String[] args) {
        // Define sample states for testing
        int[] initialState = {
                2, 4, 3,
                1, 5, 6,
                7, 0, 8
        };

        int[] goalState = {
                1, 2, 3,
                4, 5, 6,
                7, 8, 0
        };

        for (int row = 0; row < 3; row++) {
            // Print the corresponding row from the first array
            System.out.print("[ ");
            for (int col = 0; col < 3; col++) {
                System.out.print(initialState[row * 3 + col] + " ");
            }
            if (row == 1) {
                System.out.print("] -> [ ");
            } else {
                System.out.print("]    [ ");
            }

            // Print the corresponding row from the second array
            for (int col = 0; col < 3; col++) {
                System.out.print(goalState[row * 3 + col] + " ");
            }
            System.out.print("]");

            // Move to the next line
            System.out.println();
        }

        // Test Misplaced Tiles Distance
        int misplacedTiles = MisplacedTiles(initialState, goalState);
        System.out.println("Misplaced Tiles Distance: " + misplacedTiles);

        // Test Manhattan Distance
        int manhattan = Manhattan(initialState, goalState);
        System.out.println("Manhattan Distance: " + manhattan);

    }

    // ================= Distance Functions ============================
    public static Integer Manhattan(int[] state, int[] destination) {
        int cost = 0;
        int[] goalPosition = new int[9]; // Lookup table for goal positions

        // Precompute the goal positions
        for (int i = 0; i < 9; i++) {
            goalPosition[destination[i]] = i;
        }

        // Calculate Manhattan distance
        for (int i = 0; i < 9; i++) {
            if (state[i] != 0) { // Skip the blank tile
                int stateTile = state[i];
                int goalIndex = goalPosition[stateTile];

                int stateX = i % 3, stateY = i / 3;
                int destX = goalIndex % 3, destY = goalIndex / 3;

                int distX = Math.abs(stateX - destX);
                int distY = Math.abs(stateY - destY);

                cost += distX + distY;
            }
        }

        return cost;
    }

    public static Integer MisplacedTiles(int[] state, int[] destination) {
        int cost = 0;
        for (int i = 0; i < 9; i++) {
            // If the value at position i in state is not the same as in destination
            if (state[i] != destination[i] && state[i] != 0) {
                cost++;
            }
        }
        return cost;
    }
}
