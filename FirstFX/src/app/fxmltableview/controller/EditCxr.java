package app.fxmltableview.controller;

import java.io.IOException;

import app.fxmltableview.model.Carrier;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditCxr extends Application {
	
	public boolean confirmed = false;
	public Carrier cxr = null;
	@FXML
	public Button confirmBtn;
	@FXML
	private TextField idCxr;
	@FXML
	private TextField codeCxr;
	@FXML
	private TextField nameCxr;

	@FXML
    public void initialize(){}
	
	void initData(Carrier cxr) {
		idCxr.setText(cxr.getId());
		codeCxr.setText(cxr.getCode());
		nameCxr.setText(cxr.getName());
	}
	
	@FXML
	public void confirmEdit(){
		cxr = new Carrier(idCxr.getText(), codeCxr.getText(), nameCxr.getText());
		confirmed = true;
        this.closeEdit();
	}
	
	@FXML
	public void closeEdit(){
		Stage stage = (Stage) idCxr.getScene().getWindow();
		confirmed = true;
	    stage.close();
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
