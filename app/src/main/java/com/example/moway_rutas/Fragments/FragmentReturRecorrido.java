package com.example.moway_rutas.Fragments;

import static com.example.moway_rutas.Fragments.FragmentReturRutas.UrlImgagenDeLaRuta;
import static com.example.moway_rutas.Fragments.FragmentReturRutas.id_recorrido;
import static com.example.moway_rutas.Fragments.FragmentReturRutas.numbrRuta;
import static com.example.moway_rutas.Fragments.FragmentReturRutas.urlRecorrido;
import static com.example.moway_rutas.Fragments.FrangmentEmpresa.idEmpresa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.Client.RetrofitClient;
import com.example.moway_rutas.Pojos.EmpresaAdapter;
import com.example.moway_rutas.Pojos.Empresas_Pojo;
import com.example.moway_rutas.R;
import com.example.moway_rutas.Recorrido.AdaptadorConductores;
import com.example.moway_rutas.Recorrido.AdaptadorPuntosParada;
import com.example.moway_rutas.Recorrido.AdapterHorarios;
import com.example.moway_rutas.Recorrido.ModeloConductores;
import com.example.moway_rutas.Recorrido.ModeloHorario;
import com.example.moway_rutas.Recorrido.ModeloPuntosParada;
import com.example.moway_rutas.Recorrido.ModeloRecorrido;
import com.example.moway_rutas.interfaces.Producto_API;
import com.example.moway_rutas.interfaces.RecyclerViewClik;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentReturRecorrido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReturRecorrido extends Fragment {

    TextView txtNumeroRuta , itemclikhorarios;
    ImageView imagenUrlu;
    FloatingActionButton floatingActionButton;
    ImageButton buttonAcccionBack;

    AdaptadorConductores adaptadorConductores;
    AdaptadorPuntosParada adapterparadas;
    AdapterHorarios adapterHorarios;

    RecyclerView rvConductores,rvpuntosdeparada,rvHorarios;

    String hora_finalfestivo;



    Producto_API apiservice;
    List<ModeloConductores> listsEmpresa = new ArrayList<>();
    List<ModeloPuntosParada> listsparadas = new ArrayList<>();
    List<ModeloHorario> listsHorario = new ArrayList<>();

    ModeloRecorrido item_foreach;
    ModeloPuntosParada modeloparadas;
    ModeloHorario modelohorario;

    //valoresHora


    public FragmentReturRecorrido() {
        // Required empty public constructor
    }


    public static FragmentReturRecorrido newInstance() {

        Bundle args = new Bundle();

        FragmentReturRecorrido fragment = new FragmentReturRecorrido();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =  inflater.inflate(R.layout.fragment_retur_recorrido, container, false);

             initView(vista);
            initvalues();
            getConductores(id_recorrido);
            getparadas(id_recorrido);

            gethorarios(id_recorrido);
        return vista;
    }

    private void gethorarios(String id) {
        apiservice.gethorarios(id).enqueue(new Callback<List<ModeloHorario>>() {
            @Override
            public void onResponse(Call<List<ModeloHorario>> call, Response<List<ModeloHorario>> response) {

                if (!response.isSuccessful()) {

                    Toast.makeText(getContext(), "tenemos un error en el if", Toast.LENGTH_SHORT).show();

                } else {
                     listsHorario = response.body();
                    preadapterHorarios();
                }

            }

            @Override
            public void onFailure(Call<List<ModeloHorario>> call, Throwable t) {

            }
        });


    }

    private void preadapterHorarios() {
        rvHorarios.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterHorarios = new AdapterHorarios(listsHorario,getContext());
        rvHorarios.setAdapter(adapterHorarios);
    }

    private void getparadas(String id_recorrido) {
        apiservice.get_putos_parada(id_recorrido).enqueue(new Callback<List<ModeloPuntosParada>>() {
            @Override
            public void onResponse(Call<List<ModeloPuntosParada>> call, Response<List<ModeloPuntosParada>> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getContext(), "tenemos un error en el if", Toast.LENGTH_SHORT).show();

                } else {
                    listsparadas = response.body();
                    // Toast.makeText(getContext(), "" + listsEmpresa, Toast.LENGTH_SHORT).show();

                    preadpterparadas();
                }
            }

            @Override
            public void onFailure(Call<List<ModeloPuntosParada>> call, Throwable t) {

            }
        });
    }

    private void preadpterparadas() {
        rvpuntosdeparada.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterparadas = new AdaptadorPuntosParada(listsparadas,getContext());
        rvpuntosdeparada.setAdapter(adapterparadas);
    }

    private void initView(View v ) {
        txtNumeroRuta =(TextView) v.findViewById(R.id.cajaDeTextoNumerRuta);

        imagenUrlu = (ImageView) v.findViewById(R.id.imagneRecorridos);

        floatingActionButton = v.findViewById(R.id.floating);

        rvConductores=v.findViewById(R.id.rv_conductores);

        rvpuntosdeparada=v.findViewById(R.id.rv_puntosparada);

        rvHorarios=v.findViewById(R.id.rv_horarios);

        buttonAcccionBack=v.findViewById(R.id.buttonatras_recorridos);

        buttonAcccionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new fragment and transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                // Reemplaza lo que esté en la vista de este fragmento con otro
                transaction.replace(R.id.MainFragmt, FragmentReturRutas.newInstance());
                // Commit the transaction
                transaction.commit();
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(Intent.ACTION_VIEW, Uri.parse(urlRecorrido));
                startActivity(pass);
            }
        });



        txtNumeroRuta.setText(""+ numbrRuta);
        Glide.with(getContext()).load(UrlImgagenDeLaRuta).into(imagenUrlu);

        imagenUrlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(Intent.ACTION_VIEW, Uri.parse(urlRecorrido));
                startActivity(pass);
            }
        });
    }

    private void getConductores(String id ) {
        apiservice.getConsulta_Conductores(id).enqueue(new Callback<List<ModeloConductores>>() {
            @Override
            public void onResponse(Call<List<ModeloConductores>> call, Response<List<ModeloConductores>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "tenemos un error en el if", Toast.LENGTH_SHORT).show();
                    Log.e("ErrorResponse=> ", String.valueOf(response.code()));
                    return;
                } else {
                    listsEmpresa = response.body();
                 // Toast.makeText(getContext(), "" + listsEmpresa, Toast.LENGTH_SHORT).show();
                   preadpter();
                }
            }
            @Override
            public void onFailure(Call<List<ModeloConductores>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preadpter() {
        rvConductores.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptadorConductores = new AdaptadorConductores(listsEmpresa,getContext());
        rvConductores.setAdapter(adaptadorConductores);
    }


    private void initvalues() {
        apiservice = RetrofitClient.getApiService();
    }

}