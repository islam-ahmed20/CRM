package net.systemsstars.crm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.systemsstars.crm.helper.DBController;
import net.systemsstars.crm.helper.MyApplication;
import net.systemsstars.crm.helper.ShowToast;
import net.systemsstars.crm.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("CRM"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Button addUser = (Button) findViewById(R.id.addUser);
        Button addProject = (Button) findViewById(R.id.addProject);
        Button assignProject = (Button) findViewById(R.id.assignProject);
        Button report = (Button) findViewById(R.id.report);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddUser.class);
                startActivity(i);
            }

        });

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddProject.class);
                startActivity(i);
            }

        });

        assignProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AssignProject.class);
                startActivity(i);
            }

        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Report.class);
                startActivity(i);
            }

        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NI = connMgr.getActiveNetworkInfo();
        if(NI != null && NI.isConnected())
        {

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
            final DBController controller = new DBController(this);

            final ArrayList<HashMap<String, String>> users = controller.composeAllusers();
            myRef.child("users").push().setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    new ShowToast(MainActivity.this, "Users Sync success");
                    controller.updateUsersSyncStatus(users);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    new ShowToast(MainActivity.this, e.toString());
                }
            });

            final ArrayList<HashMap<String, String>> projects = controller.composeAllProjects();
            myRef.child("projects").push().setValue(projects).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    new ShowToast(MainActivity.this, "Projects Sync success");
                    controller.updateProjectsSyncStatus(projects);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new ShowToast(MainActivity.this, e.toString());
                        }
                    });

            final ArrayList<HashMap<String, String>> projectsAssignment = controller.composeAllProjectsAssignment();
            myRef.child("projects_assignment").push().setValue(projectsAssignment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    new ShowToast(MainActivity.this, "Projects Assignment Sync success");
                    controller.updateProjectsAssignmentSyncStatus(projectsAssignment);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new ShowToast(MainActivity.this, e.toString());
                        }
                    });
        }
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
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                MyApplication.getInstance().getPrefManager().storeUser(new User(null, null,null));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
