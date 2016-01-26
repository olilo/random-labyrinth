package de.github.olilo.labyrinth;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import gui.Const;
import utilities.Path;
import utilities.VirtualPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 16.01.2016.
 */
public class AndroidPath implements Path {

    private int width = Const.playingWidth;
    private int height = Const.playingHeight;

    private boolean[][] content;
    private Drawable[][] drawables;
    private List<VirtualPoint> vps;

    public AndroidPath() {
        content = new boolean[width][height];
        vps = new ArrayList<VirtualPoint>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[][] getContent() {
        final boolean[][] result = new boolean[width][height];
        for (int i = 0; i < content.length; i++) {
            System.arraycopy(content[i], 0, result[i], 0, content[i].length);
        }
        return result;
    }

    public void setContent(boolean[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (input[i][j]) {
                    this.add(new VirtualPoint(i, j));
                }
            }
        }
    }

    @Override
    public void add(VirtualPoint lp) {
        if (isInBounds(lp) && !content[(int)lp.getX()][(int)lp.getY()]) {
            content[(int)lp.getX()][(int)lp.getY()] = true;
            vps.add(lp);
        }
    }

    @Override
    public void subtract(VirtualPoint lp) {
        if (this.contains(lp)){
            content[(int)lp.getX()][(int)lp.getY()] = false;
            vps.remove(lp);
        }
    }

    @Override
    public boolean contains(VirtualPoint lp) {
        if (lp == null)
            return false;

        double xDouble = lp.getX();
        double yDouble = lp.getY();
        int x = (int)xDouble;
        int y = (int)yDouble;
        boolean returnValue = isInBounds(lp) && content[x][y];
        if (returnValue && (x != xDouble)) {
            returnValue = (x + 1 < width) && content[x + 1][y];

            if (returnValue && (y != yDouble)) {
                returnValue &= (y+1 < height) && content[x][y+1];
                returnValue &= (y+1 < height) && content[x+1][y+1];
            }
        } else if (y != yDouble) {
            returnValue &= (y+1 < height) && content[x][y+1];
        }

        return returnValue;
    }

    @Override
    public boolean isInBounds(VirtualPoint lp) {
        return (lp.getX() >= 0) && (lp.getX() < width)
                && (lp.getY() >= 0) && (lp.getY() < height);
    }

    @Override
    public VirtualPoint get(int entry) {
        return vps.get(entry);
    }

    @Override
    public Path merge(Path path) {
        AndroidPath ret;

        ret = new AndroidPath();
        //ret.content = this.content.clone();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (this.content[x][y] || path.contains(new VirtualPoint(x, y))) {
                    ret.add(new VirtualPoint(x, y));
                }
            }
        }

        return ret;
    }

    @Override
    public int length() {
        return vps.size();
    }

    @Override
    public void wipe() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                content[x][y] = false;
            }
        }
        drawables = null;
        vps.clear();
    }

    public Drawable[][] toDrawables(Resources resources) {
        if (this.drawables != null) {
            return this.drawables;
        }

        final Drawable[][] ret = new Drawable[width][height];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (content[i][j]) {
                    boolean LEFT, TOP, RIGHT, BOTTOM;

                    // determine whether adjacent squares are occupied
                    LEFT = (i > 0) && content[i - 1][j];
                    TOP = (j > 0) && content[i][j - 1];
                    RIGHT = (i < width - 1) && content[i + 1][j];
                    BOTTOM = (j < height - 1) && content[i][j + 1];

                    if (LEFT) {
                        if (TOP) {
                            if (RIGHT) {
                                if (BOTTOM) {
                                    ret[i][j] = null; // resources.getDrawable(R.drawable.);
                                }
                                else {
                                    ret[i][j] = resources.getDrawable(R.drawable.left_top_right);
                                }
                            }
                            else if (BOTTOM) {
                                ret[i][j] = resources.getDrawable(R.drawable.bottom_left_top);
                            }
                            else {
                                ret[i][j] = resources.getDrawable(R.drawable.top_left);
                            }
                        }
                        else {
                            if (RIGHT) {
                                if (BOTTOM) {
                                    ret[i][j] = resources.getDrawable(R.drawable.left_bottom_right);
                                }
                                else {
                                    ret[i][j] = resources.getDrawable(R.drawable.left_right);
                                }
                            }
                            else if (BOTTOM) {
                                ret[i][j] = resources.getDrawable(R.drawable.bottom_left);
                            }
                            else {
                                ret[i][j] = resources.getDrawable(R.drawable.left);
                            }
                        }
                    }
                    else {
                        if (TOP) {
                            if (RIGHT) {
                                if (BOTTOM) {
                                    ret[i][j] = resources.getDrawable(R.drawable.top_right_bottom);
                                }
                                else {
                                    ret[i][j] = resources.getDrawable(R.drawable.top_right);
                                }
                            }
                            else if (BOTTOM) {
                                ret[i][j] = resources.getDrawable(R.drawable.top_bottom);
                            }
                            else {
                                ret[i][j] = resources.getDrawable(R.drawable.top);
                            }
                        }
                        else {
                            if (RIGHT) {
                                if (BOTTOM) {
                                    ret[i][j] = resources.getDrawable(R.drawable.bottom_right);
                                }
                                else {
                                    ret[i][j] = resources.getDrawable(R.drawable.right);
                                }
                            }
                            else {
                                ret[i][j] = resources.getDrawable(R.drawable.bottom);
                            }
                        }
                    }

                }
                else
                    ret[i][j] = resources.getDrawable(R.drawable.brick);
            }
        }

        this.drawables = ret;

        return ret;
    }
}
