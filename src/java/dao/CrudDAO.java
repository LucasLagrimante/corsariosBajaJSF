/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.ErroSistema;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Lucas
 * @param <E>
 */
public interface CrudDAO<E> {

    /**
     *
     * @param entidade
     * @throws exception.ErroSistema
     */
    public void salvar(E entidade) throws ErroSistema;

    /**
     *
     * @param entidade
     * @throws exception.ErroSistema
     */
    public void excluir(E entidade) throws ErroSistema;

    /**
     *
     * @param entidade
     * @throws exception.ErroSistema
     */
    public void alterar(E entidade) throws ErroSistema;

    /**
     *
     * @return List
     */
    public List<E> buscar();
}
