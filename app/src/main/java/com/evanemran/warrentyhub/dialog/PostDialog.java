package com.evanemran.warrentyhub.dialog;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.evanemran.warrentyhub.R;
import com.evanemran.warrentyhub.adapters.CategorySpinnerAdapter;
import com.evanemran.warrentyhub.adapters.ShopSpinnerAdapter;
import com.evanemran.warrentyhub.listeners.PostListener;
import com.evanemran.warrentyhub.models.CategoryData;
import com.evanemran.warrentyhub.models.ImageUri;
import com.evanemran.warrentyhub.models.PostData;
import com.evanemran.warrentyhub.models.ShopData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostDialog extends DialogFragment {

    private PostListener listener;
    EditText editText_prodName, editText_shopAddress, editText_shopPhone, editText_invoiceNo, editText_warrantyDate,
            editText_prodDetails;
    Spinner spinnerCategory,spinnerShopName;
    Button button_post;
    ImageView button_product_upload, button_invoice_upload;
    ImageView imageView_selected;
    List<ImageUri> filePathList = new ArrayList<>();
    List<CategoryData> categoryDataList = new ArrayList<>();
    List<ShopData> shopDataList = new ArrayList<>();
    Uri filePath;
    PostData postData = new PostData();


    public PostDialog(PostListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDataList.add(new CategoryData("1", "Select Category"));
        categoryDataList.add(new CategoryData("1", "Appliances"));
        categoryDataList.add(new CategoryData("1", "Electronics"));
        categoryDataList.add(new CategoryData("1", "Mobiles"));
        categoryDataList.add(new CategoryData("1", "Accessories"));

        shopDataList.add(new ShopData("1", "Select Shop", "21", "Motalib Plaza"));
        shopDataList.add(new ShopData("1", "ABC Shop", "21", "Motalib Plaza"));
        shopDataList.add(new ShopData("1", "Star Shop", "07", "Vungchung Plaza"));
        shopDataList.add(new ShopData("1", "Ryans Shop", "225", "IDB Plaza"));
        shopDataList.add(new ShopData("1", "Bhai bhai Shop", "21", "New Eastern Plaza"));
        shopDataList.add(new ShopData("1", "Boom boom Shop", "12", "ODC Plaza"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText_prodName = view.findViewById(R.id.editText_prodName);
        editText_shopAddress = view.findViewById(R.id.editText_shopAddress);
        editText_shopPhone = view.findViewById(R.id.editText_shopPhone);
        editText_invoiceNo = view.findViewById(R.id.editText_invoiceNo);
        editText_warrantyDate = view.findViewById(R.id.editText_warrantyDate);
        editText_prodDetails = view.findViewById(R.id.editText_prodDetails);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerShopName = view.findViewById(R.id.spinnerShopName);
        button_product_upload = view.findViewById(R.id.button_product_upload);
        button_invoice_upload = view.findViewById(R.id.button_invoice_upload);
        button_post = view.findViewById(R.id.button_post);

        ArrayAdapter<CategoryData> categoryDataArrayAdapter = new ArrayAdapter<CategoryData>(getContext(),
                android.R.layout.simple_spinner_item, categoryDataList);
        categoryDataArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryDataArrayAdapter);

        ShopSpinnerAdapter shopSpinnerAdapter = new ShopSpinnerAdapter(getContext(), shopDataList);
        spinnerShopName.setAdapter(shopSpinnerAdapter);

        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(getContext(), categoryDataList);
        spinnerCategory.setAdapter(categorySpinnerAdapter);


        button_product_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage(101);
            }
        });
        button_invoice_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage(102);
            }
        });



        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prodName = editText_prodName.getText().toString();
                CategoryData categoryData = (CategoryData) spinnerCategory.getSelectedItem();
                ShopData shopData = (ShopData) spinnerShopName.getSelectedItem();
                String invoiceNo = editText_invoiceNo.getText().toString();
                String warrantyDate = editText_warrantyDate.getText().toString();
                String remarks = editText_prodDetails.getText().toString();

                if (isValidData()){
                    postData.setPostProductName(prodName);
                    postData.setCategoryData(categoryData);
                    postData.setShopData(shopData);
                    postData.setInVoiceNo(invoiceNo);
                    postData.setWarrantyDate(warrantyDate);
                    postData.setPostBody(remarks);

                    listener.onPostClicked(postData, filePathList);
                    dismiss();
                }
                else {
                    Toast.makeText(getContext(), "Please Fill up all fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isValidData() {
        return true;
    }

    private void SelectImage(int code)
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == 101
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            filePathList.add(new ImageUri(requestCode, filePath));
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContext().getContentResolver(),
                                filePath);
                button_product_upload.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

        else if (requestCode == 102
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            filePathList.add(new ImageUri(requestCode, filePath));
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContext().getContentResolver(),
                                filePath);
                button_invoice_upload.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }
    }

}
