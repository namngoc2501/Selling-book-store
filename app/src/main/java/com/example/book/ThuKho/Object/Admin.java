package com.example.book.ThuKho.Object;

public class Admin {
    private  String id;
    private  String name;
    private  String permission;

    public Admin() {
    }

    public Admin(String id, String name, String permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
