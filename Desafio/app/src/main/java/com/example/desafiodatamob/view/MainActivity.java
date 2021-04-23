package com.example.desafiodatamob.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.desafiodatamob.R;
import com.example.desafiodatamob.adapter.PersonAdapter;
import com.example.desafiodatamob.model.AppDataBase;
import com.example.desafiodatamob.model.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Person> persons;
    FloatingActionButton fab;
    AppDataBase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDataBase.getInstance(MainActivity.this);
        persons = (ArrayList<Person>) db.personDAO().getAllPerson();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(MainActivity.this, persons);
        recyclerView.setAdapter(adapter);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastrodePessoa.class));
            }
        });


//        //Inserir
//        db.personDAO().insertAll();
//        //Encontrar o nome
//        db.personDAO().findByName();
//        //Deletar
//        db.personDAO().delete();
    }
}