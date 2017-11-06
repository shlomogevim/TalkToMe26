package itAboutTalking.TalkToMe17;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    Context context;
    private ArrayList<Sentence> arrayOfSentence; //items
    Typeface myTypeface4;
    Button btnLeft,btnRight;

    public MainAdapter(ArrayList<Sentence> arrayOfSentence,Context context, Typeface myTypeface4,Button btnLeft,Button btnRight) {
        this.arrayOfSentence=arrayOfSentence;
        this.myTypeface4=myTypeface4;
        this.context=context;
        this.btnLeft=btnLeft;
        this.btnRight=btnRight;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        ViewHolder vh=new ViewHolder(v);
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

    public class ViewHolder extends  RecyclerView.ViewHolder  {
        private TextView mNum,mTitle,mBody;
        private LinearLayout rowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mNum= (TextView) itemView.findViewById(R.id.numSentence);
            mTitle= (TextView) itemView.findViewById(R.id.titleSentence);
            mBody= (TextView) itemView.findViewById(R.id.bodySentence);
            rowLayout=(LinearLayout)itemView.findViewById(R.id.rowLayout);
            mNum.setTypeface(myTypeface4);
            mTitle.setTypeface(myTypeface4);
            mBody.setTypeface(myTypeface4);
        }



    }

}
