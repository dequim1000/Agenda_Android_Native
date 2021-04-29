package com.example.desafiodatamob.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.desafiodatamob.R;
import com.example.desafiodatamob.contents.ContentTransfer;
import com.example.desafiodatamob.model.AppDataBase;
import com.example.desafiodatamob.model.Person;

import java.util.List;

public class CadastrodePessoa extends AppCompatActivity {

    private EditText nome, tipoPessoa;
    private Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrode_pessoa);

        nome = findViewById(R.id.editNome);
        tipoPessoa = findViewById(R.id.editTipoPessoa);
        btnCadastrar = findViewById(R.id.btnCadastrar);


        AppDataBase db = AppDataBase.getInstance(CadastrodePessoa.this);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Person newPerson = new Person(nome.getText().toString(), tipoPessoa.getText().toString());
                List<Long> retorno = db.personDAO().insertAll(newPerson);
                if(retorno != null && retorno.size() > 0) {

                    newPerson.setUid(retorno.get(0).intValue());
                    Intent intent = new Intent();
                    intent.putExtra(ContentTransfer.EXTRA_PESSOA, newPerson);


                    setResult(RESULT_OK, intent);
                    CadastrodePessoa.this.finish();
                }
            }
        });





    }
}