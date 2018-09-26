package com.cmitchell687.petlist.ui.petlist;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmitchell687.core.data.PetModel;
import com.cmitchell687.petlist.R;
import com.cmitchell687.petlist.databinding.ListItemPetBinding;
import com.cmitchell687.petlist.ui.details.PetDetailFragment;
import com.cmitchell687.petlist.ui.details.PetDiffUtil;

public class PetsAdapter extends ListAdapter<PetModel, PetsAdapter.PetViewHolder> {

    private FragmentManager fragmentManager;

    public PetsAdapter(FragmentManager fragmentManager) {
        super(new PetDiffUtil());
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ListItemPetBinding listItemPetBinding = ListItemPetBinding.inflate(inflater, parent, false);

        return new PetViewHolder(listItemPetBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder petViewHolder, int position) {
        PetModel model = getItem(position);
        petViewHolder.bind(model);
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {

        ListItemPetBinding binding;

        private PetViewHolder(@NonNull ListItemPetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final PetModel model) {
            binding.setPet(model);
            binding.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction()
                            .addToBackStack("abc")
                            .replace(R.id.content_container, PetDetailFragment.newInstance(model.getId()))
                            .commitAllowingStateLoss();
                }
            });
            binding.executePendingBindings();
        }
    }
}
