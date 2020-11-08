package com.example.dragonhack.ui.addProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dragonhack.api.products.RestApi;
import com.example.dragonhack.api.products.ServiceGenerator;
import com.example.dragonhack.database.entity.ProductDetails;
import com.example.dragonhack.models.dto.ProductDTO;
import com.example.dragonhack.ui.addProduct.supportingClasses.IntentIntegrator;
import com.example.dragonhack.ui.addProduct.supportingClasses.IntentResult;
import com.example.dragonhack.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductFragment extends Fragment implements View.OnClickListener {

    private AddProductViewModel addProductViewModel;
    private RestApi mRestClient = ServiceGenerator.createService(RestApi.class);

    private Button scanBtn, saveBtn;
    private EditText textViewItemName;
    private EditText editTextExpDate;
    private String keywords;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addProductViewModel = new ViewModelProvider(this).get(AddProductViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);

        scanBtn = (Button)root.findViewById(R.id.scan_button);
        saveBtn = (Button)root.findViewById(R.id.buttonSave);
        textViewItemName = (EditText)root.findViewById(R.id.productName);
        editTextExpDate = (EditText) root.findViewById(R.id.editExpirationDate);
        if (!addProductViewModel.getProductName().equals("")){
            textViewItemName.setText(addProductViewModel.getProductName());
        }

        scanBtn.setOnClickListener(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO-DO Tukaj shrani v bazo in toast
                String date = editTextExpDate.getText().toString();
                String [] arr = date.split("[.]");
                if (arr[0].length() < 2){
                    arr[0] = "0" + arr[0];
                }
                if (arr[1].length() < 2){
                    arr[1] = "0" + arr[1];
                }
                String dateFinal = arr[2] + "-" + arr[1] + "-" + arr[0];
                ProductDetails productDetails = new ProductDetails(textViewItemName.getText().toString(), dateFinal);
                keywords = addProductViewModel.getKeywords().substring(1, addProductViewModel.getKeywords().length()-2);
                productDetails.setKeywords(keywords);
                addProductViewModel.insertRec(productDetails);
                Toast.makeText(getActivity(), "Product added!", Toast.LENGTH_SHORT).show();
                editTextExpDate.setText("");
                textViewItemName.setText("No Item");
                addProductViewModel.setKeywords("");
                addProductViewModel.setProductName("");
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
            String name = scanContent.split("-")[0];

            String buf1 = "";
            if (scanContent.split("-").length > 1) {
                String edate = scanContent.split("-")[1];
                StringBuffer buf = new StringBuffer(edate);
                buf.insert(2, '.');
                buf.insert(5, '.');
                buf.insert(6, '2');
                buf1 = buf.substring(0, buf.length()-1);
            }
            final String newDate = buf1;
            mRestClient.getIngredientByBarcode(name).enqueue(new Callback<ProductDTO>() {
                @Override
                public void onResponse(Call<ProductDTO> call, Response<ProductDTO> response) {
                    if(!response.body().getStatus_verbose().equals("product found")){
                        Toast.makeText(getActivity(), "The scanned product was not found, you can still enter the name and expiration date by hand and save it.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (response.isSuccessful()){
                        ArrayList<String> keywordi = response.body().getProduct().get_keywords();
                        addProductViewModel.setKeywords(keywordi.toString());
                        String name = response.body().getProduct().getProduct_name();
                        addProductViewModel.setProductName(name);
                        textViewItemName.setText(name);
                        if (!newDate.equals(""))
                            editTextExpDate.setText(newDate);
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