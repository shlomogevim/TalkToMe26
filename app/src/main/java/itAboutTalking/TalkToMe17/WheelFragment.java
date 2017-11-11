package itAboutTalking.TalkToMe17;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WheelFragment extends Fragment{

    int rate=850;

    public WheelFragment() {
         }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wheel, container, false);


        return view;
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

}
