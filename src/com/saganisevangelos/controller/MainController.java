//Author Evangelos Saganis

package com.saganisevangelos.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.saganisevangelos.controller.services.CreateRegisterEmailAcountService;
import com.saganisevangelos.model.EmailMessageBean;
import com.saganisevangelos.model.folder.EmailFolderBean;
import com.saganisevangelos.model.table.BoldableRawFactory;
import com.saganisevangelos.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainController extends AbstractController implements Initializable {
	
	public MainController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	
	@FXML
    private TreeView<String> emailTree;
	private TreeItem<String> root = new TreeItem<String>();
	private MenuItem showDetails = new MenuItem("show details");
	
	@FXML
    private TableView<EmailMessageBean> emailTable;
	
	@FXML
    private TableColumn<EmailMessageBean, String> subjectCol;

    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;

    @FXML
    private TableColumn<EmailMessageBean, String> sizeCol;
	
	@FXML
    private Button Button1;
	
	@FXML
	private WebView messageRenderer;
	
	@FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    void Button1Action(javafx.event.ActionEvent event) {
    	
    }
    
    @FXML
    void login() {
    	EmailFolderBean<String> root = new EmailFolderBean<String>("");
		emailTree.setRoot(root);
		emailTree.setShowRoot(false);
    	CreateRegisterEmailAcountService c = 
    			new CreateRegisterEmailAcountService(
    					emailInput.getText(), passwordInput.getText(), root);
		c.start();
    }
	
    @FXML
    void unreadAction() {
    	EmailMessageBean selectedMessage = getModelAccess().getSelectedMessage();
    	if(selectedMessage!=null){
	    	selectedMessage.setReadProperty(!getModelAccess().getSelectedMessage().getReadProperty().get());
	    	EmailFolderBean<String> selectedFolder = getModelAccess().getSelectedFolder();
	    	if(selectedFolder!= null){
	    		if(!selectedMessage.isRead())
	    			selectedFolder.incrementUnreadMessagesCount(1);
	    		else
	    			selectedFolder.decrementUnreadMessagesCount();
	    	}
    	}
    }
    		
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ViewFactory viewFactory = ViewFactory.defaultFactory;
		
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));
		
		
		sizeCol.setComparator(new Comparator<String>() {
			Integer int1,int2;
			@Override
			public int compare(String o1, String o2) {
				int1 = EmailMessageBean.formattedValues.get(o1);
				int2 = EmailMessageBean.formattedValues.get(o2);
				return int1.compareTo(int2);
			}
		});
		
		
		emailTree.setOnMouseClicked(e->{
			EmailFolderBean<String> item =(EmailFolderBean<String>) emailTree.getSelectionModel().getSelectedItem();
			if(item!= null && !item.isTopElement()){
				emailTable.setItems(item.getData());
				getModelAccess().setSelectedFolder(item);
				getModelAccess().setSelectedMessage(null);
			}
		});
		
		emailTable.setRowFactory(e-> new BoldableRawFactory<>());
		emailTable.setOnMouseClicked(e->{
			EmailMessageBean message = emailTable.getSelectionModel().getSelectedItem();
			if(message!=null){
				getModelAccess().setSelectedMessage(message);
				messageRenderer.getEngine().loadContent(message.getContent());
			}
		});
		
		//right click "showDetails" will appear
		emailTable.setContextMenu(new ContextMenu(showDetails));
		showDetails.setOnAction(e->{
			Stage stage = new Stage();
			stage.setScene(viewFactory.getEmailDetailsScene());
			stage.show();
		});
		
	}
	
	
}
