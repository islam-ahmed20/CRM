package net.systemsstars.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.systemsstars.crm.helper.DBController;
import net.systemsstars.crm.helper.MyApplication;
import net.systemsstars.crm.helper.ShowToast;
import net.systemsstars.crm.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    View PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PB = (View) findViewById(R.id.PB);

        final DBController controller = new DBController(this);
        final EditText userName = (EditText) findViewById(R.id.userName);
        final EditText pass = (EditText) findViewById(R.id.pass);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userName.getEditableText().toString().isEmpty() && !pass.getEditableText().toString().isEmpty()) {

                    PB.setVisibility(View.VISIBLE);

                    HashMap<String, String> Params = new HashMap<String, String>();

                    Params.put("userName", userName.getEditableText().toString());
                    Params.put("password", pass.getEditableText().toString());
                    HashMap<String, String> userDB = controller.getUser(Params);

                    if (userDB.size() != 0) {

                        User usr = null;

                        usr = new User(userDB.get("userId"), userDB.get("userName"), userDB.get("type"));

                        MyApplication.getInstance().getPrefManager().storeUser(usr);

                        new ShowToast(Login.this, getResources().getString(R.string.toast_login_succ));

                        Intent i = null;
                        if (userDB.get("type").equals("admin")) {
                            i = new Intent(Login.this, MainActivity.class);
                        }else {
                            i = new Intent(Login.this, UserDashBoard.class);
                        }
                        startActivity(i);

                        finish();

                    } else {
                        new ShowToast(Login.this, getResources().getString(R.string.toast_pass_error));
                    }

                    PB.setVisibility(View.GONE);
                }else {
                    new ShowToast(Login.this, getResources().getString(R.string.toast_missed_data));
                }
            }

        });


    }
}
