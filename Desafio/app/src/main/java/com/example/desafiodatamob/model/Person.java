package com.example.desafiodatamob.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Person implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "nome")
    private String nome;
    @ColumnInfo(name = "tipopessoa")
    private String tipoPessoa;



    public Person(String nome, String tipoPessoa) {
        this.nome = nome;
        this.tipoPessoa = tipoPessoa;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
