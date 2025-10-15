
package com.cp.data.crud;

import com.cp.data.crud.interfaces.AbstractCrud;
import com.cp.data.model.Cidade;
import jakarta.ejb.Stateless;

/**
 *
 * @author utfpr
 */
@Stateless
public class BeanCrudCidade extends AbstractCrud<Cidade> {

    public BeanCrudCidade() {
        super(Cidade.class);
    }

}
