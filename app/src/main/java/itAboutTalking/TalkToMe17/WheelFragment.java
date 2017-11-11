package itAboutTalking.TalkToMe17;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WheelFragment extends Fragment{
    int rate=850;
    /*Button btnWheel,btnLeft, btnRight,btnExit,btnMute,btnMic, btn1, btn2;
    EditText enterWordBox;
    AnimatorSet animatorSetOpen,animatorSetClose;
    LinearLayout groupIcon;
    ImageView ivDarkBack;
    Boolean darkBackOpenPosition = false;*/

    public WheelFragment() {

         }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wheel, container, false);


        return view;
    }
/*
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
           // groupIcon.bringToFront();
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
        if (darkBackOpenPosition){
            animatorSetClose.start();

        }else{
            groupIcon.setVisibility(View.VISIBLE);
            ivDarkBack.setVisibility(View.VISIBLE);
            animatorSetOpen.start();

        }
    }

    public void btnWheel_Onclick(View view) {
        trainAndRekaAnimation();
    }



    public void ivDarkBack_Onclick(View view) {
        if (darkBackOpenPosition) trainAndRekaAnimation();
    }


    public void btn1_Onclick(View view) {
        enterWordBox.setEnabled(true);
    }

    public void btn2_Onclick(View view) {

    }*/


}
