package com.example.marilia.workout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String CATEGORY = "CATEGORY";
    public static final String NAME = "NAME";
    public static final String DURATION = "DURATION";
    public static final String SETS = "SETS";
    public static final String REPETITIONS = "REPETITIONS";
    private ListView listViewExercises;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    ArrayAdapter<Exercise> adapter;

    private static final String FILE = "com.example.marilia.workout.COLOR_PREFERENCES";
    private static final String COLOR = "COLOR";

    private int option = Color.WHITE;

    private ConstraintLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewExercises = (ListView) findViewById(R.id.listViewExercises);
        adapter = new ArrayAdapter<Exercise>(this, android.R.layout.simple_list_item_1, exercises);
        listViewExercises.setAdapter(adapter);

//        listViewExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Get the selected item text from ListView
//                Exercise selectedItem = (Exercise) parent.getItemAtPosition(position);
//
//                // Display the selected item text on TextView
//                Toast.makeText(getApplicationContext(),"Clickou em :"+selectedItem.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });

        mainLayout = findViewById(R.id.mainLayout);

        readColorPreference();

        registerForContextMenu(listViewExercises);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuItemInfo:
                Intent intent = new Intent(this,InfoActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuItemBlue:
                item.setChecked(true);
                saveColorPreference(Color.BLUE);
                return true;

            case R.id.menuItemGray:
                item.setChecked(true);
                saveColorPreference(Color.GRAY);
                return true;

            case R.id.menuItemWhite:
                item.setChecked(true);
                saveColorPreference(Color.WHITE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {

            Bundle bundle = data.getExtras();
            Exercise exercise = null;

            exercise = new Exercise(bundle.getString(this.NAME));
            exercise.setDuration(bundle.getInt(this.DURATION));
            exercise.setSets(bundle.getInt(this.SETS));
            exercise.setRepetitions(bundle.getInt(this.REPETITIONS));


            switch (bundle.getInt(this.CATEGORY)) {
                case R.id.radioButtonAerobic:
                    exercise.setCategory(0);
                    break;
                case R.id.radioButtonBodybuilding:
                    exercise.setCategory(1);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), R.string.error_fill_options, Toast.LENGTH_SHORT).show();
            }

            exercises.add(exercise);

            //Toast.makeText(getApplicationContext(), "Name: "+exercise.getName(), Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            Bundle bundle = data.getExtras();
            Exercise exercise = null;

            switch (bundle.getInt(this.CATEGORY)) {
                case R.id.radioButtonAerobic:
                    exercise = new Exercise(bundle.getString(this.NAME));
                    exercise.setDuration(bundle.getInt(this.DURATION));
                    exercise.setCategory(0);
                    break;
                case R.id.radioButtonBodybuilding:
                    exercise = new Exercise(bundle.getString(this.NAME));
                    exercise.setSets(bundle.getInt(this.SETS));
                    exercise.setRepetitions(bundle.getInt(this.REPETITIONS));
                    exercise.setCategory(1);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), R.string.error_fill_options, Toast.LENGTH_SHORT).show();

            }

            adapter.remove(adapter.getItem(bundle.getInt("POSITION")));
            adapter.add(exercise);

//            Toast.makeText(getApplicationContext(), "Position: "+bundle.getInt("POSITION"), Toast.LENGTH_SHORT).show();

        }

    }

    public void addExercise(View view) {
        Intent intent = new Intent(this, AddExerciseActivity.class);

        startActivityForResult(intent, 0);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {

            case R.id.menuItemEdit:
//                Toast.makeText(getApplicationContext(),"Apertou Edit",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,AddExerciseActivity.class);
                Exercise ex = adapter.getItem(info.position);
                int position = (int) adapter.getItemId(info.position);
                intent.putExtra("POSITION",position);
                intent.putExtra("EDIT",ex);
//                Toast.makeText(getApplicationContext(),"Position: "+position,Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,1);
                return true;

            case R.id.menuItemDelete:
                Toast.makeText(getApplicationContext(), R.string.item_deleted,Toast.LENGTH_SHORT).show();
                adapter.remove(adapter.getItem(info.position));
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void readColorPreference() {
        SharedPreferences shared = getSharedPreferences(FILE, Context.MODE_PRIVATE);

        option = shared.getInt(COLOR, option);

        mainLayout.setBackgroundColor(option);
    }

    public void saveColorPreference(int newValue) {
        SharedPreferences shared = getSharedPreferences(FILE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(COLOR, newValue);

        editor.commit();

        option = newValue;

        mainLayout.setBackgroundColor(option);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item;

        switch(option){

            case Color.BLUE:
                item = menu.findItem(R.id.menuItemBlue);
                item.setChecked(true);
                return true;

            case Color.GRAY:
                item = menu.findItem(R.id.menuItemGray);
                item.setChecked(true);
                return true;

            case Color.WHITE:
                item = menu.findItem(R.id.menuItemWhite);
                item.setChecked(true);
                return true;

            default:
                return false;
        }
    }
}