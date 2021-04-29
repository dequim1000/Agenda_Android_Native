package com.example.desafiodatamob.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.desafiodatamob.R;
import com.example.desafiodatamob.model.AppDataBase;
import com.example.desafiodatamob.model.Person;
import com.example.desafiodatamob.view.AtualizaPessoaActivity;
import com.example.desafiodatamob.view.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements Filterable {
    private ArrayList<Person> persons;
    private ArrayList<Person> personsAll;
    private Activity context;
    private AppDataBase appDataBase;
    private OnItemClickListener listener;

    public PersonAdapter(Activity context, ArrayList<Person> persons, OnItemClickListener listener) {
        this.context = context;
        this.persons = persons;
        this.listener = listener;
        this.personsAll = new ArrayList<>(persons);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome;
        public TextView tipoPessoa;
        public Button btnExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nome = itemView.findViewById(R.id.textNome);
            this.tipoPessoa = itemView.findViewById(R.id.textTipoPessoa);
            this.btnExcluir = itemView.findViewById(R.id.btnExcluir);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
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
        //Incluir
        holder.nome.setText(persons.get(position).getNome());
        holder.tipoPessoa.setText(persons.get(position).getTipoPessoa());
        //Excluir
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



    public interface OnItemClickListener {
        //Quando não for retornado nenhum objeto, não preciso me preucupar com o Intent diferente, e posso passar somente a intent que vai ser chamada
        void onItemClick(int position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Person> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(personsAll);
            }else{
                for(Person person: personsAll){
                    if(person.getNome().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(person);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //run on a ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            persons.clear();
            persons.addAll((Collection<? extends Person>) results.values);
            notifyDataSetChanged();
        }
    };

}
