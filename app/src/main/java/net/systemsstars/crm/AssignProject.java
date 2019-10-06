package net.systemsstars.crm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;

import net.systemsstars.crm.helper.DBController;
import net.systemsstars.crm.helper.ExpandableHeightGridView;
import net.systemsstars.crm.helper.GridViewAdapter;
import net.systemsstars.crm.helper.ShowToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignProject extends AppCompatActivity {

    Spinner userSP;
    Spinner projectAssignmentSP;

    ArrayAdapter<String> userAdapter;
    ArrayAdapter<String> projectAssignmentAdapter;

    ArrayList<String> userId = new ArrayList<String>();
    ArrayList<String> projectAssignmentId = new ArrayList<String>();

    ArrayList<String> projectsTV = new ArrayList<String>();
    ExpandableHeightGridView projectsLL;
    GridViewAdapter gvAdapter;

    ArrayList<HashMap<String, String>> assginProjectList;
    ArrayList<String> assginedProject = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_project);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Assign Project"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DBController controller = new DBController(this);

        userSP = (Spinner) findViewById(R.id.userSP);
        projectAssignmentSP = (Spinner) findViewById(R.id.projectAssignmentSP);

        projectsLL = (ExpandableHeightGridView) findViewById(R.id.projectLL);
        projectsLL.setExpanded(true);

        gvAdapter = new GridViewAdapter(this, projectsTV);
        projectsLL.setAdapter(gvAdapter);

        userAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item) {

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

        userAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        userAdapter.add("Choose User");
        userId.add("0");

        ArrayList<HashMap<String, String>> usersList =  controller.getAllUsers();
        for(int i=0 ; i<usersList.size(); i++) {
            userAdapter.add(usersList.get(i).get("userName"));
            userId.add(usersList.get(i).get("userId"));
        }

        userSP.setAdapter(userAdapter);
        userSP.setSelection(0,false);

        userSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                assginedProject.clear();
                projectsTV.clear();

                assginProjectList =  controller.getAllAssignmentProjects(userId.get(userSP.getSelectedItemPosition()));
                for(int i=0 ; i<assginProjectList.size(); i++)
                    projectsTV.add(assginProjectList.get(i).get("projectName"));

                projectsLL.setAdapter(gvAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
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

        ImageView assignProject = (ImageView) findViewById(R.id.assignProject);
        assignProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!projectAssignmentId.get(projectAssignmentSP.getSelectedItemPosition()).equals("0")) {

                    projectsTV.add(projectAssignmentSP.getSelectedItem().toString());
                    projectsLL.setAdapter(gvAdapter);
                    assginedProject.add(projectAssignmentSP.getSelectedItem().toString());
                }else {
                    new ShowToast(AssignProject.this, "Please Choose Project");
                }
            }

        });


        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userSP.getSelectedItemPosition() !=0) {
                    HashMap<String, String> Params = new HashMap<String, String>();

                    for(int i=0 ; i<assginedProject.size(); i++) {
                        Params = new HashMap<String, String>();
                        Params.put("userId", userId.get(userSP.getSelectedItemPosition()));
                        Params.put("projectName", assginedProject.get(i));

                        controller.insertProjectAssignment(Params);
                    }

                    new ShowToast(AssignProject.this, "Save successes");

                    userAdapter.clear();
                    userId.clear();

                    userAdapter.add("Choose User");
                    userId.add("0");

                    ArrayList<HashMap<String, String>> usersList =  controller.getAllUsers();
                    for(int i=0 ; i<usersList.size(); i++) {
                        userAdapter.add(usersList.get(i).get("userName"));
                        userId.add(usersList.get(i).get("userId"));
                    }

                    userSP.setAdapter(userAdapter);
                    userSP.setSelection(0,false);

                    projectAssignmentAdapter.clear();
                    projectAssignmentId.clear();

                    projectAssignmentAdapter.add("Choose Project");
                    projectAssignmentId.add("0");

                    ArrayList<HashMap<String, String>> projectsList =  controller.getAllProjects();
                    for(int i=0 ; i<projectsList.size(); i++) {
                        projectAssignmentAdapter.add(projectsList.get(i).get("projectName"));
                        projectAssignmentId.add(projectsList.get(i).get("projectId"));
                    }

                    projectAssignmentSP.setAdapter(projectAssignmentAdapter);
                    projectAssignmentSP.setSelection(0,false);

                    projectsTV.clear();
                    projectsLL.setAdapter(gvAdapter);
                }else {
                    new ShowToast(AssignProject.this, "Please Select User");
                }
            }

        });
    }

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
