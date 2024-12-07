import java.util.Comparator;

public class StateComparators {
    public static Comparator<State> byHeuristicCost = Comparator.comparingInt(s -> s.hCost);
    public static Comparator<State> fixedhue = (a, b) -> Integer.compare(b.hCost, a.hCost);
    public static Comparator<State> byDepth = Comparator.comparingInt(s -> s.fCost());

    public static Comparator<State> byAstar = (s1, s2) -> {
        // Example: prioritize heuristic cost, then depth.
        int result = Integer.compare(s1.fCost(), s2.fCost());
        if (result == 0) {
            result = Integer.compare(s1.hCost, s2.hCost);
        }
        return result;
    };
}