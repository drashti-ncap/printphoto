package com.mobile.cover.photo.editor.back.maker.adapter.Un_Usefull_Old_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.Address;

import java.util.ArrayList;

public class PlaceOrderAddressRecyclerAdapter extends RecyclerView.Adapter<PlaceOrderAddressRecyclerAdapter.MyViewHolder> {
    Context context;

    ArrayList<Address> addressArrayList;
    OnItemClickListener onItemClickListener;

    public PlaceOrderAddressRecyclerAdapter(Context context, ArrayList<Address> addressArrayList) {
        this.context = context;
        this.addressArrayList = addressArrayList;
    }

    @Override
    public PlaceOrderAddressRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_address_item, parent, false);
        return new PlaceOrderAddressRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceOrderAddressRecyclerAdapter.MyViewHolder holder, final int position) {

//        holder.main_card.getLayoutParams().height = (int) (Share.screenHeight / 5);
//        holder.id_ll_title.getLayoutParams().height = (int) (Share.screenHeight * 0.05);
        Address address = addressArrayList.get(position);
        holder.id_add_title.setText("Address " + (position + 1));

        holder.id_radio.setVisibility(View.VISIBLE);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" " + address.getHome_no());
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
        holder.address_pincode.setText(address.getPincode());

        holder.id_radio.setClickable(false);
        if (address.isSelect()) {
            holder.id_radio.setChecked(true);
            Share.address_value = holder.address_detail.getText().toString();
        } else {
            holder.id_radio.setChecked(false);
        }

        holder.edit_address.setVisibility(View.GONE);
        holder.delete_address.setVisibility(View.GONE);
        holder.ll_edit_delete.setVisibility(View.GONE);
        holder.main_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickLister(view, position);
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

    public void notifydata(ArrayList<Address> addressArrayList) {
        this.addressArrayList = addressArrayList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView edit_address, delete_address;
        TextView id_add_title, address_detail, address_pincode;

        RadioButton id_radio;
        //        LinearLayout id_ll_title;
        LinearLayout main_card, ll_edit_delete;

        public MyViewHolder(View view) {
            super(view);

            edit_address = view.findViewById(R.id.edit_address);
            delete_address = view.findViewById(R.id.delete_address);
            id_add_title = view.findViewById(R.id.id_add_title);
            address_detail = view.findViewById(R.id.address_detail);
            address_pincode = view.findViewById(R.id.address_pincode);
            main_card = view.findViewById(R.id.main_card);
            ll_edit_delete = view.findViewById(R.id.ll_edit_delete);
//            id_ll_title = view.findViewById(R.id.id_ll_title);
            id_radio = view.findViewById(R.id.id_radio);


        }
    }
}
