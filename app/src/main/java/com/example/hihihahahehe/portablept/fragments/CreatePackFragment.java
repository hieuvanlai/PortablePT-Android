package com.example.hihihahahehe.portablept.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.JSONModel.PackJSONModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.AddPack;
import com.example.hihihahahehe.portablept.utils.RealmHandle;
import com.example.hihihahahehe.portablept.utils.ScreenManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePackFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CreatePackFragment.class.toString();
    private static final int RESULT_LOAD_IMAGE = 1;
    private FaceBookModel faceBookModel;
    private String duration;
    private Uri imageUrl;
    private Cloudinary cloudinary;
    private InputStream inputStream;
    private String packName;
    private String type;
    @BindView(R.id.edt_pack_name)
    EditText edtPackName;
    @BindView(R.id.edt_pack_price)
    EditText edtPackPrice;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.bt_cancel)
    TextView btCancel;
    @BindView(R.id.bt_create)
    TextView btCreate;
    @BindView(R.id.tv_set_week)
    TextView tvSetWeek;
    @BindView(R.id.tv_set_type)
    TextView tvSetType;
    @BindView(R.id.iv_add_image)
    ImageView ivAddImage;
    @BindView(R.id.edt_address)
    EditText edtAddress;

    public CreatePackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_pack, container, false);
        setupUI(view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        setOnClickItem();
    }

    private void setOnClickItem() {
        btCancel.setOnClickListener(this);
        tvSetWeek.setOnClickListener(this);
        btCreate.setOnClickListener(this);
        ivAddImage.setOnClickListener(this);
        tvSetType.setOnClickListener(this);
    }

    private void showChooseTypeDialog() {
        AlertDialog.Builder chooseType = new AlertDialog.Builder(getContext());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.add("Fitness");
        arrayAdapter.add("Zumba");
        arrayAdapter.add("Kickfit");

        chooseType.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvSetType.setText(arrayAdapter.getItem(i) + " ▾");
                type = arrayAdapter.getItem(i);
            }
        });
        chooseType.show();
    }

    private void showChooseWeekDialog() {
        AlertDialog.Builder chooseWeek = new AlertDialog.Builder(getContext());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.add("1 tuần");
        arrayAdapter.add("2 tuần");
        arrayAdapter.add("3 tuần");
        arrayAdapter.add("4 tuần");

        chooseWeek.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvSetWeek.setText(arrayAdapter.getItem(i) + " ▾");
                duration = arrayAdapter.getItem(i);
            }
        });
        chooseWeek.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            imageUrl = data.getData();
            ivAddImage.setImageURI(imageUrl);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                ScreenManager.onBackPressed(getActivity().getSupportFragmentManager());
                break;
            case R.id.tv_set_week:
                showChooseWeekDialog();
                break;
            case R.id.bt_create:
                new UploadService().execute();
                final AddPack addPack = RetrofitFactory.getInstance().create(AddPack.class);

                faceBookModel = RealmHandle.getData();
                String phoneNumber = edtPhone.getText().toString();
                packName = String.valueOf(edtPackName.getText());
                String coach = faceBookModel.getLast_Name() + " " + faceBookModel.getFirst_Name();
                String price = edtPackPrice.getText().toString() + " VND";
                String address = edtAddress.getText().toString();
                String imageUrl = "https://res.cloudinary.com/dekbhfa6g/image/upload/" + edtPackName.getText().toString() + ".jpg";

                PackJSONModel model = new PackJSONModel(phoneNumber, type, packName, coach, price, duration, imageUrl, address);

                addPack.addPack(model).enqueue(new Callback<PackJSONModel>() {
                    @Override
                    public void onResponse(Call<PackJSONModel> call, Response<PackJSONModel> response) {
                        Toast.makeText(getContext(), "Đã thêm gói tập mới !", Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack();
                    }

                    @Override
                    public void onFailure(Call<PackJSONModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Không thể tạo, vui lòng kiểm tra lại kết nối !", Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack();
                    }
                });
                break;
            case R.id.iv_add_image:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, RESULT_LOAD_IMAGE);
                break;
            case R.id.tv_set_type:
                showChooseTypeDialog();
                break;
        }
    }

    public class UploadService extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            uploadImage();
            return null;
        }
    }

    private void uploadImage() {
        cloudinary = new Cloudinary("cloudinary://764643933774622:eO4wWI6IeBnROiOvsR3auqbyDQg@dekbhfa6g");
        String imageName = com.example.hihihahahehe.portablept.utils.Utils.removeAccent(edtPackName.getText().toString());
        cloudinary.url().generate(imageName + ".jpg");
        try {
            inputStream = getActivity().getContentResolver().openInputStream(imageUrl);
            cloudinary.uploader().upload(inputStream, ObjectUtils.asMap("public_id", imageName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}