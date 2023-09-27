package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.Address;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.File;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.Item;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.Option;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.ResponseDashboard;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.response_data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class testing_json extends AppCompatActivity {
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_json);

        pd = ProgressDialog.show(testing_json.this, "", getString(R.string.loading), true, false);

        ResponseDashboard responseDashboard = new ResponseDashboard();
        responseDashboard.setApikey("fcfa8dfa44ecc1a8d26a0296e0a85350");
        responseDashboard.setReference("123456789101234");
        responseDashboard.setEmail("support@client-company.com");
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setType("delivery");
        address.setCompany("Example company");
        address.setFirstname("Example firstname");
        address.setLastname("Example lastname");
        address.setStreet1("Example street 1234");
        address.setZip("99999");
        address.setCity("Example city");
        address.setCountry("DE");
        address.setEmail("email@example.com");
        address.setPhone("12345678");
        addresses.add(address);

        responseDashboard.setAddresses(addresses);
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setReference("12345678");
        item.setProduct("photobook_cw_a5_p_fc");
        item.setShippingLevel("cp_saver");
        item.setTitle("Book Hardcover 21x30");
        item.setCount("5");
        List<File> files = new ArrayList<>();

        File file = new File();
        file.setType("cover");
        file.setUrl("https://download.example.com/files/9aade201b3a85ceec318b2240d5eb373");
        file.setMd5sum("4578c3ecf64e47581b175d542f8b0160");
        files.add(file);

        item.setFiles(files);
        List<Option> options = new ArrayList<>();
        Option option = new Option();

        option.setType("total_pages");
        option.setCount("18");
        options.add(option);

        item.setOptions(options);

        items.add(item);
        responseDashboard.setItems(items);


        testing_MainApiClient mainApiClient;
        mainApiClient = new testing_MainApiClient();

        testing_APIService api = mainApiClient.getClientSend().create(testing_APIService.class);
        Call<response_data> call = api.sendLocation(responseDashboard);
        call.enqueue(new Callback<response_data>() {
            @Override
            public void onResponse(Call<response_data> call, Response<response_data> response) {
                Log.e("response", "Getting response from server : " + response.body().getOrder());
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<response_data> call, Throwable t) {
                Log.e("response", "Getting response from server : " + t.getMessage());
                pd.dismiss();
            }
        });

    }
}
