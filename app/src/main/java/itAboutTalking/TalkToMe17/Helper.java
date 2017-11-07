package itAboutTalking.TalkToMe17;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Helper extends AppCompatActivity {


    private Context context;
    private int widthOfTheScreen,hightOfTheScreen,groupNumber,maxPage;
    private Typeface myTypeface4;
    private ArrayList<ArrayList<String>> bigArrayList=new ArrayList<ArrayList<String>>();
    private ArrayList<Sentence> arrayOfSentence;
    private String bigString,newWord1;
    String[] rArray,sArray,wArray,arr4Array;
    String[]sLittleArray,wLittleArray;
    List<String> sArrayOfList,arr3List;



    public Helper(Context context) {
        this.context = context;
        myTypeface4=Typeface.createFromAsset(context.getAssets(),"Frank.ttf");

    }

    public  ArrayList<Sentence> setWord(String newWord) {
        newWord1=newWord;
        groupNumber = getGroupNumber1(bigString,newWord);
      /*  if (groupNumber == 0) {
             createRWgroupNumber(arr3List,arr4Array,newWord);
        }*/
        if (groupNumber <= 0) {
        groupNumber = getGroupNumber2(newWord);
    }
        if (groupNumber > 0) {
        findSentences();
        maxPage = sArrayOfList.size();
        arrayOfSentence=creatFirstArray(maxPage,sArrayOfList,sArray);
    }
                           else {
        arrayOfSentence=creatNoSentece();
    }
        return arrayOfSentence;
}
    private void findSentences() {
        int groupNumber1=0;
        String st1=rArray[groupNumber-1];
        st1=improveString(st1);
        sLittleArray = st1.split(",");
        sArrayOfList=new ArrayList<String>(Arrays.asList(sLittleArray));
        String st2=wArray[groupNumber-1];
        if (!st2.contains(",")){
            st2=improveString(st2);
            groupNumber1=Integer.parseInt(st2);
            st2 = wArray[groupNumber1 - 1];
            st2 = improveString(st2);
        }
        wLittleArray = st2.split(",");
        if (groupNumber1==0){
            String stt=wLittleArray[0];
            stt=improveString(stt);
            groupNumber1=Integer.parseInt(stt);
        }
        for (int i=0; i<wLittleArray.length;i++){
            String stt1 =wLittleArray[i];
            stt1=improveString(stt1);
            if (Collections.frequency(sArrayOfList, stt1)==0){
                if (Integer.parseInt(stt1)!=groupNumber1){
                    sArrayOfList.add(stt1);
                }
            }
        }
    }

    public String improveString(String stt){
        String st1=stt;
        st1=st1.trim();
        st1=st1.replace("[","");
        st1=st1.replace("]","");
        return st1;
    }


    public void readData(){
        bigString=readFromAssets1();
        rArray=readR();
        wArray=readW();
        sArray=readS();
        arr3List=readArr3();
        arr4Array=readArr4();
    }

    public ArrayList<Sentence> creatFirstArray(int maxPage, List<String> sArrayOfList, String[] sArray) {
        arrayOfSentence=new ArrayList<Sentence>();
        for (int i=1; i<=maxPage;i++){
            Sentence sen=new Sentence("","","");
            arrayOfSentence.add(sen);
        }
        for (int i=0; i<maxPage; i++) {
            int i2=maxPage-i-1;
            Sentence sen = arrayOfSentence.get(i2);
            int i1 = i + 1;
            String sNum = i1 + "/" + maxPage;
            sen.setNumSentence(sNum);
            String s = sArrayOfList.get(i2);
            sen.setTitleSentence(getTitle(s,sArray));
            s = sArrayOfList.get(i2);
            String st = getSentence(s,sArray);
            String str = correctString(st);
            sen.setBodySentence(str);
        }
        return arrayOfSentence;
    }
    public ArrayList<Sentence> creatNoSentece() {
        arrayOfSentence=new ArrayList<Sentence>();
        Sentence sen=new Sentence("","","");
        arrayOfSentence.add(sen);
        String sNum = "1/1";
        sen.setNumSentence(sNum);
        String s =newWord1;
        sen.setTitleSentence(s);
        s = "לא נמצאו משפטים למילה הזאת, אנא נסה להקליק פעם נוספת. ";
        sen.setBodySentence(s);
        return arrayOfSentence;
    }
    private String getSentence(String st,String[] sArray){
        int ii1=Integer.parseInt((st.replaceAll("[\\D]","")));
        String st1=sArray[ii1-1];
        return  st1;
    }

    private String getTitle(String st,String[] sArray){
        Boolean bo=true;
        String sent=null;
        int ii1=Integer.parseInt((st.replaceAll("[\\D]","")))-1;

        while (bo){
            String st1=sArray[ii1];
            if (st1.contains("*")){
                st1= st1.replace(".", "");
                st1=st1.replace("\"","");
                st1=st1.trim();
                sent = st1.replace("*", "");
                bo=false;
            } else{
                ii1--;
            }
        }

        return sent;
    }
    public int getGroupNumber1(String bigString,String newWord) {
        int num = 0;
        int ii, ii1 = 0;
        Boolean bo = true;
        Boolean bo1 = true;
        String bigString1 = bigString.replace("\"", "");
        String newWord1 ="\""+ newWord + "-";
        if (bigString.contains(newWord1)) {
            ii = bigString.indexOf(newWord1)+1;
            while (bo && ii>0) {
                String st = String.valueOf(bigString.charAt(ii));
                if (st.equals("-")) {
                    ii1 = ii + 1;
                    while (bo1) {
                        String st1 = String.valueOf(bigString.charAt(ii1));
                        if (st1.equals("\"")) {
                            bo1 = false;
                        } else {
                            ii1++;
                        }
                    }
                    bo = false;
                } else {
                    ii++;
                }
            }
            if (ii1 > 0) {
                String st5 = bigString.substring(ii + 1, ii1);
                num = Integer.parseInt(st5);
            }

        }
        return num;
    }
    public int getGroupNumber2(String newWord) {
        int num = 0;
        int ii, ii1 = 0;
        Boolean bo = true;
        Boolean bo1 = true;
        String littleString=readpro172();
        String newWord1 = "\"" + newWord + "-";
        if (littleString.contains(newWord1)) {
            ii = littleString.indexOf(newWord1) + 1;
            while (bo && ii > 0) {
                String st = String.valueOf(littleString.charAt(ii));
                if (st.equals("-")) {
                    ii1 = ii + 1;
                    while (bo1) {
                        String st1 = String.valueOf(littleString.charAt(ii1));
                        if (st1.equals("\"")) {
                            bo1 = false;
                        } else {
                            ii1++;
                        }
                    }
                    bo = false;
                } else {
                    ii++;
                }
            }
            if (ii1 > 0) {
                String st5 = littleString.substring(ii + 1, ii1);
                num = Integer.parseInt(st5);
            }

        }
        return num;
    }

    public String readFromAssets1(){
        ArrayList<String> littleList1=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {bufferReader1 = new BufferedReader(
                new InputStreamReader(context.getAssets().open("pro171.txt"), "windows-1255"));

            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length()>4) {
                    littleList1.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            String bigString=littleList1.get(0);
            bigString=improveString(bigString);
            return bigString;
        }
    }
    public int createRWgroupNumber(List<String> arr3List, String[] arr4Array,String newWord){

        int num=arr3List.indexOf(newWord);
        int num1=arr3List.indexOf(" "+newWord);
        int num2=arr3List.indexOf(newWord+" ");
        int groupNumber=Math.max(num,Math.max(num1,num2));
        if (groupNumber>0) {
            String stt = arr4Array[groupNumber];
            groupNumber = Integer.parseInt(stt.toString());

        }
        return groupNumber;
    }
    private String readpro172() {
        ArrayList<String> littleList1=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {bufferReader1 = new BufferedReader(
                new InputStreamReader(context.getAssets().open("pro172.txt"), "windows-1255"));

            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length()>4) {
                    littleList1.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            String littleString=littleList1.get(0);

            improveString(littleString);
            return littleString;
        }

    }
    public String[] readR(){
        ArrayList<String> littleList1=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {bufferReader1 = new BufferedReader(
                new InputStreamReader(context.getAssets().open("rSent.txt"), "windows-1255"));

            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length()>4) {
                    littleList1.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            String rString=littleList1.get(0);
            rString=improveString(rString);
            String stt="\""+","+"\"";
            String[] rArray=rString.split(stt);
            return rArray;
        }
    }
    public String[] readW(){
        ArrayList<String> littleList1=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {bufferReader1 = new BufferedReader(
                new InputStreamReader(context.getAssets().open("wSent.txt"), "windows-1255"));
            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length()>4) {
                    littleList1.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            String wString=littleList1.get(0);
            wString=improveString(wString);
            String stt="\""+","+"\"";
            String[] wArray=wString.split(stt);
            return wArray;
        }
    }
    public String[] readS(){
        ArrayList<String> littleList1=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {bufferReader1 = new BufferedReader(
                new InputStreamReader(context.getAssets().open("sSent.txt"), "windows-1255"));
            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length()>4) {
                    littleList1.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        String sString=littleList1.get(0);
        sString=improveString(sString);
        String stt="\""+","+"\"";
        String[] sArray=sString.split(stt);
        return sArray;
    }




    public List<String> readArr3(){
        ArrayList<String> littleList1=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {bufferReader1 = new BufferedReader(
                new InputStreamReader(context.getAssets().open("a3Sent.txt"), "windows-1255"));
            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length()>4) {
                    littleList1.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            String arr3BigString=littleList1.get(0);
            arr3BigString=improveString(arr3BigString);
            arr3BigString=arr3BigString.replace("\"","");
            //  arr3Array=arr3BigString.split(",");
            List<String> arr3List=new ArrayList<String>(Arrays.asList(arr3BigString.split(",")));
            return arr3List;
        }

    }
    public String[] readArr4(){
        ArrayList<String> littleList1=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {bufferReader1 = new BufferedReader(
                new InputStreamReader(context.getAssets().open("a4Sent.txt"), "windows-1255"));
            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length()>4) {
                    littleList1.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            String arr4String=littleList1.get(0);
            arr4String=improveString(arr4String);
            String stt="\""+","+"\"";
            String[] arr4Array=arr4String.split(stt);
            return arr4Array;

        }
    }

  /*  public String improveString(String stt){
        String st1=stt;
        st1=st1.trim();
        st1=st1.replace("[","");
        st1=st1.replace("]","");
        return st1;
    }*/






    public int getDimOfTheScreenHelperWidth() {
        int w=0;
        DisplayMetrics d=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(d);
        widthOfTheScreen=d.widthPixels;
        return (widthOfTheScreen);
    }
    public int getDimOfTheScreenHelperHight() {
        int h=0;
        DisplayMetrics d=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(d);
        hightOfTheScreen=d.heightPixels;
        return (hightOfTheScreen);
    }
    public void rekaAnimationHelper(Boolean darkBackOpenPosition,int rate,ImageView ivDarkBack,Button btnWheel) {
       /* ImageView ivDarkBack=(ImageView)((Activity)context). findViewById(R.id.ivDarkBack);
        ImageView ivDarkBack1 = (ImageView)((Activity)context). findViewById(R.id.ivDarkLittle);
        Button btnWheel = (Button)((Activity)context). findViewById(R.id.btnWheel);*/

        ivDarkBack.setVisibility(View.VISIBLE);
   //     ivDarkBack1.setVisibility(View.VISIBLE);
        if (darkBackOpenPosition) {
            ObjectAnimator object1 = ObjectAnimator.ofFloat(btnWheel, View.ROTATION, -250);
            object1.setDuration(rate);
            ObjectAnimator object2 = ObjectAnimator.ofFloat(ivDarkBack, View.ALPHA,0.7f, 0f);
            object2.setDuration(rate);
           /* ObjectAnimator object22 = ObjectAnimator.ofFloat(ivDarkBack1, View.ALPHA,0.7f, 0f);
            object22.setDuration(rate);*/
            AnimatorSet set = new AnimatorSet();
          //  set.play(object1).with(object2).with(object22);
            set.play(object1).with(object2);
            set.start();
            //wheelFragment.makeFragmentAnimation62(darkBackOpenPosition);
            darkBackOpenPosition = false;
            // waitWithIt();
        } else {
           // ivDarkBack.bringToFront();
           /* ivDarkBack1.setVisibility(View.VISIBLE);
            ivDarkBack1.bringToFront();*/
            ObjectAnimator object1 = ObjectAnimator.ofFloat(btnWheel, View.ROTATION, 250);
            object1.setDuration(rate);
            ObjectAnimator object2 = ObjectAnimator.ofFloat(ivDarkBack, View.ALPHA, 0f,0.7f);
            object2.setDuration(rate);
           /* ObjectAnimator object22 = ObjectAnimator.ofFloat(ivDarkBack1, View.ALPHA, 0f,0.7f);
            object22.setDuration(rate);*/
            AnimatorSet set = new AnimatorSet();
         //   set.play(object1).with(object2).with(object22);
            set.play(object1).with(object2);
            set.start();
            // wheelFragment.makeFragmentAnimation62(darkBackOpenPosition);
            darkBackOpenPosition = true;
        }

    }
  /*  private void tic(int ind){
        if (ind==0){
            startTime= System.currentTimeMillis();
        }
        if (ind==1){
            long difference= System.currentTimeMillis()-startTime;
            btn1.setText(String.valueOf(difference));
        }
        if (ind==2){
            long difference= System.currentTimeMillis()-startTime;
            btn2.setText(String.valueOf(difference));
        }

    }*/






    public int setPixel(int num){
        Resources r= ((Activity)context).getResources();
        int px;
        px=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,num,r.getDisplayMetrics());
        return px;
    }

    public void readFromAssets()
    {
        ArrayList<String> littleList=new ArrayList<String>();
        BufferedReader bufferReader1 = null;
        try {
            bufferReader1 = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("text1.txt"), "windows-1255"));
            String oneLine1=null;
            while ((oneLine1 = bufferReader1.readLine()) != null) {
                if (oneLine1.length() > 5) {
                    if (oneLine1.contains("*")) {
                        if (littleList.size() > 0) {
                            bigArrayList.add(littleList);
                            littleList=new ArrayList<String>();
                        }
                    }
                    littleList.add(oneLine1);
                }
            }
            bufferReader1.close();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (bufferReader1 != null) {
                try {
                    bufferReader1.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    private  String correctString(String oneLine1){
        String newLine=oneLine1;
        newLine=newLine.replace("\"","");
        newLine=newLine.replace("*","");
        char ch=newLine.charAt(newLine.length()-2);
        if (ch=='.'){
            newLine=newLine.substring(0,newLine.length()-1);
        }
        return newLine;
    }









}
