import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Collections;

public class Result {
    public Stack<int[]> boards; // boards accessed in plan

    // Plan Output
    public ArrayList<Character> plan; // list of moves done (l,u,r,d)
    public boolean validity; // plan validity
    public int actions; // length of the plan
    public int uniqueStatesCount; // number of unique states generated including unopened states
    public long duration; // time taken to execute

    public void retracePlan(State state) {
        boards = new Stack<>(); // stack to hold plan in correct order
        plan = new ArrayList<>();

        // ====================== Retrace Step ==========================
        State ancestor = state.parent;
        boards.push(state.board);
        plan.add(state.move);

        while (ancestor != null) {
            boards.push(ancestor.board);
            if (ancestor.parent != null) {
                plan.add(ancestor.move);
            }
            actions++;
            ancestor = ancestor.parent;
        }
        Collections.reverse(plan);

        // ===================== Validation Step =========================
        int[] simBoard = boards.peek().clone();
        int simEmptyIndex = -1;
        for(int i = 0; i < 9; i++){
            if(simBoard[i] == 0){
                simEmptyIndex = i;
                break;
            }
        }

        for (int i = 0; i < plan.size(); i++) {
            switch (plan.get(i)) {
                case 'd':
                    if (simEmptyIndex - 3 >= 0) {
                        Swap(simBoard, simEmptyIndex, -3);
                        simEmptyIndex -= 3;
                    }
                    break;
                case 'u':
                    if (simEmptyIndex + 3 <= 8) {
                        Swap(simBoard, simEmptyIndex, 3);
                        simEmptyIndex += 3;
                    }
                    break;
                case 'l':
                    if (simEmptyIndex % 3 == 0 || simEmptyIndex % 3 == 1) {
                        Swap(simBoard, simEmptyIndex, 1);
                        simEmptyIndex += 1;
                    }
                    break;
                case 'r':
                    if (simEmptyIndex % 3 == 1 || simEmptyIndex % 3 == 2) {
                        Swap(simBoard, simEmptyIndex, -1);
                        simEmptyIndex -= 1;
                    }
                    break;

            }
        }

        if (Arrays.equals(simBoard, new int[]{1,2,3,4,5,6,7,8,0})) {
            validity = true;
        }
    }

    public void Swap(int[] board, int emptyTileIndex, int shift) {
        board[emptyTileIndex] = board[emptyTileIndex
                + shift];
        board[emptyTileIndex + shift] = 0;
    }
}
