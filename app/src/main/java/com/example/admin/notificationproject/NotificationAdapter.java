package com.example.admin.notificationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Admin on 9/1/2017.
 */

public class NotificationAdapter extends BaseAdapter {
    private Context c;

    private int resource;
    private List<TitlePojo> reports;
    private LayoutInflater inflater;

    public static String STORE = "";
    public  static double AVERAGE= 0;
    public static String COMMENT="";
    public static String NAME_SUBJECT="";
    private double pertotal= 0;


    public NotificationAdapter(Context c, List<TitlePojo> reports) {
        this.c = c;
        this.reports = reports;
    }


    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return reports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View rootView, ViewGroup parent) {

        if (inflater == null) {

            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        }
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.report_model, parent, false);
        }


        Report report = reports.get(position);
        TextView tvSubjcet = (TextView) rootView.findViewById(R.id.tvSubjcet);
        TextView tvMarks = (TextView) rootView.findViewById(R.id.tvMarks);
        TextView tvComment = (TextView) rootView.findViewById(R.id.tvComment);
        TextView tvTerm = (TextView) rootView.findViewById(R.id.tvTerm);
        TextView tvTotal = (TextView) rootView.findViewById(R.id.tvTotal);
        ProgressBar pdMarks =rootView.findViewById(R.id.pbMarks);






        tvSubjcet.setText(report.getSubject());
        tvTerm.setText("Term : \n"+report.getTerm()+"");
        tvMarks.setText("Marks: \n"+report.getMarks());
        tvComment.setText("Comments: \n"+report.getComment());
        tvTotal.setText("Out of : \n"+report.getTotalMarks());

        final double mark = report.getMarks();
        final double markTotal =report.getTotalMarks();
        final double total = mark /markTotal;
        final   double per =total*100;


        int totalsize = reports.size();
        NAME_SUBJECT = report.getSubject()+"********"+per;
        pertotal=pertotal+per;
        double allPer= Math.round(pertotal/totalsize);
        AVERAGE= allPer;
        if(AVERAGE>40 && AVERAGE<60)
        {
            COMMENT="Well done you pass";
        } if(AVERAGE<40)
        {
            COMMENT="Sorry it a fail!! you need to push a little hard ";
        }
        if(AVERAGE>59 &&AVERAGE<75)
        {
            COMMENT="Very well done!! ";
        }
        if(AVERAGE>75 &&AVERAGE<100)
        {
            COMMENT="Excellent Good Have pass with Distinction keep it up";
        }


        tvSubjcet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(c, ""+total, Toast.LENGTH_SHORT).show();

                Toast.makeText(c, ""+per, Toast.LENGTH_SHORT).show();
            }
        });


        pdMarks.setProgress((int)per);



        // String name = report.getName();
//        String letter = Character.toString(name.charAt(0));
//        tvName.setText(letter);
//        tvCompletName.setText(name);
        return rootView;
    }
}

}
