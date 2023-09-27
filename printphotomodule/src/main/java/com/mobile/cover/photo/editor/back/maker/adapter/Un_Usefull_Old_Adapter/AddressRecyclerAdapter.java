package com.mobile.cover.photo.editor.back.maker.adapter.Un_Usefull_Old_Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemDeleteListener;
import com.mobile.cover.photo.editor.back.maker.model.Address;

import java.util.ArrayList;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.MyViewHolder> {
    Context context;
    ProgressDialog pd;
    ArrayList<Address> addressArrayList;
    OnItemClickListener onItemClickListener;
    OnItemDeleteListener onItemDeleteListener;

    public AddressRecyclerAdapter(Context context, ArrayList<Address> addressArrayList) {
        this.context = context;
        this.addressArrayList = addressArrayList;
    }

    @Override
    public AddressRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_address_item, parent, false);
        return new AddressRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AddressRecyclerAdapter.MyViewHolder holder, final int position) {

//        holder.id_ll_title.getLayoutParams().height = (int) (Share.screenHeight * 0.04);
        final Address address = addressArrayList.get(position);
        holder.id_add_title.setText("Address " + (position + 1));

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(address.getHome_no());
        stringBuilder.append(",");
        stringBuilder.append(address.getStreet());
        stringBuilder.append(",");
        stringBuilder.append(address.getLandmark());
        stringBuilder.append(",\n");
        stringBuilder.append(address.getCity());
        stringBuilder.append(",\n");
        stringBuilder.append(address.getState());
        stringBuilder.append(",\n");
        stringBuilder.append(address.getCountry());
        stringBuilder.append(" -" + address.getPincode() + ".");

        holder.address_detail.setText(stringBuilder.toString());
//        holder.address_pincode.setText(""+SharedPrefs.getString(context, Share.key_ + RegReq.mobile_1));


        if (addressArrayList.size() == 1) {
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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView edit_address;
        TextView id_add_title, address_detail, address_pincode;
        TextView delete_address;
        //        LinearLayout id_ll_title;
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
//            id_ll_title = view.findViewById(R.id.id_ll_title);


        }
    }
}
