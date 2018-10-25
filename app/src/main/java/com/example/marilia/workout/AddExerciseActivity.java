package com.example.marilia.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddExerciseActivity extends AppCompatActivity {

    RadioGroup radioGroupCategories;
    RadioButton radioButtonAerobic;
    RadioButton radioButtonBodybuilding;
    EditText editTextDuration;
    EditText editTextSets;
    EditText editTextRepetitions;
    EditText editTextName;
    Button buttonConfirm;
    int itemId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        radioGroupCategories = (RadioGroup) findViewById(R.id.radioGroupCategories);
        radioButtonAerobic = findViewById(R.id.radioButtonAerobic);
        radioButtonBodybuilding = findViewById(R.id.radioButtonBodybuilding);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextSets = findViewById(R.id.editTextSets);
        editTextRepetitions = findViewById(R.id.editTextRepetitions);
        editTextName = findViewById(R.id.editTextName);
        buttonConfirm = findViewById(R.id.buttonRegister);




        radioGroupCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case (R.id.radioButtonAerobic):
                        editTextSets.setEnabled(false);
                        editTextRepetitions.setEnabled(false);
                        editTextDuration.setEnabled(true);
                        break;

                    case (R.id.radioButtonBodybuilding):
                        editTextSets.setEnabled(true);
                        editTextRepetitions.setEnabled(true);
                        editTextDuration.setEnabled(false);
                        break;

                    default:
                        editTextDuration.setEnabled(true);
                        editTextRepetitions.setEnabled(true);
                        editTextSets.setEnabled(true);
                }
            }
        });


        Bundle bundle = getIntent().getExtras();

        if(getIntent().getSerializableExtra("EDIT")!=null){
            setTitle(getString(R.string.edit_exercise));
            buttonConfirm.setText(R.string.alter);
            Exercise ex = (Exercise) getIntent().getSerializableExtra("EDIT");
//            Toast.makeText(getApplicationContext(),"Nome: "+ex.getName(),Toast.LENGTH_SHORT).show();
            editTextName.setText(ex.getName());
            itemId = bundle.getInt("POSITION",-1);

            switch (ex.getCategory()) {
                case 0:
                    radioButtonAerobic.setChecked(true);
                    editTextDuration.setText(Integer.toString(ex.getDuration()));
                    break;

                case 1:
                    radioButtonBodybuilding.setChecked(true);
                    editTextSets.setText(Integer.toString(ex.getSets()));
                    editTextRepetitions.setText(Integer.toString(ex.getRepetitions()));
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void back(View view) {
        finish();
    }

    public void register(View view) {

        Intent intent = new Intent();

        int checkedId = radioGroupCategories.getCheckedRadioButtonId();

        String name = editTextName.getText().toString();


        if(itemId != -1){
            intent.putExtra("POSITION",itemId);
        }

            switch (checkedId) {
                case R.id.radioButtonAerobic:

                    if ((editTextName.getText().toString().matches("")) || (editTextDuration.getText().toString().matches(""))) {
                        Toast.makeText(getApplicationContext(), R.string.error_fill_options, Toast.LENGTH_LONG).show();

                    } else {
                        int duration = Integer.parseInt(editTextDuration.getText().toString());
                        intent.putExtra(MainActivity.CATEGORY, R.id.radioButtonAerobic);
                        intent.putExtra(MainActivity.DURATION, duration);
                        intent.putExtra(MainActivity.NAME, name);
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                    break;

                case R.id.radioButtonBodybuilding:

                    if ((editTextName.getText().toString().matches("")) || (editTextSets.getText().toString().matches("")) || (editTextRepetitions.getText().toString().matches(""))) {
                        Toast.makeText(getApplicationContext(), R.string.error_fill_options, Toast.LENGTH_LONG).show();

                    } else {
                        int sets = Integer.parseInt(editTextSets.getText().toString());
                        int repetitions = Integer.parseInt(editTextRepetitions.getText().toString());

                        intent.putExtra(MainActivity.CATEGORY, R.id.radioButtonBodybuilding);
                        intent.putExtra(MainActivity.NAME, name);
                        intent.putExtra(MainActivity.SETS, sets);
                        intent.putExtra(MainActivity.REPETITIONS, repetitions);

                        setResult(RESULT_OK, intent);
                        finish();

                    }
                    break;

                default:
                    Toast.makeText(getApplicationContext(), R.string.error_fill_options, Toast.LENGTH_LONG).show();
                    break;
            }
    }


}
