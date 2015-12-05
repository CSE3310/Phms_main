package phms.main.Activities;

import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import phms.main.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener  {

    /*
        - search term
        - category
        - type {local, internet} // we don't need to get to internet if it's too much
     */
    EditText etSearch;
    TextView tList;
    Button searchButton;
    String category;
    Spinner dropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etSearch = (EditText) findViewById(R.id.etSearch);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        tList = (TextView) findViewById(R.id.tList);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"All", "Diet", "Medicine", "Notes", "Reminders", "People"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public void onClick(View v) {
        category = dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString();
        tList.setText("");
        switch (v.getId()) {
            case R.id.searchButton:
                if(category.equals("All") || category.equals("Diet")){
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("diet");
                    query.whereEqualTo("author", ParseUser.getCurrentUser());
                    query.orderByDescending("createdAt");
                    query.whereContains("foodIntake", etSearch.getText().toString());
                }

                if(category.equals("All") || category.equals("Medicine")){
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("medicine");
                    query.whereEqualTo("author", ParseUser.getCurrentUser());
                    query.orderByDescending("createdAt");
                    query.whereContains("medicineName", etSearch.getText().toString());
                }

                if(category.equals("All") || category.equals("Notes")){
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("note");
                    query.whereEqualTo("author", ParseUser.getCurrentUser());
                    query.orderByDescending("createdAt");
                    query.whereContains("title", etSearch.getText().toString());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> allNotes, ParseException e) {
                            if (allNotes.size() > 0) {
                                tList.append("Notes:\n");
                                for (ParseObject reminder : allNotes) {
                                    tList.append("  " + reminder.get("title").toString() + ": " + reminder.get("note") +"\n");
                                }
                            }
                        }
                    });
                }

                if(category.equals("All") || category.equals("Reminders")){
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("reminder");
                    query.whereEqualTo("author", ParseUser.getCurrentUser());
                    query.orderByDescending("createdAt");
                    query.whereContains("eventsTitle", etSearch.getText().toString());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> allReminders, ParseException e) {
                            if (allReminders.size() > 0) {
                                tList.append("Reminders:\n");
                                for (ParseObject reminder : allReminders) {
                                    tList.append("  " + reminder.get("eventsTitle").toString() + " " + reminder.get("year") + " " + reminder.get("month") + " " + reminder.get("day") + " " + reminder.get("hour") + " " + reminder.get("minute") + " " + "\n");
                                }
                            }
                        }
                    });
                }

                if(category.equals("All") || category.equals("People")) {
                    ParseUser user = ParseUser.getCurrentUser();
                    String pName = user.get("age").toString();
                    String pNumber = user.get("physicianNumber").toString();
                    String pEmail = user.get("physicianEmail").toString();
                    String eName = user.get("age").toString();
                    String eNumber = user.get("physicianNumber").toString();
                    String eEmail = user.get("physicianEmail").toString();



                }

                    break;
        }
    }
}
