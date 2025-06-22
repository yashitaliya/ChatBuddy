package com.example.chatbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;


public class messagesAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<msgModelClass> messagesAdpterArrayList;
    int ITEM_SEND=1;
    int ITEM_RECIVE=2;

    public messagesAdapter(Context context, ArrayList<msgModelClass> messagesAdpterArrayList) {
        this.context = context;
        this.messagesAdpterArrayList = messagesAdpterArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new senderVierwHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_layout, parent, false);
            return new reciverViewHolder(view);
        }

    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        msgModelClass messages = messagesAdpterArrayList.get(position);
//        if (holder.getClass()==senderVierwHolder.class){
//            senderVierwHolder viewHolder = (senderVierwHolder) holder;
//            viewHolder.msgtxt.setText(messages.getMessage());
//        }else {
//            reciverViewHolder viewHolder = (reciverViewHolder) holder;
//            viewHolder.msgtxt.setText(messages.getMessage());
//        }
//        /*holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                new AlertDialog.Builder(context).setTitle("Delete")
//                        .setMessage("Are you sure you want to delete this message?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).show();
//
//                return false;
//            }
//        });
//    */
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelClass messages = messagesAdpterArrayList.get(position);
        if (holder instanceof senderVierwHolder) {
            Log.d("Adapter", "Binding sender message: " + messages.getMessage());
            ((senderVierwHolder) holder).msgtxt.setText(messages.getMessage());
        } else if (holder instanceof reciverViewHolder) {
            Log.d("Adapter", "Binding receiver message: " + messages.getMessage());
            ((reciverViewHolder) holder).msgtxtt.setText(messages.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return messagesAdpterArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelClass messages = messagesAdpterArrayList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderid())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECIVE;
        }
    }

    class  senderVierwHolder extends RecyclerView.ViewHolder {
        TextView msgtxt;
        public senderVierwHolder(@NonNull View itemView) {
            super(itemView);
            msgtxt = itemView.findViewById(R.id.msgsendertyp);

        }
    }
    class reciverViewHolder extends RecyclerView.ViewHolder {
        TextView msgtxtt;
        public reciverViewHolder(@NonNull View itemView) {
            super(itemView);
            msgtxtt = itemView.findViewById(R.id.recivertextset);
        }
    }
}