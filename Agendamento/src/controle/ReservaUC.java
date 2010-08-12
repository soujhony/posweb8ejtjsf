package controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;

import modelo.Reserva;
import modelo.Sala;
import util.JPAUtil;
import dao.JPAReservaDao;
import dao.JPASalaDAO;

@ManagedBean(name="reservaUC")
@SessionScoped
public class ReservaUC {

	private UIData select;
	private final ArrayList<Integer> horarios = new ArrayList<Integer>();
    private Reserva reserva = new Reserva();
    private JPASalaDAO dao;
    private Sala dadosConfiguracao = new Sala();

	public ReservaUC() {
		try {
			dao = new JPASalaDAO(JPAUtil.getInstance());
			for(int i= 1; i <= 15; i++)
	    		horarios.add(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UIData getSelect() {
        return select;
    }

    public void setSelect(UIData select) {
        this.select = select;
    }

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva modelo) {
		this.reserva = modelo;
	}

	public List<Integer> getHorarios(){
    	return horarios;
    }
	public List<Sala> getSalasPeloBloco() throws Exception{
    	return dao.listaSalasPeloBlocoECampus(dadosConfiguracao);
    }
    
    public List<String> getBlocosPeloCampus() throws Exception{
    	return dao.listaBlocosPeloCampus(dadosConfiguracao);
    }
        
    public void setDadosConfiguracao(Sala dadosConfiguracao) {
		this.dadosConfiguracao = dadosConfiguracao;
	}

	public Sala getDadosConfiguracao() {
		return dadosConfiguracao;
	}
    
	public List<Sala> getSalas() {
		List<Sala> l;
		try {
			l = dao.listarTodos();
		} catch (Exception e) {
			l = new ArrayList<Sala>();
			e.printStackTrace();
		}
		return l;
	}
	
    public String salvar() throws Exception{
    	if(reserva.getSala() == null){
    		FacesContext.getCurrentInstance().addMessage("data", new FacesMessage("Selecione uma sala v�lida!"));
			return null;
    	}
    	
    	JPAUtil jpa = JPAUtil.getInstance();
    	try {
    		JPAReservaDao daoReserva = new JPAReservaDao(jpa);
    		if(daoReserva.projetoresReservados(reserva) < daoReserva.projetoresPossiveisReserva(reserva)){
    			daoReserva.gravar(reserva);
	        	return "reservaSucesso";
    		}else
    			FacesContext.getCurrentInstance().addMessage("data", new FacesMessage("N�o existem projetores dispon�veis nesta data"));
    			return null;
		} finally {
			JPAUtil.finalizar();
		}
    }
    
    public String novo() throws Exception{
        reserva = new Reserva();
        dadosConfiguracao = new Sala();
        return "formReserva";
    }

    public String cancelar(){
         return "indexAdmin";
    }
    


}
