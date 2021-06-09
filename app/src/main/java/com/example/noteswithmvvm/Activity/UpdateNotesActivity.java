package com.example.noteswithmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteswithmvvm.Model.Notes;
import com.example.noteswithmvvm.R;
import com.example.noteswithmvvm.Viewmodel.NotesViewModel;
import com.example.noteswithmvvm.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {

    ActivityUpdateNotesBinding binding;


    NotesViewModel notesViewModel;


    String priority = "1";

    //NotesAdapter se jo data aa raha usko get krenge

    String stitle,ssubtitle, snotes, spriority;

    int iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //data le rahe jo notesAdapter se aa raha

        iid = getIntent().getIntExtra("id",0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        snotes = getIntent().getStringExtra("note");
        spriority = getIntent().getStringExtra("priority");



        binding.upTitle.setText(stitle);
        binding.upSubtitle.setText(ssubtitle);
        binding.upNotes.setText(snotes);


        if(spriority.equals("1"))
        {
            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
        }
        else if(spriority.equals("2"))
        {
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);

        }
        else if(spriority.equals("3"))
        {
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);

        }

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);




        binding.greenPriority.setOnClickListener(v -> {

            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            priority = "1";
        });

        binding.yellowPriority.setOnClickListener(v -> {

            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.redPriority.setImageResource(0);
            priority = "2";
        });





        binding.redPriority.setOnClickListener(v -> {

            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.greenPriority.setImageResource(0);
            priority = "3";
        });



        binding.updateNotesBtn.setOnClickListener(v -> {


               String title = binding.upTitle.getText().toString();

            String subtitle = binding.upSubtitle.getText().toString();

            String notes = binding.upNotes.getText().toString();


            UpDateNotes(title, subtitle, notes);

        });



    }

    private void UpDateNotes(String title, String subtitle, String notes) {



        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d,yyyy", date.getTime());


        Toast.makeText(this, "Notes Updated Successfully!",Toast.LENGTH_SHORT).show();

        Notes updateNotes = new Notes();


        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subtitle;
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();


        notesViewModel.updateNote(updateNotes);

        finish();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.ic_delete)
        {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this,R.style.BottomSheetStyle);

            View views = LayoutInflater.from(UpdateNotesActivity.this).inflate(R.layout.delete_botton_sheet,(LinearLayout)
                                 findViewById(R.id.BottomSheet));


            sheetDialog.setContentView(views);


            TextView yes, no;

            yes=views.findViewById(R.id.delete_yes);
            no=views.findViewById(R.id.delete_no);



            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iid);
                finish();
            });


            no.setOnClickListener(v -> {
                sheetDialog.dismiss();
            });

            sheetDialog.show();


        }
        return true;
    }
}
















