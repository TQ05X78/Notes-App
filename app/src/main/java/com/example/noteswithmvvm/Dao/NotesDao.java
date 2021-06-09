package com.example.noteswithmvvm.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteswithmvvm.Model.Notes;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {

    @Query("SELECT * FROM  `Notes.Database`")
    LiveData<List<Notes>> getallNotes();





    @Query("SELECT * FROM  `Notes.Database` ORDER BY notes_priority DESC")
    LiveData<List<Notes>> HighToLow();




    @Query("SELECT * FROM  `Notes.Database` ORDER BY notes_priority ASC")
    LiveData<List<Notes>> LowToHigh();


  //  List<Notes> getallNotes();


  @Insert
    void insertNotes(Notes... notes);

  @Query("DELETE FROM `Notes.Database` WHERE id=:id")
   void deleteNotes(int id);



  @Update
    void updateNotes(Notes notes);




}
