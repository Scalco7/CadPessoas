package com.cp.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.io.Serializable;

import static jakarta.persistence.CascadeType.ALL;

/**
 *
 * @author utfpr
 */
@Entity
@Data
public class Pessoa implements Serializable {

    //bla bla bla
    public Pessoa(){
        //bla bla bla
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;


    @ManyToOne(fetch = FetchType.LAZY,cascade = ALL, optional = true)
    @JoinColumn(name = "cidade")
    //  @JsonManagedReference
    private Cidade cidade;
}