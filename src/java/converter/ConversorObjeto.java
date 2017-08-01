/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import converter.BaseEntity;
import java.io.Serializable;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author lucas
 */
@FacesConverter("generic")
public class ConversorObjeto implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !"".equals(value)) {

            BaseEntity entity = (BaseEntity) value;

            this.addAttribute(component, entity);

            Long codigo = entity.pegarId();
            if (codigo != null) {
                return String.valueOf(codigo);
            }
        }

        return (String) value;

    }

    protected void addAttribute(UIComponent component, BaseEntity o) {
        String key = o.pegarId().toString();
        this.getAttributesFrom(component).put(key, o);
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
