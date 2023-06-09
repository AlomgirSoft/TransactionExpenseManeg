package com.example.nomn.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomn.Model.Category;
import com.example.nomn.Model.Transaction;
import com.example.nomn.R;
import com.example.nomn.Utils.Constants;
import com.example.nomn.Utils.Helper;
import com.example.nomn.Views.MainActivity;
import com.example.nomn.databinding.TransactionItmeBinding;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{
    private Context context;
    private RealmResults<Transaction> transactionArrayList;
    public interface  ItemClick{
        void itemClick(Transaction transaction);
    }
ItemClick itemClick;

    public TransactionAdapter(Context context, RealmResults<Transaction> transactionArrayList) {
        this.context = context;
        this.transactionArrayList = transactionArrayList;

    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.transaction_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionArrayList.get(position);
        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountName.setText(transaction.getAccount());
        holder.binding.transactionDate.setText(Helper.showDate(transaction.getDate()));
        holder.binding.category.setText(transaction.getCategory());


        Category category = Constants.getCategoryDitails(transaction.getCategory());
        holder.binding.transactionIcon.setImageResource(category.getCategoryImage());
        holder.binding.transactionIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));

        holder.binding.accountName.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction.getAccount())));


     if (transaction.getType().equals(Constants.INCOME)){
         holder.binding.transactionAmount.setTextColor(context.getColor(R.color.green));
     }else if (transaction.getType().equals(Constants.EXPENSE)){
         holder.binding.transactionAmount.setTextColor(context.getColor(R.color.orenge));
     }


     holder.itemView.setOnClickListener(view -> {
         AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
           deleteDialog.setTitle("Delete Transaction");
           deleteDialog.setMessage("are you sure delete transaction  ");
              deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      ((MainActivity)context).viewModel.deleteTransaction(transaction);
                  }
              });

           deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   deleteDialog.dismiss();
               }
           });
              deleteDialog.show();

     });







    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{
    TransactionItmeBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =TransactionItmeBinding.bind(itemView);
        }
    }
}
