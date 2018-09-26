package com.cmitchell687.petlist.ui.details;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.cmitchell687.core.data.PetModel;

public class PetDiffUtil extends DiffUtil.ItemCallback<PetModel> {
    @Override
    public boolean areItemsTheSame(@NonNull PetModel x, @NonNull PetModel y) {
        return x.getId() == y.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull PetModel x, @NonNull PetModel y) {
        return x.hashCode() == y.hashCode();
    }
}
