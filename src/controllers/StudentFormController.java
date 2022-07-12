package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import model.Student;
import util.CrudUtil;

import java.sql.SQLException;

public class StudentFormController {
    public TextField txtId;
    public TextField txtNAme;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtNIC;
    public Button btnSaveStudent;
    public Button btnDelete;
    public Button btnNew;
    public TableView tblStudent;
    public TableColumn RID;
    public TableColumn RNAme;
    public TableColumn REmail;
    public TableColumn RContact;
    public TableColumn RAddress;
    public TableColumn RNic;

    public void textFeild_key_releseed2(KeyEvent keyEvent) {
    }

    public void HOnAction(ActionEvent actionEvent) {
    }

    public void NewOnAction(ActionEvent actionEvent) {
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
    }

    public void SaveOnAction(ActionEvent actionEvent) {
        Student c = new Student(
                txtId.getText(),
                txtNAme.getText(),
                txtEmail.getText(),
                txtContact.getText(),
                txtAddress.getText(),
                txtNIC.getText()

        );

        try {
            if (CrudUtil.execute("INSERT INTO student VALUES (?,?,?,?,?,?)",c.getStudent_id(),c.getName(),c.getEmail(),c.getContact(),c.getAddress(),c.getNic())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
