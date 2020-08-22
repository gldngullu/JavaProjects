package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField resultText;
    private String[] operationTrinity = new String[3];
    private String[] exclusiveOperationBinary = new String[2];
    private boolean isCurrentOperationThere = false;
    private boolean isFirstOperationThere = false;
    private String previousEntry = "";
    private String lastChangeFlag = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultText.setEditable(false);
        resultText.setText(previousEntry);
    }

    @FXML
    private void getClickedDeleteButton(ActionEvent actionEvent) {
        String enteredDelete = ((Button) actionEvent.getSource()).getText();
        performDeletion(enteredDelete);
    }

    @FXML
    private void getClickedButton(ActionEvent actionEvent) {
        String stringEntry = ((Button) actionEvent.getSource()).getText();

        if ((stringEntry.matches(".*\\d.*") || stringEntry.equals(".")) && !stringEntry.equals("x^2") && !stringEntry.equals("1/x"))
            handleClickedNumber(stringEntry);
        else if (stringEntry.matches("[+\\-*/]") || stringEntry.equals("="))
            handleOperation(stringEntry);
        else
            handleExclusiveOperation(stringEntry);
    }

    private void performDeletion(String deleteType) {
        if (deleteType.equals("C")) {
            operationTrinity = new String[3];
            isCurrentOperationThere = false;
            isFirstOperationThere = false;
            previousEntry = "";
            displayText("");
        } else if (deleteType.equals("CE")) {
            switch (lastChangeFlag) {
                case "firstElement":
                    displayText("");
                    operationTrinity[0] = null;
                    previousEntry = "";
                    break;
                case "secondElement":
                    displayText(resultText.getText().substring(0, resultText.getLength() - operationTrinity[2].length()));
                    operationTrinity[2] = null;
                    previousEntry = "operation";
                    break;
                case "result":
                    operationTrinity = new String[3];
                    isCurrentOperationThere = false;
                    isFirstOperationThere = false;
                    previousEntry = "";
                    displayText("");
            }
        } else if (deleteType.equals("<-")) {
            switch (lastChangeFlag) {
                case "firstElement":
                    operationTrinity[0] = String.valueOf((int) (Double.parseDouble(operationTrinity[0]) / 10));
                    displayText(resultText.getText().substring(0, resultText.getLength() - 1));
                    previousEntry = "";
                    break;
                case "secondElement":
                    operationTrinity[2] = String.valueOf((int) (Double.parseDouble(operationTrinity[2]) / 10));
                    displayText(resultText.getText().substring(0, resultText.getLength() - 1));
                    previousEntry = "operation";
                    break;
            }
        }
    }

    private void createOperation(String s) {
        if (s.matches("^[-+]?[0-9]+\\.?[0-9]*$") || s.equals(".")) {
            if (operationTrinity[1] == null) {
                operationTrinity[0] = s;
                lastChangeFlag = "firstElement";
            } else {
                operationTrinity[2] = s;
                isFirstOperationThere = true;
                lastChangeFlag = "secondElement";
            }
        } else {
            operationTrinity[1] = s;
            isCurrentOperationThere = true;
            lastChangeFlag = "operation";
        }

    }

    private void handleClickedNumber(String enteredText) {
        String displayText = resultText.getText() + enteredText;
        if (lastChangeFlag.equals("result")) {
            performDeletion("C");
            lastChangeFlag = "begin";
        }
        if (previousEntry.matches(".*\\d.*") || previousEntry.equals(".")) {
            if (!isCurrentOperationThere) {
                operationTrinity[0] = operationTrinity[0].replace("null", "");
                if (operationTrinity[0].contains(".") && enteredText.equals(".")) {
                    displayText = operationTrinity[0];
                    return;
                }
                createOperation(operationTrinity[0] + enteredText);
            } else {
                operationTrinity[2] = operationTrinity[2].replace("null", "");
                if (operationTrinity[2].contains(".") && enteredText.equals(".")) {
                    displayText = resultText.getText();
                    return;
                }
                createOperation(operationTrinity[2] + enteredText);
            }
        } else
            createOperation(enteredText);
        displayText(displayText);
        previousEntry = enteredText;
    }

    private void displayText(String text) {
        resultText.setText(text);
    }

    private void handleExclusiveOperation(String enteredOperation) {
        if (!isCurrentOperationThere)
            exclusiveOperationBinary[0] = operationTrinity[0];
        else
            exclusiveOperationBinary[1] = operationTrinity[2];
        exclusiveOperationBinary[1] = enteredOperation;
        double result = performExclusiveOperation(exclusiveOperationBinary);
        if (!isCurrentOperationThere) {
            operationTrinity[0] = String.valueOf(result);
            displayText(operationTrinity[0]);
        } else {
            operationTrinity[2] = String.valueOf(result);
            displayText(operationTrinity[0] + operationTrinity[1] + operationTrinity[2]);
        }

    }

    private double performExclusiveOperation(String[] operationBinary) {
        double numberToOperate = Double.parseDouble(operationBinary[0]);
        double result = 0;
        switch (operationBinary[1]) {
            case "√x":
                result = Math.sqrt(numberToOperate);
                break;
            case "x^2":
                result = numberToOperate * numberToOperate;
                break;
            case "1/x":
                result = 1 / numberToOperate;
                break;
            case "+/-":
                result = -1 * numberToOperate;
                break;
            case "%":
                if (isCurrentOperationThere)
                    result = numberToOperate / 100;
                break;
        }
        return result;
    }

    private void handleOperation(String enteredOperation) {
        if (previousEntry.matches("[+\\-*/]") && enteredOperation.matches("[+\\-*/]"))
            displayText(resultText.getText().substring(0, resultText.getLength() - 1) + enteredOperation);
        else
            displayText(resultText.getText() + enteredOperation);
        if (isFirstOperationThere) {
            double result = performOperation(operationTrinity);
            operationTrinity = new String[3];
            isFirstOperationThere = false;
            isCurrentOperationThere = false;
            createOperation(String.valueOf(result));
            createOperation(enteredOperation);
            if (enteredOperation.equals("=")) {
                displayText(String.valueOf(result));
                lastChangeFlag = "result";
            } else
                displayText(result + enteredOperation);
        } else {
            createOperation(enteredOperation);
        }
        previousEntry = enteredOperation;
    }

    private double performOperation(String[] operationTrinity) {
        double number1 = Double.parseDouble(operationTrinity[0]);
        double number2 = Double.parseDouble(operationTrinity[2]);
        double result = 0;
        switch (operationTrinity[1]) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                result = number1 / number2;
                break;
        }
        return (double) Math.round(result * 100d) / 100d;
    }

}