package com.example.moway_rutas.Fragments;

import static com.example.moway_rutas.Fragments.FrangmentEmpresa.idEmpresa;
import static com.example.moway_rutas.Fragments.FrangmentEmpresa.nameEmpresaStatic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moway_rutas.R;
import com.example.moway_rutas.Rutas.RutasAdapter;
import com.example.moway_rutas.Rutas.RutasModelo;
import com.example.moway_rutas.interfaces.Producto_API;
import com.example.moway_rutas.interfaces.RecyclerViewClik;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentReturRutas extends Fragment implements RecyclerViewClik {

    RecyclerView recyclerView;
    RutasAdapter adapterRutas;
    TextView textViewTitlle;
    RutasModelo itemdetail;
    ImageButton backBotton;

    ProgressBar progressBar;

    List<RutasModelo> listsrutas = new ArrayList<>();

    public  String baseurl;
    public  String nombreEmpresaTitle;

    public static String numbrRuta;
    public static String UrlImgagenDeLaRuta;
    public static String urlRecorrido;
    public static String id_recorrido;

    public FragmentReturRutas() {
        // Required empty public constructor
    }
    public static FragmentReturRutas newInstance() {

        Bundle args = new Bundle();

        FragmentReturRutas fragment = new FragmentReturRutas();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistas = inflater.inflate(R.layout.fragment_retur_rutas, container, false);

        backBotton = vistas.findViewById(R.id.buttonatras);

        progressBar = vistas.findViewById(R.id.prgresbarrutas);


        botonback();
        referenciar(vistas);
       /*METODOS*/
        igualarvariables();
        comsumoRetofit();
        //pruebarecycler();


        return vistas;
    }

    private void botonback() {
        backBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Create new fragment and transaction
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setReorderingAllowed(true);
                    // Reemplaza lo que esté en la vista de este fragmento con otro
                    transaction.replace(R.id.MainFragmt, FrangmentEmpresa.newInstance());
                    // Commit the transaction
                    transaction.commit();
            }
        });
    }

    private void referenciar(View vistas) {
        recyclerView = vistas.findViewById(R.id.recyclerFragmentRutas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textViewTitlle = vistas.findViewById(R.id.textviewRutas);
    }

    private void pruebarecycler() {
        for (int i = 0 ;i <=0 ;i++) {
            listsrutas.add(new RutasModelo( "1" , "https://www.google.com/maps/d/u/0/viewer?mid=12b_iVg6HJiX4j-9Kr6U0LufmDGdqAKs&ll=2.46052043975323%2C-76.6096990454948&z=12","https://map.viamichelin.com/map/carte?map=viamichelin&z=10&lat=2.44472&lon=-76.61473&width=550&height=382&format=png&version=latest&layer=background&debug_pattern=.*","terraplaza-campanario","1"));
            listsrutas.add(new RutasModelo( "2" , "https://www.google.com/maps/d/u/0/viewer?mid=12b_iVg6HJiX4j-9Kr6U0LufmDGdqAKs&ll=2.46052043975323%2C-76.6096990454948&z=12","https://map.viamichelin.com/map/carte?map=viamichelin&z=10&lat=2.44472&lon=-76.61473&width=550&height=382&format=png&version=latest&layer=background&debug_pattern=.*","terraplaza-campanario","2"));
            listsrutas.add(new RutasModelo( "3" , "https://www.google.com/maps/d/u/0/viewer?mid=12b_iVg6HJiX4j-9Kr6U0LufmDGdqAKs&ll=2.46052043975323%2C-76.6096990454948&z=12","https://map.viamichelin.com/map/carte?map=viamichelin&z=10&lat=2.44472&lon=-76.61473&width=550&height=382&format=png&version=latest&layer=background&debug_pattern=.*","terraplaza-campanario","3"));
            listsrutas.add(new RutasModelo( "4" , "https://www.google.com/maps/d/u/0/viewer?mid=12b_iVg6HJiX4j-9Kr6U0LufmDGdqAKs&ll=2.46052043975323%2C-76.6096990454948&z=12","https://map.viamichelin.com/map/carte?map=viamichelin&z=10&lat=2.44472&lon=-76.61473&width=550&height=382&format=png&version=latest&layer=background&debug_pattern=.*","terraplaza-campanario","4"));
        }
        preAdapter();
    }

    private void preAdapter() {
        adapterRutas = new RutasAdapter(listsrutas,getContext(),this);
        recyclerView.setAdapter(adapterRutas);

        int prueba = 1;
        if (prueba == 1){
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void igualarvariables() {
        baseurl = idEmpresa;
        nombreEmpresaTitle = nameEmpresaStatic;
        textViewTitlle.setText(""+nameEmpresaStatic);
        //Toast.makeText(getContext(), ""+idEmpresa, Toast.LENGTH_SHORT).show();
    }

    private void comsumoRetofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://appmoway.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Producto_API producto_api = retrofit.create(Producto_API.class);
        Call<List<RutasModelo>> call = producto_api.getRecorrido_consulta(baseurl);//LLAMO AL METODO EN EL CUAL ACOMULO LA DORECION QUE FALTA DE MI API
        call.enqueue(new Callback<List<RutasModelo>>() {
            @Override
            public void onResponse(Call<List<RutasModelo>> call, Response<List<RutasModelo>> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "tenesmo un error en el if ", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    listsrutas = response.body();
                    //Toast.makeText(RutasReturn.this, ""+rutasModeloslist, Toast.LENGTH_SHORT).show();
                    //metodo para referecnciar que va al recycler
                    preAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<RutasModelo>> call, Throwable t) {
                Toast.makeText(getContext(), "Faield de los duros", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void reaccionItemClick(int position) {
        itemdetail = listsrutas.get(position);

        numbrRuta = itemdetail.getNumero_ruta();
        UrlImgagenDeLaRuta = itemdetail.getImagen_recorrido();
        urlRecorrido = itemdetail.getUrl_recorrido();
        id_recorrido = itemdetail.getId();

        //Toast.makeText(getContext(), ""+numbrRuta, Toast.LENGTH_SHORT).show();
        remplazofragmento();
    }

    private void remplazofragmento() {
        // Create new fragment and transaction
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        // Reemplaza lo que esté en la vista de este fragmento con otro
        transaction.replace(R.id.MainFragmt, FragmentReturRecorrido.newInstance());
        // Commit the transaction
        transaction.commit();
    }
}