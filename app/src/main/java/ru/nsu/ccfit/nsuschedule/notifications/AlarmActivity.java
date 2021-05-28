package ru.nsu.ccfit.nsuschedule.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.nsu.ccfit.nsuschedule.R;

import static ru.nsu.ccfit.nsuschedule.notifications.AndroidScheduleNotificationManager.CONTENT_KEY;
import static ru.nsu.ccfit.nsuschedule.notifications.AndroidScheduleNotificationManager.TITLE_KEY;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        TextView titleTextView = findViewById(R.id.title_text_view);
        TextView textTextView = findViewById(R.id.text_text_view);
        Button stopAlarmButton = findViewById(R.id.stop_alarm_button);
        String title = getIntent().getStringExtra(TITLE_KEY);
        String text = getIntent().getStringExtra(CONTENT_KEY);
        titleTextView.setText(title);
        textTextView.setText(text);
        stopAlarmButton.setOnClickListener(this);


            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ringtone.play();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.stop_alarm_button) {
            stopAlarm();
            finish();
        }
    }

    private void stopAlarm() {
        ringtone.stop();
    }

}