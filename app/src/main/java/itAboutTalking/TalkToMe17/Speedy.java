package itAboutTalking.TalkToMe17;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by sh on 06/11/2017.
 */

public class Speedy extends LinearLayoutManager {
    int interval;

    public Speedy(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public Speedy(Context context) {
        super(context);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {

        Log.i("state", " state" + String.valueOf(state) + "    position=" + String.valueOf(position)+"\n");

        // super.smoothScrollToPosition(recyclerView, state, position);
        final LinearSmoothScroller linearSmoothScroller=new LinearSmoothScroller(recyclerView.getContext()){

            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                //     Log.i("state", "   target= " + String.valueOf(targetPosition)+"\n");
                return Speedy.this.computeScrollVectorForPosition(targetPosition);

            }


            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {

                float div=0;
                // div=100f/displayMetrics.densityDpi;
                if (interval<1200){
                    div=200f/displayMetrics.densityDpi;
                }else if(interval<2500){
                    div=75f/displayMetrics.densityDpi;
                } else{
                    div=20f/displayMetrics.densityDpi;
                }
                Log.i("state", "   interval= " + String.valueOf(interval)+"\n");
                Log.i("state", "   div= " + String.valueOf(div)+"\n");
                return div;
            }

            @Override
            protected int calculateTimeForDeceleration(int dx) {
                interval=dx;

                //  Log.i("state", "   dx= " + String.valueOf(dx)+"\n");

                return super.calculateTimeForDeceleration((int)(dx*1.2));
            }
        };


        linearSmoothScroller.setTargetPosition(position);

        startSmoothScroll(linearSmoothScroller);


        /*dis = Math.max(dis, minScrolling);
        dis = Math.min(dis, -widthOfTheScreen);
        mainScrollLayout.animate().translationX(dis).setDuration(2000)
                .setInterpolator(new DecelerateInterpolator(4f)).start();*/



    }
}
