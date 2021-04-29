package com.example.desafiodatamob.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.desafiodatamob.R;
import com.example.desafiodatamob.adapter.PersonAdapter;
import com.example.desafiodatamob.contents.ContentTransfer;
import com.example.desafiodatamob.model.AppDataBase;
import com.example.desafiodatamob.model.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PersonAdapter.OnItemClickListener{
    RecyclerView recyclerView;
    ArrayList<Person> persons;
    FloatingActionButton fab;
    AppDataBase db;
    PersonAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDataBase.getInstance(MainActivity.this);
        persons = (ArrayList<Person>) db.personDAO().getAllPerson();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new PersonAdapter(MainActivity.this, persons, this);
        recyclerView.setAdapter(recyclerAdapter);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCadastrar = new Intent(getApplicationContext(), CadastrodePessoa.class);
                startActivityForResult(intentCadastrar, ContentTransfer.REQUEST_CADASTROS);
            }
        });


//        //Inserir
//        db.personDAO().insertAll();
//        //Encontrar o nome
//        db.personDAO().findByName();
//        //Deletar
//        db.personDAO().delete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pesquisar, menu);
        MenuItem item = menu.findItem(R.id.itemPesquisar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override //Metodo que espera receber um codigo de retorono, junto com uma intent
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case ContentTransfer.REQUEST_CADASTROS: //Ai caso o retorno foi igual a 1
                if(resultCode == RESULT_OK) //Ele ele for igual o RESULT_OK (Pre definido por padrão)
                {   //Ele add na lista pessoa o Intent q pega o metodo SeriazalizableExtra que é implementado na Classe Pessoa e passa o Content EXTRA_PESSOA, que é uma Key para identificar o objeto passado
                    //Add
                    persons.add((Person) data.getSerializableExtra(ContentTransfer.EXTRA_PESSOA));
                    recyclerAdapter.notifyItemInserted(persons.size() - 1);

                }
                break;

            case ContentTransfer.REQUEST_ATUALIZACAO:
                if(resultCode == RESULT_OK) //Ele ele for igual o RESULT_OK (Pre definido por padrão)
                {
                    //Atualizar
                    persons.clear();
                    persons.addAll(db.personDAO().getAllPerson());
                    recyclerAdapter.notifyDataSetChanged();//Aqui notifica o Adapter que foi alterado algo no Banco
                }
                break;
        }
    }

    @Override //Quando não for retornado nenhum objeto, não preciso me preucupar com o Intent diferente, e posso passar somente a intent que vai ser chamada
    public void onItemClick(int position) {
        Intent intent = new Intent(this, AtualizaPessoaActivity.class);
        //Passa para a tela os dados - PRECISA NO MODEL PESSOA DE UM Serializable
        intent.putExtra(ContentTransfer.EXTRA_PESSOA, persons.get(position));
        //STARTA A TELA
        startActivityForResult(intent, ContentTransfer.REQUEST_ATUALIZACAO);
    }
}