package net.systemsstars.crm.helper;

import android.content.Context;
import android.content.SharedPreferences;

import net.systemsstars.crm.model.User;

/**
 * Created by Lincoln on 07/01/16.
 */
public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "androidhive_gcm";

    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_TYPE = "user_type";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_ACTIVATION = "activation";
    private static final String KEY_EXPAIRE = "expaire";
    private static final String KEY_CREATED_AT = "created_at";

    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(User user) {
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_TYPE, user.getType());
        editor.commit();

       // Log.e(TAG, "User is stored in shared preferences. " + user.getName() + ", " + user.getType());
    }

    public User getUser() {
        if (pref.getString(KEY_USER_NAME, null) != null) {
            String id;
        	String name;
            String type;
            id = pref.getString(KEY_USER_ID, null);
            name = pref.getString(KEY_USER_NAME, null);
            type = pref.getString(KEY_USER_TYPE, null);

            User user = new User(id, name, type);
            return user;
        }
        return null;
    }
    
    public void storeAct(String act) {
        editor.putString(KEY_ACTIVATION, act);
        editor.commit();
    }

    public String getAct() {
        if (pref.getString(KEY_ACTIVATION, null) != null) {
        	String act;
            act = pref.getString(KEY_ACTIVATION, null);

            return act;
        }
        return null;
    }
    
    public void storeExp(String exp) {
        editor.putString(KEY_EXPAIRE, exp);
        editor.commit();
    }

    public String getExp() {
        if (pref.getString(KEY_EXPAIRE, null) != null) {
        	String exp;
            exp = pref.getString(KEY_EXPAIRE, null);

            return exp;
        }
        return null;
    }
    
    public void addNotification(String notification) {

        // get old notifications
        String oldNotifications = getNotifications();

        if (oldNotifications != null) {
            oldNotifications += "|" + notification;
        } else {
            oldNotifications = notification;
        }

        editor.putString(KEY_NOTIFICATIONS, oldNotifications);
        editor.commit();
    }

    public String getNotifications() {
        return pref.getString(KEY_NOTIFICATIONS, null);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
