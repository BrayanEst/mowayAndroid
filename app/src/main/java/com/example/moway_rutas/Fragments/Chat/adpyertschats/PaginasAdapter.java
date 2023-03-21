package com.example.moway_rutas.Fragments.Chat.adpyertschats;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.moway_rutas.Fragments.Chat.FragmentChatGlobal;
import com.example.moway_rutas.Fragments.Chat.FragmentChatporpersona;
import com.example.moway_rutas.Fragments.Chat.FragmentSolisitudes;
import com.example.moway_rutas.Fragments.Chat.FragmentUsersTheChat;
import com.example.moway_rutas.Fragments.FragmentChat;

public class PaginasAdapter extends FragmentStateAdapter {

    public PaginasAdapter(@NonNull FragmentChat fragmentActivity) {
        super(fragmentActivity);
    }

    public PaginasAdapter(Context context) {
        super((FragmentActivity) context);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentUsersTheChat();
            case 1 :
                return new FragmentChatporpersona();
            case 2 :
                return new FragmentSolisitudes();
            case 3 :
                return new FragmentChatGlobal();

            default:
                return new FragmentUsersTheChat();
        }


    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
