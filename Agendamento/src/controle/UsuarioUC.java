package controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import modelo.Usuario;
import util.JPAUtil;
import dao.JPACrudDao;
import dao.JPAUsuarioDao;

@SessionScoped
@ManagedBean(name="usuarioUC")
public class UsuarioUC {
	
	private Usuario usuario = new Usuario();
	private Usuario usuarioLogado = null;
    private UIData select;
    
    public UsuarioUC() {
    }
   
    public UIData getSelect() {
        return select;
    }

    public void setSelect(UIData select) {
        this.select = select;
    }

    public String selecione(){
        usuario = (Usuario)select.getRowData();
        return "formUsuario";
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public List<Usuario> getUsuarios() throws Exception{
    	JPAUtil jpa = JPAUtil.getInstance();
    	try {
    		JPACrudDao<Usuario> daoUsuario = new JPACrudDao<Usuario>(jpa , Usuario.class);
            return daoUsuario.listarTodos();		
		} finally {
			JPAUtil.finalizar();
		}
    }

    public String salvar() throws Exception{
    	JPAUtil jpa = JPAUtil.getInstance();
    	try {
    		JPACrudDao<Usuario> daoUsuario = new JPACrudDao<Usuario>(jpa , Usuario.class);
        	daoUsuario.gravar(usuario);
        	return "listarUsuario";
		} finally {
			JPAUtil.finalizar();
		}
    }

    public String novo(){
        usuario = new Usuario();
        return "formUsuario";
    }

    public String cancelar(){
         return "listarUsuario";
    }
    
    public Usuario getUsuarioLogado(){
    	return usuarioLogado;
    }
    
    public String logar() throws ValidatorException, Exception{
    	JPAUtil jpa = JPAUtil.getInstance();
    	try {
    		JPAUsuarioDao daoUsuario = new JPAUsuarioDao(jpa);
    		usuarioLogado = daoUsuario.buscarUsuario(usuario);
        	if(usuarioLogado == null){
        		FacesMessage msg=new FacesMessage("Nick e/ou senha inv&aacute;lidos.");
    	        throw new ValidatorException(msg);
        	}else
        		if(usuarioLogado.getFuncao().equals("a"))
        			return "indexAdmin";
        		else
        			if(usuarioLogado.getFuncao().equals("p"))
            			return "";
            		else
            			return "";
        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JPAUtil.finalizar();
		}
		return "";
    }
    
    public void validarEmail(FacesContext facesContex, UIComponent validate, Object value) throws ValidatorException {
	    String email = (String)value;
	    if (email.indexOf("@") < 1) {
	        FacesMessage msg=new FacesMessage("E-mail invalido.");
	        throw new ValidatorException(msg);
	    }
	}
    
    public static void inicializarUsuario() throws Exception{
    	JPAUtil jpa = JPAUtil.getInstance();
    	try {
    		JPACrudDao<Usuario> daoUsuario = new JPACrudDao<Usuario>(jpa , Usuario.class);
        	Usuario usuario = new Usuario();
			usuario.setFuncao("a");
			usuario.setNick("admin");
			usuario.setSenha("admin");
			daoUsuario.gravar(usuario);
    	} finally {
			JPAUtil.finalizar();
		}//criar primeiro usuario no banco
		
    }
   
}
