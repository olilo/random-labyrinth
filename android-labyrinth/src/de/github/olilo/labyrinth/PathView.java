package de.github.olilo.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import utilities.PathGenerator;
import utilities.VirtualPoint;

import java.util.ArrayList;

public class PathView extends View {

    private AndroidPath path;
    private VirtualPoint current;
    private final PathGenerator pathGenerator = new PathGenerator();
    private DisplayMetrics metrics;

    public PathView(Context context) {
        super(context);
        initialize(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
        } else {
            metrics.widthPixels = 1280;
            metrics.heightPixels = 768;
        }
    }

    public AndroidPath getPath() {
        return path;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end

        ss.path = this.path;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        this.path = ss.path;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int offsetX = metrics.widthPixels / 2 - 64;
        int offsetY = metrics.heightPixels / 2 - 128;

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
                            (int) (128 * (x - current.getX()) + offsetX), (int) (128 * (y - current.getY()) + offsetY),
                            (int) (128 * (x + 1 - current.getX()) + offsetX), (int) (128 * (y + 1 - current.getY())) + offsetY);
                    drawable.draw(canvas);
                }
            }
        }
    }

    static class Row implements Parcelable {

        private boolean[] data;

        public static final Parcelable.Creator<Row> CREATOR
                = new Parcelable.Creator<Row>() {
            public Row createFromParcel(Parcel in) {
                return new Row(in);
            }

            public Row[] newArray(int size) {
                return new Row[size];
            }
        };

        private Row(boolean[] data) {
            this.data = data;
        }

        private Row(Parcel in) {
            data = in.createBooleanArray();
            in.readBooleanArray(data);
        }

        public boolean[] getData() {
            return data;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBooleanArray(data);
        }
    }

    static class SavedState extends BaseSavedState {
        AndroidPath path;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);

            path = new AndroidPath();
            final ArrayList<Row> pathData = in.readBundle().getParcelableArrayList("path");
            final boolean[][] content = new boolean[pathData.size()][];
            for (int i = 0; i < pathData.size(); i++) {
                final boolean[] rowData = pathData.get(i).getData();
                System.arraycopy(rowData, 0, content[i], 0, rowData.length);
            }
            path.setContent(content);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            final Bundle bundle = new Bundle();
            boolean[][] content = this.path.getContent();
            final ArrayList<Row> pathData = new ArrayList<Row>();
            for (boolean[] row : content) {
                pathData.add(new Row(row));
            }
            bundle.putParcelableArrayList("path", pathData);
            out.writeBundle(bundle);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
