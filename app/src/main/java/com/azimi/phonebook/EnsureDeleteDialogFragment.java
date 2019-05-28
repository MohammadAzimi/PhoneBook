package com.azimi.phonebook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EnsureDeleteDialogFragment extends BottomSheetDialogFragment {

    private OnDelete mOnDelete;

    public static EnsureDeleteDialogFragment newInstance() {
        return new EnsureDeleteDialogFragment();
    }

    public EnsureDeleteDialogFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnDelete = (OnDelete) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnDelete = null;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_delete, container, false);
        Button btnDelete = view.findViewById(R.id.btn_delete);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDelete != null) {
                    mOnDelete.delete();
                }
            }
        });
        return view;
    }

    public interface OnDelete {
        void delete();
    }
}
