import java.util.ArrayList;
import java.util.Arrays;

public class State implements Cloneable {
  public int[] board = new int[9]; // order of tiles
  public int emptyTileIndex; // Array index of zero (empty tile) (i.e. {1,2,3,4,5,6,7,8,0} eTI = 8)

  public int gCost; // Cost from start state to state n // Use in Greedy
  public int hCost; // Cost from state n to goal state (when using hueristic)

  public char move;

  public State parent; // Store state's parent state

  public State(int[] board, int emptyTileIndex, State parent) {
    this.board = board;
    this.parent = parent;
    this.emptyTileIndex = emptyTileIndex;
  }

  protected State clone() {
    try {
      // Create a shallow copy
      State cloned = (State) super.clone();

      // Deep copy the board array
      cloned.board = this.board.clone();

      return cloned;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError("Cloning not supported for State");
    }
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
      State child = new State(this.board.clone(), emptyTileIndex, this);

      Swap(child, -3);

      child.parent = this;
      child.move = 'd';
      child.gCost = this.gCost++;
      children.add(child);
    }

    if (emptyTileIndex + 3 <= 8) { // Precondition for up move
      State child = new State(this.board.clone(), emptyTileIndex, this);

      Swap(child, 3);

      child.parent = this;
      child.move = 'u';
      child.gCost = this.gCost++;
      children.add(child);
    }

    if (emptyTileIndex % 3 == 0 || emptyTileIndex % 3 == 1) { // Precondition for left move
      State child = new State(this.board.clone(), emptyTileIndex, this);

      Swap(child, 1);

      child.parent = this;
      child.move = 'l';
      child.gCost = this.gCost++;
      children.add(child);
    }

    if (emptyTileIndex % 3 == 1 || emptyTileIndex % 3 == 2) { // Precondition for right move
      State child = new State(this.board.clone(), emptyTileIndex, this);

      Swap(child, -1);

      child.parent = this;
      child.move = 'r';
      child.gCost = this.gCost++;
      children.add(child);
    }
    return children;
  }

  public void Swap(State state, int shift) {
    state.board[state.emptyTileIndex] = state.board[state.emptyTileIndex
        + shift];
    state.board[state.emptyTileIndex + shift] = 0;
    state.emptyTileIndex += shift;  
  }
}
