package com.cp.rest;

import com.cp.data.crud.BeanCrudCidade;
import com.cp.data.model.Cidade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("cidades")
@Stateless
public class CidadesResource {


    @EJB
    private BeanCrudCidade beanCrudCidade;


    @GET
    @Path("all")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<JsonCidade> getCids(){
        ArrayList<JsonCidade> cidadel = new ArrayList<>();
        for(Cidade cid:beanCrudCidade.getAll()){
            cidadel.add(new JsonCidade(cid.getId(),cid.getNome()));
        }
        return cidadel;
    }

    /**
     * MÉTODO NOVO E PADRONIZADO PARA CRIAR CIDADES.
     * Utiliza o verbo HTTP POST, que é o padrão para criação.
     * Recebe um objeto Cidade completo no corpo da requisição.
     * Retorna uma resposta HTTP clara (201 Created) em caso de sucesso.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarCidade(Cidade novaCidade) {
        // Validações podem ser adicionadas aqui antes de salvar
        if (novaCidade == null || novaCidade.getNome() == null || novaCidade.getNome().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("O nome da cidade é obrigatório.").build();
        }
        beanCrudCidade.persist(novaCidade);
        return Response.status(Response.Status.CREATED).entity(novaCidade).build();
    }

    public record JsonCidade(int id, String nome){}
}