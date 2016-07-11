package lovehurts.kylersnotepad;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Kyler on 7/11/2016.
 * replaces SimpleCursorAdapter
 */
public class cursorAdapter extends CursorAdapter{
    public cursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.note_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String noteText = cursor.getString(cursor.getColumnIndex(dbOpenHelper.NOTE_TEXT));
        int pos = noteText.indexOf(10);
        if(pos != -1){
            noteText = noteText.substring(0, pos) + "...";
        }
        TextView tv = (TextView)view.findViewById(R.id.tvNote);
        tv.setText(noteText);
    }
}
