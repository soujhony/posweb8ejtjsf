package conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.Projetor;
import util.JPAUtil;
import dao.JPACrudDao;

@FacesConverter(forClass=Projetor.class)
public class ProjetorConversor implements Converter{

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	try {
			return new JPACrudDao<Projetor>(JPAUtil.getInstance(), Projetor.class).ler(Long.parseLong(value));
		} catch (Exception e) {
			return null;
		}
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ""+((Projetor)value).getCodigo();
    }



}
