package phsc.countdown;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jakewharton.threetenabp.AndroidThreeTen;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import android.content.ClipboardManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //onCreate(){
    TextView tv_name;
    TextView tv_day;
    TextView tv_hour;
    TextView tv_minute;
    TextView tv_second;
    TextView tv_nano;
    File dir;
    Button b_copy;
    final Handler handler = new Handler();
    Runnable runnableCode = new Runnable(){
        @Override
        public void run(){
            update();
            handler.postDelayed(this, 10);
        }
    };
    public static final String preferences="Current_Timer";
    SharedPreferences currentTimer;
    //}

    //update(){
    Countdown count;
    File tempFile;
    //try{
    FileReader fReader;
    BufferedReader bReader;
    StringBuilder text;
    boolean dst;
    String strLine;
    String strText;
    String[] arrText;
    String clippedText;
    String day="";
    String hour="";
    String minute="";
    String second="";
    String nanoSecond="";
    //}}
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.w("Load","set content view");

        AndroidThreeTen.init(this);
        Log.w("Load","Loaded ThreeTen");

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_addTimer=new Intent(getBaseContext(),addTimer.class);
                startActivityForResult(intent_addTimer,0);
            }
        });
        FloatingActionButton timersFAB=(FloatingActionButton)findViewById(R.id.timersFAB);
        timersFAB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_timers=new Intent(getBaseContext(),Timers.class);
                startActivityForResult(intent_timers,0);
            }
        });
        Log.w("Load","Loaded FABS");

        dir=getBaseContext().getFilesDir();

        testPaths();
        Log.w("Load","Tested Paths");

        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_day=(TextView)findViewById(R.id.tv_day);
        tv_hour=(TextView)findViewById(R.id.tv_hour);
        tv_minute=(TextView)findViewById(R.id.tv_minute);
        tv_second=(TextView)findViewById(R.id.tv_second);
        tv_nano=(TextView)findViewById(R.id.tv_nano);
        Log.w("Load","Loaded tv");

        b_copy=(Button)findViewById(R.id.b_copy);
        b_copy.setOnClickListener(this);
        Log.w("Load","Loaded copy");
        currentTimer=getSharedPreferences(preferences, 0);
        String value=currentTimer.getString("currentTimer",null);
        if(value==null){
            currentTimer=getSharedPreferences(preferences, 0);
            SharedPreferences.Editor editor=currentTimer.edit();
            editor.putString("currentTimer", "1");
            editor.apply();
        }

        handler.post(runnableCode);
        Log.w("Load","Posted handler");
    }

    public void update(){
        count=new Countdown();
        tempFile=new File(dir+"/Timers/timer_"+currentTimer.getString("currentTimer",null)+".txt");

        try{
            fReader=new FileReader(tempFile);
            bReader=new BufferedReader(fReader);
            text=new StringBuilder();
            while((strLine=bReader.readLine())!=null) text.append(strLine).append("\n");

            strText=text.toString();
            arrText=strText.split("\n");
            tv_name.setText(arrText[0]);


            if((arrText[3].toLowerCase()).equals("true"))dst=true;
            else dst=false;

            try{
                count.countdown(arrText[1],arrText[2],dst);
            } catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }

            if(count.getDays()==1)day="1 Day ";
            else if(count.getDays()>=0)day=count.getDays()+" Days ";

            if(count.getHours()==1)hour="1 Hour ";
            else if(count.getHours()>=0)hour=count.getHours()+" Hours ";

            if(count.getMinutes()==1)minute="1 Minute ";
            else if(count.getMinutes()>=0)minute=count.getMinutes()+" Minutes ";

            if(count.getSeconds()==1)second="1 Second ";
            else if(count.getSeconds()>=0)second=count.getSeconds()+" Seconds ";

            if(count.getNanoSeconds()>0)nanoSecond=count.getNanoSeconds()+" Nano Seconds";

            tv_day.setText(day);
            tv_hour.setText(hour);
            tv_minute.setText(minute);
            tv_second.setText(second);
            tv_nano.setText(nanoSecond);

            clippedText=day+hour+minute+second+"and "+nanoSecond;
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void testPaths(){
        File testPath=new File(dir+"/Timers");
        if(!testPath.exists())testPath.mkdir();

        for(int i=1;i<=5;i++){
            testPath=new File(dir+"/Timers/timer_"+Integer.toString(i)+".txt");
            if(!testPath.exists()){
                try{
                    testPath.createNewFile();
                } catch(IOException e){
                    e.printStackTrace();
                }
                Log.w("Load","Writing file: "+testPath.getPath());
                writeFile(testPath);
            }
        }
    }

    private void writeFile(File path){
        try{
            FileWriter writer=new FileWriter(path);
            writer.write("Example Timer"+"\n");
            writer.write("01/01/2000"+"\n");
            writer.write("00:00:00"+"\n");
            writer.write("false"+"\n");
            writer.close();
            finish();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v){
         ClipboardManager clipboard=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
         ClipData clip=ClipData.newPlainText("Copied Text",clippedText);
         clipboard.setPrimaryClip(clip);
         Toast.makeText(this,"Copied to clipboard",Toast.LENGTH_LONG).show();
        //Toast.makeText(this,"Current time (MM/DD/YYYY) [HH:MM:SS}: ("+count.getNowMonthValue()+"/"+count.getNowDayOfMonth()+"/"+count.getNowYear()+") ["+count.getNowHour()+":"+count.getNowMinute()+":"+count.getNowSecond()+"]",Toast.LENGTH_LONG).show();
        //Toast.makeText(this,"Then time (MM/DD/YYYY) [HH:MM:SS}: ("+count.getThenMonth()+"/"+count.getThenDayOfMonth()+"/"+count.getThenYear()+") ["+count.getThenHour()+":"+count.getThenMinute()+":"+count.getThenSecond()+"]",Toast.LENGTH_LONG).show();
    }
}