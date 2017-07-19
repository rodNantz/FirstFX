/*
 * Copyright (c) 2012, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package app.fxmltableview.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import app.fxmltableview.model.Carrier;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMLTableViewController implements Initializable {
    @FXML private TableView<Carrier> tableView;
    EditCxr controller;
    Carrier editedCxr;
    @FXML private TextField idField;
    @FXML private TextField codeField;
    @FXML private TextField nameField;
    
    @FXML
    protected void addCarrier(ActionEvent event) {
        ObservableList<Carrier> data = tableView.getItems();
        data.add(new Carrier(idField.getText(),
            codeField.getText(),
            nameField.getText()
        ));
        
        idField.setText("");
        codeField.setText("");
        nameField.setText("");   
    }
    
    @FXML
    protected void deleteCarrier(ActionEvent event) {
        ObservableList<Carrier> data = tableView.getItems();
        Carrier cxr = tableView.getSelectionModel().getSelectedItem();
        data.remove(cxr);
        tableView.setItems(data);   
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	ObservableList<Carrier> data = tableView.getItems();
    	data.clear();
    	data.add(new Carrier("456","SS","SSD Airlines"));
    	data.add(new Carrier("567","PO","Aécio Airlines"));
    	data.add(new Carrier("666","KA","Capiroto Airlines"));
        
    	tableView.setItems(data);
    }
    
    @FXML
    protected void editCarrier() {
    	Carrier cxr = tableView.getSelectionModel().getSelectedItem();
     	try {
     		Stage secStage = new Stage();
     		secStage.setTitle("FXML Example");
     		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/fxmltableview/view/EditCxr.fxml"));
     		secStage.setScene(
     			    new Scene((Pane) loader.load()) 
     			    );
            secStage.show();
            controller = loader.<EditCxr>getController();
            controller.initData(cxr);
	    	controller.confirmBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					controller.confirmEdit();
					if(controller.cxr != null){
	            		responseEdit(controller.cxr);
	            	}
				}
			});
     	} catch (IOException e) {
 			e.printStackTrace();
 		}
    }
    
    void responseEdit(Carrier cxr){
    	ObservableList<Carrier> data = tableView.getItems();
    	Predicate<Carrier> cxrPredicate = c -> c.getId().equals(cxr.getId());
    	if(data.removeIf(cxrPredicate))
    		data.add(cxr);
        tableView.setItems(data);   
    }

}