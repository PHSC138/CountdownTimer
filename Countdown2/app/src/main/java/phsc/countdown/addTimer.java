//TODO Handle ArrayIndexOutOfBoundsException when saving timers

package phsc.countdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class addTimer extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    String name;
    String date;
    String time;
    EditText et_name;
    EditText et_date;
    EditText et_time;
    ToggleButton bt_dst;
    boolean dst=false;
    Button b_submit;
    File dir;
    List<String> spinnerContent;
    Spinner spinner;
    String spinnerSelection;
    File timerFile;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);

        name="None";
        date="01/01/2015";
        time="00:00:00";

        et_name=(EditText)findViewById(R.id.et_name);
        et_name.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                if(s.toString().length()>0)name=s.toString();
            }
            public void beforeTextChanged(CharSequence s,int start,int count,int after){}
            public void onTextChanged(CharSequence s,int start,int before,int count){}
        });

        et_date=(EditText)findViewById(R.id.et_date);
        et_date.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                if(s.toString().length()>0)date=s.toString();
            }
            public void beforeTextChanged(CharSequence s,int start,int count,int after){}
            public void onTextChanged(CharSequence s,int start,int before,int count){}
        });

        et_time=(EditText)findViewById(R.id.et_time);
        et_time.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                if(s.toString().length()>0)time=s.toString();
            }
            public void beforeTextChanged(CharSequence s,int start,int count,int after){}
            public void onTextChanged(CharSequence s,int start,int before,int count){}
        });

        bt_dst=(ToggleButton)findViewById(R.id.tb_dst);
        bt_dst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                dst=isChecked;
            }
        });

        b_submit=(Button)findViewById(R.id.b_submit);
        b_submit.setOnClickListener(this);

        dir=getBaseContext().getFilesDir();

        spinnerContent=new ArrayList<>();
        for(int i=1;i<6;i++){
            File timer=new File(dir+"/Timers/timer_"+Integer.toString(i)+".txt");
            spinnerContent.add(readFile(timer));
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, spinnerContent);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        timerFile=new File(dir+"/Timers/timer_1.txt");
    }

    private String readFile(File timer){
        try{
            FileReader fReader=new FileReader(timer);
            BufferedReader bReader=new BufferedReader(fReader);
            return bReader.readLine();

        }catch(IOException e){
            e.printStackTrace();
        }
        return "None";
    }

    @Override
    public void onClick(View v){
        try{
            FileWriter writer=new FileWriter(timerFile);

            writer.write(name+"\n");
            writer.write(date+"\n");
            writer.write(time+"\n");
            writer.write(dst+"\n");
            writer.close();

            Toast.makeText(getBaseContext(),"Saved timer to: "+timerFile.getPath(),Toast.LENGTH_LONG).show();
            finish();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
        spinnerSelection=parent.getItemAtPosition(position).toString();
        timerFile=new File(dir+"/Timers/timer_"+Integer.toString(position+1)+".txt");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){
    }
}