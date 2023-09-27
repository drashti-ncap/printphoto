package com.mobile.cover.photo.editor.back.maker.Paymentintegration;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.cover.photo.editor.back.maker.R;


public class ApproveOTPFragment extends Fragment implements View.OnClickListener {

    public String otpText = "";
    TextView textView;
    Button approveBtn;
    Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.approve_otp_layout, container, false);
        textView = view.findViewById(R.id.otptext);
        approveBtn = view.findViewById(R.id.approve);
        approveBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //text = (TextView) getActivity().findViewById(R.id.otptext);
        //approveBtn = (Button) getActivity().findViewById(R.id.approve);
        //approveBtn.setOnClickListener(this);
        communicator = (Communicator) getActivity();
        textView.setText(otpText);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.approve) {
            Log.v("Logs : ", "-------------> Approve button Clicked");
            communicator.respond(otpText);
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
            //Toast.makeText(getActivity(),"called",Toast.LENGTH_SHORT).show();
        }
    }

    public void setOtpText(String data) {
        otpText = data;
    }
}
