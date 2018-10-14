package guardiaomobile.model;

import java.util.List;

import guardiaomobile.enums.Role;

public class User {
	
	private String nome;
	
	private String matricula;
	
	private String senha;
	
	private List<Role> roles;
	
	public User() {
		
	}
	
	public User(String matricula, String senha) {
		this.matricula = matricula;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String[] getRolesAsArray() {
		if (this.roles == null) return null;
		
		String[] rolesArray = new String[this.roles.size()];
		
		for (int i = 0; i < rolesArray.length; i++) {
			rolesArray[i] = this.roles.get(i).name();
		}
		
		return rolesArray;
	}
	
}
