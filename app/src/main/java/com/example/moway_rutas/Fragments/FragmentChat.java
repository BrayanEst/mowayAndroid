package com.example.moway_rutas.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.moway_rutas.Fragments.Chat.adpyertschats.PaginasAdapter;
import com.example.moway_rutas.Fragments.Chat.modelos.Estado;
import com.example.moway_rutas.Fragments.Chat.modelos.UsersChat;
import com.example.moway_rutas.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FragmentChat extends Fragment {

     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference ref_user = database.getReference("Users").child(user.getUid());

     DatabaseReference ref_solicitudCound = database.getReference("Contador").child(user.getUid());


     DatabaseReference ref_estado = database.getReference("Estado").child(user.getUid());


    private BottomNavigationView bottomNavigationView;
    Button button;

    public FragmentChat() {
        // Required empty public constructor
    }

    public static FragmentChat newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentChat fragment = new FragmentChat();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_chat, container, false);
        referencer(vista);
        userunico();


        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        estadoUsuario("conectado");
    }

    @Override
    public void onPause() {
        super.onPause();
        estadoUsuario("Desconectado");
        dameultimafecha();
    }

    private void dameultimafecha() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        ref_estado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref_estado.child("fecha").setValue(dateFormat.format(c.getTime()));
                ref_estado.child("hora").setValue(timeFormat.format(c.getTime()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void estadoUsuario(String estado) {
        ref_estado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Estado est = new Estado(estado , "","","");
                ref_estado.setValue(est);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void userunico() {
        ref_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    UsersChat uu = new UsersChat(
                        user.getUid(),
                        user.getDisplayName(),
                        user.getEmail(),
                        user.getPhotoUrl().toString(),
                        "desconectado",
                         "27/06/2022",
                            "12:18",
                            0,
                            0);
                    ref_user.setValue(uu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void referencer(View vista) {
        ViewPager2 viewPager2 = vista.findViewById(R.id.viewpager2);
        viewPager2.setAdapter(new PaginasAdapter(getContext()));
        TabLayout tabLayout = vista.findViewById(R.id.tabLayoud);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText("users");
                        tab.setIcon(R.drawable.ic_usuarios);
                        break;
                    }
                    case 3:{
                        tab.setText("Chat globlal");
                        tab.setIcon(R.drawable.ic_mis_solicitudes);
                        break;
                    }
                    case 2:{
                        tab.setText("Solicitudes");
                        tab.setIcon(R.drawable.ic_add_peple);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getActivity().getApplicationContext(), com.firebase.ui.auth.R.color.colorAccent)
                        );


                        ref_solicitudCound.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    Integer val = snapshot.getValue(Integer.class);
                                    badgeDrawable.setVisible(true);
                                    if (val.equals("0")){
                                        badgeDrawable.setVisible(false);
                                    }else {
                                        badgeDrawable.setNumber(val);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        break;
                    }
                    case 1:{
                        tab.setText("Chat");
                        tab.setIcon(R.drawable.ic_chat_por_persona);
                        break;
                    }


                    }
                }
        });
        tabLayoutMediator.attach();
        //desaparece notificaciones
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);

                if (position == 2 ){
                    countacero();
                }
            }
        });
    }

    private void countacero() {
        ref_solicitudCound.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ref_solicitudCound.setValue(0);
                    Toast.makeText(getContext(), "Count badg a 0", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}