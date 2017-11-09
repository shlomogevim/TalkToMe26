package itAboutTalking.TalkToMe17;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    private Boolean darkBackOpenPosition = false;
    private ImageView ivDarkBack;
    private LinearLayout groupIcon;
    private   WheelFragment wheelFragment;
    private android.app.FragmentTransaction fragmentTransaction;
    android.app.FragmentManager fragmentManager;
    private Activity activity;

    public Helper1(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        groupIcon = (LinearLayout)activity.findViewById(R.id.groupIconTrain);
        ivDarkBack = (ImageView)activity. findViewById(R.id.ivDarkBack);

        wheelFragment = new WheelFragment();
        fragmentManager =activity.getFragmentManager();           // getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnExit:
                finishIt();
                break;
        }
    }
    private void finishIt() {
        rekaAnimation();
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

    public void rekaAnimation() {
        wheelFragment.makeFragmentAnimation(darkBackOpenPosition,groupIcon,ivDarkBack);
        if (darkBackOpenPosition) {
            waitWithIt();
        }
        darkBackOpenPosition=!darkBackOpenPosition;
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
}
