package itAboutTalking.TalkToMe17;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private final int REQ_CODE_SPEECH_INPUT = 143;
    ImageView ivDarkBack, ivDarkLittle;
    Button btnWheel,btnLeft, btnRight,btnExit,btnMute,btnMic, btn1, btn2;
   //WheelFragment wheelFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Boolean darkBackOpenPosition = false;
    Runnable r;
    Handler h;
    LinearLayout groupIcon;
    int widthOfTheScreen,heightOfTheScreen;
    int rate = 850;
    Typeface myTypeface4;
    int maxPage = 0;
    Helper helper;
    Helper1 helper1;
    EditText  enterWordBox;
    String newWord;
    int sumOfDx;
    SnapHelper snapHelper;
    int page;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Sentence> arrayOfSentence;
  //  AnimatorSet animatorSetOpen,animatorSetClose;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        helper = new Helper(this);
        helper1=new Helper1(this);
        init();
        receycrcleIt();
        helper.readData();
        enterWordBox.setOnTouchListener(this);
        makeOnScroll();
        btnExit.setOnClickListener(helper1);
        btnMute.setOnClickListener(helper1);
        btnWheel.setOnClickListener(helper1);
        ivDarkBack.setOnClickListener(helper1);

        helper1.activatAnimatorSet();
        //  demiAction();
     }


    private void demiAction() {
        //  newWord="לא";
        newWord = "שולל";
        // newWord="סבתא";
        //  newWord="אישה";
        //  newWord="סב";
        arrayOfSentence = helper.setWord(newWord);
        makeRecyeclView();
        enterWordBox.setText(newWord);
    }

  /*  @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnExit:
                finishIt();
                break;
                default:
                    newWord="ll";
        }
    }*/

/*public void btnWheel_Onclick(View view) {
                                helper1.trainAndRekaAnimation();
}*/

