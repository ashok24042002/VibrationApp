package com.example.vibrationapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity2 extends AppCompatActivity {
    private Button showFrame;
    private Button showLinear;
    private Button showRegister;
    private TextView ID1;
    private TextView ID2;
    private TextView ID3;
    private TextView about;

    private TextView st1;
    private TextView st2;
    private TextView st3;
    private static final String CHANNEL_ID = "notification_channel_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //test noti
        //sendNotification("Test Notification", "This is a test notification");
        showFrame=(Button) findViewById(R.id.btnFrame);
        showFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAct3();
            }
        });
        showLinear=(Button) findViewById(R.id.btnLinear);
        showLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAct4();
            }
        });
        showRegister=(Button) findViewById(R.id.btnRegister);
        showRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAct9();
            }
        });

        Button btnSet = (Button) findViewById(R.id.btnSetValue);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAct8();
            }
        });
        Button btnGet = (Button) findViewById(R.id.btnGetValue);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAct7();
            }
        });
        ID1=(TextView)findViewById(R.id.id1);
        registerForContextMenu(ID1);
//        ID1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {movePg1();}
//        });
        ID2=(TextView)findViewById(R.id.id2);
        registerForContextMenu(ID2);
//        ID2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {movePg2();}
//        });
        ID3=(TextView)findViewById(R.id.id3);
        registerForContextMenu(ID3);
//        ID3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {movePg3();}
//        });
        about=(TextView)findViewById(R.id.aboutUs);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {moveAbtUs();}
        });

        //Click to send Status Notifications:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        TextView st1 = findViewById(R.id.sts1);
        String text1 = st1.getText().toString();
        if(text1.equals("Bad")) {
            sendNotification("Threshold Limit Reached!", "The Rice Cooker needs urgent care");
        }

        TextView st2 = findViewById(R.id.sts2);
        String text2 = st2.getText().toString();
        if(text2.equals("Bad")) {
            sendNotification("Threshold Limit Reached!", "The Wheat Machine needs urgent care");
        }

        TextView st3 = findViewById(R.id.sts3);
        String text3 = st3.getText().toString();
        if(text3.equals("Bad")) {
            sendNotification("Threshold Limit Reached!", "The Paddy Machine needs urgent care");
        }
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.id1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contextmenu, menu);
        } else if (v.getId() == R.id.id2) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contextmenu2, menu);
        } else if(v.getId() == R.id.id3) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contextmenu3, menu);
        }
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open1:
                movePg1();
                return true;
            case R.id.cancel1:
                return true;
            case R.id.open2:
                movePg2();
                return true;
            case R.id.cancel2:
                return true;
            case R.id.open3:
                movePg3();
                return true;
            case R.id.cancel3:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                // do something
                moveAct4();
                return true;
            case R.id.menu_item2:
                // do something else
                moveAct5();
                return true;
            case R.id.menu_item3:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void moveAct3(){
        Intent intent = new Intent(this,MainActivity3.class);
        startActivity(intent);
    }
    private void moveAct4(){
        Intent intent = new Intent(this,MainActivity4.class);
        startActivity(intent);
    }
    private void moveAct5(){
        Intent intent = new Intent(this,MainActivity5.class);
        startActivity(intent);
    }
    private void moveAct7(){
        Intent intent = new Intent(this,MainActivity7.class);
        startActivity(intent);
    }
    private void moveAct8(){
        Intent intent = new Intent(this,MainActivity8.class);
        startActivity(intent);
    }
    private void moveAct9(){
        Intent intent = new Intent(this,MainActivity9.class);
        startActivity(intent);
    }
    private void moveAbtUs(){
        Intent intent = new Intent(this,MainActivity6.class);
        startActivity(intent);
    }
    private void movePg1(){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.amazon.in/Pigeon-Rice-Cooker-1-8L-White/dp/B01N7WZ675/ref=sr_1_3?crid=10JVPKDFE7U85&keywords=rice+cooker&qid=1676818574&sprefix=rice+cooker%2Caps%2C307&sr=8-3"));
        startActivity(intent);
    }
    private void movePg2(){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.amazon.in/TechnoPure-Automatic-Mill-Gharghanti-Tiny/dp/B0BHPBD9SS/ref=sr_1_3?crid=3AANCGM8PDMA6&keywords=wheat+machine&qid=1676818649&sprefix=wheat+machine%2Caps%2C237&sr=8-3"));
        startActivity(intent);
    }
    private void movePg3(){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.amazon.in/SHREEJIFINE-Domestic-Ghanti-Chakki-Machine/dp/B09413P4KC/ref=sr_1_5?crid=3AANCGM8PDMA6&keywords=wheat+machine&qid=1676818649&sprefix=wheat+machine%2Caps%2C237&sr=8-5"));
        startActivity(intent);
    }
    public void sendNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }

}