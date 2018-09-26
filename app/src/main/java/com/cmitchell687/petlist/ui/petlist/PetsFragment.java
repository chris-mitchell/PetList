package com.cmitchell687.petlist.ui.petlist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cmitchell687.core.data.PetModel;
import com.cmitchell687.petlist.R;
import com.cmitchell687.petlist.databinding.FragmentPetsBinding;
import com.cmitchell687.petlist.ui.add.AddPetFragment;
import com.cmitchell687.petlist.utils.InjectorUtils;

import java.util.List;

public class PetsFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    public static PetsFragment newInstance() {
        return new PetsFragment();
    }

    private PetsViewModel petsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final FragmentPetsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pets,
                container, false);

        View view = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.pets_list_title);

        PetsViewModelFactory factory = InjectorUtils.providePetsViewModelFactory(getContext());
        petsViewModel = ViewModelProviders.of(this, factory)
                .get(PetsViewModel.class);

        final PetsAdapter petsAdapter = new PetsAdapter(getFragmentManager());

        RecyclerView recyclerView = view.findViewById(R.id.pets_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(petsAdapter);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "petAddFragment";
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(tag)
                        .replace(R.id.content_container, AddPetFragment.newInstance(), tag)
                        .commit();
            }
        });

        petsViewModel.getPets().observe(this, new Observer<List<PetModel>>() {
            @Override
            public void onChanged(@Nullable List<PetModel> petModels) {
                if (petModels != null && petModels.size() > 0) {
                    petsAdapter.submitList(petModels);
                }
                binding.setViewModel(petsViewModel);
            }
        });
        binding.setViewModel(petsViewModel);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pets_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        petsViewModel.updateSearchTerm(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        petsViewModel.updateSearchTerm(s);
        return true;
    }

    @Override
    public boolean onClose() {
        petsViewModel.clearSearchTerm();
        return false;
    }
}
