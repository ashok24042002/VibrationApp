package com.example.vibrationapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
public class MainActivity5 extends AppCompatActivity {
    private EditText mEditTextName;
    private EditText mEditAge;
    private RadioGroup mRadioGroupGender;
    private DatePicker mDatePicker;
    private Spinner mSpinnerType;
    private SimpleDateFormat mDateFormat;
    private ArrayList<String> mFormData;
    private String date;
    private ImageView imageView;
    private Button captureButton;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        mEditTextName = findViewById(R.id.input_name);
        mEditAge = findViewById(R.id.input_age);
        mRadioGroupGender = findViewById(R.id.radio_group);
        //mDatePicker = findViewById(R.id.date_picker_dob);
        mSpinnerType = findViewById(R.id.spinner_department);

        mFormData = new ArrayList<>();
        imageView = findViewById(R.id.imageView);
        // ...

        Button button = findViewById(R.id.button_popup);
        PopupMenu popupMenu = new PopupMenu(this, button);
        popupMenu.getMenuInflater().inflate(R.menu.datepickerpopupmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_date_picker:
                        showDatePicker();
                        return true;
                    default:
                        return false;
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });



        Button buttonSubmit = findViewById(R.id.button_submit);

        captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                mFormData.add(name);

                String age = mEditAge.getText().toString();
                mFormData.add(age);

                int selectedRadioButtonID = mRadioGroupGender.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);
                String condition = selectedRadioButton.getText().toString();
                mFormData.add(condition);

                String selectedItem = mSpinnerType.getSelectedItem().toString();
                mFormData.add(selectedItem);

                mFormData.add(date);
                // Do something with the form data, for example, log it
                Log.d("FormData", mFormData.toString());
                mFormData.clear();
                mEditTextName.setText("");
                mEditAge.setText("");
                mRadioGroupGender.clearCheck();
                imageView.setVisibility(View.GONE);
                mSpinnerType.setSelection(0);
                Toast.makeText(getApplicationContext(), "Data Recorded", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the date selection here
                        int yr=year;
                        int mn=month+1;
                        int day=dayOfMonth;
                        date=""+day+"/"+mn+"/"+yr;
                    }
                },
                // Set the initial date to display in the date picker
                2023, 1, 26);
        datePickerDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(imageBitmap);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
