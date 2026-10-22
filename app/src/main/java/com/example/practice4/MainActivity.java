package com.example.littleprince; // Замени на имя своего пакета

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class MainActivity extends AppCompatActivity {

    private Button btnMorning, btnDay, btnEvening, btnNight;
    private ImageView imgMorning, imgDay, imgEvening, imgNight;
    private androidx.constraintlayout.widget.ConstraintLayout layoutMorning;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация кнопок навигации
        btnMorning = findViewById(R.id.btnMorning);
        btnDay = findViewById(R.id.btnDay);
        btnEvening = findViewById(R.id.btnEvening);
        btnNight = findViewById(R.id.btnNight);

        // Инициализация изображений и контейнеров
        imgMorning = findViewById(R.id.imageMorning);
        imgDay = findViewById(R.id.imageDay);
        imgEvening = findViewById(R.id.imageEvening);
        imgNight = findViewById(R.id.imageNight);
        layoutMorning = findViewById(R.id.layoutMorning);
    }

    // Вспомогательный метод создания уведомлений
    private void sendNotification(String title, String text, int iconResId) {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(iconResId)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(PRIORITY_HIGH);

        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, "Prince Notifications", NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(notificationChannel);
        }
    }

    // --- Обработчики кликов навигации (Шаг 3 и Шаг 4) ---

    public void btnMorningClick(View view) {
        layoutMorning.setVisibility(View.VISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification("Утро", "Привести в порядок свою планету", R.drawable.cleaning);
    }

    public void btnDayClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.VISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification("День", "Полить розу", R.drawable.rose);
    }

    public void btnEveningClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.VISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification("Вечер", "Закрыть розу ширмой", R.drawable.home);
    }

    public void btnNightClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.VISIBLE);

        sendNotification("Ночь", "Полюбоваться звездами", R.drawable.sunset);
    }

    // --- Обработчики кликов по объектам "Утра" (Шаг 5) ---

    private void showToast(String message) {
        Toast myToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        myToast.setGravity(Gravity.BOTTOM, 0, 0);
        myToast.show();
    }

    public void onPrinceClick(View view) {
        showToast("Маленький принц");
    }

    public void onPlanetClick(View view) {
        showToast("Астероид Б-612, планета на которой живет Маленький принц");
    }

    public void onVolcanoClick(View view) {
        showToast("Потухший вулкан. О нем нужно заботиться");
    }

    public void onRoseClick(View view) {
        showToast("Её нужно поливать, а на ночь укрывать ширмой и колпаком");
    }

    public void onBreakfastClick(View view) {
        showToast("Действующий вулкан, на нем удобно разогревать завтрак");
    }
}
