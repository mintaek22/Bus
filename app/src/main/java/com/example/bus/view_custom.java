package com.example.bus;

import static com.example.bus.MainActivity.in_bus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class view_custom extends RecyclerView.Adapter<view_custom.CustomViewHolder> {

    protected static ArrayList<data> list;

    public view_custom(ArrayList<data> list) { view_custom.list = list; }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_layout,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bus_st.setText(list.get(position).getStationNm());

        String direction=list.get(position).getDirection();

        for(int[] check :in_bus) {
            if ((check)[0] == position) {
                if (direction.equals("0")) {
                    if (check[2] == 0)
                        holder.bus_line.setImageResource(R.drawable.busing_line_blue);
                    else holder.bus_line.setImageResource(R.drawable.busline_blue);
                } else {
                    if (check[2] == 0) holder.bus_line.setImageResource(R.drawable.busing_line_red);
                    else holder.bus_line.setImageResource(R.drawable.busline_red);
                }
                switch (check[1]) {
                    case 4:
                        holder.congestion.setImageResource(R.drawable.soso);
                        break;
                    case 5:
                    case 6:
                        holder.congestion.setImageResource(R.drawable.bad);
                        break;
                    default:
                        holder.congestion.setImageResource(R.drawable.smile);
                }
                return;
            }
        }
        if(direction.equals("0")) holder.bus_line.setImageResource(R.drawable.inline_blue);
        else  holder.bus_line.setImageResource(R.drawable.inline_red);
        holder.congestion.setImageResource(R.drawable.whitepaper);

    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size():0);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView bus_line;
        protected TextView bus_st;
        protected ImageView congestion;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bus_line = itemView.findViewById(R.id.bus_line);
            this.bus_st = itemView.findViewById(R.id.bus_st);
            this.congestion=itemView.findViewById(R.id.congestion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        forThread forThread = new forThread("bus_arrive", list.get(pos).getSt_id(), Integer.toString(pos + 1));
                        forThread.start();
                        try {
                            forThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String bus_arrive_xml = forThread.getResult();
                        String[] time = new String[2];
                        try {
                            time= com.example.bus.xml_parser.xml_parsing3(bus_arrive_xml);
                        } catch (ParserConfigurationException | IOException | SAXException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(view.getContext(),time[0]+" / "+time[1], Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}

