package lovehurts.kylersnotepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteTakingActivity extends AppCompatActivity {

    private String action;
    private EditText subject;
    private String noteFilter;
    private String oldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);

        subject = (EditText) findViewById(R.id.editTextSubject);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(notesController.CONTENT_ITEM_TYPE);
        if(uri == null){
            action = intent.ACTION_INSERT;
            setTitle("New Note");
        }else {
            action = intent.ACTION_EDIT;
            noteFilter = dbOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri, dbOpenHelper.ALL_COLUMNS, noteFilter, null, null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(dbOpenHelper.NOTE_TEXT));
            subject.setText(oldText);
            subject.requestFocus();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case android.R.id.home:
                finishEditing();
                break;
        }
        return true;
    }

    private void finishEditing(){
        String texts = subject.getText().toString().trim();

        switch (action){
            case Intent.ACTION_INSERT:
                if(texts.length() == 0){
                    setResult(RESULT_CANCELED);
                } else{
                    insertNote(texts);
                }
                break;
            case Intent.ACTION_EDIT:
                if(texts.length() == 0){
                    //delete note
                    deleteNote();
                }else if(oldText.equals(texts)){
                    //do nothing
                    setResult(RESULT_CANCELED);
                }else {
                    //update note
                    updateNote(texts);
                }
        }
        finish();

    }

    private void deleteNote() {
        getContentResolver().delete(notesController.CONTENT_URI, noteFilter, null);
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void updateNote(String txt) {
        ContentValues contentVal = new ContentValues();
        contentVal.put(dbOpenHelper.NOTE_TEXT, txt);
        getContentResolver().update(notesController.CONTENT_URI, contentVal, noteFilter, null);
        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void insertNote(String txt) {
        ContentValues contentVal = new ContentValues();
        contentVal.put(dbOpenHelper.NOTE_TEXT, txt);
        getContentResolver().insert(notesController.CONTENT_URI, contentVal);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishEditing();
    }

    public void btnEmailMeghan(View view) {
        EditText edittxt = (EditText) findViewById(R.id.editTextSubject);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sent From Kyler's Notes");
        intent.putExtra(Intent.EXTRA_TEXT, edittxt.getText().toString());

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(NoteTakingActivity.this,
                    "email could not send",
                    Toast.LENGTH_SHORT).show();
        }
    }

//    public void btnTextMeghan(View view) {
//        EditText edittxt = (EditText) findViewById(R.id.editTextSubject);
//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.putExtra("sms_body", edittxt.getText().toString());
//        sendIntent.setType("vnd.android-dir/mms-sms");
//        if(sendIntent.resolveActivity(getPackageManager()) != null){
//            startActivity(sendIntent);
//        }else{
//            Toast.makeText(NoteTakingActivity.this,
//                    "Message could not send",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
}
