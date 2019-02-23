package com.azimi.phonebook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azimi.phonebook.R;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPhoneEmail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Phone> phones;
    private List<Email> emails;
    private Context context;

    private static final int VIEW_TYPE_PHONE_WITH_ICON = 0;
    private static final int VIEW_TYPE_PHONE = 1;
    private static final int VIEW_TYPE_EMAIL_WITH_ICON = 2;
    private static final int VIEW_TYPE_EMAIL = 3;

    public RecyclerViewPhoneEmail(Context context) {
        this.context = context;
        phones = new ArrayList<>();
        emails = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_PHONE_WITH_ICON) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_phone_show_with_icon, parent, false);
            return new PhoneViewHolder(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_email_show_with_icon, parent, false);
            return new EmailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhoneViewHolder) {
            PhoneViewHolder viewHolder = (PhoneViewHolder) holder;
            viewHolder.txtPhoneNumber.setText(phones.get(position).getNumber());
            viewHolder.txtPhoneType.setText(String.valueOf(phones.get(position).getType()));
            //First item should have an icon
            if (position != 0)
                viewHolder.iconPhone.setVisibility(View.GONE);
        } else if (holder instanceof EmailViewHolder) {
            EmailViewHolder viewHolder = (EmailViewHolder) holder;
            viewHolder.txtEmail.setText(emails.get(position - phones.size()).getEmail());
            //First item should have an icon
            if (position - phones.size() != 0)
                viewHolder.iconEmail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return phones.size() + emails.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < phones.size()) {
            return VIEW_TYPE_PHONE_WITH_ICON;
        } else return VIEW_TYPE_EMAIL_WITH_ICON;
    }

    public void setData(List<Phone> phones, List<Email> emails) {
        this.phones = phones;
        this.emails = emails;
        notifyDataSetChanged();
    }

    class PhoneViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPhoneNumber;
        private TextView txtPhoneType;
        private ImageView iconPhone;

        PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPhoneNumber = itemView.findViewById(R.id.txt_phone_number);
            txtPhoneType = itemView.findViewById(R.id.txt_phone_type);
            iconPhone = itemView.findViewById(R.id.icon_phone);
        }
    }

    class EmailViewHolder extends RecyclerView.ViewHolder {
        private TextView txtEmail;
        private ImageView iconEmail;

        EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmail = itemView.findViewById(R.id.txt_email);
            iconEmail = itemView.findViewById(R.id.icon_email);
        }
    }

}
