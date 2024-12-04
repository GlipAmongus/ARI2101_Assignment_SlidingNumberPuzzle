import java.util.ArrayList;

public class State {
  public int[] puzzleStructure = new int[9]; // order of tiles
  public int emptyTileIndex; // Array index of zero (empty tile) (i.e. {1,2,3,4,5,6,7,8,0} eTI = 8)

  public int gCost; // Cost from start state to state n
  public int hCost; // Cost from state n to goal state (when using hueristic)

  // Will redundancy be checked by retracing and comparing states to parents, or
  // keeping a dynamic array during run time?
  public State parent; // Store state's parent state

  public State(int[] puzzleStructure) {
    this.puzzleStructure = puzzleStructure;
    emptyTileIndex(); // Automatically update
  }

  public int fCost() { // use only when using hueristic
    return hCost + gCost;
  }

  public void emptyTileIndex() { // sets the position of empty tile
    for(int i = 0; i < 9; i++) {
      if(this.puzzleStructure[i] == 0)
        this.emptyTileIndex = i;
    }
  }

  public ArrayList<State> children() { // allowed moves
    ArrayList<State> children = new ArrayList<>(); // Current state's reachable children

    if (emptyTileIndex - 3 >= 0) { // Precondition for down move
      State child = new State(this.puzzleStructure);
      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex - 3];
      child.puzzleStructure[emptyTileIndex - 3] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex - 3;
        child.parent = this;
        children.add(child);
      }
    }

    if (emptyTileIndex + 3 <= 9) { // Precondition for up move
      State child = new State(this.puzzleStructure);

      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex + 3];
      child.puzzleStructure[emptyTileIndex + 3] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex + 3;
        child.parent = this;
        children.add(child);
      }
    }

    if (emptyTileIndex % 3 == 0 || emptyTileIndex % 3 == 1) { // Precondition for left move
      State child = new State(this.puzzleStructure);
      
      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex + 1];
      child.puzzleStructure[emptyTileIndex + 1] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex + 1;
        child.parent = this;
        children.add(child);
      }
    }

    if (emptyTileIndex % 3 == 1 || emptyTileIndex % 3 == 2) { // Precondition for right move
      State child = new State(this.puzzleStructure);
      
      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex - 1];
      child.puzzleStructure[emptyTileIndex - 1] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex - 0;
        child.parent = this;
        children.add(child);
      }
    }

    return children;
  }

  public int misplacedTiles() {
    int misplacedTiles = 0;
    for (int i = 0; i < 8; i++) {
      if (this.puzzleStructure[i] == i + 1) {
        misplacedTiles++;
      }
    }
    return misplacedTiles;
  }

  // Traverse ancestry of state until copy found or entire ancestry checked
  public boolean RedundantState(State state) {
    State ancestor = state.parent;
    while (ancestor != null) {
      if (state.equals(ancestor)) {
        return true;
      } else {
        ancestor = ancestor.parent;
      }
    }
    return false;
  }
}
