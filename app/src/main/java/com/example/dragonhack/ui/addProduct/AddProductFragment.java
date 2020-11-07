package com.example.dragonhack.ui.addProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

    private Button scanBtn, saveBtn;
    private TextView textViewItemName;
    private EditText editTextAmount, editTextExpDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addProductViewModel = new ViewModelProvider(this).get(AddProductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);

        scanBtn = (Button)root.findViewById(R.id.scan_button);
        saveBtn = (Button)root.findViewById(R.id.buttonSave);
        textViewItemName = (TextView)root.findViewById(R.id.productName);
        editTextAmount = (EditText) root.findViewById(R.id.productAmount);
        editTextExpDate = (EditText) root.findViewById(R.id.editExpirationDate);
        if (!addProductViewModel.getProductName().equals("")){
            textViewItemName.setText(addProductViewModel.getProductName());
        }

        scanBtn.setOnClickListener(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO-DO Tukaj shrani v bazo in toast

                editTextAmount.setText("");
                editTextExpDate.setText("");
                textViewItemName.setText("No Item");
            }
        });

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
            mRestClient.getIngredientByBarcode(scanContent).enqueue(new Callback<ProductDTO>() {
                @Override
                public void onResponse(Call<ProductDTO> call, Response<ProductDTO> response) {
                    if (response.isSuccessful()){
                        String name = response.body().getProduct().getProduct_name();
                        addProductViewModel.setProductName(name);
                        textViewItemName.setText(name);
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