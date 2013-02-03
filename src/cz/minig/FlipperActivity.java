package cz.minig;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class FlipperActivity extends Activity {

    private int cols = 3;
    private int rows = 4;
    private Button[] buttons;
    private LinearLayout[] rowLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create central layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.FILL);
        setContentView(mainLayout);
        // Generate row layouts
        rowLayouts = new LinearLayout[rows];
        for (int i = 0; i < rowLayouts.length; i++) {
            rowLayouts[i] = new LinearLayout(this);
            rowLayouts[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
            rowLayouts[i].setGravity(Gravity.FILL);
            mainLayout.addView(rowLayouts[i]);
        }
        // Prepare buttons
        buttons = new Button[rows * cols];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(this);
            buttons[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1));
            rowLayouts[i / cols].addView(buttons[i]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_flipper, menu);
        return true;
    }

}
