package techkids.com.android9_tidypiano_onclass.touches;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.view.MotionEvent.*;

/**
 * Created by tungthanh.1497 on 06/14/2017.
 */

public class Touch {

    private float x;
    private float y;
    private TouchAction touchAction;
    private int touchId;

    public Touch(float x, float y, TouchAction touchAction, int touchId) {
        this.x = x;
        this.y = y;
        this.touchAction = touchAction;
        this.touchId = touchId;
    }

    public TouchAction getTouchAction() {
        return touchAction;
    }

    public int getTouchId() {
        return touchId;
    }

    public static List<Touch> processEvent(MotionEvent event) {
        List<Touch> touches = new ArrayList<>();

        int maskedAction = event.getActionMasked(); // bit
        if (maskedAction == ACTION_DOWN || maskedAction == ACTION_POINTER_DOWN) {
            Touch touch = getTouch(event, event.getActionIndex(), TouchAction.DOWN);
            touches.add(touch);
        } else if (maskedAction == ACTION_UP || maskedAction == ACTION_POINTER_UP) {
            Touch touch = getTouch(event, event.getActionIndex(), TouchAction.UP);
            touches.add(touch);
        } else if (maskedAction == ACTION_MOVE) {
            for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
                Touch touch = getTouch(event, pointerIndex, TouchAction.MOVE);
                touches.add(touch);
            }
        }

        return touches;
    }

    private static Touch getTouch(MotionEvent event, int pointerIndex, TouchAction touchAction) {
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        int id = event.getPointerId(pointerIndex);

        Touch touch = new Touch(x, y, touchAction, id);
        return touch;
    }

    public boolean checkHit(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();

        return (x < right && x > left && y < bottom && y > top);
    }

    @Override
    public String toString() {
        return "Touch{" +
                "x=" + x +
                ", y=" + y +
                ", touchAction=" + touchAction +
                ", touchId=" + touchId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Touch other = (Touch) obj;
        return touchId == other.touchId;
    }
}
