//Author Evangelos Saganis

package com.saganisevangelos.model.table;

import javafx.beans.property.SimpleBooleanProperty;

public abstract class AbstractTableItem {
	private final SimpleBooleanProperty read = new SimpleBooleanProperty();
	
	public AbstractTableItem(boolean isRead){
		this.setReadProperty(isRead);
	}
	
	public SimpleBooleanProperty getReadProperty(){
		return read;
	}
	
	public void setReadProperty(boolean isRead){
		read.set(isRead);
	}
	
	public boolean isRead (){
		return read.get();
	}
}
