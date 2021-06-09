package com.example.noteswithmvvm.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.noteswithmvvm.Dao.NotesDao;
import com.example.noteswithmvvm.Database.NotesDatabase;
import com.example.noteswithmvvm.Model.Notes;

import java.util.List;

public class NotesRepository {


    public NotesDao notesDao;

    public LiveData<List<Notes>> getallNotes;

    public LiveData<List<Notes>> hightolow;

    public LiveData<List<Notes>> lowtohigh;


    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);


        notesDao = database.notesDao();
        getallNotes = notesDao.getallNotes();
        hightolow = notesDao.HighToLow();
        lowtohigh = notesDao.LowToHigh(); //for filter
    }


   public void insertNotes(Notes notes)
   {
       notesDao.insertNotes(notes);
   }




    public void deleteNotes(int id)
   {
       notesDao.deleteNotes(id);
   }



   public void updateNotes(Notes notes)
   {
       notesDao.updateNotes(notes);
   }



}
