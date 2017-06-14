package techkids.com.android9_tidypiano_onclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import techkids.com.android9_tidypiano_onclass.notePlayers.NotePlayer;
import techkids.com.android9_tidypiano_onclass.touches.Touch;
import techkids.com.android9_tidypiano_onclass.touches.TouchAction;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.toString();

    List<ImageView> blackKeys;
    List<Boolean> booleanBlackKeys;
    List<ImageView> whiteKeys;
    List<Boolean> booleanWhiteKeys;

    List<TouchInfo> touchInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        {
            whiteKeys = new ArrayList<>();
            whiteKeys.add((ImageView) findViewById(R.id.c));
            whiteKeys.add((ImageView) findViewById(R.id.d));
            whiteKeys.add((ImageView) findViewById(R.id.e));
            whiteKeys.add((ImageView) findViewById(R.id.f));
            whiteKeys.add((ImageView) findViewById(R.id.g));
            whiteKeys.add((ImageView) findViewById(R.id.a));
            whiteKeys.add((ImageView) findViewById(R.id.b));
            booleanWhiteKeys = new ArrayList<>();
            booleanWhiteKeys.add(false);
            booleanWhiteKeys.add(false);
            booleanWhiteKeys.add(false);
            booleanWhiteKeys.add(false);
            booleanWhiteKeys.add(false);
            booleanWhiteKeys.add(false);
            booleanWhiteKeys.add(false);

            blackKeys = new ArrayList<>();
            blackKeys.add((ImageView) findViewById(R.id.cs));
            blackKeys.add((ImageView) findViewById(R.id.ds));
            blackKeys.add((ImageView) findViewById(R.id.fs));
            blackKeys.add((ImageView) findViewById(R.id.gs));
            blackKeys.add((ImageView) findViewById(R.id.as));
            booleanBlackKeys = new ArrayList<>();
            booleanBlackKeys.add(false);
            booleanBlackKeys.add(false);
            booleanBlackKeys.add(false);
            booleanBlackKeys.add(false);
            booleanBlackKeys.add(false);
        }

        touchInfoList = new ArrayList<>();

        NotePlayer.loadSounds(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        List<Touch> touches = Touch.processEvent(event);
        Log.d(TAG, "onTouchEvent: " + touches);

        if (touches.size() == 0) {
            return false;
        }
        Touch firstTouch = touches.get(0);
        if (firstTouch.getTouchAction() == TouchAction.DOWN) {
            ImageView pressedKey = findKeyByTouch(firstTouch);
            if (!isPressedKey(pressedKey)) {
                // TODO: play note
                String note = pressedKey.getTag().toString();
                NotePlayer.playSound(note);

                touchInfoList.add(new TouchInfo(pressedKey, firstTouch));
            }
        } else if (firstTouch.getTouchAction() == TouchAction.UP) {
            ImageView pressedKey = findKeyByTouch(firstTouch);
            Iterator<TouchInfo> touchInfoIterator = touchInfoList.iterator();
            while (touchInfoIterator.hasNext()) {
                TouchInfo touchInfo = touchInfoIterator.next();
                if (touchInfo.pressedKey == pressedKey) {
                    touchInfoIterator.remove();
                }
            }
        } else if (firstTouch.getTouchAction() == TouchAction.MOVE) {
            for (Touch touch : touches) {
                ImageView pressedKey = findKeyByTouch(touch);
                Iterator<TouchInfo> touchInfoIterator = touchInfoList.iterator();
                while (touchInfoIterator.hasNext()) {
                    TouchInfo touchInfo = touchInfoIterator.next();
                    if (touchInfo.touch.equals(touch) && touchInfo.pressedKey != pressedKey) {
                        touchInfoIterator.remove();
                        break;
                    }
                }
                if (!isPressedKey(pressedKey)) {
                    touchInfoList.add(new TouchInfo(pressedKey, touch));
                    // TODO: play note
                    String note = pressedKey.getTag().toString();
                    NotePlayer.playSound(note);
                }
            }
        }

        updateKeyImage();
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            for (ImageView whiteKey : whiteKeys)
//                whiteKey.setImageResource(R.drawable.default_white_key);
//            for (ImageView blackKey : blackKeys)
//                blackKey.setImageResource(R.drawable.default_black_key);
//            for (Boolean booleanWhiteKey : booleanWhiteKeys)
//                booleanWhiteKey = false;
//            for (Boolean booleanBlackKey : booleanBlackKeys)
//                booleanBlackKey = false;
//            return false;
//        }
//
//        {
//            int blackIsTouching = blackKeys.size();
//            {
//                for (int i = 0; i < blackKeys.size(); i++) {
//                    ImageView blackKey = blackKeys.get(i);
//                    if (isInside(event.getX(), event.getY(), blackKey)) {
////                    if (Touch.checkHit(blackKey)) {
////                        Touch.processEvent(event);
//                        if(!booleanBlackKeys.get(i)) {
//                            blackKey.setImageResource(R.drawable.pressed_black_key);
//                            booleanBlackKeys.set(i, true);
//                            if (blackKey.getTag().equals("cs")) {
//                                Log.d(TAG, "onTouchEvent: press cs");
//                            }
//                            if (blackKey.getTag().equals("ds")) {
//                                Log.d(TAG, "onTouchEvent: press ds");
//                            }
//                            if (blackKey.getTag().equals("fs")) {
//                                Log.d(TAG, "onTouchEvent: press fs");
//                            }
//                            if (blackKey.getTag().equals("gs")) {
//                                Log.d(TAG, "onTouchEvent: press gs");
//                            }
//                            if (blackKey.getTag().equals("as")) {
//                                Log.d(TAG, "onTouchEvent: press as");
//                            }
//                        }
//                    } else {
//                        booleanBlackKeys.set(i, false);
//                        blackIsTouching--;
//                        blackKey.setImageResource(R.drawable.default_black_key);
//                    }
//                }
//            }
//            if (blackIsTouching == 0) {
//                for (int i = 0; i < whiteKeys.size(); i++) {
//                    ImageView whiteKey = whiteKeys.get(i);
//
//                    if (isInside(event.getX(), event.getY(), whiteKey)) {
//                        if (!booleanWhiteKeys.get(i)) {
//                            whiteKey.setImageResource(R.drawable.pressed_white_key);
//                            booleanWhiteKeys.set(i, true);
//                            if (whiteKey.getTag().equals("c")) {
//                                Log.d(TAG, "onTouchEvent: press c");
//                            }
//                            if (whiteKey.getTag().equals("d")) {
//                                Log.d(TAG, "onTouchEvent: press d");
//                            }
//                            if (whiteKey.getTag().equals("e")) {
//                                Log.d(TAG, "onTouchEvent: press e");
//                            }
//                            if (whiteKey.getTag().equals("f")) {
//                                Log.d(TAG, "onTouchEvent: press f");
//                            }
//                            if (whiteKey.getTag().equals("g")) {
//                                Log.d(TAG, "onTouchEvent: press g");
//                            }
//                            if (whiteKey.getTag().equals("a")) {
//                                Log.d(TAG, "onTouchEvent: press a");
//                            }
//                            if (whiteKey.getTag().equals("b")) {
//                                Log.d(TAG, "onTouchEvent: press b");
//                            }
//                        }
//                    } else {
//                        booleanWhiteKeys.set(i, false);
//                        whiteKey.setImageResource(R.drawable.default_white_key);
//                    }
//                }
//            }
//        }
        return super.onTouchEvent(event);
    }

    void updateKeyImage() {
        for (ImageView blackKey : blackKeys) {
            if (isPressedKey(blackKey)) {
                blackKey.setImageResource(R.drawable.pressed_black_key);
            } else {
                blackKey.setImageResource(R.drawable.default_black_key);
            }
        }
        for (ImageView whiteKey : whiteKeys) {
            if (isPressedKey(whiteKey)) {
                whiteKey.setImageResource(R.drawable.pressed_white_key);
            } else {
                whiteKey.setImageResource(R.drawable.default_white_key);
            }
        }
    }

    boolean isPressedKey(ImageView pressedKey) {
        for (TouchInfo touchInfo : touchInfoList) {
            if (pressedKey == touchInfo.pressedKey) {
                return true;
            }
        }
        return false;
    }

    private ImageView findKeyByTouch(Touch touch) {
        for (ImageView blackKey : blackKeys) {
            if (touch.checkHit(blackKey)) {
                return blackKey;
            }
        }
        for (ImageView whiteKey : whiteKeys) {
            if (touch.checkHit(whiteKey)) {
                return whiteKey;
            }
        }
        return null;
    }

//    boolean isInside(float x, float y, View view) {
//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
//
//        int left = location[0];
//        int top = location[1];
//        int right = left + view.getWidth();
//        int bottom = top + view.getHeight();
//
//        return (x < right && x > left && y < bottom && y > top);
//    }

    class TouchInfo {
        public ImageView pressedKey;
        public Touch touch;

        public TouchInfo(ImageView pressedKey, Touch touch) {
            this.pressedKey = pressedKey;
            this.touch = touch;
        }
    }
}
