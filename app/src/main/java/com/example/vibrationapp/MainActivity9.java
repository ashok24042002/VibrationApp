package com.example.vibrationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity9 extends AppCompatActivity {
    private EditText mEditTextName;
    private EditText mEditAge;
    private EditText mEditThreshold;
    private RadioGroup mRadioGroupGender;
    private DatePicker mDatePicker;
    private Spinner mSpinnerType;
    private SimpleDateFormat mDateFormat;
    private ArrayList<String> mFormData;
    private String date;
    private ImageView selectedImage;
    private Button captureButton;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    FirebaseStorage storage;
    String currentPhotoPath;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mEditTextName = findViewById(R.id.in_name);
        mEditAge = findViewById(R.id.in_age);
        mEditThreshold = findViewById(R.id.in_threshold);
        mRadioGroupGender = findViewById(R.id.rad_group);
        mSpinnerType = findViewById(R.id.spin_department);
        selectedImage = findViewById(R.id.imgView);

        Button button = findViewById(R.id.bt_popup);
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

        captureButton = findViewById(R.id.captureBtn);
//        captureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                askCameraPermissions();
//            }
//        });

        PopupMenu popupMenu2 = new PopupMenu(this, captureButton);
        popupMenu2.getMenuInflater().inflate(R.menu.gallery, popupMenu2.getMenu());
        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.camera:
                        askCameraPermissions();
                        return true;
                    case R.id.gallery:
                        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                    default:
                        return false;
                }
            }
        });
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu2.show();
            }
        });

        Button buttonSubmit = findViewById(R.id.bttn_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDataToFirebase();
            }
        });
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
    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String name = mEditTextName.getText().toString();
        String imageFileName = "JPEG_" + name + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                selectedImage.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                uploadImageToFirebase(f.getName(), contentUri);


            }

        }

        if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String name = mEditTextName.getText().toString();
                String imageFileName = "JPEG_" + name +"."+getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " +  imageFileName);
                selectedImage.setImageURI(contentUri);

                uploadImageToFirebase(imageFileName,contentUri);


            }

        }
    }
    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("pictures/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                    }
                });

                Toast.makeText(MainActivity9.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity9.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void uploadDataToFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("machines");
        String name = mEditTextName.getText().toString();
        int age = Integer.parseInt(mEditAge.getText().toString());
        float threshold=0;
        try{
            threshold = Float.parseFloat(mEditThreshold.getText().toString());
        }catch (NumberFormatException e) {}
        int genderId = mRadioGroupGender.getCheckedRadioButtonId();
        String condition = "";
        if (genderId == R.id.rad_button_good) {
            condition = "Good";
        } else if (genderId == R.id.rad_button_bad) {
            condition = "Bad";
        }
        String department = mSpinnerType.getSelectedItem().toString();
        User user = new User(name, age, threshold, condition, department,date );
        myRef.push().setValue(user);
        mEditTextName.setText("");
        mEditAge.setText("");
        mRadioGroupGender.clearCheck();
        mSpinnerType.setSelection(0);
        mEditThreshold.setText("");
        Toast.makeText(getApplicationContext(), "Data Uploaded", Toast.LENGTH_SHORT).show();
    }


}
class User {
    public String name;
    public int age;
    public float threshold;
    public String condition;
    public String department;
    public String date;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, int age, float threshold, String condition, String department, String date) {
        this.name = name;
        this.age = age;
        this.threshold = threshold;
        this.condition = condition;
        this.department = department;
        this.date = date;
    }
}
