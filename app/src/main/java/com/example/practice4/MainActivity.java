package com.example.practice4;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView imgDay, imgEvening, imgNight;
    private ConstraintLayout layoutMorning;
    private NotificationManager notificationManager;

    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_LITTLE_PRINCE";
    private static final int PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgDay = findViewById(R.id.imageDay);
        imgEvening = findViewById(R.id.imageEvening);
        imgNight = findViewById(R.id.imageNight);
        layoutMorning = findViewById(R.id.layoutMorning);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createChannelIfNeeded(notificationManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    public void btnMorningClick(View view) {
        layoutMorning.setVisibility(View.VISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification(R.drawable.cleaning, "Утро", "Привести в порядок свою планету");
    }

    public void btnDayClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.VISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification(R.drawable.rose, "День", "Полить розу");
    }

    public void btnEveningClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.VISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification(R.drawable.home, "Вечер", "Закрыть розу ширмой");
    }

    public void btnNightClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.VISIBLE);

        sendNotification(R.drawable.sunset, "Ночь", "Полюбоваться звездами");
    }
    public void onPrinceClick(View view) {
        Toast.makeText(this, "Маленький принц", Toast.LENGTH_LONG).show();
    }

    public void onPlanetClick(View view) {
        Toast.makeText(this, "Астероид Б-612, планета на которой живет Маленький принц", Toast.LENGTH_LONG).show();
    }

    public void onVolcanoClick(View view) {
        Toast.makeText(this, "Потухший вулкан. О нем нужно заботиться", Toast.LENGTH_LONG).show();
    }

    public void onRoseClick(View view) {
        Toast.makeText(this, "Её нужно поливать, а на ночь укрывать ширмой и колпаком", Toast.LENGTH_LONG).show();
    }

    public void onBreakfastClick(View view) {
        Toast.makeText(this, "Действующий вулкан, на нем удобно разогревать завтрак", Toast.LENGTH_LONG).show();
    }
    private void sendNotification(int iconResId, String title, String text) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(iconResId)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
    }
    private static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Little Prince Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("Уведомления о распорядке дня Маленького принца");
            manager.createNotificationChannel(notificationChannel);
        }
    }
}