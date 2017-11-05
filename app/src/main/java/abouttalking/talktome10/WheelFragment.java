package abouttalking.talktome10;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WheelFragment extends Fragment{
    LinearLayout groupIcon;
    int rate=850;
    ImageView ivDarkBack;

    public WheelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wheel, container, false);
        groupIcon = (LinearLayout) view.findViewById(R.id.groupIconTrain);
        ivDarkBack=(ImageView)view.findViewById(R.id.ivDark);

        return view;
    }

    public void makeDark(boolean darkBackOpen) {
        ivDarkBack.setVisibility(View.VISIBLE);
        if (darkBackOpen) {
            ObjectAnimator object2 = ObjectAnimator.ofFloat(ivDarkBack, View.ALPHA, 0.7f, 0f);
            object2.setDuration(rate);
            AnimatorSet set = new AnimatorSet();
            set.play(object2);
            set.start();
        } else {
            ivDarkBack.setVisibility(View.VISIBLE);
            ObjectAnimator object2 = ObjectAnimator.ofFloat(ivDarkBack, View.ALPHA, 0f, 0.7f);
            object2.setDuration(rate);
            AnimatorSet set = new AnimatorSet();
            set.play(object2);
            set.start();
        }
    }
    public void makeFragmentAnimation62(boolean darkBackOpen) {
        groupIcon.setVisibility(View.VISIBLE);
        makeDark(darkBackOpen);
        if (darkBackOpen) {
            groupIcon.bringToFront();
            ObjectAnimator object1 = ObjectAnimator.ofFloat(groupIcon, View.TRANSLATION_X, 0, -900);
            object1.setDuration(rate);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(groupIcon, View.SCALE_X, 1f, 0f);
            scaleX.setDuration(rate);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(groupIcon, View.SCALE_Y, 1f, 0f);
            scaleY.setDuration(rate);
            AnimatorSet set = new AnimatorSet();
            set.play(object1).with(scaleX).with(scaleY);
            set.start();
            groupIcon.bringToFront();

        } else {
            groupIcon.bringToFront();
            ObjectAnimator object1 = ObjectAnimator.ofFloat(groupIcon, View.TRANSLATION_X, -900, 0);
            object1.setDuration(rate);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(groupIcon, View.SCALE_X, 0f, 1f);
            scaleX.setDuration(rate);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(groupIcon, View.SCALE_Y, 0f, 1f);
            scaleY.setDuration(rate);
            AnimatorSet set = new AnimatorSet();
            set.play(object1).with(scaleX).with(scaleY);
            set.start();
            groupIcon.bringToFront();
        }

    }



}
