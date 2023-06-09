package com.example.nomn.Utils;

import com.example.nomn.Model.Category;
import com.example.nomn.R;

import java.util.ArrayList;

public class Constants {

    public static String INCOME="INCOME";
    public static String EXPENSE="EXPENSE";

    public static int DAILY=0;
    public static int MONTHLY=1;
    public static int CALENDAR=2;
    public static int SUMMARY=3;
    public static int NOTE=4;

    public static int SELECTED_TAB=0;

  public static ArrayList<Category> categories;

    public static void setCategorys(){
              categories=new ArrayList<>();

        categories.add(new Category("Salary", R.drawable.salary,R.color.category1));
        categories.add(new Category("Loan",R.drawable.loan,R.color.category2));
        categories.add(new Category("Business",R.drawable.busness,R.color.category3));
        categories.add(new Category("Investment",R.drawable.trans,R.color.category4));
        categories.add(new Category("Rent",R.drawable.rend,R.color.category5));
        categories.add(new Category("Other",R.drawable.others,R.color.category6));
    }

    public static Category getCategoryDitails(String accountName){
        for (Category category:
             categories) {
            if (category.getCategoryName().equals(accountName)){
                return category;
            }

        }
   return null;

    }

    public static int getAccountsColor(String accountName) {
        switch (accountName) {
            case "Bank":
                return R.color.bankColor;
            case "Cash":
                return R.color.cashColor;
            case "Card":
                return R.color.cartColor;
            default:
                return R.color.defultColo;
        }
    }
}
