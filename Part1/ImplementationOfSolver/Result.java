import java.util.Arrays;
import java.util.Stack;

public class Result {
    // Plan Statistics
    public State lastState; // Last state search algorithm looked at

    public int uniqueStatesCount;
    public int generatedStatesCount;

    public Stack<Character> plan; // list of moves done (l,u,r,d)
    public boolean validity; // plan validity
    public int actions; // length of the plan

    public long duration; // time taken to execute

    public Result() {
        this.generatedStatesCount = 0;
        this.validity = false;
    }

    public void retracePlan(State state) {
        Stack<State> planInOrder = new Stack<>(); // stack to hold plan in correct order

        State ancestor = state.parent;
        planInOrder.add(state);
        plan.add(ancestor.move);

        while (ancestor != null) {
            planInOrder.add(ancestor);
            plan.add(ancestor.move);
            actions++;
            ancestor = ancestor.parent;
        }

        // ===================== Validation Step =========================
        State currentState = planInOrder.peek();

        for (int i = 0; i < actions; i++) {
            switch (plan.elementAt(i)) {
                case 'd':
                    currentState.puzzleStructure[currentState.emptyTileIndex] = currentState.puzzleStructure[currentState.emptyTileIndex - 3];
                    currentState.puzzleStructure[currentState.emptyTileIndex - 3] = 0;
                case 'u':
                    currentState.puzzleStructure[currentState.emptyTileIndex] = currentState.puzzleStructure[currentState.emptyTileIndex + 3];
                    currentState.puzzleStructure[currentState.emptyTileIndex + 3] = 0;
                case 'l':
                    currentState.puzzleStructure[currentState.emptyTileIndex] = currentState.puzzleStructure[currentState.emptyTileIndex - 1];
                    currentState.puzzleStructure[currentState.emptyTileIndex - 1] = 0;
                case 'r':
                    currentState.puzzleStructure[currentState.emptyTileIndex] = currentState.puzzleStructure[currentState.emptyTileIndex + 1];
                    currentState.puzzleStructure[currentState.emptyTileIndex + 1] = 0;

            }
        }
        
        if (Arrays.equals(currentState.puzzleStructure, new int[] {1,2,3,4,5,6,7,8,0})) {
            validity = true;
        }
    }
}
