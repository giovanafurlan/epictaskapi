package br.com.fiap.epictaskapi.dto.request;

public class UserUpdateForm {

    private String name;
    private String email;
    
    public UserUpdateForm() {
    	
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}   
    
}
