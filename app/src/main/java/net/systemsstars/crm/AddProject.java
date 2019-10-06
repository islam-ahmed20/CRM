package net.systemsstars.crm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import net.systemsstars.crm.helper.DBController;
import net.systemsstars.crm.helper.ShowToast;

import java.util.Calendar;
import java.util.HashMap;

public class AddProject extends AppCompatActivity {

    View PB;
    EditText deliveryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add Project"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PB = (View) findViewById(R.id.PB);

        final DBController controller = new DBController(this);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText description = (EditText) findViewById(R.id.description);
        final EditText cost = (EditText) findViewById(R.id.cost);
        deliveryDate = (EditText) findViewById(R.id.deliveryDate);
        ImageView setDate = (ImageView) findViewById(R.id.setDate);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(AddProject.this, myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name.getEditableText().toString().isEmpty() && !description.getEditableText().toString().isEmpty() && !cost.getEditableText().toString().isEmpty() && !deliveryDate.getEditableText().toString().isEmpty()) {
                    PB.setVisibility(View.VISIBLE);

                    HashMap<String, String> Params = new HashMap<String, String>();

                    Params.put("projectName", name.getEditableText().toString());
                    Params.put("description", description.getEditableText().toString());
                    Params.put("cost", cost.getEditableText().toString());
                    Params.put("deliveryDate", deliveryDate.getText().toString());

                    controller.insertProject(Params);

                    new ShowToast(AddProject.this, "Add Project successes");

                    name.setText("");
                    description.setText("");
                    cost.setText("");
                    deliveryDate.setText("");

                    PB.setVisibility(View.GONE);
                }else {
                    new ShowToast(AddProject.this, getResources().getString(R.string.toast_missed_data));
                }

            }

        });
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int day) {
                    deliveryDate.setText(day+"/"+(month+1)+"/"+year);
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
