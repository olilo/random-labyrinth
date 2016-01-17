package de.github.olilo.labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import utilities.PathGenerator;
import utilities.VirtualPoint;

public class MyActivity extends Activity {

    private VirtualPoint player;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = new VirtualPoint(0, 0, 128, 128);
        /*
        final PathGenerator pathGenerator = new PathGenerator();
        player.setX(pathGenerator.getStart().getX());
        player.setY(pathGenerator.getStart().getY());
        */

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
