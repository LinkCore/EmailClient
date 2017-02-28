//Author Evangelos Saganis

package com.saganisevangelos.model.folder;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Flags.Flag;

import com.saganisevangelos.model.EmailMessageBean;
import com.saganisevangelos.view.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailFolderBean<T> extends TreeItem<String> {
	private boolean topElement = false;
	private int unreadMessageCount;
	private String name, completeName;
	private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
	
	
	//Constructor for top elements
	public EmailFolderBean (String value){
		super(value,ViewFactory.defaultFactory.resolveIcon(value));
		this.name =value;
		this.completeName = value;
		data = null;
		topElement = true;
		this.setExpanded(true);
	}
	
	//Constructor for normal elements
	public EmailFolderBean (String value,String completeName){
		super(value,ViewFactory.defaultFactory.resolveIcon(value));
		this.name =value;
		this.completeName = completeName;
	}
	
	private void updateValue(){
		if(unreadMessageCount>0){
			this.setValue((String) (name + "(" + unreadMessageCount +")"));
		} else
			this.setValue(name);
	}
		
	public void incrementUnreadMessagesCount(int newMessages){
		unreadMessageCount +=newMessages;
		System.out.println("e");
		updateValue();
	}
	
	public void decrementUnreadMessagesCount(){
		unreadMessageCount --;
		updateValue();
	}
	
	public void addEmail(Message message) throws MessagingException{
		boolean isRead = message.getFlags().contains(Flag.SEEN);
		EmailMessageBean m = new EmailMessageBean(message.getSubject(), 
				message.getFrom()[0].toString(), message.getSize(), "", isRead);
		data.add(m);
		if(!m.isRead())
			incrementUnreadMessagesCount(1);
		else
			decrementUnreadMessagesCount();
	}
	
	public boolean isTopElement(){
		return topElement;
	}
	
	public ObservableList <EmailMessageBean> getData(){
		return data;
	}
}
