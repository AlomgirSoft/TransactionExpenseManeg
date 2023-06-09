package com.example.nomn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomn.Model.Account;
import com.example.nomn.R;
import com.example.nomn.databinding.RowAccountsBinding;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>{
    private Context context;
    private ArrayList<Account>accountArrayList;


    public interface AccountItemClickLisener{
        void  accountItemClick(Account account);
    }

    AccountItemClickLisener accountItemClickLisener;
    public AccountAdapter(Context context, ArrayList<Account> accountArrayList,    AccountItemClickLisener accountItemClickLisener) {
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.accountItemClickLisener=accountItemClickLisener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.row_accounts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
         Account account = accountArrayList.get(position);
         holder.binding.accountTv.setText(account.getAccountName());


         holder.itemView.setOnClickListener(v->{

             accountItemClickLisener.accountItemClick(account);
         });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{
          RowAccountsBinding binding;
    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);

        binding=RowAccountsBinding.bind(itemView);
    }
}


}
