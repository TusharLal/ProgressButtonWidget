package button.progress.example.tusharlal.progressbuttonexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressButton btnShareToCar;
    Button button;
    Switch aSwitch;

    boolean errorFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShareToCar = findViewById(R.id.progressButton);
        btnShareToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Progress Bar Showing", Toast.LENGTH_SHORT).show();
                if (btnShareToCar.onViewClick()) new ClickProcess().execute();
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSwitch.setChecked(false);
                btnShareToCar.resetButton();
            }
        });

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                errorFlag = b;
            }
        });
    }

    private class ClickProcess extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btnShareToCar.processCompleted(errorFlag);
            Toast.makeText(MainActivity.this, "Progress Bar Hidden", Toast.LENGTH_SHORT).show();
        }
    }
}
