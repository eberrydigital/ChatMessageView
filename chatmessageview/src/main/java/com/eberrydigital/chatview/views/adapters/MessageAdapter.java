package com.eberrydigital.chatview.views.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eberrydigital.chatview.R;
import com.eberrydigital.chatview.model.Attribute;
import com.eberrydigital.chatview.models.Message;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Custom list adapter for the chat timeline
 * Created by nakayama on 2016/08/08.
 */
public class MessageAdapter extends ArrayAdapter<Object> {

    private LayoutInflater mLayoutInflater;
    private List<Object> mObjects;
    private List<Object> mViewTypes = new ArrayList<>();

    private Message.OnIconClickListener mOnIconClickListener;
    private Message.OnBubbleClickListener mOnBubbleClickListener;
    private Message.OnIconLongClickListener mOnIconLongClickListener;
    private Message.OnBubbleLongClickListener mOnBubbleLongClickListener;
    private Message.OnStausIconClickListener mOnStausIconClickListener;

    private int mDateSeparatorColor = ContextCompat.getColor(getContext(), R.color.blueGray500);
    private int mLeftBubbleColor;
    private int mRightBubbleColor;
    private int mStatusColor = ContextCompat.getColor(getContext(), R.color.blueGray500);
    /**
     * Default message item margin top
     */
    private int mMessageTopMargin = 5;
    /**
     * Default message item margin bottom
     */
    private int mMessageBottomMargin = 5;

    private Attribute mAttribute;

    public MessageAdapter(Context context, int resource, List<Object> objects, Attribute attribute) {
        super(context, resource, objects);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObjects = objects;
        mViewTypes.add(String.class);
        mViewTypes.add(Message.class);
        mLeftBubbleColor = ContextCompat.getColor(context, R.color.default_left_item_color);
        mRightBubbleColor = ContextCompat.getColor(context, R.color.default_right_item_color);
        mAttribute = attribute;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mObjects.get(position);
        return mViewTypes.indexOf(item);
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypes.size();
    }

    @NonNull
    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Object item = getItem(position);

