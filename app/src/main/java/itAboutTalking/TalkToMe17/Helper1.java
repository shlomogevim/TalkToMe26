package itAboutTalking.TalkToMe17;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by sh on 09/11/2017.
 */

public class Helper1 extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {
    private final int REQ_CODE_SPEECH_INPUT = 143;
    private Context context;
    private int rate = 850;
    int page, maxPage = 0;
    private Runnable r;
    int sumOfDx, widthOfTheScreen;
    private Handler h;
    Typeface myTypeface4;
    private WheelFragment wheelFragment;
    private android.app.FragmentTransaction fragmentTransaction;
    android.app.FragmentManager fragmentManager;
    Button btnWheel, btnLeft, btnRight, btnExit, btnMute, btnRepeat, btnMic, btn1, btn2;
    EditText enterWordBox;
    AnimatorSet animatorSetOpen, animatorSetClose;
    LinearLayout groupIcon;
    ImageView ivDarkBack;
    Boolean darkBackOpenPosition = false;
    private String myColor;
    private Activity activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Sentence> arrayOfSentence;
    private String newWord;
    private Helper helper;
    SnapHelper snapHelper;

    /* public Helper1(Context context,String newWord,ArrayList<Sentence> arrayOfSentence) {
         this.context = context;
         this.activity=(Activity)context;
         this.newWord=newWord;
         this.arrayOfSentence=arrayOfSentence;
         helper=new Helper(context);
         init();
         receycrcleIt();
     }*/
    public Helper1(Context context,int ind) {
        this.context = context;
        this.activity = (Activity) context;
        helper = new Helper(context);
        helper.readData();
        init();
        receycrcleIt();
        activatAnimatorSet();
        makeOnScroll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                animatorSetClose.start();
                finishIt();
                break;
            case R.id.btnWheel:
                trainAndRekaAnimation();
                break;
            case R.id.ivDarkBack:
                trainAndRekaAnimation();
           /* {
                if (darkBackOpenPosition) trainAndRekaAnimation();
            }*/
                break;
            case R.id.btnLeftArrow:
                updateCurrentPage(-1);
                break;
            case R.id.btnRightArrow:
                updateCurrentPage(1);
                break;
            case R.id.btnSend:
                serachNewWord();
                break;
            case R.id.btnEraseNewWord:
                eraseWord();
                break;
            case R.id.btnMic:
                      makeMic();
                break;
        }
    }



    public void serachNewWord() {
        newWord = enterWordBox.getText().toString();
        Boolean bo = chk_newWord();
        if (bo) {
            activateKeyboard(0);
            newWord = newWord.trim();
            arrayOfSentence = helper.setWord(newWord);
            makeRecyeclView();
        } else {
            enterWordBox.setText("");
            if (arrayOfSentence != null) {
                arrayOfSentence.clear(); //clear list
                mAdapter.notifyDataSetChanged();
                activateKeyboard(0);
                arrowAppearance("none");
                page = 0;
            }
        }
    }

    public void serachNewWord1(String word) {
        newWord = word;
        Boolean bo = chk_newWord();
        if (bo) {
            activateKeyboard(0);
            newWord = newWord.trim();
            arrayOfSentence = helper.setWord(newWord);
            makeRecyeclView();
        } else {
            enterWordBox.setText("");
            if (arrayOfSentence != null) {
                arrayOfSentence.clear(); //clear list
                mAdapter.notifyDataSetChanged();
                activateKeyboard(0);
                arrowAppearance("none");
                page = 0;
            }

        }
    }

    private void receycrcleIt() {
        mRecyclerView = (RecyclerView) activity.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        //   mLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager = new Speedy(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        snapHelper = new LinearSnapHelper();
        try {
            snapHelper.attachToRecyclerView(mRecyclerView);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void makeRecyeclView() {
        maxPage = arrayOfSentence.size();
        mAdapter = new MainAdapter(arrayOfSentence, this, myTypeface4,myColor, btnLeft, btnRight);
        mRecyclerView.setAdapter(mAdapter);
        locatInTheBeginig();
        enterWordBox.setText(newWord);
    }

    public void activateKeyboard(int ind) {
        InputMethodManager imm = (InputMethodManager)context. getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (ind == 0) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            btnWheel.setVisibility(View.VISIBLE);
            btnWheel.bringToFront();
        } else {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
      //      btnWheel.setVisibility(View.INVISIBLE);
        }
    }

    private boolean chk_newWord() {
        int le;
        String st = "אבגדהוזחטיכלמנסעפצקרשתםןףךץ";
        boolean bo = true;
        char ch;
        newWord = newWord.trim();
        le = newWord.length();
        if (le >= 2) {
            for (int i = 0; i < le; i++) {
                ch = newWord.charAt(i);
                String s = String.valueOf(ch);
                if (!st.contains(s)) {
                    bo = false;
                }
            }
        } else {
            bo = false;
        }
        return bo;
    }

    public void eraseWord() {
        if (newWord != null) {
            enterWordBox.setText("");
            arrayOfSentence.clear(); //clear list
            mAdapter.notifyDataSetChanged();
            arrowAppearance("none");
            page = 0;
        }
    }

    public void makeOnScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float d;
                sumOfDx += dx;
                d = (float) sumOfDx / (float) widthOfTheScreen;
                if (page != 0) {
                    page = Math.round(Math.abs(d)) + 1;
                }
              /*  btn1.setText(String.valueOf(sumOfDx));
                btn2.setText(String.valueOf(dx));*/

                if (Math.abs(dx) <= 4) {
                    if (page == 1 || page == 0) {
                        btnRight.setVisibility(View.INVISIBLE);
                    } else {
                        btnRight.setVisibility(View.VISIBLE);
                    }
                    if (page == maxPage || page == 0) {
                        btnLeft.setVisibility(View.INVISIBLE);
                    } else {
                        btnLeft.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public void locatInTheBeginig() {
        r = new Runnable() {
            @Override
            public void run() {
                mLayoutManager.scrollToPosition(maxPage - 1);
                arrowAppearance("none");
                if (maxPage > 1) {
                    sumOfDx = 0;
                    page = 1;
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


    private void updateCurrentPage(int addPage) {
        page = page - addPage;
        page = Math.max(page, 1);
        page = Math.min(page, maxPage);
        int page1 = maxPage - page;
        mRecyclerView.smoothScrollToPosition(page1);
    }

    public void activatAnimatorSet() {
        animatorSetOpen = makeTrainAndBackAnimation(false, groupIcon, ivDarkBack, btnWheel);
        animatorSetClose = makeTrainAndBackAnimation(true, groupIcon, ivDarkBack, btnWheel);
        groupIcon.setVisibility(View.INVISIBLE);
        ivDarkBack.setVisibility(View.INVISIBLE);
        darkBackOpenPosition = false;
        animatorSetClose.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                activateBtn(true);
                darkBackOpenPosition = false;
                ivDarkBack.setVisibility(View.INVISIBLE);
            }
        });
        animatorSetOpen.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                activateBtn(false);
                darkBackOpenPosition = true;
            }
        });
    }

    private void activateBtn(Boolean allBtns) {
        //  Log.i("state"," bo= "+String.valueOf(bo)+"\n");
        btnWheel.setEnabled(true);
        btnExit.setEnabled(true);
        ivDarkBack.setEnabled(true);
        if (allBtns) {
            btnMute.setEnabled(true);
            btnExit.setEnabled(true);
            btnLeft.setEnabled(true);
            btnRight.setEnabled(true);
            btnMic.setEnabled(true);
            enterWordBox.setEnabled(true);
            ivDarkBack.setVisibility(View.INVISIBLE);
        }
    }

    private void disableBtns() {
        //  Log.i("state"," bo= "+String.valueOf(bo)+"\n");
        btnWheel.setEnabled(false);
        ivDarkBack.setEnabled(false);
        btnMute.setEnabled(false);
        btnExit.setEnabled(false);
        btnLeft.setEnabled(false);
        btnRight.setEnabled(false);
        btnMic.setEnabled(false);
        enterWordBox.setEnabled(false);
    }

    public AnimatorSet makeTrainAndBackAnimation(boolean darkBackOpenPosition, LinearLayout groupIcon, ImageView ivDarkBack, Button btnWheel) {
        AnimatorSet animatorSet;
        if (darkBackOpenPosition) {
            ObjectAnimator object0 = ObjectAnimator.ofFloat(btnWheel, View.ROTATION, -250);
            object0.setDuration(rate);
            ObjectAnimator object1 = ObjectAnimator.ofFloat(groupIcon, View.TRANSLATION_X, 0, -900);
            object1.setDuration(rate);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(groupIcon, View.SCALE_X, 1f, 0f);
            scaleX.setDuration(rate);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(groupIcon, View.SCALE_Y, 1f, 0f);
            scaleY.setDuration(rate);
            ObjectAnimator objectDark = ObjectAnimator.ofFloat(ivDarkBack, View.ALPHA, 0.7f, 0f);
            objectDark.setDuration(rate);
            animatorSet = new AnimatorSet();
            animatorSet.play(object0).with(object1).with(scaleX).with(scaleY).with(objectDark);
            // animatorSet.play(object1).with(scaleX).with(scaleY).with(objectDark);
        } else {
            // groupIcon.setVisibility(View.VISIBLE);
            groupIcon.bringToFront();
            ivDarkBack.setVisibility(View.VISIBLE);
            ObjectAnimator object0 = ObjectAnimator.ofFloat(btnWheel, View.ROTATION, 250);
            object0.setDuration(rate);
            ObjectAnimator object1 = ObjectAnimator.ofFloat(groupIcon, View.TRANSLATION_X, -900, 0);
            object1.setDuration(rate);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(groupIcon, View.SCALE_X, 0f, 1f);
            scaleX.setDuration(rate);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(groupIcon, View.SCALE_Y, 0f, 1f);
            scaleY.setDuration(rate);
            ObjectAnimator objectDark = ObjectAnimator.ofFloat(ivDarkBack, View.ALPHA, 0f, 0.7f);
            objectDark.setDuration(rate);
            animatorSet = new AnimatorSet();
            animatorSet.play(object0).with(object1).with(scaleX).with(scaleY).with(objectDark);
            // animatorSet.play(object1).with(scaleX).with(scaleY).with(objectDark);
        }
        btnWheel.bringToFront();
        return animatorSet;
    }

    public void trainAndRekaAnimation() {
        disableBtns();
        if (ivDarkBack.getVisibility() == View.VISIBLE) {
            darkBackOpenPosition = true;
            animatorSetClose.start();
        } else {
            darkBackOpenPosition = false;
            groupIcon.setVisibility(View.VISIBLE);
            groupIcon.setEnabled(true);
            btnExit.setEnabled(true);
            btnMute.setEnabled(true);
            btnRepeat.setEnabled(true);
            ivDarkBack.setVisibility(View.VISIBLE);
            animatorSetOpen.start();

        }
    }

  /*  public void btnWheel_Onclick(View view) {
        trainAndRekaAnimation();
    }*/


    public void ivDarkBack_Onclick(View view) {
        if (darkBackOpenPosition) trainAndRekaAnimation();
    }


    private void finishIt() {
        //rekaAnimation();
        r = new Runnable() {
            @Override
            public void run() {
                activity.finish();
                System.exit(0);
            }
        };

        h = new Handler();
        h.postDelayed(r, rate);
    }
    public void makeSetupChange(int fontIndecator,String colorString){
       Boolean bo=true;

        if (fontIndecator>=0){
            String fontSt=setOtherFont(fontIndecator);
            if (!fontSt.equals("") && fontSt != null) {
                myTypeface4 = Typeface.createFromAsset(context.getAssets(), fontSt);
                enterWordBox.setTypeface(myTypeface4);
            }
        }
        if (!colorString.equals("")){
                            myColor="#"+colorString;
            try {
                enterWordBox.setTextColor(Color.parseColor(myColor));
            } catch (Exception e) {
                e.printStackTrace();
                bo=false;
                activateKeyboard(0);
            }
        }
      if (bo) {
          activateKeyboard(0);
          makeRecyeclView();
      }
    }

    public String setOtherFont(int fontIndicator) {
           String fontSt="";
        switch (fontIndicator) {
            case 0:
                fontSt = "Frank.ttf";
                break;
            case 1:
                fontSt = "Abraham.ttf";
                break;
            case 2:
                fontSt = "aharoni.ttf";
                break;
            case 3:
                fontSt = "Alef.ttf";
                break;
            case 4:
                fontSt = "AmaticaSC.ttf";
                break;
            case 5:
                fontSt = "anka.ttf";
                break;
            case 6:
                fontSt = "Assistant.ttf";
                break;
            case 7:
                fontSt = "carmela.ttf";
                break;
            case 8:
                fontSt = "carmelitregular.ttf";
                break;
            case 9:
                fontSt = "DavidLibre-Regular.ttf";
                break;
            case 10:
                fontSt = "dorianclm-book-webfont.ttf";
                break;
            case 11:
                fontSt = "dragon-webfont.ttf";
                break;
            case 12:
                fontSt = "drugulinclm-bolditalic-webfont.ttf";
                break;
            case 13:
                fontSt = "elliniaclm-bolditalic-webfont.ttf";
                break;
            case 14:
                fontSt = "FrankRuhlLibre-Regular.ttf";
                break;
            case 15:
                fontSt = "ganclm_bold-webfont.ttf";
                break;
            case 16:
                fontSt = "gladiaclm-bold-webfont.ttf";
                break;
            case 17:
                fontSt = "hadasimclm-regularoblique-webfont.ttf";
                break;
            case 18:
                fontSt = "Heebo-Regular.ttf";
                break;
            case 19:
                fontSt = "hillelclm-medium-webfont.ttf";
                break;
            case 20:
                fontSt = "horevclm-heavy-webfont.ttf";
                break;
            case 21:
                fontSt = "journalclm-light-webfont.ttf";
                break;
            case 22:
                fontSt = "keteryg-mediumoblique-webfont.ttf";
                break;
            case 23:
                fontSt = "makabiyg-webfont.ttf";
                break;
            case 24:
                fontSt = "migdalfontwin-webfont.ttf";
                break;
            case 25:
                fontSt = "MiriamLibre-Regular.ttf";
                break;
            case 26:
                fontSt = "nachlieliclm-boldoblique-webfont.ttf";
                break;
            case 27:
                fontSt = "nehama-webfont.ttf";
                break;
            case 28:
                fontSt = "noot-aj.ttf";
                break;
            case 29:
                fontSt = "opensanshebrew-extrabolditalic-webfont.ttf";
                break;
            case 30:
                fontSt = "Rubik-Medium.ttf";
                break;
            case 31:
                fontSt = "SecularOne-Regular.ttf";
                break;
            case 32:
                fontSt = "SuezOne-Regular.ttf";
                break;
            case 33:
                fontSt = "trashimclm-bold-webfont.ttf";
                break;
            case 34:
                fontSt = "VarelaRound-Regular.ttf";
                break;
            case 35:
                fontSt = "yehudaclm-bold-webfont.ttf";
                break;

        }
     return fontSt;
   }

    private void init() {
        myTypeface4 = Typeface.createFromAsset(context.getAssets(), "Frank.ttf");
        groupIcon = (LinearLayout) activity.findViewById(R.id.groupIconTrain);
        ivDarkBack = (ImageView) activity.findViewById(R.id.ivDarkBack);

        enterWordBox = (EditText) activity.findViewById(R.id.enterWordBox);
        enterWordBox.setTextColor(Color.CYAN);
        enterWordBox.setTypeface(myTypeface4);

        btnWheel = (Button) activity.findViewById(R.id.btnWheel);
        btnMic = (Button) activity.findViewById(R.id.btnMic);
        ivDarkBack = (ImageView) activity.findViewById(R.id.ivDarkBack);

        btnLeft = (Button) activity.findViewById(R.id.btnLeftArrow);
        btnRight = (Button) activity.findViewById(R.id.btnRightArrow);
        btnRight.setVisibility(View.INVISIBLE);
        btnLeft.setVisibility(View.INVISIBLE);
        btnExit = (Button) activity.findViewById(R.id.btnExit);
        btnMute = (Button) activity.findViewById(R.id.btnMute);
        btnRepeat = (Button) activity.findViewById(R.id.btnRepeat);
        btn1 = (Button) activity.findViewById(R.id.btn1);
        btn2 = (Button) activity.findViewById(R.id.btn2);

        widthOfTheScreen = getDimOfTheScreenHelperWidth();
        wheelFragment = new WheelFragment();

        fragmentManager = activity.getFragmentManager();           // getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
    }

    public int getDimOfTheScreenHelperWidth() {
        int w = 0;
        DisplayMetrics d = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(d);
        widthOfTheScreen = d.widthPixels;
        return (widthOfTheScreen);
    }


    /* public void rekaAnimation() {
        wheelFragment.makeFragmentAnimation(darkBackOpenPosition,groupIcon,ivDarkBack);
        if (darkBackOpenPosition) {
            waitWithIt();
        }
        darkBackOpenPosition=!darkBackOpenPosition;
    }*/
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            newWord = String.valueOf(enterWordBox.getText());
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
    private void makeMic() {
               Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
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

}