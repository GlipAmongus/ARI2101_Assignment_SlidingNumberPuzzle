public class State {
  public int emptyTileIndex; // Array index of zero (empty tile) (i.e. {1,2,3,4,5,6,7,8,0} eTI = 8)

  public boolean redundantState;

  public int gCost; // Cost from start state to state n
  public int hCost; // Cost from state n to goal state (when using hueristic)
  public int misplacedTiles; // Count of number of displaced tiles

  // Will redundancy be checked by retracing and comparing states to parents, or
  // keeping a dynamic array during run time?
  public State parent; // Store state's parent state
  public State[] children; // Current state's reachable children

  public State[] Children() {

    return children;
  }

  public int fCost() {
    return hCost + gCost;
  }

  public void redundantState(State newborn) {
    State ancestor = newborn.parent;
    while(ancestor != null){
      if(newborn.equals(ancestor)){
        newborn.redundantState = true;
        return;
      }else{
        ancestor = ancestor.parent;
      }
    }
    newborn.redundantState = false;
  }
}
