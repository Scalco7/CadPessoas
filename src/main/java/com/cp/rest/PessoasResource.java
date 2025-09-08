package com.cp.rest;

import com.cp.data.crud.BeanCrudCidade;
import com.cp.data.crud.BeanCrudPessoa;
import com.cp.data.model.Cidade;
import com.cp.data.model.Pessoa;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("pessoas")
@Stateless
public class PessoasResource {


    @EJB
    BeanCrudPessoa beanCrudPessoa;

    @EJB
    BeanCrudCidade beanCrudCidade;

    /**
     * MÉTODO REFATORADO USANDO A API DE STREAMS DO JAVA.
     * O código agora é mais conciso, legível e segue práticas modernas de desenvolvimento.
     */
    @GET
    @Path("all")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<JsonPessoa> getAll(){
        return beanCrudPessoa.getAll().stream()
                .map(pessoa -> {
                    JsonCidade cidadeJson = (pessoa.getCidade() != null)
                            ? new JsonCidade(pessoa.getCidade().getId(), pessoa.getCidade().getNome())
                            : null;
                    return new JsonPessoa(pessoa.getId(), pessoa.getNome(), cidadeJson);
                })
                .collect(Collectors.toList());
    }

    /**
     * MELHORIA 5 (Parte 2): Adiciona o endpoint para deletar uma pessoa.
     * Utiliza o verbo HTTP DELETE e o ID da pessoa como parâmetro na URL.
     * Retorna respostas HTTP padrão para sucesso (204 No Content) ou falha (404 Not Found).
     */
    @DELETE
    @Path("{id}")
    public Response deletarPessoa(@PathParam("id") int id) {
        if (beanCrudPessoa.deletar(id)) {
            return Response.noContent().build(); // Sucesso
        } else {
            return Response.status(Response.Status.NOT_FOUND).build(); // Recurso não encontrado
        }
    }


    @GET
    @Path("cid")
    @Produces({ MediaType.APPLICATION_JSON })
    public ArrayList<JsonCidade> getCids(){
        ArrayList<JsonCidade> cidadel = new ArrayList<>();
        for(Cidade cid:beanCrudCidade.getAll()){
            cidadel.add(new JsonCidade(cid.getId(),cid.getNome()));
        }
        return cidadel;
    }

    public record JsonCidade(int id, String nome){}
    public record JsonPessoa(int id, String nome, JsonCidade cidade){}

}