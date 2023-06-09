package com.example.nomn.Views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.nomn.Adapter.AccountAdapter;
import com.example.nomn.Adapter.CategoryAdapter;
import com.example.nomn.Model.Account;
import com.example.nomn.Model.Category;
import com.example.nomn.Model.Transaction;
import com.example.nomn.R;
import com.example.nomn.Utils.Constants;
import com.example.nomn.Utils.Helper;
import com.example.nomn.databinding.FragmentAddTransactionBinding;
import com.example.nomn.databinding.ItemCategoryListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddTransactionBinding binding;
  Transaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransactionBinding.inflate(inflater,container,false);
        transaction= new Transaction();
        binding.incomeBtn.setOnClickListener(v->{
             binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.fragment_top_textback_rount_income_select));
             binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.defalecolor));
             binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
             binding.incomeBtn.setTextColor(getContext().getColor(R.color.green));

             transaction.setType(Constants.INCOME);

        });


        binding.expenseBtn.setOnClickListener(v->{
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.defalecolor));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.fragment_top_textback_rount_expanse_select));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.orenge));
            transaction.setType(Constants.EXPENSE);


        });



        binding.edDateTime.setOnClickListener( v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                Calendar calendar =Calendar.getInstance();

                calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
                calendar.set(Calendar.MONDAY,datePicker.getMonth());
                calendar.set(Calendar.YEAR,datePicker.getYear());

              //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                String date= Helper.showDate(calendar.getTime());

                binding.edDateTime.setText(date);
                transaction.setDate(calendar.getTime());
                transaction.setId(calendar.getTime().getTime());
            });
            datePickerDialog.show();

        });
   // Category eidText OnClick
   binding.edCategory.setOnClickListener(v->{
       ItemCategoryListBinding binding1 =ItemCategoryListBinding.inflate(inflater);
       AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
       alertDialog.setView(binding1.getRoot());



       CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickLisener() {
           @Override
           public void categoryClick(Category category) {
               binding.edCategory.setText(category.getCategoryName());
               transaction.setCategory(category.getCategoryName());
               alertDialog.dismiss();
           }
       });
       binding1.categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
       binding1.categoryRecyclerView.setAdapter(categoryAdapter);


          alertDialog.show();


   });

   binding.edAccount.setOnClickListener(v->{
       ItemCategoryListBinding binding1 =ItemCategoryListBinding.inflate(inflater);
       AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
       alertDialog.setView(binding1.getRoot());


       ArrayList<Account>accountArrayList=new ArrayList<>();
       accountArrayList.add(new Account(0,"Bank"));
       accountArrayList.add(new Account(0,"Cash"));
       accountArrayList.add(new Account(0,"PayTM"));
       accountArrayList.add(new Account(0,"Cart"));
       accountArrayList.add(new Account(0,"Other"));

       AccountAdapter accountAdapter = new AccountAdapter(getContext(), accountArrayList, new AccountAdapter.AccountItemClickLisener() {
           @Override
           public void accountItemClick(Account account) {
               binding.edAccount.setText(account.getAccountName());
               transaction.setAccount(account.getAccountName());
               alertDialog.dismiss();
           }
       });
       binding1.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       binding1.categoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
       binding1.categoryRecyclerView.setAdapter(accountAdapter);

       alertDialog.show();

   });


  binding.saveTransactionBtn.setOnClickListener(view -> {
        double amount=Double.parseDouble(binding.edAmount.getText().toString());
        String note = binding.edNote.getText().toString();

        if (transaction.getType().equals(Constants.EXPENSE)){
            transaction.setAmount(amount*-1);
        }else {
            transaction.setAmount(amount);
        }

      transaction.setNote(note);

      ((MainActivity)getActivity()).viewModel.addTransaction(transaction);
      ((MainActivity)getActivity()).getTransactions();
      dismiss();



  });

        return binding.getRoot();
    }
}