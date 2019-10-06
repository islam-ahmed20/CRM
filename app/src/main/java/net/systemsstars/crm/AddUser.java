package net.systemsstars.crm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.systemsstars.crm.helper.DBController;
import net.systemsstars.crm.helper.ShowToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddUser extends AppCompatActivity {

    View PB;
    EditText paymentsDate;
    Spinner projectAssignmentSP;
    ArrayAdapter<String> projectAssignmentAdapter;
    ArrayList<String> projectAssignmentId = new ArrayList<String>();
    ArrayList<TextView> projectsTV = new ArrayList<TextView>();
    LinearLayout projectsLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add User"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PB = (View) findViewById(R.id.PB);

        final DBController controller = new DBController(this);
        final EditText userName = (EditText) findViewById(R.id.userName);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final EditText password = (EditText) findViewById(R.id.password);
        projectAssignmentSP = (Spinner) findViewById(R.id.projectAssignmentSP);
        final EditText payments = (EditText) findViewById(R.id.payments);
        paymentsDate = (EditText) findViewById(R.id.paymentsDate);
        ImageView setDate = (ImageView) findViewById(R.id.setDate);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(AddUser.this, myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        projectAssignmentAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount(); // you dont display last item. It is used as hint.
            }

        };

        projectAssignmentAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        projectAssignmentAdapter.add("Choose Project");
        projectAssignmentId.add("0");

        ArrayList<HashMap<String, String>> projectsList =  controller.getAllProjects();
        for(int i=0 ; i<projectsList.size(); i++) {
            projectAssignmentAdapter.add(projectsList.get(i).get("projectName"));
            projectAssignmentId.add(projectsList.get(i).get("projectId"));
        }

        projectAssignmentSP.setAdapter(projectAssignmentAdapter);
        projectAssignmentSP.setSelection(0,false);

        projectsLL = (LinearLayout) findViewById(R.id.projectsLL);

        ImageView assignProject = (ImageView) findViewById(R.id.assignProject);
        assignProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!projectAssignmentId.get(projectAssignmentSP.getSelectedItemPosition()).equals("0")) {

                    final TextView projectTV = new EditText(AddUser.this);
                    LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    LP.setMargins(15,5,15,5);
                    projectTV.setLayoutParams(LP);
                    projectTV.setPadding(20, 10, 20, 10);
                    projectTV.setBackground(getResources().getDrawable(R.drawable.round_btn_large));
                    projectTV.setGravity(Gravity.CENTER);
                    projectTV.setSingleLine();
                    projectTV.setText(projectAssignmentSP.getSelectedItem().toString());
                    projectTV.setTextSize(12);
                    projectTV.setTextColor(Color.parseColor("#FFFFFF"));

                    projectsTV.add(projectTV);
                    projectsLL.addView(projectTV);

                }else {
                    new ShowToast(AddUser.this, "Please Choose Project");
                }
            }

        });

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userName.getEditableText().toString().isEmpty() && !phone.getEditableText().toString().isEmpty() && !payments.getEditableText().toString().isEmpty() && !paymentsDate.getEditableText().toString().isEmpty() && !password.getEditableText().toString().isEmpty()) {

                    PB.setVisibility(View.VISIBLE);

                    HashMap<String, String> Params = new HashMap<String, String>();

                    Params.put("userName", userName.getEditableText().toString());
                    Params.put("phone", phone.getEditableText().toString());
                    Params.put("password", password.getEditableText().toString());
                    Params.put("type", "user");
                    Params.put("payments", payments.getEditableText().toString());
                    Params.put("paymentsDate", paymentsDate.getEditableText().toString());

                    long userId = controller.insertUser(Params);

                    for(int i=0 ; i<projectsTV.size(); i++) {
                        Params = new HashMap<String, String>();
                        Params.put("userId", String.valueOf(userId));
                        Params.put("projectName", projectsTV.get(i).getText().toString());

                        controller.insertProjectAssignment(Params);
                    }

                    new ShowToast(AddUser.this, "Add user successes");

                    userName.setText("");
                    phone.setText("");
                    password.setText("");
                    payments.setText("");
                    paymentsDate.setText("");
                    projectsLL.removeAllViews();

                    projectAssignmentAdapter.clear();
                    projectAssignmentId.clear();
                    projectAssignmentSP.setAdapter(projectAssignmentAdapter);
                    projectAssignmentSP.setSelection(0,false);

                    PB.setVisibility(View.GONE);
                }else {
                    new ShowToast(AddUser.this, getResources().getString(R.string.toast_missed_data));
                }
            }

        });
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int day) {
                    paymentsDate.setText(day+"/"+(month+1)+"/"+year);
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
