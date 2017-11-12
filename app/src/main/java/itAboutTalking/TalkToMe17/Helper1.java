package itAboutTalking.TalkToMe17;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sh on 09/11/2017.
 */

public class Helper1 extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private int rate = 850;
    private Runnable r;
    private Handler h;
    private   WheelFragment wheelFragment;
    private android.app.FragmentTransaction fragmentTransaction;
    android.app.FragmentManager fragmentManager;
    Button btnWheel,btnLeft, btnRight,btnExit,btnMute,btnRepeat,btnMic, btn1, btn2;
    EditText enterWordBox;
    AnimatorSet animatorSetOpen,animatorSetClose;
    LinearLayout groupIcon;
    ImageView ivDarkBack;
    Boolean darkBackOpenPosition = false;
    private Activity activity;

    public Helper1(Context context) {
        this.context = context;
        this.activity=(Activity)context;
        init();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnExit:
                                animatorSetClose.start();
                                finishIt();
                break;
            case R.id.btnWheel:
                trainAndRekaAnimation();
                break;
            case R.id.ivDarkBack:
                trainAndRekaAnimation();
                break;
        }
    }
    public void btn1_Onclick(View view) {
        enterWordBox.setEnabled(true);
    }

    public void btn2_Onclick(View view) {

    }

     public void activatAnimatorSet() {
        animatorSetOpen=makeTrainAndBackAnimation(false,groupIcon,ivDarkBack,btnWheel);
        animatorSetClose=makeTrainAndBackAnimation(true,groupIcon,ivDarkBack,btnWheel);
        groupIcon.setVisibility(View.INVISIBLE);
        ivDarkBack.setVisibility(View.INVISIBLE);
        darkBackOpenPosition=false;
        animatorSetClose.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                activateBtn(true);
                darkBackOpenPosition=false;
                ivDarkBack.setVisibility(View.INVISIBLE);
            }
        });
        animatorSetOpen.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                activateBtn(false);
                darkBackOpenPosition=true;
            }
        });
    }
    private void activateBtn(Boolean allBtns) {
        //  Log.i("state"," bo= "+String.valueOf(bo)+"\n");
        btnWheel.setEnabled(true);
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

    private void disableBtns(){
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

    public AnimatorSet makeTrainAndBackAnimation(boolean darkBackOpenPosition, LinearLayout groupIcon, ImageView ivDarkBack,Button btnWheel) {
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
        if (ivDarkBack.getVisibility()==View.VISIBLE){
            darkBackOpenPosition=true;
            animatorSetClose.start();
        }else{
            darkBackOpenPosition=false;
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
    private void init() {
        groupIcon = (LinearLayout)activity.findViewById(R.id.groupIconTrain);
        ivDarkBack = (ImageView)activity. findViewById(R.id.ivDarkBack);

        enterWordBox = (EditText)activity. findViewById(R.id.enterWordBox);
       /* enterWordBox.setTextColor(Color.CYAN);
        enterWordBox.setTypeface(myTypeface4);*/

        btnWheel = (Button)activity. findViewById(R.id.btnWheel);
        btnMic=(Button)activity. findViewById(R.id.btnMic);
        ivDarkBack = (ImageView)activity. findViewById(R.id.ivDarkBack);

        btnLeft = (Button)activity. findViewById(R.id.btnLeftArrow);
        btnRight = (Button)activity. findViewById(R.id.btnRightArrow);
        btnRight.setVisibility(View.INVISIBLE);
        btnLeft.setVisibility(View.INVISIBLE);
        btnExit=(Button)activity.findViewById(R.id.btnExit);
        btnMute=(Button)activity.findViewById(R.id.btnMute);
        btnRepeat=(Button)activity.findViewById(R.id.btnRepeat);
        btn1 = (Button)activity. findViewById(R.id.btn1);
        btn2 = (Button)activity. findViewById(R.id.btn2);

        wheelFragment = new WheelFragment();
        fragmentManager =activity.getFragmentManager();           // getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
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
}
