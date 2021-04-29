package com.example.desafiodatamob.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.desafiodatamob.R;
import com.example.desafiodatamob.contents.ContentTransfer;
import com.example.desafiodatamob.model.AppDataBase;
import com.example.desafiodatamob.model.Person;

import java.util.ArrayList;

public class AtualizaPessoaActivity extends AppCompatActivity {
    private Person person;
    private EditText nomeAtual, tipoPessoaAtual;
    private Button btnAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_pessoa);

        btnAtualizar = findViewById(R.id.btnCadastrarAtualizar);
        nomeAtual = findViewById(R.id.editNomeAtual);
        tipoPessoaAtual = findViewById(R.id.editTipoPessoaAtual);

        AppDataBase db = AppDataBase.getInstance(AtualizaPessoaActivity.this);

        if (getIntent().getSerializableExtra(ContentTransfer.EXTRA_PESSOA) != null) {

            person = (Person) getIntent().getSerializableExtra(ContentTransfer.EXTRA_PESSOA);
            nomeAtual.setText(person.getNome());
            tipoPessoaAtual.setText(person.getTipoPessoa());

            btnAtualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    String nome_atualizado = nomeAtual.getText().toString();
                    String tipoPessoa_atualizado = tipoPessoaAtual.getText().toString();
                    //Ele pega o objeto person e pega o ID que esta vindo e seta os valores do person
                    person.setNome(nome_atualizado);
                    person.setTipoPessoa(tipoPessoa_atualizado);
                    db.personDAO().updatePerson(person);
                    setResult(RESULT_OK, intent);
                    AtualizaPessoaActivity.this.finish();
                }
            });
        }


    }
}