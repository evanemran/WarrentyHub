package com.evanemran.warrentyhub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.evanemran.warrentyhub.R;
import com.evanemran.warrentyhub.adapters.HomePostAdapter;
import com.evanemran.warrentyhub.listeners.ClickListener;
import com.evanemran.warrentyhub.models.PostData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {
    View view;
    RecyclerView recycler_home;
    DatabaseReference databaseReference;
    List<PostData> postDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        recycler_home = view.findViewById(R.id.recycler_home);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postDataList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    PostData post = postSnapshot.getValue(PostData.class);
                    postDataList.add(post);
                }

                Collections.reverse(postDataList);

                recycler_home.setHasFixedSize(true);
                recycler_home.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                HomePostAdapter adapter = new HomePostAdapter(getContext(), postDataList, clickListener);
                recycler_home.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private final ClickListener<PostData> clickListener = new ClickListener<PostData>() {
        @Override
        public void onClicked(PostData data) {
            Toast.makeText(getContext(), data.getPostedBy(), Toast.LENGTH_SHORT).show();
        }
    };
}
