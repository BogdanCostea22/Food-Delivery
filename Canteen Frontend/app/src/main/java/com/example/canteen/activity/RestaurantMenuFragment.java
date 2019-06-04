package com.example.canteen.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.canteen.R;
import com.example.canteen.adapter.TypeAdapter;;
import com.example.canteen.model.Order;
import com.example.canteen.model.Subcategory;
import com.example.canteen.model.Type;
import com.example.canteen.network.RestaurantService;
import com.example.canteen.network.RetrofitInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantMenuFragment extends Fragment implements iOnClickListener{
    //Constants
    static final String TAG = RestaurantMenuFragment.class.getName();

    //Variables
    private Spinner spinner;
    private List<String> list;
    private RecyclerView recyclerView;
    private Long typeId = new Long(-1);
    private Long category_id = new Long(-1);
    private FloatingActionButton floatingActionButton;
    private TypeAdapter adapter;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_menu, container, false);

        spinner = view.findViewById(R.id.spinner_category);

        //Set floating action button
        floatingActionButton = view.findViewById(R.id.add_new_category);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog();
            }
        });

        if(LoginFragment.role) {
            floatingActionButton.setVisibility(View.VISIBLE);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddCategoryDialog();
                }
            });

            //Set view orders button
            Button orders = view.findViewById(R.id.view_orders_button);

            //Set visibility
            orders.setVisibility(View.VISIBLE);

            orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);

                    Call<List<Order>> call = service.getOrders(Integer.parseInt(RestaurantFragment.restaurantId.toString()));

                    call.enqueue(new Callback<List<Order>>() {
                        @Override
                        public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                            Log.i("Response orders", response.body().toString());

                            showOrdersHistoryDialog(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<Order>> call, Throwable t) {
                            Log.i("Response orders", t.toString());
                        }
                    });
                }
            });
        }

        RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);
        Call<List<Type>> call = service.getTypes();
        call.enqueue(new Callback<List<Type>>() {
            @Override
            public void onResponse(Call<List<Type>> call, Response<List<Type>> response) {
                Log.i("Response", response.body().toString());

                list = new ArrayList<>();
                list.add("All");
                for(Type type: response.body())
                    list.add(type.getName());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Type>> call, Throwable t) {
                Log.i("Response1", t.toString());
            }
        });

        //New Adapter for recycler view
        adapter = new TypeAdapter(getContext(), this);

        //Set spinner listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).equals("All"))
                    typeId = new Long(-1);
                else
                    typeId = new Long(position);
                RestaurantService service1 = RetrofitInstance.getRetrofit().create(RestaurantService.class);

                Call<List<Subcategory>> result = service1.getSubcategory(RestaurantFragment.restaurantId, typeId);

                result.enqueue(new Callback<List<Subcategory>>() {
                    @Override
                    public void onResponse(Call<List<Subcategory>> call, Response<List<Subcategory>> response) {
                        Log.i("Response", response.toString() + RestaurantFragment.restaurantId + typeId);
                        adapter.setSubcategory(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Subcategory>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set recyclerView
        recyclerView = view.findViewById(R.id.category_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        retrieveCategories();

        return view;
    }

    public void retrieveCategories(){
        RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);

        Call<List<Subcategory>> result = service.getSubcategory(RestaurantFragment.restaurantId, typeId);

        result.enqueue(new Callback<List<Subcategory>>() {
            @Override
            public void onResponse(Call<List<Subcategory>> call, Response<List<Subcategory>> response) {
                Log.i("Response", response.toString() + RestaurantFragment.restaurantId + typeId);
                adapter.setSubcategory(response.body());
            }

            @Override
            public void onFailure(Call<List<Subcategory>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickListener(Object object) {

        if(!LoginFragment.role)
            showChangeLangDialog((Subcategory) object);

    }

    public void showChangeLangDialog(final Subcategory subcategory) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                boolean numeric = true;

                numeric = edt.getText().toString().matches("-?\\d+(\\.\\d+)?");
                if(numeric) {
                    int number = Integer.parseInt(edt.getText().toString());
                    if(number > 0)
                        {
                            //Send order to server
                            RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);
                            //Create body
                            int rId =  Integer.parseInt(RestaurantFragment.restaurantId.toString());
                            int userId = Integer.parseInt(LoginFragment.id.toString());

                            Call<Map<String, String>> call = service.addOrder(subcategory, number,  rId , userId);

                            call.enqueue(new Callback<Map<String, String>>() {
                                @Override
                                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                    Toast.makeText(getContext(), "Successfully send the order!", Toast.LENGTH_SHORT);
                                    Log.i("Response", response.body().toString());
                                }

                                @Override
                                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                    Toast.makeText(getContext(), "Problem in sending message", Toast.LENGTH_SHORT);
                                    Log.i("Error", t.toString());
                                }
                            });
                        }
                    Log.i("Number", edt.getText().toString());
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showAddCategoryDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.subcategory_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText categoryName = (EditText) dialogView.findViewById(R.id.dialog_subcategory_name);
        final EditText categoryPrice = dialogView.findViewById(R.id.dialog_subcategory_price);
        final Spinner spinner = dialogView.findViewById(R.id.subcategory_spinner);
         final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Set spinner listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).equals("All"))
                    category_id = new Long(-1);
                else
                    category_id = new Long(position);}
         @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Subcategory subcategory = new Subcategory();
                subcategory.setName(categoryName.getText().toString());
                subcategory.setPrice(Integer.parseInt(categoryPrice.getText().toString()));
                RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);
                if(category_id != -1) {

                    Call<Map<String, String>> call = service.addCategory(subcategory, RestaurantFragment.restaurantId, category_id);

                    call.enqueue(new Callback<Map<String, String>>() {
                        @Override
                        public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                            Log.i("Response addCategory", response.body().toString());
                            retrieveCategories();
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                            Log.i("Response addCategory", t.toString());
                        }
                    });
                }
                else
                    ((TextView)spinner.getSelectedView()).setError("Please change the type!");
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showOrdersHistoryDialog(final List<Order> orders) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.orders_layout, null);
        dialogBuilder.setView(dialogView);

        final TextView ordersHistory = dialogView.findViewById(R.id.order_textView);
        final EditText orderId = dialogView.findViewById(R.id.order_id_et);

        int id = 0;

        ordersHistory.setText("");

        for(Order order:orders)
        {
            ordersHistory.append("Order id:" + id + " \n" + order.toString() + "\n");
            id++;
        }

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //Update order status
                RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);
                Call<Map<String, String>> call = service.deliverOrder(orders.get(Integer.parseInt(orderId.getText().toString())).getId());

                call.enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        Log.i("Response Order",  response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Log.i("Response Order", t.toString());
                    }
                });
                //Close the dialog
                dialog.dismiss();
        }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
