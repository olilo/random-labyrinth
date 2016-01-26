package de.github.olilo.labyrinth;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import utilities.PathGenerator;
import utilities.VirtualPoint;

public class MyActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        final PathGenerator pathGenerator = new PathGenerator();
        player.setX(pathGenerator.getStart().getX());
        player.setY(pathGenerator.getStart().getY());
        */

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = getWindowManager();
        wm.getDefaultDisplay().getMetrics(metrics);

        setContentView(R.layout.main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public static void main(String[] args) {
        PathGenerator pathGenerator = new PathGenerator();
        System.out.println(pathGenerator.buildNewPath(AndroidPath.class).length());
    }
}
