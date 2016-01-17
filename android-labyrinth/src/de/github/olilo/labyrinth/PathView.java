package de.github.olilo.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import utilities.Path;
import utilities.PathGenerator;
import utilities.VirtualPoint;

/**
 * Created by Oliver on 16.01.2016.
 */
public class PathView extends View {

    private AndroidPath path;
    private VirtualPoint current;
    private final PathGenerator pathGenerator = new PathGenerator();

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AndroidPath getPath() {
        return path;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (path == null) {
            path = pathGenerator.buildNewPath(AndroidPath.class);
            current = pathGenerator.getStart();
        }

        //canvas.drawARGB(255, 0, 255, 0);
        if (path != null) {
            canvas.drawARGB(255, 128, 128, 0);
            final Drawable[][] drawables = getPath().toDrawables(getContext().getResources());
            canvas.drawARGB(255, 0, 0, 255);
            for (int x = 0; x < path.getWidth(); x++) {
                for (int y = 0; y < path.getHeight(); y++) {
                    Drawable drawable = drawables[x][y];
                    drawable.setBounds(
                            (int) (128 * (x - current.getX())), (int) (128 * (y - current.getY())),
                            (int) (128 * (x + 1 - current.getX())), (int) (128 * (y + 1 - current.getY())));
                    drawable.draw(canvas);
                }
            }
        }
    }
}