        if (item instanceof String) {
            // item is Date label
            DateViewHolder dateViewHolder;
            String dateText = (String) item;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.date_cell, null);
                dateViewHolder = new DateViewHolder();
                dateViewHolder.dateSeparatorText = (TextView) convertView.findViewById(R.id.date_separate_text);
                convertView.setTag(dateViewHolder);
            } else {
                dateViewHolder = (DateViewHolder) convertView.getTag();
            }
            dateViewHolder.dateSeparatorText.setText(dateText);
            dateViewHolder.dateSeparatorText.setTextColor(mDateSeparatorColor);
            dateViewHolder.dateSeparatorText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttribute.getDateSeparatorFontSize());
        } else {
            //Item is a message
            MessageViewHolder holder;
            final Message message = (Message) item;
            if (position > 0) {
                Object prevItem = getItem(position - 1);
                if (prevItem instanceof Message) {
                    final Message prevMessage = (Message) prevItem;
                    if (prevMessage.getUser()!= null && prevMessage.getUser().equals(message.getUser())) {
                        //If send same person, hide username and icon.
                        message.setIconVisibility(false);
                        message.setUsernameVisibility(false);
                    }
                }
            }

            String userName = message.getUser();

            //Bubble color
            Drawable bubbleDrawable;
            Drawable wrappedDrawable;
            ColorStateList colorStateList;

            if (!message.isIncoming()) {
                //Left message
                if (convertView == null) {
                    convertView = mLayoutInflater.inflate(R.layout.message_view_right, null);
                    holder = new MessageViewHolder();
                    holder.mainMessageContainer = (FrameLayout) convertView.findViewById(R.id.main_message_container);
                    holder.timeText = (TextView) convertView.findViewById(R.id.time_label_text);
                    holder.usernameContainer = (FrameLayout) convertView.findViewById(R.id.message_user_name_container);
                    holder.statusContainer = (FrameLayout) convertView.findViewById(R.id.message_status_container);
                    convertView.setTag(holder);
                } else {
                    holder = (MessageViewHolder) convertView.getTag();
                }

                //Remove view in each container
                holder.usernameContainer.removeAllViews();
                holder.statusContainer.removeAllViews();
                holder.mainMessageContainer.removeAllViews();

                if (userName != null && message.getUsernameVisibility()) {
                    View usernameView = mLayoutInflater.inflate(R.layout.user_name_right, holder.usernameContainer);
                    holder.username = (TextView) usernameView.findViewById(R.id.message_user_name);
                    holder.username.setText(userName);
                }


                //Show message status
                if (message.getMessageStatusType() == Message.MESSAGE_STATUS_ICON || message.getMessageStatusType() == Message.MESSAGE_STATUS_ICON_RIGHT_ONLY) {
                    //Show message status icon
                    View statusIcon = mLayoutInflater.inflate(R.layout.message_status_icon, holder.statusContainer);
                    holder.statusIcon = (ImageView) statusIcon.findViewById(R.id.status_icon_image_view);
                    holder.statusIcon.setImageDrawable(message.getStatusIcon());
                    if (mOnStausIconClickListener != null) {
                        holder.statusContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnStausIconClickListener.onStausIconClick(message);
                            }
                        });
                    }
                } else if (message.getMessageStatusType() == Message.MESSAGE_STATUS_TEXT || message.getMessageStatusType() == Message.MESSAGE_STATUS_TEXT_RIGHT_ONLY) {
                    //Show message status text
                    View statusText = mLayoutInflater.inflate(R.layout.message_status_text, holder.statusContainer);
                    holder.statusText = (TextView) statusText.findViewById(R.id.status_text_view);
                    holder.statusText.setText(message.getStatusText());
                    holder.statusText.setTextColor(mStatusColor);
                }

                //Set text
                View textBubble = mLayoutInflater.inflate(R.layout.message_text_right, holder.mainMessageContainer);
                holder.messageText = (TextView) textBubble.findViewById(R.id.message_text);
                holder.messageText.setText(message.getMessageText());

                holder.timeText.setText(message.getTimeText());

                //Set Padding
                convertView.setPadding(0, mMessageTopMargin, 0, mMessageBottomMargin);

            } else {
                //Right message
                if (convertView == null) {
                    convertView = mLayoutInflater.inflate(R.layout.message_view_left, null);
                    holder = new MessageViewHolder();
                    holder.mainMessageContainer = (FrameLayout) convertView.findViewById(R.id.main_message_container);
                    holder.timeText = (TextView) convertView.findViewById(R.id.time_label_text);
                    holder.usernameContainer = (FrameLayout) convertView.findViewById(R.id.message_user_name_container);
                    holder.statusContainer = (FrameLayout) convertView.findViewById(R.id.message_status_container);
                    convertView.setTag(holder);
                } else {
                    holder = (MessageViewHolder) convertView.getTag();
                }


                //Remove view in each container
                holder.usernameContainer.removeAllViews();
                holder.statusContainer.removeAllViews();
                holder.mainMessageContainer.removeAllViews();


                if (userName != null && message.getUsernameVisibility()) {
                    View usernameView = mLayoutInflater.inflate(R.layout.user_name_left, holder.usernameContainer);
                    holder.username = (TextView) usernameView.findViewById(R.id.message_user_name);
                    holder.username.setText(userName);
                }

                //Show message status
                if (message.getMessageStatusType() == Message.MESSAGE_STATUS_ICON || message.getMessageStatusType() == Message.MESSAGE_STATUS_ICON_LEFT_ONLY) {
                    //Show message status icon
                    View statusIcon = mLayoutInflater.inflate(R.layout.message_status_icon, holder.statusContainer);
                    holder.statusIcon = (ImageView) statusIcon.findViewById(R.id.status_icon_image_view);
                    holder.statusIcon.setImageDrawable(message.getStatusIcon());

                } else if (message.getMessageStatusType() == Message.MESSAGE_STATUS_TEXT || message.getMessageStatusType() == Message.MESSAGE_STATUS_TEXT_LEFT_ONLY) {
                    //Show message status text
                    View statusText = mLayoutInflater.inflate(R.layout.message_status_text, holder.statusContainer);
                    holder.statusText = (TextView) statusText.findViewById(R.id.status_text_view);
                    holder.statusText.setText(message.getStatusText());
                    holder.statusText.setTextColor(mStatusColor);
                }

                //Set text
                View textBubble = mLayoutInflater.inflate(R.layout.message_text_left, holder.mainMessageContainer);
                holder.messageText = (TextView) textBubble.findViewById(R.id.message_text);
                holder.messageText.setText(message.getMessageText());
                holder.timeText.setText(message.getTimeText());
                //Set Padding
                convertView.setPadding(0, mMessageTopMargin, 0, mMessageBottomMargin);

            }

            if (holder.mainMessageContainer != null) {
                //Set bubble click listener
                if (mOnBubbleClickListener != null) {
                    holder.mainMessageContainer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnBubbleClickListener.onClick(message);
                        }
                    });
                }

                //Set bubble long click listener
                if (mOnBubbleLongClickListener != null) {
                    holder.mainMessageContainer.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            mOnBubbleLongClickListener.onLongClick(message);
                            return true;//ignore onclick event
                        }
                    });
                }
            }

            //Set icon events if icon is shown
            if (message.getIconVisibility() && holder.icon != null) {
                //Set icon click listener
                if (mOnIconClickListener != null) {
                    holder.icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnIconClickListener.onIconClick(message);
                        }
                    });
                }

                if (mOnIconLongClickListener != null) {
                    holder.icon.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            mOnIconLongClickListener.onIconLongClick(message);
                            return true;
                        }
                    });
                }
            }

            if (null != holder.messageText) {
                holder.messageText.setMaxWidth(mAttribute.getMessageMaxWidth());
            }
        }

        return convertView;
    }

    /**
     * Set left bubble background color
     *
     * @param color left bubble color
     */
    public void setLeftBubbleColor(int color) {
        mLeftBubbleColor = color;
        notifyDataSetChanged();
    }

    /**
     * Set right bubble background color
     *
     * @param color right bubble color
     */
    public void setRightBubbleColor(int color) {
        mRightBubbleColor = color;
        notifyDataSetChanged();
    }

    public void setOnIconClickListener(Message.OnIconClickListener onIconClickListener) {
        mOnIconClickListener = onIconClickListener;
    }

    public void setOnBubbleClickListener(Message.OnBubbleClickListener onBubbleClickListener) {
        mOnBubbleClickListener = onBubbleClickListener;
    }

    public void setOnIconLongClickListener(Message.OnIconLongClickListener onIconLongClickListener) {
        mOnIconLongClickListener = onIconLongClickListener;
    }

    public void setOnBubbleLongClickListener(Message.OnBubbleLongClickListener onBubbleLongClickListener) {
        mOnBubbleLongClickListener = onBubbleLongClickListener;
    }

    public void setOnStatusClickListener(Message.OnStausIconClickListener onStausIconClickListener) {
        mOnStausIconClickListener = onStausIconClickListener;
    }

    public void setDateSeparatorColor(int dateSeparatorColor) {
        mDateSeparatorColor = dateSeparatorColor;
        notifyDataSetChanged();
    }

    public void setMessageTopMargin(int messageTopMargin) {
        mMessageTopMargin = messageTopMargin;
    }

    public void setMessageBottomMargin(int messageBottomMargin) {
        mMessageBottomMargin = messageBottomMargin;
    }

    public void setStatusColor(int statusTextColor) {
        mStatusColor = statusTextColor;
        notifyDataSetChanged();
    }

    class MessageViewHolder {
        CircleImageView icon;
        TextView messageText;
        TextView timeText;
        TextView username;
        FrameLayout mainMessageContainer;
        FrameLayout usernameContainer;
        FrameLayout statusContainer;
        ImageView statusIcon;
        TextView statusText;
    }

    class DateViewHolder {
        TextView dateSeparatorText;
    }


}
