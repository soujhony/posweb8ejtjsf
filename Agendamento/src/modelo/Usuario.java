package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Emerson
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="usuarioLogado", query="select u from Usuario as u where u.nick = ?1 and u.senha = ?2")
public class Usuario implements IModelo, Serializable {

	private static final long serialVersionUID = 2467153244033670115L;

	@Id
    @GeneratedValue
    @Column(name="cd_usuario")
    private Long codigo;

    @Column(name="ds_nick", unique=true, nullable=false)
    private String nick;
    
    @Column(name="ds_senha", nullable=false)
    private String senha;
    
    @Column(name="ds_email")
    private String email;
    
    @Column(name="ds_funcao", nullable=false)
    private String funcao="a";
    @Transient
    private String descFuncao = "Administrador";

	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public String getDescFuncao(){
		if(funcao.equals("a"))
			descFuncao = "Administrador";
		else
			if(funcao.equals("p"))
				descFuncao = "Professor";
			else
				descFuncao = "Instalador";
		return descFuncao;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
        
}
