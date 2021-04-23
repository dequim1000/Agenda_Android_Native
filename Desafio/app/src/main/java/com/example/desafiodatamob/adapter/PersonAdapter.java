package com.example.desafiodatamob.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.desafiodatamob.R;
import com.example.desafiodatamob.model.AppDataBase;
import com.example.desafiodatamob.model.Person;
import com.example.desafiodatamob.view.MainActivity;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private ArrayList<Person> persons;
    private Activity context;
    private AppDataBase appDataBase;

    public PersonAdapter(Activity context,  ArrayList<Person> persons) {
        this.context = context;
        this.persons = persons;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nome;
        public TextView tipoPessoa;
        public Button btnExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nome = itemView.findViewById(R.id.textNome);
            this.tipoPessoa = itemView.findViewById(R.id.textTipoPessoa);
            this.btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        appDataBase = AppDataBase.getInstance(context);
        holder.nome.setText(persons.get(position).getNome());
        holder.tipoPessoa.setText(persons.get(position).getTipoPessoa());
        holder.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = persons.get(holder.getAdapterPosition());
                appDataBase.personDAO().delete(person);
                int position = holder.getAdapterPosition();
                persons.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, persons.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    void removerItem(int position) {
        persons.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, persons.size());
    }
}
