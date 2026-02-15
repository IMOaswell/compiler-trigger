package imo.active_compiler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		EditText filePathText = findViewById(R.id.filepath_edittext);
		Button notificationButton = findViewById(R.id.notification_button);
		
		// launch notification on load if file path has path
		// launch notification on button click
		// make notification that sends an intent action to com.tyron.code
		
		//to be removed:
		notificationButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
// adb shell am start -n com.tyron.code/.MainActivity -a imo.buildapk.RECEIVE_PROJECT_PATH --es project_path "storage/emulated/0/example-project"
				Intent intent = new Intent();
				intent.setClassName("com.tyron.code", "com.tyron.code.MainActivity");
				intent.setAction(Intent.ACTION_SEND);
				intent.putExtra("project_path", "/storage/emulated/0/Download/buildapk/active_compiler");
				startActivity(intent);
			}
		});
    }
}
