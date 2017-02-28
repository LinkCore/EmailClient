//Author Evangelos Saganis

package com.saganisevangelos.view;

import javax.swing.AbstractAction;

import com.saganisevangelos.controller.AbstractController;
import com.saganisevangelos.controller.EmailDetailsController;
import com.saganisevangelos.controller.MainController;
import com.saganisevangelos.controller.ModelAccess;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ViewFactory {
	
	public static ViewFactory defaultFactory = new ViewFactory();
	
	private final String MAINCSS = "style.css";
	private final String MAIN_LAYOUT = "MainLayout.fxml";
	private final String EMAIL_DETAILS_LAYOUT = "EmailDetailsLayout.fxml";
	
	private ModelAccess modelAccess = new ModelAccess();
	
	private MainController mainController;
	private EmailDetailsController emailDetailsController;
	
	
	public Scene getMainScene(){
		mainController = new MainController(modelAccess);
		return initializeScene(MAIN_LAYOUT, mainController);
	}
	
	public Scene getEmailDetailsScene(){
		emailDetailsController = new EmailDetailsController(modelAccess);
		return initializeScene(EMAIL_DETAILS_LAYOUT, emailDetailsController);
	}
	
	private Scene initializeScene(String fxmlPath,AbstractController controller){
		FXMLLoader loader;// FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
		Parent pane; // or pane
		Scene scene ;//= new Scene(pane);
		try{
			loader = new FXMLLoader(getClass().getResource(fxmlPath));
			loader.setController(controller);
			pane = loader.load();
		} catch (Exception e) {
			return null;
		}
		
		scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource(MAINCSS).toExternalForm());
		return scene;
	}
	
	public Node resolveIcon(String treeItemValue){
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
			try {
				if(lowerCaseTreeItemValue.contains("inbox")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/inbox.png")));
				} else if(lowerCaseTreeItemValue.contains("sent")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/sent2.png")));
				} else if(lowerCaseTreeItemValue.contains("spam")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/spam.png")));
				} else if(lowerCaseTreeItemValue.contains("@")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/email.png")));
				} else{
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
				}
			} catch (NullPointerException e) {
				System.out.println("Invalid image location!!!");
				e.printStackTrace();
				returnIcon = new ImageView();
			}
			
			returnIcon.setFitHeight(16);
			returnIcon.setFitWidth(16);

		return returnIcon;
	}
}
