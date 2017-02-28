//Author Evangelos Saganis

package com.saganisevangelos.controller.services;

import javax.mail.Folder;

import com.saganisevangelos.model.EmailAccountBean;
import com.saganisevangelos.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFoldersService extends Service<Void>{


	private EmailFolderBean <String> folderRoot;
	private EmailAccountBean emailAccountBean;
	
	public FetchFoldersService(EmailFolderBean<String> folderRoot, EmailAccountBean emailAccountBean) {
		this.folderRoot = folderRoot;
		this.emailAccountBean = emailAccountBean;
	}

	
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				if(emailAccountBean!=null){
					Folder[] folders = emailAccountBean.getStore().getDefaultFolder().list();
					
					for (Folder folder :folders) {
						EmailFolderBean<String> item = new EmailFolderBean<String>(folder.getName(),folder.getFullName());
						folderRoot.getChildren().add(item);
						item.setExpanded(true);
						System.out.println("added a folder "+folder.getName());
						FetchMessagesOnFolderService fetchMessagesService = new FetchMessagesOnFolderService(item,folder);
						fetchMessagesService.start();
						
						Folder[] subFolders = folder.list();
						for (Folder subFolder :subFolders){
							EmailFolderBean<String> subItem = new EmailFolderBean<String>(subFolder.getName(),subFolder.getFullName());
							item.getChildren().add(subItem);
							
							FetchMessagesOnFolderService fetchMessagesServiceSub = new FetchMessagesOnFolderService(subItem,subFolder);
							fetchMessagesServiceSub.start();
						}
					}
				}
				return null;
			}
		};
	}
	
}
