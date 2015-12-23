package com.example.lsj.workspace;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    private static final int DURATION = 1500;
    private static final float BALL_SIZE = 100f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.root_view);
        final BallView ballView = new BallView(this);
//        ballView.init(50, 0);
        layout.addView(ballView);
        Button button = (Button) findViewById(R.id.button_play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballView.startAnimation();
            }
        });
    }

    private void ddd() {
        //        CircleScrollView scrollView = (CircleScrollView) findViewById(R.id.circlescrollview);
//        scrollView.setItems();
//        MyCircleTextView circleTextView = (MyCircleTextView) findViewById(R.id.circleview);
        MyCircleTextView circleTextView = new MyCircleTextView(this);
        circleTextView.setmLabelRadius(100);
        circleTextView.setmLabelTextColor(R.color.black);
        circleTextView.setmLabelColor(R.color.white);
        circleTextView.setmLabelText("ÄãºÃ");
//        layout.addView(circleTextView);
        PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("scaleX", circleTextView.getmLabelRadius() * 2,
                circleTextView.getmLabelRadius() * 4);
        PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("scaleY", circleTextView.getmLabelRadius() * 2,
                circleTextView.getmLabelRadius() * 4);
        /*PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x", circleTextView.getCenterX(),
                circleTextView.getCenterX() - BALL_SIZE / 2f);
        PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y", circleTextView.getCenterY(),
                circleTextView.getCenterY() - BALL_SIZE / 2f);*/
        ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(circleTextView, pvhW, pvhH
        ).setDuration(DURATION / 2);
        final Animator bounceAnim = new AnimatorSet();
        whxyBouncer.setRepeatCount(1);
        whxyBouncer.setRepeatMode(ValueAnimator.REVERSE);
        ((AnimatorSet) bounceAnim).play(whxyBouncer);

//        ((AnimatorSet) bounceAnim).playTogether(yBouncer, yAlphaBouncer, whxyBouncer,
//                yxBouncer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
