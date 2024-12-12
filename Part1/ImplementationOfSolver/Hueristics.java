public class Hueristics {
    // ================= Hueristic Functions ============================
    public static Integer ManhattanFunction(int[] state, int[] destination) {
        int cost = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Find distance between specific state and destination tiles
                if (state[i] == destination[j] && state[i] != 0) {
                    // co ords in array
                    int stateX = j % 3, stateY = j / 3;
                    int destX = i % 3, destY = i / 3;

                    int distX = Math.abs(stateX - destX);
                    int distY = Math.abs(stateY - destY);

                    cost += distX + distY;
                }
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
