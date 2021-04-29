package com.example.exercicio.model.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.exercicio.model.classes.PessoaView;

import java.util.List;

@Dao
public interface ViewDAO {

    @Query("" +
            "select c.idColaborador id, c.nomeColaborador nome, 'Colaborador' tipo" +
            "  from colaborador c" +
            " union " +
            "select t.uid id, t.nomeTerceiro nome, 'Terceiro' tipo" +
            "  from terceiro t" +
            "")
    List<PessoaView> selectPessoas();
}
