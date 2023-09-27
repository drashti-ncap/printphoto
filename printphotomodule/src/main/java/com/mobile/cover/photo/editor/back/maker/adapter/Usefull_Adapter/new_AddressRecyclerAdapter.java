package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.save_address_response_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemDeleteListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnaddressItemClickListener;

import java.util.List;

public class new_AddressRecyclerAdapter extends RecyclerView.Adapter<new_AddressRecyclerAdapter.MyViewHolder> {
    Context context;
    List<save_address_response_data> addressArrayList;
    OnItemClickListener onItemClickListener;
    OnItemDeleteListener onItemDeleteListener;
    OnaddressItemClickListener onaddressItemClickListener;


    public new_AddressRecyclerAdapter(Context context, List<save_address_response_data> addressArrayList) {
        this.context = context;
        this.addressArrayList = addressArrayList;
    }

    @Override
    public new_AddressRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_address_item, parent, false);
        return new new_AddressRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final new_AddressRecyclerAdapter.MyViewHolder holder, final int position) {

        final save_address_response_data address = addressArrayList.get(position);
        holder.id_add_title.setText(address.getReceiverName());

        final StringBuilder stringBuilder = new StringBuilder();
//        if (Share.isinternational == 1) {
        stringBuilder.append(address.getAddress());
        stringBuilder.append(",");
        stringBuilder.append(address.getAddress1());
        stringBuilder.append(",");
        stringBuilder.append(address.getCity().getName());
        stringBuilder.append(",\n");
        stringBuilder.append(address.getCity().getState().getName());
        stringBuilder.append(",\n");
        stringBuilder.append(address.getCountry().getName());
        stringBuilder.append(" - " + address.getPincode() + ".");
        stringBuilder.append("\n");
        stringBuilder.append(context.getString(R.string.mobile_no_val) + SharedPrefs.getString(context, Share.key_ + RegReq.mobile_1));
        stringBuilder.append("\n");
        stringBuilder.append(context.getString(R.string.alternate_mobile_no) + address.getAlternateMobile());
//        } else {
//            stringBuilder.append(address.getAddress());
//            stringBuilder.append(",");
//            stringBuilder.append(address.getAddress1());
//            stringBuilder.append(",");
//            if (address.getCity().getState() != null) {
//                stringBuilder.append(address.getCity().getName());
//                stringBuilder.append(",\n");
//                stringBuilder.append(address.getCity().getState().getName());
//                stringBuilder.append(",\n");
//                stringBuilder.append(address.getCity().getState().getCountry().getName());
//            }
//            stringBuilder.append(" - " + address.getPincode() + ".");
//            stringBuilder.append("\n");
//            stringBuilder.append("Mobile No. : " + SharedPrefs.getString(context, Share.key_ + RegReq.mobile_1));
//            stringBuilder.append("\n");
//            stringBuilder.append("Alternate Mobile No. : " + address.getAlternateMobile());
//        }

        holder.address_detail.setText(stringBuilder.toString());

        if (Share.saved_address_list.get(position).getIsSelect()) {
            holder.id_radio.setChecked(true);
        } else {
            holder.id_radio.setChecked(false);
        }

        if (Share.saved_address_list.size() == 1) {
            holder.delete_address.setVisibility(View.GONE);
        } else {
            holder.delete_address.setVisibility(View.VISIBLE);
        }


        holder.edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("POSITION", "onClick: " + position);
                onItemClickListener.onItemClickLister(view, position);
            }
        });
        holder.delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("POSITION", "onClick: " + position);
                onItemDeleteListener.onItemDeletekLister(v, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return addressArrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemDeletekLister(OnItemDeleteListener onItemClickListener) {
        this.onItemDeleteListener = onItemClickListener;
    }

    public void setOnaddressItemClickListener(OnaddressItemClickListener onaddressItemClickListener) {
        this.onaddressItemClickListener = onaddressItemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView edit_address;
        TextView id_add_title, address_detail, address_pincode;
        TextView delete_address;
        LinearLayout main_card;
        RadioButton id_radio;

        public MyViewHolder(View view) {
            super(view);

            edit_address = view.findViewById(R.id.edit_address);
            delete_address = view.findViewById(R.id.delete_address);
            id_add_title = view.findViewById(R.id.id_add_title);
            address_detail = view.findViewById(R.id.address_detail);
            address_pincode = view.findViewById(R.id.address_pincode);
            main_card = view.findViewById(R.id.main_card);
            id_radio = view.findViewById(R.id.id_radio);
            if (Share.isfromplaceorder) {
                id_radio.setVisibility(View.VISIBLE);
            } else {
                id_radio.setVisibility(View.GONE);
            }
        }
    }
}
