package guardiaomobile.model;

public class User {
	
	private String matricula;
	
	private String senha;
	
	private String role;
	
	public User() {
		
	}
	
	public User(String matricula, String senha) {
		this.matricula = matricula;
		this.senha = senha;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
