package itAboutTalking.TalkToMe17;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    private final int REQ_CODE_SPEECH_INPUT = 143;
    Button btn1, btn2;
    Helper helper;
    Helper1 helper1;
    EditText  enterWordBox;
    String newWord;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        helper = new Helper(this);
        helper1=new Helper1(this);

        enterWordBox.setOnTouchListener(helper1);
        //  demiAction();
     }

    private void demiAction() {
        //  newWord="לא";
        newWord = "שולל";
        // newWord="סבתא";
        //  newWord="אישה";
        //  newWord="סב";
        helper1.serachNewWord1(newWord);
   }

    private void init() {
        enterWordBox = (EditText) findViewById(R.id.enterWordBox);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
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

    public void btn1_Onclick(View view) {
    }

    public void btn2_Onclick(View view) {
    }



}
