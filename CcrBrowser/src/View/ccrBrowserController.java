package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ccrBrowserController implements Initializable {
    @FXML
    TextField yearSelectionForGraduates;
    @FXML
    TextField slotSelectionForCourses;
    @FXML
    TextField yearSelectionForCourses;
    @FXML
    TableView coursesTable;
    @FXML
    TableView graduatesTable;

    private Connection connection;
    private ResultSet coursesResultSet;
    private ResultSet graduatesResultSet;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver).newInstance();
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/w13courses?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey",
                    username,
                    password);
            if (connection.isValid(0)) {
                coursesResultSet = connection.createStatement().executeQuery("SELECT * FROM course");
                buildTable(coursesTable, "course", coursesResultSet);
                graduatesResultSet = connection.createStatement().executeQuery("SELECT * FROM student");
                buildTable(graduatesTable, "student", graduatesResultSet);
            }

            slotSelectionForCourses.textProperty().addListener((observable, oldValue, newValue) -> selectCourses());
            yearSelectionForCourses.textProperty().addListener((observable, oldValue, newValue) -> selectCourses());
            yearSelectionForGraduates.textProperty().addListener((observable, oldValue, newValue) -> selectGraduates());


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private ArrayList findPrimaryKeysOfTable(String tableName) {
        ArrayList currentTablePrimaryKeys = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
            while (primaryKeys.next()) {
                currentTablePrimaryKeys.add(primaryKeys.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentTablePrimaryKeys;
    }

    private void buildTable(TableView table, String tableName, ResultSet resultSet) {
        try {
            table.getItems().clear();
            table.getColumns().clear();
            ObservableList tableContent = FXCollections.observableArrayList();

            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((
                        Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                        new SimpleStringProperty(param.getValue().get(j).toString()));
                table.getColumns().addAll(col);
            }

            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                tableContent.add(row);
            }
            table.setItems(tableContent);
            findPrimaryKeysOfTable(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectGraduates() {
        String year = yearSelectionForGraduates.getText();
        String query;
        try {
            if (year.equals(""))
                query = "SELECT * FROM student";
            else {
                int yearValue = Integer.parseInt(year);
                query = "SELECT * FROM student WHERE graduation_date > '" + (yearValue - 1) + "-12-31' AND graduation_date < '" + (yearValue + 1) + "-01-01'";
            }
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            buildTable(graduatesTable, "student", resultSet);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void selectCourses() {
        String slot = slotSelectionForCourses.getText();
        String year = yearSelectionForCourses.getText();
        String query;
        try {
            if (slot.equals("") && year.equals(""))
                query = "SELECT * FROM course";
            else if (slot.equals("") && !year.equals("")) {
                query = "SELECT * FROM course WHERE course.code IN (SELECT course_code FROM enrollment WHERE year = " + year + ")";
            } else if (!slot.equals("") && year.equals(""))
                query = "SELECT * FROM course WHERE slotCode = '" + slot + "'";
            else {
                query = "SELECT *" +
                        "FROM course WHERE slotCode = '" + slot + "' AND course.code IN (SELECT course_code FROM enrollment WHERE year = " + year + ")";
            }
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            buildTable(coursesTable, "course", resultSet);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


}
