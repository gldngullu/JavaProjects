package knapsackProblem;

public class Knapsack {
    private int numberOfElements = 11;
    private int maximumWeight = 420;
    private double[] elementValues = {20, 40, 50, 36, 26, 64, 54, 18, 47, 28, 25};
    private double[] elementWeights = {15, 32, 60, 80, 43, 120, 77, 6, 93, 35, 37};
    /* private int numberOfElements = 3;
    private int maximumWeight = 9;
    private double[] elementWeights = {6, 8, 3};
    private double[] elementValues = {3,10,4};*/
    private double[] elementids;
    private double[][] elements;//3rd element contains valueWeight, 4th element contains weight

    public Knapsack(){
        elements = new double[numberOfElements][4];
        elementids = new double[numberOfElements];
        for (int i = 1; i <= elementids.length ; i++) {
            elementids[i-1] = i;
        }
        fillTheValuePerWeightArray();
        //sort(elements, 0, elements.length-1);

    }

    private void fillTheValuePerWeightArray(){
        for (int i = 0; i< numberOfElements; i++){
            elements[i][0] = i;
            elements[i][1] = elementValues[i] / elementWeights[i];
            elements[i][2] = elementValues[i];
            elements[i][3] = elementWeights[i];
        }
    }

    void merge(double arr[][], int l, int m, int r){
        int n1 = m - l + 1;
        int n2 = r - m;
        double L[][] = new double [n1][2];
        double R[][] = new double [n2][2];

        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i][1] <= R[j][1]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    void sort(double arr[][], int l, int r) {
        if (l < r) {
            int m = (l+r)/2;
            sort(arr, l, m);
            sort(arr , m+1, r);
            merge(arr, l, m, r);
        }
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int getMaximumWeight() {
        return maximumWeight;
    }

    public double[] getElementValues() {
        return elementValues;
    }

    public double[] getElementWeights() {
        return elementWeights;
    }

    public double[][] getElements() {
        return elements;
    }

    public double[] getElementids() {
        return elementids;
    }
}
