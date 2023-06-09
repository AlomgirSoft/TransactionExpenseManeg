package com.example.nomn.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.nomn.Model.Transaction;
import com.example.nomn.Utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
    Realm realm;
    Calendar calendar;
    public MutableLiveData<RealmResults<Transaction> >mutableLiveData= new MutableLiveData<>();

  public MutableLiveData<Double>totalIncome= new MutableLiveData<>();
 public  MutableLiveData<Double>totalExpense= new MutableLiveData<>();
   public MutableLiveData<Double>totalAmount= new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setUpdatabase();
    }


public void getTransaction(Calendar calendar){
        this.calendar=calendar;

    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    double income=0;
    double expense=0;
    double total=0;
    RealmResults<Transaction> transactionRealmResults=null;
     if (Constants.SELECTED_TAB==Constants.DAILY) {

           transactionRealmResults = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", calendar.getTime())
                 .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                 .findAll();


     income = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", calendar.getTime())
                 .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                 .equalTo("type", Constants.INCOME)
                 .sum("amount")
                 .doubleValue();


     expense = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", calendar.getTime())
                 .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                 .equalTo("type", Constants.EXPENSE)
                 .sum("amount")
                 .doubleValue();

         total = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", calendar.getTime())
                 .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                 .sum("amount")
                 .doubleValue();
//
//    RealmResults<Transaction>transactionRealmResults=realm.where(Transaction.class)
//            .equalTo("date",calendar.getTime())
//            .findAll();



     } else if (Constants.SELECTED_TAB ==Constants.MONTHLY) {
          calendar.set(Calendar.DAY_OF_MONTH,0);

          Date startTime=calendar.getTime();
         calendar.add(Calendar.MONTH,1);
         Date endTime = calendar.getTime();

        transactionRealmResults = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", startTime)
                 .lessThan("date",endTime)
                 .findAll();



         income = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", startTime)
                 .lessThan("date",endTime)
                 .equalTo("type", Constants.INCOME)
                 .sum("amount")
                 .doubleValue();


         expense = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", startTime)
                 .lessThan("date",endTime)
                 .equalTo("type", Constants.EXPENSE)
                 .sum("amount")
                 .doubleValue();

         total = realm.where(Transaction.class)
                 .greaterThanOrEqualTo("date", startTime)
                 .lessThan("date",endTime)

                 .sum("amount")
                 .doubleValue();
     }
    totalIncome.setValue(income);
    totalExpense.setValue(expense);
    totalAmount.setValue(total);
    mutableLiveData.setValue(transactionRealmResults);
}
    public void addTransaction(Transaction transaction){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transaction);

        realm.commitTransaction();
    }

    public void  deleteTransaction(Transaction transaction){
        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
        getTransaction(calendar);
    }


    public void addTransactions(){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Business","Bank","some about note",new Date(),500.5,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Investment","Cash","some about note",new Date(),500.5,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Salary","Cart","some about note",new Date(),500.5,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Loan","Other","some about note",new Date(),500.5,new Date().getTime()));
        realm.commitTransaction();
    }



    void   setUpdatabase(){


        realm =Realm.getDefaultInstance();

    }
}
