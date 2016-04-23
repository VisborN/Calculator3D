package com.dudkovlad.CalcTDP;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormatSymbols;

/**
 * Created by vlad on 21.03.2014.
 */
public class MyEditText {/*{
 extends EditText{
    private static final int BLINK = 500;
    private final long mShowCursor = SystemClock.uptimeMillis();
    Paint mHighlightPaint = new Paint();
    Handler mHandler = new Handler();
    Runnable mRefresher = new Runnable() {
        @Override
        public void run() {
            MyEditText.this.invalidate();
        }
    };

    private String mInput = "";
    private int mSelectionHandle = 0;

    public MyEditText(Context context) {
        super(context);
        setUp();
    }


    private void setUp() {
        // Hide the keyboard
        setCustomSelectionActionModeCallback(new NoTextSelectionMode());
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);

        // Display ^ , and other visual cues
        addTextChangedListener(new TextWatcher() {
            boolean updating = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing here
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing here
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (updating) {
                    return;
                }

                mInput = s.toString();
                updating = true;

                // Get the selection handle, since we're setting text and that'll overwrite it
                mSelectionHandle = getSelectionStart();

                // Adjust the handle by removing any comas or spacing to the left
                String cs = s.subSequence(0, mSelectionHandle).toString();
                mSelectionHandle -= countOccurrences(cs, mDecSeparator.charAt(0));
                if (!mBinSeparator.equals(mDecSeparator)) {
                    mSelectionHandle -= countOccurrences(cs, mBinSeparator.charAt(0));
                }
                if (!mHexSeparator.equals(mBinSeparator) && !mHexSeparator.equals(mDecSeparator)) {
                    mSelectionHandle -= countOccurrences(cs, mHexSeparator.charAt(0));
                }

                setText(formatText(mInput));
                setSelection(Math.min(mSelectionHandle, getText().length()));
                updating = false;
            }
        });




    }

    @Override
    public String toString() {
        return mInput;
    }



    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TextViews don't draw the cursor if textLength is 0. Because we're an
        // array of TextViews, we'd prefer that it did.
        if (getText().length() == 0 && isEnabled() && (isFocused() || isPressed())) {
            if ((SystemClock.uptimeMillis() - mShowCursor) % (2 * BLINK) < BLINK) {
                mHighlightPaint.setColor(getCurrentTextColor());
                mHighlightPaint.setStyle(Paint.Style.STROKE);
                canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mHighlightPaint);
                mHandler.postAtTime(mRefresher, SystemClock.uptimeMillis() + BLINK);
            }
        }
    }

    private Spanned formatText(String input) {
        BaseModule bm = mDisplay.mLogic.getBaseModule();
        if (CalculatorSettings.digitGrouping(getContext())) {
            // Add grouping, and then split on the selection handle
            // which is saved as a unique char
            String grouped = bm.groupSentence(input, mSelectionHandle);
            if (grouped.contains(String.valueOf(BaseModule.SELECTION_HANDLE))) {
                String[] temp = grouped.split(String.valueOf(BaseModule.SELECTION_HANDLE));
                mSelectionHandle = temp[0].length();

                input = "";
                for (String s : temp) {
                    input += s;
                }
            } else {
                input = grouped;
                mSelectionHandle = input.length();
            }
        }

        return Html.fromHtml(mEquationFormatter.insertSupscripts(input));
    }



    class NoTextSelectionMode implements ActionMode.Callback {
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Prevents the selection action mode on double tap.
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // Do nothing here
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    }//*/
}
