package itAboutTalking.TalkToMe17;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQ_CODE_SPEECH_INPUT = 143;
    Button btn1, btn2,btnExit;
    Helper1 helper1;
    EditText  enterWordBox;
    String newWord;
    Button btnSubmite;
    EditText etFont,etColor;
    Boolean italiceMode=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        helper1=new Helper1(this,0);
        enterWordBox.setOnTouchListener(helper1);
        btn2.setOnClickListener(this);
        etFont.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    etFont.setText("");
                }
                return false;
            }
        });
        etColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    etColor.setText("");
                }
                return false;
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper1.animatorSetClose.start();
                helper1.finishIt();
            }
        });
        demiAction();
        }

    public void submit_Onclick(View view) {
        int fontNum;
        String colorString;
        String font=etFont.getText().toString();
        if (!font.equals("")){
               fontNum=Integer.parseInt(font);
        }else{
            fontNum =-1;
        }
        if (fontNum>35){
                         fontNum=-1;
        }
         colorString=etColor.getText().toString();

       /* if (!color.equals("")){
            colorNum=Integer.parseInt(color);
        }else{
            colorNum =-1;
        }*/
       italiceMode=false;
        helper1.makeSetupChange(fontNum,colorString,italiceMode);
     }
    @Override
    public void onClick(View v) {
        int fontNum;
        String colorString;
        italiceMode=!italiceMode;
        String font=etFont.getText().toString();
        if (!font.equals("")){
            fontNum=Integer.parseInt(font);
        }else{
            fontNum =-1;
        }
        if (fontNum>35){
            fontNum=-1;
        }
        colorString=etColor.getText().toString();
        helper1.makeSetupChange(fontNum,colorString,italiceMode);
        helper1.activateKeyboard(0);
    }

  private void demiAction() {
        //  newWord="לא";
        newWord = "שולל";
        // newWord="סבתא";
        //  newWord="אישה";
        //  newWord="סב";
        helper1.eraseWord();
        helper1.serachNewWord1(newWord);
   }

    private void init() {
        enterWordBox = (EditText) findViewById(R.id.enterWordBox);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnSubmite = (Button) findViewById(R.id.btnSubmit);
        etFont=(EditText)findViewById(R.id.etNumOfFont);
        etColor=(EditText)findViewById(R.id.etNumOfColor);

   }

    public void main_Onclick(View view) {
        helper1.onClick(view);
    }

  public void btnMic_Onclick(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"תגיד משהו");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
                                           enterWordBox.setText("לא נמצא דבר");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && data !=null){
                                    ArrayList<String> voice=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                                    enterWordBox.setText(voice.get(0));
                                    newWord =enterWordBox.getText().toString();
                                    helper1.serachNewWord1(newWord);
                                  }
                              }
            }
        }



}
