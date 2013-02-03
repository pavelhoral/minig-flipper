package cz.minig;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class FlipperActivity extends Activity {

    private static final String ON_TEXT = "O";
    private static final String OFF_TEXT = "X";

    private int cols = 3;
    private int rows = 4;
    private Button[] buttons;
    private Flipper[] flippers;
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
            buttons[i].setText(ON_TEXT);
            buttons[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1));
            rowLayouts[i / cols].addView(buttons[i]);
        }
        // Prepare flippers
        flippers = new Flipper[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            flippers[i] = generateFlipper();
            buttons[i].setOnClickListener(flippers[i]);
        }
        randomizeFlippers();
    }

    private Flipper generateFlipper() {
        // How many tiles will be flipped
        int count = 1;
        while (Math.random() < 0.6 && count < buttons.length - 1) {
            count++;
        }
        // Generate permutation field
        int[] perm = new int[buttons.length];
        for (int i = 0; i < perm.length; i++) {
            perm[i] = i;
        }
        // Generate tile indexes
        int[] flips = new int[count];
        for (int i = 0; i < flips.length; i++) {
            int trans = (int) (Math.random() * (buttons.length - i)) + i;
            flips[i] = perm[trans];
            perm[trans] = perm[i];
        }
        // Create flipper
        return new Flipper(flips);
    }

    private void randomizeFlippers() {
        int min = 5;
        while (min > 0 || Math.random() < 0.8) {
            min--;
            flippers[(int) (Math.random() * buttons.length)].onClick(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_flipper, menu);
        return true;
    }


    //~ Flipper Class

    private class Flipper implements OnClickListener {

        private int[] flips;

        public Flipper(int[] flips) {
            this.flips = flips;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < flips.length; i++) {
                buttons[flips[i]].setText(ON_TEXT.equals(buttons[flips[i]].getText()) ? OFF_TEXT : ON_TEXT);
            }
        }

    }

}
