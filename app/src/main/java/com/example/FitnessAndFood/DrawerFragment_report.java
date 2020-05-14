package com.example.FitnessAndFood;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class DrawerFragment_report extends Fragment {

    private TextView desc,desc2,desc3,desc4;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    View v;
    GraphView graph;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.drawer_fragment_report,container,false);
        desc = v.findViewById(R.id.desc);
        desc2 = v.findViewById(R.id.desc2);
        desc3 = v.findViewById(R.id.desc3);
        desc4 = v.findViewById(R.id.desc4);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        readFrmDatabase();
        graph = (GraphView) v.findViewById(R.id.graph);
        return v;
    }

    //CHG
    public void readFrmDatabase(){
        db.collection("Users").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                desc.setText(task.getResult().getString("weight"));
                desc2.setText(task.getResult().getString("lightest"));
                desc3.setText(task.getResult().getString("heaviest"));
                desc4.setText(task.getResult().getString("bmi"));

                if(task.getResult().getString("history1").length()>0 && task.getResult().getString("history2").length()>0 && task.getResult().getString("history3").length()>0 && task.getResult().getString("history4").length()>0 && task.getResult().getString("history5").length()>0 ) {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(1, Double.parseDouble(task.getResult().getString("history1"))),
                            new DataPoint(2, Double.parseDouble(task.getResult().getString("history2"))),
                            new DataPoint(3, Double.parseDouble(task.getResult().getString("history3"))),
                            new DataPoint(4, Double.parseDouble(task.getResult().getString("history4"))),
                            new DataPoint(5, Double.parseDouble(task.getResult().getString("history5"))),
                    });
                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                    graph.setTitle("Weight Changes");
                    graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
                    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                    graph.setTitleColor(Color.GRAY);
                }
                else if(task.getResult().getString("history1").length()>0 && task.getResult().getString("history2").length()>0 && task.getResult().getString("history3").length()>0 && task.getResult().getString("history4").length()>0) {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(1, Double.parseDouble(task.getResult().getString("history1"))),
                            new DataPoint(2, Double.parseDouble(task.getResult().getString("history2"))),
                            new DataPoint(3, Double.parseDouble(task.getResult().getString("history3"))),
                            new DataPoint(4, Double.parseDouble(task.getResult().getString("history4"))),
                    });
                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                    graph.setTitle("Weight Changes");
                    graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
                    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                    graph.setTitleColor(Color.GRAY);
                }
                else if(task.getResult().getString("history1").length()>0 && task.getResult().getString("history2").length()>0 && task.getResult().getString("history3").length()>0) {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(1, Double.parseDouble(task.getResult().getString("history1"))),
                            new DataPoint(2, Double.parseDouble(task.getResult().getString("history2"))),
                            new DataPoint(3, Double.parseDouble(task.getResult().getString("history3"))),
                    });
                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                    graph.setTitle("Weight Changes");
                    graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
                    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                    graph.setTitleColor(Color.GRAY);
                }
                else if(task.getResult().getString("history1").length()>0 && task.getResult().getString("history2").length()>0) {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(1, Double.parseDouble(task.getResult().getString("history1"))),
                            new DataPoint(2, Double.parseDouble(task.getResult().getString("history2"))),
                    });
                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                    graph.setTitle("Weight Changes");
                    graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
                    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                    graph.setTitleColor(Color.GRAY);
                }
                else if(task.getResult().getString("history1").length()>0) {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(1, Double.parseDouble(task.getResult().getString("history1"))),
                    });
                    graph.setTitle("Weight Changes");
                    graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                    graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
                    graph.addSeries(series);
                    graph.setTitleColor(Color.GRAY);
                }
            }
        });
    }
}