/* public void ivDarkBack_Onclick(View view) {
                               helper1.trainAndRekaAnimation();
    }*/

    private void makeOnScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float d;
                sumOfDx += dx;
                d = (float) sumOfDx / (float) widthOfTheScreen;
                if (page!=0) {
                              page = Math.round(Math.abs(d)) + 1;
                }
              /*  btn1.setText(String.valueOf(sumOfDx));
                btn2.setText(String.valueOf(dx));*/

                if (Math.abs(dx) <= 4) {
                    if (page == 1||page==0) {
                        btnRight.setVisibility(View.INVISIBLE);
                    } else {
                        btnRight.setVisibility(View.VISIBLE);
                    }
                    if (page == maxPage||page==0) {
                        btnLeft.setVisibility(View.INVISIBLE);
                    } else {
                        btnLeft.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }



    private void receycrcleIt() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        //   mLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager = new Speedy(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }

    private void makeRecyeclView() {
        maxPage = arrayOfSentence.size();
        mAdapter = new MainAdapter(arrayOfSentence, this, myTypeface4, btnLeft, btnRight);
        mRecyclerView.setAdapter(mAdapter);
        locatInTheBeginig();
    }

    private void locatInTheBeginig() {
        r = new Runnable() {
            @Override
            public void run() {
                mLayoutManager.scrollToPosition(maxPage - 1);
                arrowAppearance("none");
                if (maxPage > 1) {
                    sumOfDx=0;
                    page=1;
                    arrowAppearance("left");
                }
            }
        };
        h = new Handler();
        h.postDelayed(r, 10);
    }

    private void arrowAppearance(String st) {
        if (st.equals("left")) {
            btnLeft.setVisibility(View.VISIBLE);
        }
        if (st.equals("right")) {
            btnRight.setVisibility(View.VISIBLE);
        }
        if (st.equals("none")) {
            btnLeft.setVisibility(View.INVISIBLE);
            btnRight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            newWord = String.valueOf(    enterWordBox.getText());
            if (newWord.equals("")) {
                activateKeyboard(1);
            } else {
                    enterWordBox.setText("");
                activateKeyboard(1);
                arrayOfSentence.clear(); //clear list
                mAdapter.notifyDataSetChanged();
            }
        }
        return false;
    }

    private void activateKeyboard(int ind) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (ind == 0) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            btnWheel.setVisibility(View.VISIBLE);
            btnWheel.bringToFront();
        } else {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            btnWheel.setVisibility(View.INVISIBLE);
        }
    }

    private void init() {
        myTypeface4 = Typeface.createFromAsset(getAssets(), "Frank.ttf");
        widthOfTheScreen = helper.getDimOfTheScreenHelperWidth();
        heightOfTheScreen = helper.getDimOfTheScreenHelperHight();

        enterWordBox = (EditText) findViewById(R.id.enterWordBox);
        enterWordBox.setTextColor(Color.CYAN);
        enterWordBox.setTypeface(myTypeface4);

        btnWheel = (Button) findViewById(R.id.btnWheel);
        btnMic=(Button) findViewById(R.id.btnMic);
        ivDarkBack = (ImageView) findViewById(R.id.ivDarkBack);
        btnLeft = (Button) findViewById(R.id.btnLeftArrow);
        btnRight = (Button) findViewById(R.id.btnRightArrow);
        btnRight.setVisibility(View.INVISIBLE);
        btnLeft.setVisibility(View.INVISIBLE);
        btnExit=(Button)findViewById(R.id.btnExit);
        btnMute=(Button)findViewById(R.id.btnMute);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        groupIcon = (LinearLayout)findViewById(R.id.groupIconTrain);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
    }


    private void updateCurrentPage(int addPage) {
        page = page - addPage;
        page = Math.max(page, 1);
        page = Math.min(page, maxPage);
        int page1 = maxPage - page;
        mRecyclerView.smoothScrollToPosition(page1);
    }

    private boolean chk_newWord(){
        int le;
        String st="אבגדהוזחטיכלמנסעפצקרשתםןףךץ";
        boolean bo=true;
        char ch;
        newWord = newWord.trim();
        le=newWord.length();
        if (le>=2) {
                    for (int i = 0; i < le; i++) {
                        ch = newWord.charAt(i);
                        String s = String.valueOf(ch);
                        if (!st.contains(s)) {
                            bo = false;
                        }
                    }
                }else{
                   bo=false;
        }
           return bo;
        }


    public void serachBtn_Onclick(View view) {
        newWord =  enterWordBox.getText().toString();
        Boolean bo=chk_newWord();
        if (bo) {
            activateKeyboard(0);
            newWord = newWord.trim();
            arrayOfSentence = helper.setWord(newWord);
            makeRecyeclView();
        } else {
            enterWordBox.setText("");
            if (arrayOfSentence!=null) {
                arrayOfSentence.clear(); //clear list
                mAdapter.notifyDataSetChanged();
                activateKeyboard(0);
                arrowAppearance("none");
                page = 0;
            }

        }
     }


    public void eraseBtn_onClick(View view) {
        if (newWord!=null) {
                        enterWordBox.setText("");
                        arrayOfSentence.clear(); //clear list
                        mAdapter.notifyDataSetChanged();
                        arrowAppearance("none");
                        page = 0;
        }
    }

    public void btnMic_Onclick(View view) {
        String st;
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
                                    activateKeyboard(0);
                                    newWord = newWord.trim();
                                    arrayOfSentence = helper.setWord(newWord);
                                    makeRecyeclView();
                                  }
                              }

            }
        }


      public void arrowLeft_OnClick(View view) {
              updateCurrentPage(-1);
    }

    public void arrowRightOnclick(View view) {

        updateCurrentPage(1);
    }







    private void finishIt() {
        //rekaAnimation();
        r = new Runnable() {
            @Override
            public void run() {
                finish();
                System.exit(0);
            }
        };
        h = new Handler();
        h.postDelayed(r, rate);
    }

   /* public void waitWithIt() {
        r = new Runnable() {
            @Override
            public void run() {
                ivDarkBack.setVisibility(View.INVISIBLE);
            }
        };

        h = new Handler();
        h.postDelayed(r, rate);
    }*/


}
