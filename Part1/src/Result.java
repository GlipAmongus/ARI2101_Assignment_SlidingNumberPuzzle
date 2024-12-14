import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Collections;

public class Result {
    // Plan Statistics
    public State lastState; // Last state search algorithm looked at

    public int uniqueStatesCount;

    public Stack<State> boards; // 
    public ArrayList<Character> plan; // list of moves done (l,u,r,d)
    public boolean validity; // plan validity
    public int actions; // length of the plan

    public long duration; // time taken to execute

    public Result() {
        this.validity = false;
    }

    public void retracePlan(State state) {
        boards = new Stack<>(); // stack to hold plan in correct order
        plan = new ArrayList<>();

        State ancestor = state.parent;
        boards.add(state);
        plan.add(state.move);

        while (ancestor != null) {
            boards.add(ancestor);
            if(ancestor.parent != null){
                plan.add(ancestor.move);
            }
            actions++;
            ancestor = ancestor.parent;
        }
        Collections.reverse(plan);

        // ===================== Validation Step =========================
        State currentState = boards.peek();
        State simulatedState = currentState.clone();

        for (int i = 0; i < plan.size(); i++) {
            switch (plan.get(i)) {
                case 'd':
                    if (simulatedState.emptyTileIndex - 3 >= 0) {
                        Swap(simulatedState, -3);
                    }
                    break;
                case 'u':
                    if (simulatedState.emptyTileIndex + 3 <= 8) {
                        Swap(simulatedState, 3);
                    }
                    break;
                case 'l':
                    if (simulatedState.emptyTileIndex % 3 == 0 || simulatedState.emptyTileIndex % 3 == 1) {
                        Swap(simulatedState, 1);
                    }
                    break;
                case 'r':
                    if (simulatedState.emptyTileIndex % 3 == 1 || simulatedState.emptyTileIndex % 3 == 2) {
                        Swap(simulatedState, -1);
                    }
                    break;

            }
        }
        
        if (Arrays.equals(simulatedState.board, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 })) {
            validity = true;
        }
    }

    public void Swap(State state, int shift) {
        state.board[state.emptyTileIndex] = state.board[state.emptyTileIndex
                + shift];
        state.board[state.emptyTileIndex + shift] = 0;
        state.emptyTileIndex += shift;
    }
}
