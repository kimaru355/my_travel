package com.example.mytravel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Flights extends AppCompatActivity {
    private RecyclerView flightsRecyclerView;
    private SearchView searchView;
    private List<Flight> flightList, filteredList;
    private FlightAdapter flightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        searchView = findViewById(R.id.searchView);
        flightsRecyclerView = findViewById(R.id.flightsRecyclerView);
        flightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        flightList = getSampleFlights();
        filteredList = new ArrayList<>(flightList);
        flightAdapter = new FlightAdapter(filteredList);
        flightsRecyclerView.setAdapter(flightAdapter);
        Button moreFlights = (Button) findViewById(R.id.btnMoreFlights);

        moreFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/travel/flights"));
                startActivity(i);
            }
        });

        // Handle SearchView text input for filtering flights
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFlights(newText);
                return true;
            }
        });
    }

    // Sample flight data
    private List<Flight> getSampleFlights() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight("New York", "10:00 AM", "$500"));
        flights.add(new Flight("Los Angeles", "12:00 PM", "$450"));
        flights.add(new Flight("London", "5:00 PM", "$700"));
        return flights;
    }

    // Filter flights based on search query
    private void filterFlights(String query) {
        filteredList.clear();
        for (Flight flight : flightList) {
            if (flight.getDestination().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(flight);
            }
        }
        flightAdapter.notifyDataSetChanged();
    }

    // Adapter for displaying flights
    private class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
        private List<Flight> flights;

        public FlightAdapter(List<Flight> flights) {
            this.flights = flights;
        }

        @Override
        public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.flight_item, parent, false);
            return new FlightViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FlightViewHolder holder, int position) {
            Flight flight = flights.get(position);
            holder.destinationTextView.setText(flight.getDestination());
            holder.timeTextView.setText(flight.getTime());
            holder.priceTextView.setText(flight.getPrice());
            holder.bookButton.setOnClickListener(v ->
                    Toast.makeText(Flights.this, "Booking flight to " + flight.getDestination(), Toast.LENGTH_SHORT).show()
            );
        }

        @Override
        public int getItemCount() {
            return flights.size();
        }

        class FlightViewHolder extends RecyclerView.ViewHolder {
            TextView destinationTextView, timeTextView, priceTextView;
            Button bookButton;

            public FlightViewHolder(View itemView) {
                super(itemView);
                destinationTextView = itemView.findViewById(R.id.destinationTextView);
                timeTextView = itemView.findViewById(R.id.timeTextView);
                priceTextView = itemView.findViewById(R.id.priceTextView);
                bookButton = itemView.findViewById(R.id.bookButton);
            }
        }
    }

    // Flight Model Class
    private class Flight {
        private String destination, time, price;

        public Flight(String destination, String time, String price) {
            this.destination = destination;
            this.time = time;
            this.price = price;
        }

        public String getDestination() { return destination; }
        public String getTime() { return time; }
        public String getPrice() { return price; }
    }
}