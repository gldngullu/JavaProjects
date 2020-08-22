package kakurogame;

import java.io.*;
import java.util.Scanner;


public class Game {

    static int xDim;
    static int yDim;
    Queue[] gameQueues;
    Scanner scn, scn2;
    String[] gameInfo;

    public static void main(String[] args) throws IOException {
        new Game();
    }

    public Game() throws IOException {
        File newGame = new File("resources//file1.txt");
        scn = new Scanner(newGame);
        setGameSize(scn);
        setGameInfo();
        gameQueues = new Queue[30];
        gameQueues = setHorizontalQueues();
        gameQueues = setVerticalQueues();
        new solution(gameQueues[0]);

    }

    public void setGameInfo() {
        gameInfo = new String[(xDim * yDim)];
        for (int i = 0; i < gameInfo.length; i++) {
            gameInfo[i] = scn.nextLine();
        }
    }

    public char getSquareType(String type) {
        if (type.equals("NoClue"))
            return 'S';
        if (type.charAt(0) == 'C')
            return 'C';
        else
            return 'P';
    }


    public Queue[] setHorizontalQueues() {
        int queueLength = 0;
        int queueCount = 0;
        int sumNumber = 0;
        int lineCount = 0;
        for (int i = 0; i < yDim; i++) {
            for (int j = 0; j < xDim; j++) {
                String lineContent = gameInfo[lineCount];
                char squareType = getSquareType(lineContent);
                if (queueLength != 0 && (squareType == 'C' || squareType == 'S')) {
                    gameQueues[queueCount] = new Queue(queueLength + 1);
                    Element<Integer> sum = new Element<>(sumNumber);
                    gameQueues[queueCount].enqueue(sum);
                    //System.out.println(gameQueues[queueCount].array[0].data);
                    queueCount++;
                    queueLength = 0;
                }
                if (squareType == 'C') {
                    queueLength = 0;
                    String sumString = String.valueOf(lineContent.charAt(5));
                    char secondDigit = lineContent.charAt(6);
                    if (secondDigit != ' ')
                        sumString += String.valueOf(secondDigit);
                    sumNumber = Integer.valueOf(sumString);
                } else if (squareType == 'P')
                    queueLength++;
                lineCount++;
            }
        }
        return gameQueues;
    }


    public Queue[] setVerticalQueues() {
        int queueLength = 0;
        int queueCount = 4;
        int lineCount = 0;
        int sumNumber = 0;
        for (int i = 0; i < xDim; i++) {
            for (int j = 0; j < gameInfo.length - 1; j += 5) {
                String lineContent = gameInfo[i + j];
                char squareType = getSquareType(lineContent);
                if (queueLength != 0 && (squareType == 'C' || squareType == 'S')) {
                    gameQueues[queueCount] = new Queue(queueLength + 1);
                    Element<Integer> sum = new Element<>(sumNumber);
                    gameQueues[queueCount].enqueue(sum);
                    //System.out.println(gameQueues[queueCount].array[0].data);
                    queueCount++;
                    queueLength = 0;
                }
                if (squareType == 'C') {
                    queueLength = 0;
                    String sumString;
                    char secondDigit = ' ';
                    if (lineContent.charAt(6) == ' ') {
                        sumString = String.valueOf(lineContent.charAt(7));
                        if (lineContent.length() > 8)
                            secondDigit = lineContent.charAt(8);
                    } else {
                        sumString = String.valueOf(lineContent.charAt(8));
                        if (lineContent.length() > 9)
                            secondDigit = lineContent.charAt(9);
                    }
                    if (secondDigit != ' ')
                        sumString += String.valueOf(secondDigit);
                    sumNumber = Integer.valueOf(sumString);
                } else if (squareType == 'P')
                    queueLength++;
                lineCount++;
            }
        }
        return gameQueues;
    }


    public void setGameSize(Scanner scn) {
        String gameSize = scn.nextLine();
        xDim = Character.getNumericValue(gameSize.charAt(0));
        yDim = Character.getNumericValue(gameSize.charAt(2));
    }

    public class solution {

        int number;
        Integer[] possibleSolution;
        LinkedListQueue<Integer[]> solutions;

        public solution(Queue<Integer> solveMe) {
            number = solveMe.array[solveMe.first].data;
            possibleSolution = new Integer[solveMe.N - 1];
            solutions = new LinkedListQueue<>();
            findSolutions(solveMe, solveMe.N, 1);
        }


        public void findSolutions(Queue<Integer> solveMe,int count1, int a) {
            int number = solveMe.array[solveMe.first].data;
            int total = 0;
            int possibleSolutionCount = 0;
            int count = 0;
            int b = a;

            if (count1 == 1) {
                int lastNumber = number - total;
                possibleSolution[solveMe.N - 1] = lastNumber;
            }
                if (total == number) {
                    Node<Integer[]> newSolution = new Node<Integer[]>(possibleSolution);//TODO : Check me. OK, I think i got it
                    solutions.enqueue(newSolution);
                }
                else if(total + a < number && count <= possibleSolution.length) {
                    possibleSolution[possibleSolutionCount] = a;
                    total += a;
                    possibleSolutionCount++;
                    count++;
                }
            b++;
            for (int i = 1; i <= 9; i++) {
                findSolutions(solveMe, count1 - 1, i);
            }



        }
    }


}
