package net.systemsstars.crm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import net.systemsstars.crm.helper.DBController;
import net.systemsstars.crm.helper.MyApplication;
import net.systemsstars.crm.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("User DashBoard"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        DBController controller = new DBController(this);
        TextView report = (TextView) findViewById(R.id.report);

        HashMap<String, String> userList =  controller.getUserInfo(MyApplication.getInstance().getPrefManager().getUser().getId());

        String reportText = "";

        String projectText = "";
        ArrayList<HashMap<String, String>> projectsList =  controller.getAllAssignmentProjects(userList.get("userId"));
        for(int y=0 ; y<projectsList.size(); y++) {
            projectText += projectsList.get(y).get("projectName") + ", ";
        }

        reportText += " userName : " + userList.get("userName")
                + "\n userType : " + userList.get("type")
                + "\n phone : " + userList.get("phone")
                + "\n Projects : " + projectText
                + "\n payments : " + userList.get("payments")
                + "\n paymentsDate : " + userList.get("paymentsDate");

        report.setText(reportText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(UserDashBoard.this, Login.class);
                startActivity(intent);
                MyApplication.getInstance().getPrefManager().storeUser(new User(null, null,null));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
