//Author Evangelos Saganis

package com.saganisevangelos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.saganisevangelos.model.EmailMessageBean;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class EmailDetailsController extends AbstractController  implements Initializable{
	
	public EmailDetailsController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	@FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private WebView emailDetailsWebView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EmailMessageBean message = getModelAccess().getSelectedMessage();
		
		subjectLabel.setText(message.getSubject());
		senderLabel.setText(message.getSender());
		emailDetailsWebView.getEngine().loadContent(getModelAccess().getSelectedMessage().getContent());
		
		
	}
}
