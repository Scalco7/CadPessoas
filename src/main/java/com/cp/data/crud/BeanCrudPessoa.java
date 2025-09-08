package com.cp.data.crud;

import com.cp.data.crud.interfaces.EMNames;
import com.cp.data.crud.interfaces.AbstractCrud;
import com.cp.data.model.Pessoa;
import com.cp.util.AppLog;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 *
 * @author utfpr
 */
@Stateless
public class BeanCrudPessoa extends AbstractCrud<Pessoa> {

    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory(EMNames.EMN1, EMNames.getEMN1Props()).createEntityManager();
            AppLog.getInstance().info("Entity manager criado com sucesso");
        }
        return em;
    }

    @Override
    protected void close() {
        if(em!=null){
            getEntityManager().close();
        }
    }

    public BeanCrudPessoa() {
        super(Pessoa.class);
    }

    /**
     * MELHORIA 4: Adiciona um método para salvar com tratamento de exceção.
     * Isso aumenta a estabilidade do sistema, pois falhas no banco de dados
     * são capturadas e registradas adequadamente.
     */
    public boolean salvarPessoaComSeguranca(Pessoa p) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(p);
            getEntityManager().getTransaction().commit();
            return true; // Sucesso
        } catch (Exception e) {
            // Usa o sistema de log para registrar o erro real
            AppLog.getInstance().error("Falha ao persistir a entidade Pessoa", e);
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
            return false; // Falha
        }
    }

    /**
     * MELHORIA 5 (Parte 1): Adiciona a lógica para deletar uma pessoa.
     * Este método será consumido pela API para permitir a remoção de registros.
     */
    public boolean deletar(int id) {
        try {
            Pessoa p = find(id);
            if (p != null) {
                getEntityManager().getTransaction().begin();
                getEntityManager().remove(p);
                getEntityManager().getTransaction().commit();
                return true; // Deletado com sucesso
            }
            return false; // Não encontrou a pessoa para deletar
        } catch (Exception e) {
            AppLog.getInstance().error("Falha ao deletar Pessoa com id: " + id, e);
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
            return false;
        }
    }
}