package com.eberrydigital.chatview.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.eberrydigital.chatview.util.DateFormatter;
import com.eberrydigital.chatview.util.DefaultTimeFormatter;
import com.eberrydigital.chatview.util.IMessageStatusIconFormatter;
import com.eberrydigital.chatview.util.IMessageStatusTextFormatter;
import com.eberrydigital.chatview.util.ITimeFormatter;

import java.util.Calendar;

/**
 * Message object
 * Created by nakayama on 2016/08/08.
 */
public class Message {

    /**
     * Message status is not shown.
     */
    public static final int MESSAGE_STATUS_NONE = 0;
    /**
     * Show message status icon.
     */
    public static final int MESSAGE_STATUS_ICON = 1;
    /**
     * Show message status text.
     * ex. seen, fail, delivered
     */
    public static final int MESSAGE_STATUS_TEXT = 2;
    /**
     * Show message status icon only right message
     */
    public static final int MESSAGE_STATUS_ICON_RIGHT_ONLY = 3;
    /**
     * Show message status icon only left message
     */
    public static final int MESSAGE_STATUS_ICON_LEFT_ONLY = 4;
    /**
     * Show message status text only right message
     */
    public static final int MESSAGE_STATUS_TEXT_RIGHT_ONLY = 5;
    /**
     * Show message status text only left message
     */
    public static final int MESSAGE_STATUS_TEXT_LEFT_ONLY = 6;
    /**
     * Sender information
     */
    private String employeeName;
    /**
     * Whether sender username is shown or not
     */
    private boolean mUsernameVisibility = true;
    /**
     * If true, there is the icon space but invisible.
     */
    private boolean mIconVisibility = true;
    /**
     * If true, there is no icon space.
     */
    private boolean mHideIcon = false;


    /**
     * The time message that was created
     */
    private Calendar mCreatedAt;
    /**
     * Whether cell of list view is date separator text or not.
     */
    private boolean isDateCell;
    /**
     * TEXT format of the send time that is next to the message
     */
    private ITimeFormatter mSendTimeFormatter;
    /**
     * Date separator text format.
     * This text is shown if the before or after message was sent different day
     */
    private ITimeFormatter mDateFormatter;
    /**
     * Message status
     * You can use to know the message status such as fail, delivered, seen.. etc.
     */
    private int mStatus;
    /**
     * Message status type such as icon, text, or none.
     */
    private int mMessageStatusType = MESSAGE_STATUS_NONE;

    /**
     * Message status icon formatter
     */
    private IMessageStatusIconFormatter mStatusIconFormatter;

    /**
     * Message status text formatter
     */
    private IMessageStatusTextFormatter mStatusTextFormatter;

    /**
     * PICTURE message
     */
    private Bitmap mPicture;
    /**
     * Message type
     */
    private Type mType;
    /**
     * Eberry
     */
    private boolean isIncoming;
    private String created;
    private String hash;
    private String text;

    /**
     * Constructor
     */
    public Message() {
        mCreatedAt = Calendar.getInstance();
        mSendTimeFormatter = new DefaultTimeFormatter();
        mDateFormatter = new DateFormatter();
        mSendTimeFormatter = new DefaultTimeFormatter();
        mType = Type.TEXT;
    }

    public Message(String employeeName, boolean isIncoming, String text, String created, String hash) {
        this.employeeName = employeeName;
        this.isIncoming = isIncoming;
        this.text = text;
        this.created = created;
        this.hash = hash;
    }

    public String getUser() {
        if (employeeName == null) {
            employeeName = "You";
        }
        return employeeName;
    }

    public void setUser(String string) {
        employeeName = string;
    }

    public boolean getUsernameVisibility() {
        return mUsernameVisibility;
    }

    public void setUsernameVisibility(boolean usernameVisibility) {
        mUsernameVisibility = usernameVisibility;
    }

    public boolean isIconHided() {
        return mHideIcon;
    }

    public void hideIcon(boolean hideIcon) {
        mHideIcon = hideIcon;
    }

    public boolean isIncoming() {
        if (!employeeName.equals("You")) {
            isIncoming = false;
        } else {
            isIncoming = true;
        }
        return isIncoming;
    }

    public void setIncoming(boolean isRightMessage) {
        this.isIncoming = isRightMessage;
    }

    public boolean getIconVisibility() {
        return mIconVisibility;
    }

    public void setIconVisibility(boolean iconVisibility) {
        mIconVisibility = iconVisibility;
    }

    public String getMessageText() {
        return text;
    }

    public void setMessageText(String messageText) {
        text = messageText;
    }

