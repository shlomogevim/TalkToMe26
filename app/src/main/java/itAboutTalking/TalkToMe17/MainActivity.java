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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    ImageView ivDarkBack, ivDarkLittle;
    Button btnWheel,btnLeft, btnRight,btnExit,btnMute, btn1, btn2;
    WheelFragment wheelFragment;
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
    EditText  enterWordBox;
    String newWord;
    int sumOfDx;
    SnapHelper snapHelper;
    int page;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Sentence> arrayOfSentence;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        helper = new Helper(this);
        init();
        receycrcleIt();
        helper.readData();
        enterWordBox.setOnTouchListener(this);
        makeOnScroll();
        btnExit.setOnClickListener(this);
        btnMute.setOnClickListener(this);
      //  demiAction();
       }

    public void rekaAnimation() {
       /* if (!newWord.equals("")) {
           // ivDarkBack.setVisibility(View.VISIBLE);
          //  ivDarkLittle.setVisibility(View.VISIBLE);
         //   helper.rekaAnimationHelper(darkBackOpenPosition,rate,ivDarkBack,btnWheel);*/
            wheelFragment.makeFragmentAnimation(darkBackOpenPosition,groupIcon,ivDarkBack);
            if (darkBackOpenPosition) {
                          waitWithIt();
                            }
            darkBackOpenPosition=!darkBackOpenPosition;
       // }
    }


    public void btnWheel_Onclick(View view) {

        rekaAnimation();

       /* if (newWord!=null) {
            if (!newWord.equals("")) {
                rekaAnimation();
            }
        }*/
    }



    public void ivDarkBack_Onclick(View view) {
                                             if (darkBackOpenPosition) rekaAnimation();
    }

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
                btn1.setText(String.valueOf(sumOfDx));
                btn2.setText(String.valueOf(dx));

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


    public void eraseBtn_onClick(View view) {
            enterWordBox.setText("");
        arrayOfSentence.clear(); //clear list
        mAdapter.notifyDataSetChanged();
        arrowAppearance("none");
        page=0;
    }

    public void serachBtn_Onclick(View view) {
        newWord =     enterWordBox.getText().toString();
        if (!newWord.equals("")) {
            activateKeyboard(0);
            newWord = newWord.trim();
            arrayOfSentence = helper.setWord(newWord);
            makeRecyeclView();
        } else {
                enterWordBox.setText("");
            arrayOfSentence.clear(); //clear list
            mAdapter.notifyDataSetChanged();
            activateKeyboard(0);
            arrowAppearance("none");
            page=0;
        }
    }

    private void activateKeyboard(int ind) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (ind == 0) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            btnWheel.setVisibility(View.VISIBLE);
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
        ivDarkBack = (ImageView) findViewById(R.id.ivDarkBack);
        ivDarkLittle = (ImageView) findViewById(R.id.ivDarkLittle);
        btnLeft = (Button) findViewById(R.id.btnLeftArrow);
        btnRight = (Button) findViewById(R.id.btnRightArrow);
        btnRight.setVisibility(View.INVISIBLE);
        btnLeft.setVisibility(View.INVISIBLE);
        btnExit=(Button)findViewById(R.id.btnExit);
        btnMute=(Button)findViewById(R.id.btnMute);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        groupIcon = (LinearLayout)findViewById(R.id.groupIconTrain);

        wheelFragment = new WheelFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentTransaction.add(R.id.continarTrain,wheelFragment);
        fragmentTransaction.commit();
    }

    public void waitWithIt() {
        r = new Runnable() {
            @Override
            public void run() {
                ivDarkBack.setVisibility(View.INVISIBLE);
            }
        };

        h = new Handler();
        h.postDelayed(r, rate);
    }



    public void sayOnClick(View view) {
        promptSpeechInput();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCurrentPage(int addPage) {
        page = page - addPage;
        page = Math.max(page, 1);
        page = Math.min(page, maxPage);
        int page1 = maxPage - page;
        mRecyclerView.smoothScrollToPosition(page1);
    }


    public void arrowLeft_OnClick(View view) {
        updateCurrentPage(-1);
    }

    public void arrowRightOnclick(View view) {
        updateCurrentPage(1);
    }



    public void btn1_Onclick(View view) {
        float ff=100;
        enterWordBox.setText("");

         ivDarkBack.setVisibility(View.VISIBLE);
      //  ivDarkLittle.setVisibility(View.VISIBLE);
    }

    public void btn2_Onclick(View view) {
        // ivDarkBack.setVisibility(View.INVISIBLE);
       // ivDarkLittle.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        if(view==btnExit){
            rekaAnimation();
            r = new Runnable() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            };

            h = new Handler();
            h.postDelayed(r, rate);
        } else if (view==btnLeft){
                               promptSpeechInput();

        }



    }
}
