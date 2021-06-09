package com.example.noteswithmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.noteswithmvvm.Activity.InsertNoteActivity;
import com.example.noteswithmvvm.Adapter.NotesAdapt;
import com.example.noteswithmvvm.Model.Notes;
import com.example.noteswithmvvm.Viewmodel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotesBtn;

    NotesViewModel notesViewModel;

    RecyclerView notesRecycler;

    NotesAdapt adapter;


    TextView nofilter, hightolow, lowtohigh;


    List<Notes> filterNoteslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotesBtn = findViewById(R.id.newNotesBtn);

        notesRecycler = findViewById(R.id.notesRecyclerView);



        nofilter = findViewById(R.id.nofilter);
        hightolow = findViewById(R.id.hightolow);
        lowtohigh = findViewById(R.id.lowtohigh);


        nofilter.setBackgroundResource(R.drawable.filter_selected_shape);//filter select ho raha



          nofilter.setOnClickListener(v -> {

              loadData(0);
              hightolow.setBackgroundResource(R.drawable.filter_unselected_shape);
              lowtohigh.setBackgroundResource(R.drawable.filter_unselected_shape);
              nofilter.setBackgroundResource(R.drawable.filter_selected_shape);//filter select ho raha



          });



          hightolow.setOnClickListener(v -> {
              loadData(1);
              nofilter.setBackgroundResource(R.drawable.filter_unselected_shape);
              lowtohigh.setBackgroundResource(R.drawable.filter_unselected_shape);
              hightolow.setBackgroundResource(R.drawable.filter_selected_shape);//filter select ho raha

          });

          lowtohigh.setOnClickListener(v -> {

              loadData(2);
              hightolow.setBackgroundResource(R.drawable.filter_unselected_shape);
              nofilter.setBackgroundResource(R.drawable.filter_unselected_shape);
              lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);//filter select ho raha

          });








        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, InsertNoteActivity.class));
        });

    notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
        @Override
        public void onChanged(List<Notes> notes) {
            setAdapter(notes);
            filterNoteslist = notes;
        }
    });



    }

    private void loadData(int i) {
      if(i == 0)
      {
          notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
              @Override
              public void onChanged(List<Notes> notes) {
                  setAdapter(notes);
                   filterNoteslist = notes;
              }

          });
      }else if(i == 1)
      {
          notesViewModel.hightolow.observe(this, new Observer<List<Notes>>() {
              @Override
              public void onChanged(List<Notes> notes) {
                  setAdapter(notes);
                  filterNoteslist = notes;
              }

          });
          }else if(i == 2)
              {
                  notesViewModel.lowtohigh.observe(this, new Observer<List<Notes>>() {
                      @Override
                      public void onChanged(List<Notes> notes) {
                          setAdapter(notes);
                          filterNoteslist = notes;
                      }
                  });
              }



    }



    public void setAdapter(List<Notes> notes)
    {
        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));
         adapter = new NotesAdapt(MainActivity.this,notes);
         notesRecycler.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_noes,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search Notes Here...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Notesfilter(newText);

                return false;
            }
        });
















        return super.onCreateOptionsMenu(menu);
    }

    private void Notesfilter(String newText) {

        ArrayList<Notes> filterName = new ArrayList<>();
         for(Notes notes: this.filterNoteslist) //for each loop mostly used in php
         {
             if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText))
             {
                 filterName.add(notes);
             }

         }


         this.adapter.searchNotes(filterName);

    }
}