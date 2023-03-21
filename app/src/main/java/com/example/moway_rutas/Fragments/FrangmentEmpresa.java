package com.example.moway_rutas.Fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moway_rutas.Pojos.EmpresaAdapter;
import com.example.moway_rutas.Pojos.Empresas_Pojo;
import com.example.moway_rutas.R;
import com.example.moway_rutas.interfaces.Producto_API;
import com.example.moway_rutas.interfaces.RecyclerViewClik;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FrangmentEmpresa extends Fragment implements RecyclerViewClik {

    RecyclerView recyclerEmpesas;
    List<Empresas_Pojo> listsEmpresa = new ArrayList<>();
    EmpresaAdapter adapter;
    Empresas_Pojo itemDetail;

    ProgressBar progressBar;

    public static String idEmpresa;
    public static String nameEmpresaStatic;


    public FrangmentEmpresa() {
        // Required empty public constructor
    }

    public static FrangmentEmpresa newInstance() {

        Bundle args = new Bundle();

        FrangmentEmpresa fragment = new FrangmentEmpresa();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        OnBackPressedCallback onBackPressedCallback;
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_frangment_empresa, container, false);

        progressBar = vista.findViewById(R.id.prgresbarEmpresa);

        recyclerEmpesas = (RecyclerView) vista.findViewById(R.id.recyclerFragmentEmpresa);
        recyclerEmpesas.setLayoutManager(new LinearLayoutManager(getContext()));
        //Iniciarelemtos();
        getEmpresa();
        return vista;
    }

    private void getEmpresa() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://appmoway.herokuapp.com/api/")//Url api BASE
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Producto_API productoAPI = retrofit.create(Producto_API.class);
        Call<List<Empresas_Pojo>> call = productoAPI.getEmpresa();//llamo al metodo con el nombre que le di en la interfaz
        call.enqueue(new Callback<List<Empresas_Pojo>>() {
            @Override
            public void onResponse(Call<List<Empresas_Pojo>> call, Response<List<Empresas_Pojo>> response) {

                recyclerEmpesas.setLayoutManager(new LinearLayoutManager(getContext()));

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "tenemos un error en el if", Toast.LENGTH_SHORT).show();
                    Log.e("ErrorResponse=> ", String.valueOf(response.code()));
                    return;
                } else {
                    listsEmpresa = response.body();
                    //Toast.makeText(Empresa.this, ""+listsEmpresa, Toast.LENGTH_LONG).show();
                    //adapterempesa= new EmpresaAdapter(this, android.R.layout.simple_list_item_1,listsEmpresa);
                    //adapter = new ArrayAdapter<>(Empresa.this, android.R.layout.simple_list_item_1,listsEmpresa);
                    preAdapter();
                }
            }
            @Override
            public void onFailure(Call<List<Empresas_Pojo>> call, Throwable t) {

                Toast.makeText(getContext(), "Tienes un fallo en la consulta", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Iniciarelemtos() {
        for (int i = 0 ;i <=0 ;i++) {
            listsEmpresa.add(new Empresas_Pojo("1", "Translivertad" ,"data:image/jpeg;base64,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" ));
            listsEmpresa.add(new Empresas_Pojo("2", "Transpubenza " , "data:image/png;base64,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"));
            listsEmpresa.add(new Empresas_Pojo("3", "Transtambo" , "data:image/jpeg;base64,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"));
            listsEmpresa.add(new Empresas_Pojo("4", "Sotracauca" , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfL-ggb1Ua0-_uoVp60MSh_pQ1StMYUiSHvtxSKUu7sKtpsCq-t7cdKDe4ulCPVunqp_0&usqp=CAU"));
        }
        preAdapter();
    }

    private void preAdapter() {
        adapter = new EmpresaAdapter(listsEmpresa,getContext(),this);
        recyclerEmpesas.setAdapter(adapter);
        int prueba = 1;

        if (prueba == 1){
            recyclerEmpesas.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            recyclerEmpesas.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void reaccionItemClick(int position) {
        itemDetail = listsEmpresa.get(position);
        idEmpresa = "";
        idEmpresa = itemDetail.getId();
        nameEmpresaStatic = "";
        nameEmpresaStatic = itemDetail.getNombre_empresa();
       // Toast.makeText(getContext(), ""+idEmpresa, Toast.LENGTH_SHORT).show();
        remplazofragmento();
    }


    public void remplazofragmento() {
        // Create new fragment and transaction
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        // Reemplaza lo que esté en la vista de este fragmento con otro
        transaction.replace(R.id.MainFragmt, FragmentReturRutas.newInstance());
        // Commit the transaction
        transaction.commit();
    }
}