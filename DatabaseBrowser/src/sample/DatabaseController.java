package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DatabaseController implements Initializable {
    @FXML
    Label jdbcDriver;
    @FXML
    TextArea usernameField;
    @FXML
    Button connectButton;
    @FXML
    ComboBox schemaSelection;
    @FXML
    TextArea passwordField;
    @FXML
    Label feedbackLabel;
    @FXML
    ComboBox tableNameSelection;
    @FXML
    TableView databaseTable;
    @FXML
    Button insertButton;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;
    @FXML
    VBox operationPane;

    private ObservableList<ObservableList> currentTable;
    private Connection connection;
    private ArrayList<String> currentTablePrimaryKeys;
    private String driver = "com.mysql.cj.jdbc.Driver";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectButton.setOnAction(event -> initializeDB());
        tableNameSelection.valueProperty().addListener((observable, oldValue, newValue) -> {
            buildTable(newValue.toString());
            operationPane.getChildren().clear();
        });

        schemaSelection.valueProperty().addListener((observable, oldValue, newValue) -> {
            listTables();
        });
        jdbcDriver.setText(driver);
    }

    private void initializeDB() {
        try {
            Class.forName(driver).newInstance();
            String username = usernameField.getText();
            String password = passwordField.getText();

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey",
                    username,
                    password);
            if (connection.isValid(0)) {
                feedbackLabel.setText("Connected");
                feedbackLabel.setStyle("-fx-background-color: rgba(80,255,98,0.86); ");
                listSchemas();
            }
            EventHandler buttonHandler =
                    (EventHandler<ActionEvent>) event -> handleOperationRequest(((Button)event.getSource()).getText());

            insertButton.setOnAction(buttonHandler);
            updateButton.setOnAction(buttonHandler);
            deleteButton.setOnAction(buttonHandler);


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void findPrimaryKeysOfTable(){
        currentTablePrimaryKeys = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the columns in the database
            ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableNameSelection.getValue().toString());

            //Adding the column name
            while (primaryKeys.next()) {
                currentTablePrimaryKeys.add(primaryKeys.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void buildTable(String tableName){
        try {
            databaseTable.getItems().clear();
            databaseTable.getColumns().clear();
            currentTable = FXCollections.observableArrayList();
            String query = "SELECT * FROM " + tableName;
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            for(int i = 0 ; i < resultSet.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((
                        Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                        new SimpleStringProperty(param.getValue().get(j).toString()));
                databaseTable.getColumns().addAll(col);
            }

            while(resultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
                    row.add(resultSet.getString(i));
                }
                currentTable.add(row);
            }

            databaseTable.setItems(currentTable);
            findPrimaryKeysOfTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getSelectedRow(){
        return databaseTable.getSelectionModel().getSelectedIndex();
    }

    private void handleOperationRequest(String requestType){
        if(requestType.equals("INSERT") || requestType.equals("UPDATE"))
            buildOperationPane(requestType);
        else if(requestType.equals("DELETE"))
            runDeleteQuery();
    }

    private void buildOperationPane(String operation){
        ObservableList<HBox> items = FXCollections.observableArrayList();
        operationPane.getChildren().clear();
        Double width = operationPane.getWidth();
        int numberOfColumns = currentTable.get(0).size();
        operationPane.setSpacing(30);
        try {
            for (int i = 0; i < numberOfColumns; i++) {
                HBox hBox = new HBox();
                hBox.setSpacing(30);
                hBox.setPrefWidth(width);

                //--- Setting label for column name
                Label columnName = new Label();
                columnName.setFont(new Font(20));
                columnName.setAlignment(Pos.CENTER_RIGHT);
                columnName.setPrefSize(width * 2 / 5, 50);
                columnName.setText(((TableColumn) databaseTable.getColumns().get(i)).getText() + ":");

                //--- Setting text field for the column
                TextField columnContent = new TextField();
                columnContent.setPrefSize(width * 3 / 5, 50);
                columnContent.setFont(new Font(20));
                columnContent.setAlignment(Pos.CENTER_LEFT);

                // Set text field content
                if (operation.equals("UPDATE")) {
                    columnContent.setText(String.valueOf(currentTable.get(getSelectedRow()).get(i)));
                }

                hBox.getChildren().addAll(columnName, columnContent);

                items.add(hBox);
            }
            HBox buttonBox = new HBox();
            buttonBox.setAlignment(Pos.CENTER);
            Button operationButton = new Button();
            operationButton.setPrefSize(width / 3, 50);
            operationButton.setFont(new Font(20));
            operationButton.setText(operation);
            operationButton.setOnAction(event -> runUpdateAndInsertQueries(operationButton.getText()));
            buttonBox.getChildren().add(operationButton);
            items.add(buttonBox);
            operationPane.getChildren().addAll(items);

        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "You need to select a row from the table!");
        }
    }

    private ArrayList<String[]> getValuesFromForm(){
        ArrayList<String[]> formContent = new ArrayList<>();
        String[] formContentContent;
        ObservableList childrenOfVBox = operationPane.getChildren();

        for (int i = 0; i < childrenOfVBox.size()-1 ; i++) {
            formContentContent = new String[2];
            HBox hBox = (HBox)childrenOfVBox.get(i);
            ObservableList childrenOfHBox = hBox.getChildren();
            formContentContent[0] = ((Label)childrenOfHBox.get(0)).getText();
            formContentContent[1] = ((TextField)childrenOfHBox.get(1)).getText();
            formContent.add(formContentContent);
        }
        return formContent;
    }

    private void runUpdateAndInsertQueries(String type) {
        ArrayList<String[]> formContent = getValuesFromForm();
        String tableName = (String)tableNameSelection.getValue();
        int numberOfColumns = currentTable.get(0).size();
        PreparedStatement preparedStatement;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            switch (type) {
                case "UPDATE":
                    //Prepare set values
                    for (int i = 0; i < numberOfColumns; i++) {
                        stringBuilder.append(formContent.get(i)[0].substring(0, formContent.get(i)[0].length() - 1) + " = ?, ");
                    }
                    String updateSetValues = stringBuilder.substring(0, stringBuilder.length() - 2);

                    //Prepare where values
                    stringBuilder = new StringBuilder();
                    for (int i = 0; i < databaseTable.getColumns().size(); i++) {
                        if (currentTablePrimaryKeys.contains(((TableColumn) databaseTable.getColumns().get(i)).getText())) {
                            String valueOfCell = (String) currentTable.get(databaseTable.getSelectionModel().getFocusedIndex()).get(i);
                            stringBuilder.append(((TableColumn) databaseTable.getColumns().get(i)).getText() + " = \"" + valueOfCell + "\"");
                            stringBuilder.append(" AND ");
                        }
                    }
                    String updateWhereValues = stringBuilder.substring(0, stringBuilder.length() - 4);

                    preparedStatement = connection.prepareStatement(
                            "UPDATE " + tableName + " SET " + updateSetValues + " WHERE " + updateWhereValues);

                    // Set values
                    for (int i = 0; i < formContent.size(); i++) {
                        preparedStatement.setString(i + 1, formContent.get(i)[1]);
                    }

                    if(isUserSure("update")){
                        preparedStatement.executeUpdate();
                        buildTable(tableName);
                    }
                    break;

                case "INSERT":
                    //Prepare values
                    stringBuilder = new StringBuilder("(");
                    for (int i = 0; i < numberOfColumns; i++) {
                        stringBuilder.append("?, ");
                    }
                    String insertValuesString = stringBuilder.substring(0, stringBuilder.length() - 2);
                    insertValuesString = insertValuesString + ")";

                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO " + tableName + " VALUES " + insertValuesString);

                    //Set values
                    for (int i = 0; i < formContent.size(); i++) {
                        preparedStatement.setString(i + 1, formContent.get(i)[1]);
                    }
                    preparedStatement.executeUpdate();
                    buildTable(tableName);
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void runDeleteQuery(){

        try {
            currentTable.get(getSelectedRow());
            String tableName = (String) tableNameSelection.getValue();

            //Prepare where values
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < databaseTable.getColumns().size(); i++) {
                if (currentTablePrimaryKeys.contains(((TableColumn) databaseTable.getColumns().get(i)).getText())) {
                    String valueOfCell = (String) currentTable.get(databaseTable.getSelectionModel().getFocusedIndex()).get(i);
                    stringBuilder.append(((TableColumn) databaseTable.getColumns().get(i)).getText() + " = \"" + valueOfCell + "\"");
                    stringBuilder.append(" AND ");
                }
            }
            String deleteWhereValues = stringBuilder.substring(0, stringBuilder.length() - 4);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM " + tableName + " WHERE " + deleteWhereValues);

            if(isUserSure("delete")){
                preparedStatement.executeUpdate();
                buildTable(tableName);
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            JOptionPane.showMessageDialog(null, "You need to select a row from the table!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private boolean isUserSure(String change){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("You are making a change");
        alert.setHeaderText("Hey! Be careful");
        alert.setContentText("Are you sure you want to " + change + " this row of the table?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void listTables(){
        ArrayList<String> tables = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + schemaSelection.getValue() + "?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey",
                    usernameField.getText(),
                    passwordField.getText());
            ResultSet resultSet =
                    connection.getMetaData().getTables(connection.getCatalog(), null, "%", new String [] {"TABLE"});
            while (resultSet.next())
                tables.add(resultSet.getString(3));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        ObservableList tableNames = FXCollections.observableArrayList(tables);
        tableNameSelection.setItems(tableNames);
        tableNameSelection.getSelectionModel().select(0);
        buildTable(tableNameSelection.getItems().get(0).toString());
    }

    private void listSchemas(){
        ArrayList<String> schemas = new ArrayList<>();
        try {
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next())
                schemas.add(resultSet.getString("TABLE_CAT"));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        ObservableList schemaNames = FXCollections.observableArrayList(schemas);
        schemaSelection.setItems(schemaNames);

    }
}
