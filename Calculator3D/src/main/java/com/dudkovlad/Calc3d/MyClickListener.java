package com.dudkovlad.Calc3d;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by vlad on 22.11.2014.
 */
public class MyClickListener implements View.OnClickListener, View.OnLongClickListener {

    Context context;
    boolean save_if_delete;
    String last_ok;


    public MyClickListener(Context context)
    {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        String eq_now = ((MainActivity)context).mainFragment.equation_view.getText().toString();
        switch (v.getId())
        {
            case R.id.delete_button:
            case R.id.resultview:
                if (save_if_delete&&!eq_now.isEmpty()){

                    Data.history_items.add(new HistoryItem(eq_now,
                            ((MainActivity)context).mainFragment.result_view.getText().toString(),
                            10,
                            false));
                    save_if_delete = false;
                }

                if (eq_now.isEmpty()) break;
                else if (eq_now.length()==1)
                    ((MainActivity)context).mainFragment.equation_view.setText("");

                else ((MainActivity)context).mainFragment.equation_view.
                            setText(eq_now.substring(0, eq_now.length() - 1));

                ((MainActivity)context).threadFC.Result(
                        ((MainActivity)context).mainFragment.equation_view.getText().toString());
                break;

            case R.id.settings_button: ((MainActivity)context).go_to_graphics_fragment();break;
            case R.id.button_in_button_for_adapter:
                save_if_delete = true;
                ((MainActivity)context).mainFragment.equation_view.
                    setText(
                        ((MainActivity)context).mainFragment.equation_view.getText().toString()+
                                ((Button)v).getText().toString());

                if (((MainActivity)context).mainFragment.equation_view.getText().toString().equals("Die"))
                {
                    ((MainActivity)context).debugview.setBackgroundColor(0xff0000aa);
                    ((MainActivity)context).debugview.setText(
                            "\n\n\nAn error has occurred. To Continue:\n" +
                            "Press Enter to return to Android, or\n" +
                            "Press CTRL+ALT+DEL to restart your computer.\n" +
                            "If you do this, you will lose any unsaved information in all open applications.\n" +
                            "Error: 0E : 016F : BFF9B3D4\n" +
                            "               Press any key to continue\n");
                }
                else
                    ((MainActivity)context).threadFC.Result(
                            ((MainActivity)context).mainFragment.equation_view.getText().toString());
                break;
            case R.id.history_item:
                if (!eq_now.isEmpty()&&save_if_delete)
                {
                    Data.history_items.add(new HistoryItem(eq_now,
                            ((MainActivity)context).mainFragment.result_view.getText().toString(),
                            10,
                            false));
                    save_if_delete = false;
                }
                ((MainActivity)context).mainFragment.equation_view.setText(((TextView)v.findViewById(R.id.history_src)).getText());

                ((MainActivity)context).threadFC.Result(
                        ((MainActivity) context).mainFragment.equation_view.getText().toString());
                break;



        }
    }

    @Override
    public boolean onLongClick(View v) {
        String eq_now = ((MainActivity)context).mainFragment.equation_view.getText().toString();
        switch (v.getId())
        {
            case R.id.delete_button:
            case R.id.resultview:
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                if (save_if_delete&&!eq_now.isEmpty()){

                    Data.history_items.add(new HistoryItem(eq_now,
                            ((MainActivity)context).mainFragment.result_view.getText().toString(),
                            10,
                            false));
                    save_if_delete = false;
                }


                ((MainActivity)context).mainFragment.equation_view.setText("");

                ((MainActivity)context).threadFC.Result(
                        ((MainActivity)context).mainFragment.equation_view.getText().toString());
                return true;
            case R.id.history_item:
                if (!eq_now.isEmpty()&&save_if_delete)
                {
                    Data.history_items.add(new HistoryItem(eq_now,
                            ((MainActivity)context).mainFragment.result_view.getText().toString(),
                            10,
                            false));
                    save_if_delete = false;
                }
                ((MainActivity)context).mainFragment.equation_view.setText(((TextView)v.findViewById(R.id.history_src)).getText());

                ((MainActivity)context).threadFC.Result(
                        ((MainActivity) context).mainFragment.equation_view.getText().toString());
                break;
        }
        return true;
    }
}
