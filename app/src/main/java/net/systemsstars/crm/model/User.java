package net.systemsstars.crm.model;

import java.io.Serializable;

/**
 * Created by Lincoln on 07/01/16.
 */
public class User implements Serializable {
    String id;
	String email;
    String name;
    String type;
    String state;

	public User() {
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public User(String id, String name, String type, String state) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}