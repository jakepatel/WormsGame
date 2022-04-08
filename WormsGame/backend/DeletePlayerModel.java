package backend;

import java.io.Serializable;

public class DeletePlayerModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	
	public DeletePlayerModel(String id){
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	

	public String getId() {
		return id;
	}
	
}
