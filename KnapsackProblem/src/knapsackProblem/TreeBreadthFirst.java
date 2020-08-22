package knapsackProblem;
import java.util.LinkedList;
import java.util.Queue;

public class TreeBreadthFirst {
    ListNode root;
    Queue queue = new LinkedList();
    double[][] elements;
    Knapsack game;
    double[] bestSolution;
    double[] bestSolutionInfo;
    int whereToEndPushing;
    int nodeIdCounter = 0;

    public TreeBreadthFirst() {
        game = new Knapsack();
        elements = game.getElements();
        whereToEndPushing = elements.length;
        double[] aa = {nodeIdCounter, 0, 0};
        bestSolution = aa;
        bestSolutionInfo = new double[elements.length];
        double[] solutionInfo = new double[elements.length];
        for (int i = 0 ; i< elements.length ; i++) {
            solutionInfo[i] = -1;
            bestSolutionInfo[i] = -1;
        }
        root = new ListNode(bestSolution, solutionInfo);
        queue.add(root);
    }

    public static void main(String args[]) {
        long startTime = System.nanoTime();
        TreeBreadthFirst newGame = new TreeBreadthFirst();
        double[] solution = newGame.createTreeFindSolution();
        newGame.printSolution(newGame.bestSolutionInfo, newGame);
        long endTime = System.nanoTime();
        System.out.println("\nThat took " + (endTime - startTime) / 1000000 + " miliseconds");
    }

    private void printSolution(double[] bestSolutionInfo, TreeBreadthFirst newGame){
        System.out.print("\nYou should take element(s) with id's: ");
        for (int i = 0; i < bestSolutionInfo.length; i++) {
            if(bestSolutionInfo[i] == 1){
                Double temp = game.getElementids()[i];
                System.out.print("\t"+ temp.intValue());
            }
        }
    }

    private int getLevelOfUnaddedNode(ListNode willBeAdded){
    int whichLevel= -1;
    if(willBeAdded.solutionInfo[willBeAdded.solutionInfo.length-1] != -1)
        whichLevel = elements.length+1;
    else {
        for (int i = 0; i < willBeAdded.solutionInfo.length - 1; i++) {
            if (willBeAdded.solutionInfo[i] != -1 && willBeAdded.solutionInfo[i + 1] == -1)
                whichLevel = i + 2;
        }
    }
    return whichLevel;
    }

    private ListNode findTheParent(ListNode isThisParent, ListNode findMyParent){
        boolean isMyParent = true;
        int level = getLevelOfUnaddedNode(findMyParent);
        for (int i = 0; i < level-2 ; i++) {
            if(findMyParent.solutionInfo[i] != isThisParent.solutionInfo[i]){
                isMyParent = false;
            }
        }
        if(isThisParent.solutionInfo[level-2] != -1){
            isMyParent = false;
        }

        if(isMyParent)
            return isThisParent;
        ListNode node1,node2;
        if(isThisParent.left != null) {
            node1 = findTheParent(isThisParent.left, findMyParent);
            if(node1 != null)
                return node1;
        }
        if(isThisParent.right != null) {
            node2 = findTheParent(isThisParent.right, findMyParent);
            if(node2 != null)
                return node2;
        }
        return null;
    }

    private double[] createTreeFindSolution() {
        ListNode theParent = (ListNode)queue.peek();
        while (!queue.isEmpty()) {
            ListNode current = (ListNode) queue.remove();
            int level = -1;
            double leftOrRight = -1;
            int levelOfNode;
            if(!theParent.equals(current)) {
                //BEST SOLUTION FINDING
                levelOfNode = getLevelOfUnaddedNode(current);
                if (levelOfNode == elements.length + 1 && current.valueWeight[2] > bestSolution[2]) {
                    bestSolution = current.valueWeight;
                    bestSolutionInfo = current.solutionInfo;
                    theParent = findTheParent(root, current);
                    leftOrRight = current.solutionInfo[levelOfNode - 2];
                    if (leftOrRight == 0)
                        theParent.right = current;
                    else
                        theParent.left = current;
                } else if (levelOfNode != elements.length + 1) {
                    theParent = findTheParent(root, current);
                    leftOrRight = current.solutionInfo[levelOfNode - 2];
                    if (leftOrRight == 0)
                        theParent.right = current;
                    else
                        theParent.left = current;
                }
            }
            boolean isLeafNode = true;
            for (int i = 0; i < current.solutionInfo.length; i++) {
                if(current.solutionInfo[i] == -1) {
                    isLeafNode = false;
                    break;
                }
            }
            if(isLeafNode)
                continue;
            nodeIdCounter++;
            double tempLeftNodeId = nodeIdCounter;
            double tempLeftWeight = current.valueWeight[1] + elements[getLevel(this.root, current.valueWeight) - 1][3];
            double tempLeftValue = current.valueWeight[2] + elements[getLevel(this.root, current.valueWeight) - 1][2];
            double[] tempLeftValueWeight = {tempLeftNodeId, tempLeftWeight, tempLeftValue};
            nodeIdCounter++;
            double[] tempRightValueWeight = {nodeIdCounter, current.valueWeight[1], current.valueWeight[2]};
            ListNode tempRight = new ListNode(tempRightValueWeight, current.solutionInfo.clone());
            levelOfNode = getLevelOfUnaddedNode(tempRight);
            if(levelOfNode != elements.length+1 || tempRightValueWeight[2] > bestSolution[2]) {
                tempRight.solutionInfo[getLevel(this.root, current.valueWeight) - 1] = 0;
                queue.add(tempRight);
            }
            ListNode tempLeft = new ListNode(tempLeftValueWeight, current.solutionInfo.clone());
            levelOfNode = getLevelOfUnaddedNode(tempLeft);
            if(tempLeftValueWeight[1] <= game.getMaximumWeight()) {
                if (levelOfNode != elements.length+1 || tempLeftValueWeight[2] > bestSolution[2]) {
                    tempLeft.solutionInfo[getLevel(this.root, current.valueWeight) - 1] = 1;
                    queue.add(tempLeft);
                }
            }
        }
        return bestSolution;
    }

    //Getting level of a node
    int getLevel(ListNode node, double[] data) {
        return getLevelUtil(node, data, 1);
    }

    int getLevelUtil(ListNode node, double[] data, int level) {
        if (node == null)
            return 0;

        if (node.valueWeight[0] == data[0])
            return level;

        int downlevel = getLevelUtil(node.left, data, level + 1);
        if (downlevel != 0)
            return downlevel;

        downlevel = getLevelUtil(node.right, data, level + 1);
        return downlevel;
    }

}