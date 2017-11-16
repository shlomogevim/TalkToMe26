package itAboutTalking.TalkToMe17;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Context context;
    private ArrayList<Sentence> arrayOfSentence; //items
    Typeface myTypeface4;
    Button btnLeft, btnRight;
    String myColor;
    Boolean italiceMode;


    public MainAdapter(ArrayList<Sentence> arrayOfSentence, Context context, Typeface myTypeface4,
                       String myColor, Boolean italiceMode) {
        this.arrayOfSentence = arrayOfSentence;
        this.myTypeface4 = myTypeface4;
        this.myColor = myColor;
        this.context = context;
        this.italiceMode = italiceMode;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mNum.setText(arrayOfSentence.get(position).getNumSentence());
        holder.mTitle.setText(arrayOfSentence.get(position).getTitleSentence());
        holder.mBody.setText(arrayOfSentence.get(position).getBodySentence());
    }


    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.rowLayout.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return arrayOfSentence.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNum, mTitle, mBody;
        private LinearLayout rowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mNum = (TextView) itemView.findViewById(R.id.numSentence);
            mTitle = (TextView) itemView.findViewById(R.id.titleSentence);
            mBody = (TextView) itemView.findViewById(R.id.bodySentence);
            rowLayout = (LinearLayout) itemView.findViewById(R.id.rowLayout);
            if (myTypeface4 != null) {
                if (italiceMode) {
                    mNum.setTypeface(myTypeface4, Typeface.ITALIC);
                    mTitle.setTypeface(myTypeface4, Typeface.ITALIC);
                    mBody.setTypeface(myTypeface4, Typeface.ITALIC);
                } else {
                    mNum.setTypeface(myTypeface4);
                    mTitle.setTypeface(myTypeface4);
                    mBody.setTypeface(myTypeface4);
                }
                if (myColor != null) {
                    mNum.setTextColor(Color.parseColor(myColor));
                    mTitle.setTextColor(Color.parseColor(myColor));
                    mBody.setTextColor(Color.parseColor(myColor));
                }
           /* mNum.setTypeface(null,Typeface.ITALIC);
            mTitle.setTypeface(null,Typeface.ITALIC);
            mBody.setTypeface(null,Typeface.ITALIC);*/
            }

        }

    }
}
