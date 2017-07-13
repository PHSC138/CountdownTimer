//TODO On change of timer, reset text fields
package phsc.countdown;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Timers extends AppCompatActivity implements View.OnClickListener{
    File dir;
    public static final String preferences="Current_Timer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);

        dir=getBaseContext().getFilesDir();

        Button b1=(Button)findViewById(R.id.b1);
        File timer=new File(dir+"/Timers/timer_1.txt");
        b1.setText(readFile(timer));
        b1.setOnClickListener(this);

        Button b2=(Button)findViewById(R.id.b2);
        timer=new File(dir+"/Timers/timer_2.txt");
        b2.setText(readFile(timer));
        b2.setOnClickListener(this);

        Button b3=(Button)findViewById(R.id.b3);
        timer=new File(dir+"/Timers/timer_3.txt");
        b3.setText(readFile(timer));
        b3.setOnClickListener(this);

        Button b4=(Button)findViewById(R.id.b4);
        timer=new File(dir+"/Timers/timer_4.txt");
        b4.setText(readFile(timer));
        b4.setOnClickListener(this);

        Button b5=(Button)findViewById(R.id.b5);
        timer=new File(dir+"/Timers/timer_5.txt");
        b5.setText(readFile(timer));
        b5.setOnClickListener(this);
    }

    private String readFile(File timer){
        try{
            FileReader fReader=new FileReader(timer);
            BufferedReader bReader=new BufferedReader(fReader);
            return bReader.readLine();

        } catch(IOException e){
            e.printStackTrace();
        }
        return "None";
    }

    @Override
    public void onClick(View v){
        SharedPreferences currentTimer=getSharedPreferences(preferences, 0);
        SharedPreferences.Editor editor=currentTimer.edit();
        switch (v.getId()){
            case R.id.b1:
                editor.putString("currentTimer", "1");
                editor.apply();
                finish();
                break;

            case R.id.b2:
                editor.putString("currentTimer", "2");
                editor.commit();
                finish();
                break;

            case R.id.b3:
                editor.putString("currentTimer", "3");
                editor.commit();
                finish();
                break;

            case R.id.b4:
                editor.putString("currentTimer", "4");
                editor.commit();
                finish();
                break;

            case R.id.b5:
                editor.putString("currentTimer", "5");
                editor.commit();
                finish();
                break;
        }
    }
}