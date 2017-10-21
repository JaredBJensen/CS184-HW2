package edu.ucsb.cs.cs184.jaredbjensen.jjensendrawingmultitouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    final int FINGER_MAX = 5;

    SurfaceHolder holder;

    int[] X = new int[FINGER_MAX];
    int[] Y = new int[FINGER_MAX];
    int[] lastX = new int[FINGER_MAX];
    int[] lastY = new int[FINGER_MAX];
    Path[] paths = new Path[FINGER_MAX];
    boolean[] active = new boolean[FINGER_MAX];
    int[] colors = new int[FINGER_MAX];

    private Paint paint = new Paint();

    public void setColors(ArrayList<Integer> colors) {
        for (int i=0; i<colors.size(); i++) {
            this.colors[i] = colors.get(i);
        }
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);

        colors[FINGER_MAX-1] = Color.BLACK;
    }

    public boolean onTouchEvent(MotionEvent event) {

        int pointerCount = event.getPointerCount();

        for (int i=0; i<pointerCount; i++) {
            int x = (int) event.getX(i);
            int y = (int) event.getY(i);
            lastX[i] = X[i];
            lastY[i] = Y[i];
            X[i] = x;
            Y[i] = y;
            int id = event.getPointerId(i);
            int action = event.getActionMasked();
            int actionIndex = event.getActionIndex();

            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    paths[i].moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    paths[i].lineTo(x, y);
                    break;
                default:
                    break;
            }

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(colors[i]);

            Canvas canvas = holder.lockCanvas();
            canvas.drawPath(paths[i], paint);
            holder.unlockCanvasAndPost(canvas);
        }

        return true;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void surfaceCreated(SurfaceHolder holder) {

    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean clearCanvas() {
        paint.setStyle(Paint.Style.FILL);
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);

        for (int i=0; i<paths.length; i++) {
            paths[i] = new Path();
        }

        return true;
    }

    public boolean setBrushSize() {
        return false;
    }

    private boolean refreshView() {
        return false;
    }


}
