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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    ImageView ivDarkBack, ivDarkBack1;
    Button btnWheel;
    Button btnLeft, btnRight, btn1, btn2;
    WheelFragment wheelFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Boolean darkBackOpen = false;
    Runnable r;
    Handler h;
    int widthOfTheScreen;
    int rate = 850;
    Typeface myTypeface4;
    int maxPage = 0;
    Helper helper;
    EditText etEnterWord;
    String bigString, newWord;
    int sumOfDx;
    String[] rArray, sArray, wArray, arr4Array;
    String[] sLittleArray, wLittleArray;
    List<String> sArrayOfList, arr3List;
    long startTime;
    Boolean newMovement;
    int currentPage;
    Boolean changeLcation = false;
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
        btnLeft.setVisibility(View.INVISIBLE);
        // enterWord();
        btnWheelActivation();
        etEnterWord.setOnTouchListener(this);
        buttonActivate();
        demiAction();
        activateBtn();
        makeOnScroll();
    }

    private void makeOnScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float d;
                sumOfDx += dx;
                d = (float) sumOfDx / (float) widthOfTheScreen;
                page = Math.round(Math.abs(d)) + 1;
                btn1.setText(String.valueOf(sumOfDx));
                btn2.setText(String.valueOf(dx));

                if (Math.abs(dx) <= 4) {
                    if (page == 1) {
                        btnRight.setVisibility(View.INVISIBLE);
                    } else {
                        btnRight.setVisibility(View.VISIBLE);
                    }
                    if (page == maxPage) {
                        btnLeft.setVisibility(View.INVISIBLE);
                    } else {
                        btnLeft.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void buttonActivate() {
        btn1.setOnClickListener(new View.OnClickListener() {
            int def0, def, def1;

            @Override
            public void onClick(View v) {
                btnRight.setVisibility(View.VISIBLE);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(maxPage - 1);
            }
        });
    }


    private void activateBtn() {
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCurrentPage(1);

            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCurrentPage(-1);
            }
        });
    }

    private void updateCurrentPage(int addPage) {
        page = page - addPage;
        page = Math.max(page, 1);
        page = Math.min(page, maxPage);
        int page1 = maxPage - page;
        mRecyclerView.smoothScrollToPosition(page1);
    }


    private void demiAction() {
        //  newWord="לא";
        newWord = "שולל";
        // newWord="סבתא";
        //  newWord="אישה";
        //  newWord="סב";
        arrayOfSentence = helper.setWord(newWord);
        makeRecyeclView();
        etEnterWord.setText(newWord);
    }


    private void receycrcleIt() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        //   mLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager = new Speedy(this, LinearLayoutManager.HORIZONTAL, true);
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
                    btnLeft.setVisibility(View.VISIBLE);
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
            newWord = String.valueOf(etEnterWord.getText());
            if (newWord.equals("")) {
                activateKeyboard(1);
            } else {
                etEnterWord.setText("");
                activateKeyboard(1);
                arrayOfSentence.clear(); //clear list
                mAdapter.notifyDataSetChanged();
            }
        }

        return false;
    }


    public void eraseBtn_onClick(View view) {
        etEnterWord.setText("");
        arrayOfSentence.clear(); //clear list
        mAdapter.notifyDataSetChanged();
    }

    public void serachBtn_Onclick(View view) {
        newWord = etEnterWord.getText().toString();
        if (!newWord.equals("")) {
            activateKeyboard(0);
            newWord = newWord.trim();
            arrayOfSentence = helper.setWord(newWord);
            makeRecyeclView();
        } else {
            etEnterWord.setText("");
            arrayOfSentence.clear(); //clear list
            mAdapter.notifyDataSetChanged();
            activateKeyboard(0);
        }
    }

    private void activateKeyboard(int ind) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (ind == 0) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } else {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }


    private void btnWheelActivation() {
        btnWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rekaAnimation();
            }
        });

        ivDarkBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (darkBackOpen) rekaAnimation();
            }
        });
    }


    private void init() {
        myTypeface4 = Typeface.createFromAsset(getAssets(), "Frank.ttf");
        widthOfTheScreen = helper.getDimOfTheScreenHelperWidth();

        etEnterWord = (EditText) findViewById(R.id.enterWord);
        etEnterWord.setTextColor(Color.CYAN);
        etEnterWord.setTypeface(myTypeface4);

        btnWheel = (Button) findViewById(R.id.btnWheel);
        ivDarkBack = (ImageView) findViewById(R.id.ivDarkBackTrain);
        ivDarkBack1 = (ImageView) findViewById(R.id.ivDarkLittle);
        btnLeft = (Button) findViewById(R.id.btnLeftArrow);
        btnRight = (Button) findViewById(R.id.btnRightArrow);
        btnRight.setVisibility(View.INVISIBLE);
        btnLeft.setVisibility(View.INVISIBLE);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

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

    public void rekaAnimation() {
        ivDarkBack.setVisibility(View.VISIBLE);
        ivDarkBack1.setVisibility(View.VISIBLE);
        if (darkBackOpen) {
            helper.rekaAnimationHelper(darkBackOpen, rate);
            wheelFragment.makeFragmentAnimation62(darkBackOpen);
            darkBackOpen = false;
            waitWithIt();
        } else {
            helper.rekaAnimationHelper(darkBackOpen, rate);
            wheelFragment.makeFragmentAnimation62(darkBackOpen);
            darkBackOpen = true;
        }
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

}
