package net.systemsstars.crm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import net.systemsstars.crm.helper.DBController;

import java.util.ArrayList;
import java.util.HashMap;

public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Report"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBController controller = new DBController(this);
        TextView report = (TextView) findViewById(R.id.report);

        ArrayList<HashMap<String, String>> userList =  controller.getAllUsers();

        String reportText = "";
        for(int i=0 ; i<userList.size(); i++) {

            String projectText = "";
            ArrayList<HashMap<String, String>> projectsList =  controller.getAllAssignmentProjects(userList.get(i).get("userId"));
            for(int y=0 ; y<projectsList.size(); y++) {
                projectText += projectsList.get(y).get("projectName") + ", ";
            }

            reportText += " userId : " + userList.get(i).get("userId")
                        + "\n userName : " + userList.get(i).get("userName")
                        + "\n userType : " + userList.get(i).get("type")
                        + "\n phone : " + userList.get(i).get("phone")
                        + "\n Projects : " + projectText
                        + "\n payments : " + userList.get(i).get("payments")
                        + "\n paymentsDate : " + userList.get(i).get("paymentsDate")
                        + "\n --------------- \n";
        }

        report.setText(reportText);
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
