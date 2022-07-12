package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Student;
import util.CrudUtil;

import java.sql.ResultSet;
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
    public TextField txtId1;


    public void initialize(){

        RID.setCellValueFactory(new PropertyValueFactory("student_id"));
        RNAme.setCellValueFactory(new PropertyValueFactory("name"));
        REmail.setCellValueFactory(new PropertyValueFactory("email"));
        RContact.setCellValueFactory(new PropertyValueFactory("contact"));
        RAddress.setCellValueFactory(new PropertyValueFactory("address"));
        RNic.setCellValueFactory(new PropertyValueFactory("nic"));

        try {
            loadAllCustomers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomers() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM student");
        ObservableList<Student> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Student(
                            result.getString("student_id"),
                            result.getString("student_name"),
                            result.getString("email"),
                            result.getString("contact"),
                            result.getString("address"),
                            result.getString("nic")


                    )
            );
        }
        tblStudent.setItems(obList);

    }

    public void textFeild_key_releseed2(KeyEvent keyEvent) {
    }

    public void HOnAction(ActionEvent actionEvent) {
        search();

        Student c = new Student(
                txtId.getText(),
                txtNAme.getText(),
                txtEmail.getText(),
                txtContact.getText(),
                txtAddress.getText(),
                txtNIC.getText()
        );

        try{
            boolean isUpdated = CrudUtil.execute("UPDATE student SET student_name=? , email=?, contact=?, address=? , nic=? WHERE student_id=?",c.getName(),c.getEmail(),c.getContact(),c.getAddress(),c.getNic(),c.getStudent_id());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        }catch (SQLException | ClassNotFoundException e){

        }

    }

    public void NewOnAction(ActionEvent actionEvent) {
        search();
    }




    public void DeleteOnAction(ActionEvent actionEvent) {
        try{
            if (CrudUtil.execute("DELETE FROM student WHERE student_id=?",txtId1.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    private void search() {


        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM student WHERE student_id=?",txtId1.getText());

            if (result.next()) {
                txtNAme.setText(result.getString(2));
                txtEmail.setText(result.getString(3));
                txtContact.setText(result.getString(4));
                txtAddress.setText(result.getString(5));
                txtNIC.setText(result.getString(6));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
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
