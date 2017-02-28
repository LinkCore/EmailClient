package com.saganisevangelos.controller.services;

import com.saganisevangelos.model.EmailAccountBean;
import com.saganisevangelos.model.EmailConstants;
import com.saganisevangelos.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CreateRegisterEmailAcountService extends Service<Integer>{

	public CreateRegisterEmailAcountService(String emailAddresss, String password, EmailFolderBean<String> folderRoot) {
		
		this.emailAddresss = emailAddresss;
		this.password = password;
		this.folderRoot = folderRoot;
	}


	private String emailAddresss;
	private String password;
	private EmailFolderBean<String> folderRoot;
	
	
	@Override
	protected Task<Integer> createTask() {
		// TODO Auto-generated method stub
		return new Task<Integer>(){

			@Override
			protected Integer call() throws Exception {
				EmailAccountBean account = new EmailAccountBean(emailAddresss, password);
				if(account.getLoginState()==EmailConstants.LOGIN_STATE_SUCCEDED){
					EmailFolderBean <String> emailFolderBean = new EmailFolderBean<String>(emailAddresss);
					folderRoot.getChildren().add(emailFolderBean);
					FetchFoldersService fetchFoldersService = new FetchFoldersService(emailFolderBean, account);
					fetchFoldersService.start();
				}
				return account.getLoginState();
			}
			
			
		};
	}

}
