package com.example.dragonhack.ui.addProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dragonhack.MainActivity;
import com.example.dragonhack.api.RestApi;
import com.example.dragonhack.api.ServiceGenerator;
import com.example.dragonhack.models.dto.ProductDTO;
import com.example.dragonhack.ui.addProduct.supportingClasses.IntentIntegrator;
import com.example.dragonhack.ui.addProduct.supportingClasses.IntentResult;
import com.example.dragonhack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductFragment extends Fragment implements View.OnClickListener {

    private AddProductViewModel addProductViewModel;
    private RestApi mRestClient = ServiceGenerator.createService(RestApi.class);

    private Button scanBtn;
    private TextView formatTxt, contentTxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addProductViewModel = new ViewModelProvider(this).get(AddProductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);

        scanBtn = (Button)root.findViewById(R.id.scan_button);
        formatTxt = (TextView)root.findViewById(R.id.scan_format);
        contentTxt = (TextView)root.findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);

        return root;
    }

    public void onClick(View v){
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity(), this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
            /*Toast toast = Toast.makeText(getActivity(),
                    "FORMAT: " + scanFormat + "\nCONTENT: " + scanContent, Toast.LENGTH_SHORT);
            toast.show();*/
            mRestClient.getIngredientByBarcode(scanContent).enqueue(new Callback<ProductDTO>() {
                @Override
                public void onResponse(Call<ProductDTO> call, Response<ProductDTO> response) {
                    if (response.isSuccessful()){
                        System.out.println("response 33: "+ (response.body().getProduct().getProduct_name()));
                    }
                }

                @Override
                public void onFailure(Call<ProductDTO> call, Throwable t){
                    Toast.makeText(getActivity(), "Something went wrong while fetching the details. Problem connecting to server.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast toast = Toast.makeText(getActivity(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}