package knapsackProblem;

public class ListNode {
    double[] valueWeight;
    double[] solutionInfo;
    ListNode left;
    ListNode right;

    ListNode(double[] valueWeight, double[] solutionInfo) {
        this.valueWeight = valueWeight;
        this.solutionInfo = solutionInfo;
        right = null;
        left = null;
    }
}
