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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    final int FINGER_MAX = 5;
    final float DEFAULT_BRUSH_SIZE = 10.0f;

    SurfaceHolder holder;
    private Paint paint = new Paint();

    ArrayList<ArrayList<Stroke>> strokes = new ArrayList<>();
    int[] strokeIdx = new int[FINGER_MAX];
    int[] colors = new int[FINGER_MAX];
    HashMap<Integer, Integer> idToIdx = new HashMap<>();


    public void setColors(ArrayList<Integer> colors) {
        for (int i=0; i<colors.size(); i++) {
            this.colors[i] = colors.get(i);
        }
    }

    public void setColor(int index, int color) {
        colors[index] = color;
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);

        colors[FINGER_MAX-1] = Color.BLACK;

        setBrushSize(DEFAULT_BRUSH_SIZE);

        for (int i=0; i<5; i++) {
            strokes.add(new ArrayList<Stroke>());
        }

        setBackgroundColor(Color.WHITE);
    }

    public boolean onTouchEvent(MotionEvent event) {

        int pointerCount = event.getPointerCount();

        for (int i=0; i<pointerCount; i++) {
            int x = (int) event.getX(i);
            int y = (int) event.getY(i);
            int id = event.getPointerId(i);
            int action = event.getActionMasked();
            int actionIndex = event.getActionIndex();

            switch(action) {
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_DOWN:
                    idToIdx.put(id, i);
                    ArrayList<Stroke> strokesThisPointer = strokes.get(idToIdx.get(id));
                    Stroke newStroke = new Stroke(new Path(), colors[idToIdx.get(id)], DEFAULT_BRUSH_SIZE);
                    strokesThisPointer.add(newStroke);
                    strokesThisPointer.get(strokeIdx[idToIdx.get(id)]).getPath().moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    strokes.get(idToIdx.get(id)).get(strokeIdx[idToIdx.get(id)]).getPath().lineTo(x, y);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                    if (actionIndex == i) {
                        strokeIdx[idToIdx.get(id)]++;
                        idToIdx.remove(id);
                    }
                    break;
                default:
                    break;
            }

            refreshView();
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (ArrayList<Stroke> strokeList : strokes) {
            for (Stroke stroke : strokeList){
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(stroke.getSize());
                if (strokes.indexOf(strokeList) == 4) paint.setColor(Color.BLACK);
                else paint.setColor(stroke.getColor());
                canvas.drawPath(stroke.getPath(), paint);
            }
        }
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
        holder.unlockCanvasAndPost(canvas);

        strokes.clear();
        for (int i=0; i<5; i++) {
            strokes.add(new ArrayList<Stroke>());
        }
        for (int i=0; i<5; i++) {
            strokeIdx[i] = 0;
        }

        refreshView();
        return true;
    }

    public void setBrushSize(float size) {
        paint.setStrokeWidth(size);
    }

    private void refreshView() {
        this.invalidate();
    }


}
