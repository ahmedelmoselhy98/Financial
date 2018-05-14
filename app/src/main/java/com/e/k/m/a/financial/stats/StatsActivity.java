package com.e.k.m.a.financial.stats;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.models.MoneyQuntaties;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatsActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", new Locale("ar"));
        String ar = dateFormat.format(Calendar.getInstance().getTime());
        TextView cc = findViewById(R.id.current_month);
        cc.setText("احصائية شهر "+ar);


        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("isLogin", true);
        spe.commit();
        userEmail = sp.getString("useremail", "");

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        MoneyQuntaties quntaties = new MoneyQuntaties();
        quntaties = databaseAdapter.readMoneyQuantitiesFromDatebase(userEmail);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setMinimumHeight(5);
        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, quntaties.getSavings()),
                new DataPoint(2, 0),
                new DataPoint(3, quntaties.getExpenses()),
                new DataPoint(4, 0),
                new DataPoint(5, quntaties.getFinancialDues()),
                new DataPoint(6, 0)
        });
        TextView t1 = findViewById(R.id.financial);
        t1.setText("(4-6)المستحقات"+"\n"+quntaties.getFinancialDues() + " RS");
        TextView t2 = findViewById(R.id.expenses);
        t2.setText("(2-4)النفقات"+"\n"+quntaties.getExpenses() + " RS");
        TextView t3 = findViewById(R.id.savings);
        t3.setText("(0-2)المدخرات"+"\n"+quntaties.getSavings() + " RS");

        graph.addSeries(series1);
    }
}
