package com.example.nomn.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.nomn.Adapter.TransactionAdapter;
import com.example.nomn.Model.Category;
import com.example.nomn.Model.Transaction;
import com.example.nomn.R;
import com.example.nomn.Utils.Constants;
import com.example.nomn.Utils.Helper;
import com.example.nomn.ViewModel.MainViewModel;
import com.example.nomn.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

   public ActivityMainBinding binding;

    Calendar calendar;

   public MainViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel= new ViewModelProvider(this).get(MainViewModel.class);

        Constants.setCategorys();

        setSupportActionBar(binding.topToolbar);
        getSupportActionBar().setTitle("Transaction");
        calendar=Calendar.getInstance();
        updateDate();


        binding.nextIconImageNextDate.setOnClickListener(view -> {

            if (Constants.SELECTED_TAB==Constants.DAILY){
                calendar.add(Calendar.DATE,1);
            } else if (Constants.SELECTED_TAB==Constants.MONTHLY) {
                calendar.add(Calendar.MONDAY,1);
            }

            updateDate();
        });

        binding.backIconImagePrivesDate.setOnClickListener(view -> {

            if (Constants.SELECTED_TAB==Constants.DAILY){
                calendar.add(Calendar.DATE,-1);
            } else if (Constants.SELECTED_TAB==Constants.MONTHLY) {
                calendar.add(Calendar.MONDAY,-1);
            }

            updateDate();
        });

        binding.floatingActionButton.setOnClickListener(v->{
            new AddTransactionFragment().show(getSupportFragmentManager(),null);

        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("monthly")){
                    Constants.SELECTED_TAB =1;
                    updateDate();
                } else if (tab.getText().equals("daily")) {
                    Constants.SELECTED_TAB=0;
                    updateDate();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


     //   ArrayList<Transaction>transactionArrayList = new ArrayList<>();

    viewModel.mutableLiveData.observe(this, new Observer<RealmResults<Transaction>>() {
        @Override
        public void onChanged(RealmResults<Transaction> transactions) {
            TransactionAdapter transactionAdapter = new TransactionAdapter(MainActivity.this, transactions);
            binding.recyclerView.setAdapter(transactionAdapter);
                    if (transactions.size()>0) {
                        binding.emptyStates.setVisibility(View.GONE);

                    }else {
                        binding.emptyStates.setVisibility(View.VISIBLE);
                    }
        }
    });

    viewModel.totalIncome.observe(this, new Observer<Double>() {
        @Override
        public void onChanged(Double aDouble) {
            binding.incomeResultTv2.setText(String.valueOf(aDouble));

        }
    });

    viewModel.totalExpense.observe(this, new Observer<Double>() {
        @Override
        public void onChanged(Double aDouble) {
            binding.expenseResultTv2.setText(String.valueOf(aDouble));
        }
    });

    viewModel.totalAmount.observe(this, new Observer<Double>() {
        @Override
        public void onChanged(Double aDouble) {
            binding.totalResultTv2.setText(String.valueOf(aDouble));
        }
    });
    viewModel.getTransaction(calendar);



    }

    public void getTransactions(){
        viewModel.getTransaction(calendar);
    }


    void updateDate(){
    if (Constants.SELECTED_TAB==Constants.DAILY){
        binding.carruntDate.setText(Helper.showDate(calendar.getTime()));
    }else if (Constants.SELECTED_TAB==Constants.MONTHLY){
        binding.carruntDate.setText(Helper.showDateMathly(calendar.getTime()));
    }


        viewModel.getTransaction(calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}