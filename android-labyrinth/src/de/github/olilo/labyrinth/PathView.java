package de.github.olilo.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import utilities.Path;
import utilities.VirtualPoint;

/**
 * Created by Oliver on 16.01.2016.
 */
public class PathView extends View {

    private AndroidPath path;

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

    public void setPath(AndroidPath path) {
        this.path = path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawARGB(255, 0, 255, 0);
        if (getPath() != null) {
            //canvas.drawARGB(255, 128, 128, 0);
            final Drawable[][] drawables = getPath().toDrawables(getContext().getResources());
            //canvas.drawARGB(255, 0, 0, 255);
            for (int x = 0; x < path.getWidth(); x++) {
                for (int y = 0; y < path.getHeight(); y++) {
                    Drawable drawable = drawables[x][y];
                    drawable.setBounds(64 * x, 64 * y, 64 * (x + 1), 64 * (y + 1));
                    drawable.draw(canvas);
                }
            }
        }
    }
}
