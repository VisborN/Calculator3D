package com.dudkovlad.CalcTDP;

import android.content.Context;
import android.graphics.Typeface;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vlad on 22.11.2014.
 */
public class MyClickListener implements View.OnClickListener, View.OnLongClickListener {

    Context context;
    boolean save_if_delete = true;
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
                if (save_if_delete)
                    Data.HistoryNext(new HistoryItem("", "",
                            Data.history_items.get(0).getHistory_num_sys(),
                            Data.history_items.get(0).getHistory_radians()));
                save_if_delete = false;

                if (eq_now.isEmpty()) break;
                else if (eq_now.length()==1)
                    ((MainActivity)context).mainFragment.equation_view.setText("");

                else ((MainActivity)context).mainFragment.equation_view.
                            setText(eq_now.substring(0, eq_now.length() - 1));

                ((MainActivity)context).threadFC.Result(
                        ((MainActivity)context).mainFragment.equation_view.getText().toString(),
                        Data.history_items.get(0).getHistory_num_sys(),
                        Data.history_items.get(0).getHistory_radians());
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
                    ((MainActivity)context).debugview.setBackgroundColor(0xff00aa00);
                    ((MainActivity)context).debugview.setTextSize(5);
                    ((MainActivity)context).debugview.setOnClickListener(this);

                    ((MainActivity)context).debugview.setTypeface(Typeface.MONOSPACE);
                    ((MainActivity)context).debugview.setText(
                                    "\n"+
                                    "_________         __                                                      \n" +
                                    "\\_   ___ \\_____ _/  |_  ______   ______ ___________   ____   ____   ____  \n" +
                                    "/    \\  \\/\\__  \\\\   __\\/  ___/  /  ___// ___\\_  __ \\_/ __ \\_/ __ \\ /    \\ \n" +
                                    "\\     \\____/ __ \\|  |  \\___ \\   \\___ \\\\  \\___|  | \\/\\  ___/\\  ___/|   |  \\\n" +
                                    " \\______  (____  /__| /____  > /____  >\\___  >__|    \\___  >\\___  >___|  /\n" +
                                    "        \\/     \\/          \\/       \\/     \\/            \\/     \\/     \\/ \n" +
                                    "        _____      .___             __  .__     \n" +
                                    "  _____/ ____\\   __| _/____ _____ _/  |_|  |__  \n" +
                                    " /  _ \\   __\\   / __ |/ __ \\\\__  \\\\   __\\  |  \\ \n" +
                                    "(  <_> )  |    / /_/ \\  ___/ / __ \\|  | |   Y  \\\n" +
                                    " \\____/|__|    \\____ |\\___  >____  /__| |___|  /\n" +
                                    "                    \\/    \\/     \\/          \\/ \n"+
                                    //"Cat's screen of death\n"+
                                    "               ___________________                     \n"+
                                    "              |                   |_____    __          \n"+
                                    "              | wow, you just     |     |__|  |_________\n"+
                                    "              | broke your phone  |     |::|  |        /\n"+
                                    "              |                   |     |::|  |       / \n"+
                                    "              |___________________|     |::|  |      <  \n"+
                                    " /\\**/\\       |                   \\.____|::|__|       \\\n"+
                                    "( o_o  )_     |                         \\::/  \\._______\\\n"+
                                    " (u--u   \\_)  |                                         \n"+
                                    "  (||___   )==\\                                         \n"+
                                    ",dP\"/b/=( /P\"/b\\                                        \n"+
                                    "|8 || 8\\=== || 8                                        \n"+
                                    "`b,  ,P  `b,  ,P                                        \n"+
                                    "  \"\"\"`     \"\"\"`                                         \n"+
                                    "think that blue is deprecated");
                }
                else
                    ((MainActivity)context).threadFC.Result(
                            ((MainActivity)context).mainFragment.equation_view.getText().toString(),
                            Data.history_items.get(0).getHistory_num_sys(),
                            Data.history_items.get(0).getHistory_radians());
                break;
            case R.id.history_item:

                if (((MainActivity)context).mainFragment != null&&((MainActivity)context).mainFragment.equation_view != null)
                    ((MainActivity)context).mainFragment.equation_view.setText(((TextView) v.findViewById(R.id.history_src)).getText());
                int pos = Integer.valueOf(((TextView)v.findViewById(R.id.history_src)).getHint().toString());
                if ( pos > 0 ) {
                    Data.HistoryNext(Data.history_items.get(pos));

                }

                ((MainActivity)context).threadFC.Result(
                        ((MainActivity) context).mainFragment.equation_view.getText().toString(),
                        Data.history_items.get(0).getHistory_num_sys(),
                        Data.history_items.get(0).getHistory_radians());
                break;



        }
    }

    @Override
    public boolean onLongClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        switch (v.getId())
        {
            case R.id.delete_button:
            case R.id.resultview:
                save_if_delete = false;
                Data.HistoryNext(new HistoryItem("", "",
                        Data.history_items.get(0).getHistory_num_sys(),
                        Data.history_items.get(0).getHistory_radians()));
                ((MainActivity)context).mainFragment.equation_view.setText("");

                ((MainActivity)context).threadFC.Result(
                        ((MainActivity)context).mainFragment.equation_view.getText().toString(),
                        Data.history_items.get(0).getHistory_num_sys(),
                        Data.history_items.get(0).getHistory_radians());
                break;
            case R.id.history_item:
                int pos = Integer.valueOf(((TextView)v.findViewById(R.id.history_src)).getHint().toString());
                if ( pos > 0 )
                    Data.HistoryRemove(pos);

                ArrayList<HistoryItem> temp = (ArrayList<HistoryItem>)Data.history_items.clone();
                ((MainActivity)context).histAdapter.clear();

                ((MainActivity)context).histAdapter.addAll(temp);
                break;
        }
        return true;
    }
}
