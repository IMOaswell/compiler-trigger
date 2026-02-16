package imo.active_compiler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		final EditText filePathText = findViewById(R.id.filepath_edittext);
		final Button notificationButton = findViewById(R.id.notification_button);
		final SharedPreferences sp = getSharedPreferences("FILE_PATH", MODE_PRIVATE);
		
		// launch notification on load if file path has path
		// launch notification on button click
		// make notification that sends an intent action to com.tyron.code
		
		String savedProjectPath = sp.getString("FILE_PATH", "");
		if(! savedProjectPath.isEmpty()) filePathText.setText(savedProjectPath);
		
		//to be removed:
		notificationButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
// adb shell am start -n com.tyron.code/.MainActivity -a imo.buildapk.RECEIVE_PROJECT_PATH --es project_path "storage/emulated/0/example-project"
				String projectPath = filePathText.getText().toString();
				if(projectPath.isEmpty()) return;
				
				sp.edit().putString("FILE_PATH", projectPath).apply();
				
				Intent intent = new Intent();
				intent.setClassName("com.tyron.code", "com.tyron.code.MainActivity");
				intent.setAction(Intent.ACTION_SEND);
				intent.putExtra("project_path", projectPath);
				startActivity(intent);
			}
		});
    }
}
