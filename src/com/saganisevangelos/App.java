//Author Evangelos Saganis
package com.saganisevangelos;

import com.saganisevangelos.view.ViewFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{
	
	public static void main (String args[]){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewFactory viewFactory = ViewFactory.defaultFactory;
		
		primaryStage.setScene(viewFactory.getMainScene());
		primaryStage.show();
		
		
	}
}
