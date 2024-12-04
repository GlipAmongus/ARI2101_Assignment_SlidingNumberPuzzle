import java.util.LinkedList;
import java.util.Arrays;

public class SearchAlgorithms {
    public static boolean FindDepthFirstPlan(State initialState){
        int[] goalStateStructure = new int[] {1,2,3,4,5,6,7,8,0};

        LinkedList<State> edgeNodes = new LinkedList<>();
        edgeNodes.add(initialState);

        while(!edgeNodes.isEmpty()){
            State headState = edgeNodes.removeFirst();
            if(Arrays.equals(headState.puzzleStructure, goalStateStructure)){
                //retrace
                return true;
            }

            edgeNodes.addAll(headState.children());
        }
        return false;
    }
}