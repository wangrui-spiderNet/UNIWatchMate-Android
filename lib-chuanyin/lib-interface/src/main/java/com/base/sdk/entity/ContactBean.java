package com.base.sdk.entity;

import java.util.Objects;

public class ContactBean {
    private String name;
    private String number;

    public ContactBean(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number.replaceAll("//s","");
    }

    public void setNumber(String number) {
        this.number = number.replaceAll("//s","");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactBean)) return false;
        ContactBean that = (ContactBean) o;
        return Objects.equals(getName().trim(), that.getName().trim()) && Objects.equals(getNumber().trim(), that.getNumber().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumber());
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
