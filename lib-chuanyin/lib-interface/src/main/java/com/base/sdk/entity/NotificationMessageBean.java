package com.base.sdk.entity;

import android.text.TextUtils;

import java.util.Objects;

public class NotificationMessageBean {
    /* Additional fields if needed */
    public int id;
    public String title;
    public String content;
    public String subContent;
    public String appPackage;

    public NotificationMessageBean(int id, String title, String content, String subContent, String appPackage) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.subContent = subContent;
        this.appPackage = appPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationMessageBean)) return false;
        NotificationMessageBean that = (NotificationMessageBean) o;
        if (TextUtils.isEmpty(((NotificationMessageBean) o).content)) {
            return title.equals(that.title) && appPackage.equals(that.appPackage);
        }

        return title.equals(that.title) && content.equals(that.content) && appPackage.equals(that.appPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, appPackage);
    }

    @Override
    public String toString() {
        return "NotificationMessageEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", subContent='" + subContent + '\'' +
                ", appPackage='" + appPackage + '\'' +
                '}';
    }
}