    public Calendar getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Calendar calendar) {
        mCreatedAt = calendar;
    }

    public String getTimeText() {
        return mSendTimeFormatter.getFormattedTimeText(mCreatedAt);
    }

    public boolean isDateCell() {
        return isDateCell;
    }

    public void setDateCell(boolean isDateCell) {
        this.isDateCell = isDateCell;
    }

    public String getDateSeparateText() {
        return mDateFormatter.getFormattedTimeText(mCreatedAt);
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getMessageStatusType() {
        return mMessageStatusType;
    }

    public void setMessageStatusType(int messageStatusType) {
        mMessageStatusType = messageStatusType;
    }

    public IMessageStatusIconFormatter getStatusIconFormatter() {
        return mStatusIconFormatter;
    }

    public void setStatusIconFormatter(IMessageStatusIconFormatter statusIconFormatter) {
        mStatusIconFormatter = statusIconFormatter;
    }

    public Drawable getStatusIcon() {
        return mStatusIconFormatter.getStatusIcon(mStatus, isIncoming());
    }

    public IMessageStatusTextFormatter getStatusTextFormatter() {
        return mStatusTextFormatter;
    }

    public void setStatusTextFormatter(IMessageStatusTextFormatter statusTextFormatter) {
        mStatusTextFormatter = statusTextFormatter;
    }

    public String getStatusText() {
        return mStatusTextFormatter.getStatusText(mStatus, isIncoming());
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public Bitmap getPicture() {
        return mPicture;
    }

    public void setPicture(Bitmap picture) {
        mPicture = picture;
    }

    /**
     * Set custom send time text formatter
     *
     * @param sendTimeFormatter custom send time formatter
     */
    public void setSendTimeFormatter(ITimeFormatter sendTimeFormatter) {
        mSendTimeFormatter = sendTimeFormatter;
    }

    /**
     * Set custom date text formatter
     *
     * @param dateFormatter custom date formatter
     */
    public void setDateFormatter(ITimeFormatter dateFormatter) {
        mDateFormatter = dateFormatter;
    }

    /**
     * Return Calendar to compare the day <br>
     * Reset hour, min, sec, milli sec.<br>
     *
     * @return formatted calendar object
     */
    public Calendar getCompareCalendar() {
        Calendar calendar = (Calendar) mCreatedAt.clone();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Message Types
     */
    public enum Type {
        TEXT,
        PICTURE,
        MAP,
        LINK
    }

    public interface OnBubbleClickListener {
        void onClick(Message message);
    }

    public interface OnBubbleLongClickListener {
        void onLongClick(Message message);
    }

    public interface OnIconClickListener {
        void onIconClick(Message message);
    }

    public interface OnIconLongClickListener {
        void onIconLongClick(Message message);
    }

    /**
     * Message builder
     */
    public static class Builder {
        private Message message;

        public Builder() {
            message = new Message();
        }

        public Builder setUser(String string) {
            message.setUser(string);
            return this;
        }

        public Builder setUsernameVisibility(boolean visibility) {
            message.setUsernameVisibility(visibility);
            return this;
        }

        public Builder setUserIconVisibility(boolean visibility) {
            message.setIconVisibility(visibility);
            return this;
        }

        public Builder hideIcon(boolean hide) {
            message.hideIcon(hide);
            return this;
        }


        public Builder setRightMessage(boolean isRight) {
            message.setIncoming(isRight);
            return this;
        }

        public Builder setMessageText(String messageText) {
            message.setMessageText(messageText);
            return this;
        }

        public Builder setCreatedAt(Calendar calendar) {
            message.setCreatedAt(calendar);
            return this;
        }

        public Builder setDateCell(boolean isDateCell) {
            message.setDateCell(isDateCell);
            return this;
        }

        public Builder setSendTimeFormatter(ITimeFormatter sendTimeFormatter) {
            message.setSendTimeFormatter(sendTimeFormatter);
            return this;
        }

        public Builder setDateFormatter(ITimeFormatter dateFormatter) {
            message.setDateFormatter(dateFormatter);
            return this;
        }

        public Builder setStatus(int status) {
            message.setStatus(status);
            return this;
        }

        public Builder setMessageStatusType(int messageStatusType) {
            message.setMessageStatusType(messageStatusType);
            return this;
        }

        public Builder setStatusIconFormatter(IMessageStatusIconFormatter formatter) {
            message.setStatusIconFormatter(formatter);
            return this;
        }

        public Builder setStatusTextFormatter(IMessageStatusTextFormatter formatter) {
            message.setStatusTextFormatter(formatter);
            return this;
        }

        public Builder setType(Type type) {
            message.setType(type);
            return this;
        }

        public Builder setPicture(Bitmap picture) {
            message.setPicture(picture);
            return this;
        }

        public Message build() {
            return message;
        }

    }
}
