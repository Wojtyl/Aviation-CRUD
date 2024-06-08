package org.bwojtal.springzalapp.enums;

public enum RolesEnum {
    USER_ROLE("ADMIN"),
    ADMIN_ROLE("USER");


    private final String type;

    RolesEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
