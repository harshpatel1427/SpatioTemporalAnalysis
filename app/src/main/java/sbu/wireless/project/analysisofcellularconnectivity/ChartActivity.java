package sbu.wireless.project.analysisofcellularconnectivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.HashSet;

import sbu.wireless.project.analysisofcellularconnectivity.database.DbHandler;

/**
 * Created by Mondrita on 14-12-2015.
 */
public class ChartActivity extends Activity {
    protected static final long TIME_DELAY = 1000;
    private View mChart;
    DbHandler dbHandler;
    ArrayList<String> networkTypeData;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValueForChart();
        handler.post(updateTextRunnable);

    }

    Runnable updateTextRunnable = new Runnable() {
        public void run() {
            getValueForChart();
            handler.postDelayed(this, TIME_DELAY);
        }
    };

    private void openChart(ArrayList<String> name, ArrayList<Integer> value) {
        // Color of each Pie Chart Sections
        int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
                Color.RED, Color.YELLOW, Color.GRAY, Color.BLACK, Color.DKGRAY};
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(
                " Cellular connectivity Analysis");
        for (int i = 0; i < value.size(); i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(name.get(i), value.get(i));
        }
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < value.size(); i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            //Adding colors to the chart
            defaultRenderer.setBackgroundColor(Color.TRANSPARENT);
            defaultRenderer.setApplyBackgroundColor(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Cellular connectivity Analysis");
        defaultRenderer.setChartTitleTextSize(30);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setLabelsTextSize(20);
        defaultRenderer.setZoomButtonsVisible(false);


        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        // remove any views before u paint the chart
        chartContainer.removeAllViews();
        // drawing pie chart
        mChart = ChartFactory.getPieChartView(getBaseContext(),
                distributionSeries, defaultRenderer);
        // adding the view to the linearlayout
        chartContainer.addView(mChart);
    }

    public void getValueForChart() {
        dbHandler = new DbHandler(this);
        networkTypeData = dbHandler.getNetworkTypeData();

        ArrayList<String> possibleValues = new ArrayList<String>();
        ArrayList<Integer> countValues = new ArrayList<Integer>();
        HashSet hs = new HashSet();
        hs.addAll(networkTypeData);
        possibleValues.addAll(hs);
        for (int i = 0; i < possibleValues.size(); i++) {
            int count = 0;
            for (int j = 0; j < networkTypeData.size(); j++) {
                if (networkTypeData.get(j).equals(possibleValues.get(i))) {
                    count++;
                }
            }
            countValues.add(count);
        }

        openChart(possibleValues, countValues);
    }

}
