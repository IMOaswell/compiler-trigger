package imo.active_compiler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.NotificationManager;
import android.os.Build;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Notification;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		final EditText filePathText = findViewById(R.id.filepath_edittext);
		final Button notificationButton = findViewById(R.id.notification_button);
		final SharedPreferences sp = getSharedPreferences("FILE_PATH", MODE_PRIVATE);
		
		String savedProjectPath = sp.getString("FILE_PATH", "");
		if(! savedProjectPath.isEmpty()){
			filePathText.setText(savedProjectPath);
			showActiveNotification(savedProjectPath);
		} 
		
		notificationButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				String projectPath = filePathText.getText().toString();
				if(projectPath.isEmpty()) return;
				
				sp.edit().putString("FILE_PATH", projectPath).apply();
				
				showActiveNotification(projectPath);
				
				Toast.makeText(MainActivity.this, "Done.", Toast.LENGTH_LONG).show();
				finish();
			}
		});
    }
	
	public void showActiveNotification(String projectPath){
		final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		String channelId = "active_compiler";

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			nm.createNotificationChannel(new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT));

		// adb shell am start -n com.tyron.code/.MainActivity -a imo.buildapk.RECEIVE_PROJECT_PATH --es project_path "storage/emulated/0/example-project"
		Intent intent = new Intent();
		intent.setClassName("com.tyron.code", "com.tyron.code.MainActivity");
		intent.setAction(Intent.ACTION_SEND);
		intent.putExtra("project_path", projectPath);
		//startActivity(intent);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

		Notification.Builder builder = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
			? new Notification.Builder(this, channelId) 
			: new Notification.Builder(this);
			
		Notification notification = builder
			.setSmallIcon(android.R.drawable.ic_media_play)
			.setContentTitle("Compile Project")
			.setContentText("Tap to compile " + projectPath)
			.setContentIntent(pendingIntent)
			.build();

		nm.notify(1, notification);
	}
}
