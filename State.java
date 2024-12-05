import java.util.ArrayList;
import java.util.Arrays;

public class State {
  public int[] puzzleStructure = new int[9]; // order of tiles
  public int emptyTileIndex; // Array index of zero (empty tile) (i.e. {1,2,3,4,5,6,7,8,0} eTI = 8)

  public char move;

  public int gCost; // Cost from start state to state n // Use in Greedy
  public int hCost; // Cost from state n to goal state (when using hueristic)

  public State parent; // Store state's parent state

  public State(int[] puzzleStructure, State parent) {
    this.puzzleStructure = puzzleStructure;
    this.parent = parent;
    for(int i = 0; i < 9; i++) {
      if(this.puzzleStructure[i] == 0){ // Calculates initial index of zero
        this.emptyTileIndex = i;
        break;
      }
    }
  }

  public int fCost() { // use only when using hueristic
    return hCost + gCost;
  }

  // Get all allowed state transitions
  public ArrayList<State> children() { 
    ArrayList<State> children = new ArrayList<>(); 

    if (emptyTileIndex - 3 >= 0) {                                                      // Precondition for down move
      State child = new State(this.puzzleStructure.clone(), this);  
      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex - 3]; // Swap zero and tile above
      child.puzzleStructure[emptyTileIndex - 3] = 0;

      // if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex - 3;
        child.parent = this;
        child.move = 'd';
        children.add(child);
      // }
    }

    if (emptyTileIndex + 3 <= 8) {                                                      // Precondition for up move
      State child = new State(this.puzzleStructure.clone(), this);
      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex + 3]; // Swap zero and tile below
      child.puzzleStructure[emptyTileIndex + 3] = 0;

      // if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex + 3;
        child.parent = this;
        child.move = 'u';
        children.add(child);
      // }
    }

    if (emptyTileIndex % 3 == 0 || emptyTileIndex % 3 == 1) {                           // Precondition for left move
      State child = new State(this.puzzleStructure.clone(), this);                    
      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex + 1]; // Swap zero and tile on the left
      child.puzzleStructure[emptyTileIndex + 1] = 0;

      // if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex + 1;
        child.parent = this;
        child.move = 'l';
        children.add(child);
      // }
    }

    if (emptyTileIndex % 3 == 1 || emptyTileIndex % 3 == 2) {                           // Precondition for right move
      State child = new State(this.puzzleStructure.clone(), this);
      child.puzzleStructure[emptyTileIndex] = this.puzzleStructure[emptyTileIndex - 1]; // Swap zero and tile on the right
      child.puzzleStructure[emptyTileIndex - 1] = 0;

      // if (!RedundantState(child)) {
        child.emptyTileIndex = emptyTileIndex - 1;
        child.parent = this;
        child.move = 'r';
        children.add(child);
      // }
    }
    return children;
  }

  // Count of misplaced tiles
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
      if (Arrays.equals(state.puzzleStructure, ancestor.puzzleStructure)) {
        return true;
      } else {
        ancestor = ancestor.parent;
      }
    }
    return false;
  }

}
