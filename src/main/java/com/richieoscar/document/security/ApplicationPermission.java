package com.richieoscar.document.security;

public enum ApplicationPermission {
    DOCUMENT_READ("document:read"),
    DOCUMENT_WRITE("document:write");
    private final String permission;

    ApplicationPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
