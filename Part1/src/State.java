import java.util.ArrayList;
import java.util.Arrays;

public class State {
  public int[] board = new int[9]; // order of tiles
  public int emptyTileIndex; // Array index of zero (empty tile) (i.e. {1,2,3,4,5,6,7,8,0} eTI = 8)

  public int gCost; // Cost from start state to state n // Use in Greedy
  public int hCost; // Cost from state n to goal state (when using hueristic)

  public char move; // Move done to acquire state
  public State parent; // Store state's parent state

  public State(int[] board, int emptyTileIndex, State parent) {
    this.board = board;
    this.parent = parent;
    this.emptyTileIndex = emptyTileIndex;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    State state = (State) obj;
    return Arrays.equals(board, state.board);
  }

  public int hashCode() {
    return Arrays.hashCode(board);
  }

  public int fCost() {
    return hCost + gCost;
  }

  // Get all allowed state transitions
  public ArrayList<State> children() {
    ArrayList<State> children = new ArrayList<>();

    if (emptyTileIndex - 3 >= 0) { // Precondition for down move
      State child = new State(this.board.clone(), emptyTileIndex - 3, this);

      // Swap helper function
      Swap(child, -3);

      // Set for retarcing path
      child.parent = this;
      child.move = 'd';

      children.add(child);
    }

    if (emptyTileIndex + 3 <= 8) { // Precondition for up move
      State child = new State(this.board.clone(), emptyTileIndex + 3, this);

      Swap(child, 3);

      child.parent = this;
      child.move = 'u';

      children.add(child);
    }

    if (emptyTileIndex % 3 == 0 || emptyTileIndex % 3 == 1) { // Precondition for left move
      State child = new State(this.board.clone(), emptyTileIndex + 1, this);

      Swap(child, 1);

      child.parent = this;
      child.move = 'l';

      children.add(child);
    }

    if (emptyTileIndex % 3 == 1 || emptyTileIndex % 3 == 2) { // Precondition for right move
      State child = new State(this.board.clone(), emptyTileIndex - 1, this);

      Swap(child, -1);

      child.parent = this;
      child.move = 'r';

      children.add(child);
    }
    return children;
  }

  private void Swap(State state, int shift) {
    state.board[this.emptyTileIndex] = state.board[this.emptyTileIndex
        + shift];
    state.board[this.emptyTileIndex + shift] = 0;
  }
}