package com.example.desafiodatamob.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDAO {
    @Query("SELECT * FROM person")
    List<Person> getAllPerson();

    @Query("SELECT * FROM person WHERE uid IN (:userIds)")
    List<Person> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM person WHERE nome LIKE :nome LIMIT 1")
    Person findByName(String nome);

    @Insert
    List<Long> insertAll(Person... person);

    @Delete
    void delete(Person person);

    @Update
    void updatePerson(Person... person);
}
