import java.util.ArrayList;

public class State {
  public int[] puzzleStructure = new int[8];
  public int emptyTileIndex; // Array index of zero (empty tile) (i.e. {1,2,3,4,5,6,7,8,0} eTI = 8)

  public int gCost; // Cost from start state to state n
  public int hCost; // Cost from state n to goal state (when using hueristic)
  public int misplacedTiles; // Count of number of displaced tiles

  // Will redundancy be checked by retracing and comparing states to parents, or
  // keeping a dynamic array during run time?
  public State parent; // Store state's parent state
  public ArrayList<State> children; // Current state's reachable children

  public int fCost() {
    return hCost + gCost;
  }

  public ArrayList<State> Children(State state) {

    if (emptyTileIndex - 3 >= 0) { // Precondition for down move
      State child = new State();

      child.puzzleStructure = state.puzzleStructure;
      child.puzzleStructure[emptyTileIndex] = state.puzzleStructure[emptyTileIndex - 3];
      child.puzzleStructure[emptyTileIndex - 3] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex - 3;
        child.misplacedTiles = MisplacedTiles(child); // Can probably remove this later
        child.parent = state;
        children.add(child);
      }
    }

    if (emptyTileIndex + 3 <= 9) { // Precondition for up move
      State child = new State();

      child.puzzleStructure = state.puzzleStructure;
      child.puzzleStructure[emptyTileIndex] = state.puzzleStructure[emptyTileIndex + 3];
      child.puzzleStructure[emptyTileIndex + 3] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex + 3;
        child.misplacedTiles = MisplacedTiles(child); // Can probably remove this later
        child.parent = state;
        children.add(child);
      }
    }

    if (emptyTileIndex % 3 == 0 || emptyTileIndex % 3 == 1) { // Precondition for left move
      State child = new State();

      child.puzzleStructure = state.puzzleStructure;
      child.puzzleStructure[emptyTileIndex] = state.puzzleStructure[emptyTileIndex + 1];
      child.puzzleStructure[emptyTileIndex + 1] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex + 1;
        child.misplacedTiles = MisplacedTiles(child); // Can probably remove this later
        child.parent = state;
        children.add(child);
      }
    }

    if (emptyTileIndex % 3 == 1 || emptyTileIndex % 3 == 2) { // Precondition for right move
      State child = new State();

      child.puzzleStructure = state.puzzleStructure;
      child.puzzleStructure[emptyTileIndex] = state.puzzleStructure[emptyTileIndex - 1];
      child.puzzleStructure[emptyTileIndex - 1] = 0;

      if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex - 0;
        child.misplacedTiles = MisplacedTiles(child); // Can probably remove this later
        child.parent = state;
        children.add(child);
      }
    }
    
    return children;
  }

  public int MisplacedTiles(State state) {
    int misplacedsTiles = 0;
    for (int i = 0; i < 8; i++) {
      if (state.puzzleStructure[i] == i + 1) {
        misplacedTiles++;
      }
    }
    return misplacedsTiles;
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
