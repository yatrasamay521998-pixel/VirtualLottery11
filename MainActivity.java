package com.example.virtuallottery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends Activity {
    TextView title, announcement, lottery, result;
    FirebaseFirestore db;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        title=findViewById(R.id.title); announcement=findViewById(R.id.announcement);
        lottery=findViewById(R.id.lottery); result=findViewById(R.id.result);
        db=FirebaseFirestore.getInstance();

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(t -> {
            // Anonymous customer session. No real-money account is used.
        });

        db.collection("settings").document("app").addSnapshotListener((s,e)->{
            if(e!=null || s==null || !s.exists()) return;
            String t=s.getString("title"), a=s.getString("announcement");
            if(t!=null) title.setText(t);
            if(a!=null) announcement.setText(a);
        });

        db.collection("lotteries").addSnapshotListener((snap,e)->{
            if(e!=null || snap==null) return;
            StringBuilder text=new StringBuilder(); String winnerText="Result pending";
            for(DocumentSnapshot d: snap.getDocuments()){
                String n=d.getString("name"); String st=d.getString("status"); String w=d.getString("winner");
                Long p=d.getLong("points"), tc=d.getLong("tickets");
                text.append(n==null?d.getId():n).append("\n")
                    .append("Virtual Points: ").append(p==null?0:p).append("\n")
                    .append("Tickets: ").append(tc==null?0:tc).append("\n")
                    .append("Status: ").append(st==null?"":st).append("\n\n");
                if(w!=null && !w.isEmpty()) winnerText="Winner Ticket: #"+w;
            }
            lottery.setText(text.length()==0?"No lottery available":text.toString());
            result.setText(winnerText);
        });
    }
}
