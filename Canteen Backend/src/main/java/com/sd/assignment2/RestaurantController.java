package com.sd.assignment2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TypesRepository typesRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    private Cache cache = new Cache();

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants()
    {
        return  restaurantRepository.findAll();
    }

    @GetMapping("/adminRestaurants/{id}")
    public List<Restaurant> getRestaurantsByAdmin(@PathVariable Long id)
    {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Restaurant> result = new ArrayList<>();

        for(Restaurant restaurant: restaurants)
            if(restaurant.getAdmin().getId() == id)
                result.add(restaurant);

        return result;
    }

    @PostMapping(value = "/addRestaurant/{id}/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addRestaurant(@RequestBody Restaurant restaurant, @PathVariable Long id) {

            Map<String, String> map = new HashMap<>();
            map.put("Response:", "Success");

            if(userRepository.existsById(id))
                userRepository.findById(id).ifPresent(user ->{
                    System.out.println(user.toString());
                    restaurant.setAdmin(user);
                    user.addRestaurant(restaurant);
                    restaurantRepository.save(restaurant);
                    userRepository.save(user);

                });

           return  map;
    }

    @GetMapping("/getCategory/{id}")
    public Optional<Set<Category>> getCategory(@PathVariable Long id)
    {
            return restaurantRepository.findById(id).map(restaurant -> {
                System.out.println(restaurant.getCategories());
               return  restaurant.getCategories();
            }
            );
    }

    @GetMapping("/getAllTypes")
    public List<Type> getCategories()
    {
        return  typesRepository.findAll();
    }


    @GetMapping("/getSubcategoriesRestaurant/{id}/{typeId}/")
    public Optional<Set<Subcategory>> getSubcategories(@PathVariable Long id, @PathVariable Long typeId)
    {
        Set<Subcategory> result = new HashSet<>();

        return restaurantRepository.findById(id).map(restaurant -> {
            if(typeId != -1) {
                for (Category category : restaurant.getCategories())
                    if (category.type.getId() == typeId)
                        return category.getSubcategories();
            }

            else
                for (Category category1 : restaurant.getCategories())
                    for(Subcategory subcategory : category1.getSubcategories())
                        result.add(subcategory);

                    return  result;
        });

    }

    @GetMapping("/orderHistory/{id}")
    public Set<Order> getOrders(@PathVariable("id") Long restaurantId)
    {
        Set<Order> result = new HashSet<>();

       List<Order> list = orderRepository.findAll();

       for(Order order: list)
           if(order.getRestaurantId() == restaurantId)
               result.add(order);

        return result;
    }

    @PostMapping("/addOrder/{number}/{restaurant_id}/{user_id}")
        public Map<String, String> addOrder(@RequestBody Subcategory subcategory, @PathVariable("number") int number, @PathVariable("restaurant_id") int restaurantId, @PathVariable("user_id") int userId){

        Map<String, String> map = new HashMap<>();
        map.put("Response:", "Success");

        Long userIdLong = new Long(userId);

        userRepository.findById(userIdLong).ifPresent(user -> {
            subcategoryRepository.findById(subcategory.getId()).ifPresent(subcategory1 -> {
                Order order = new Order();
                order.setRestaurantId(restaurantId);
                order.setNumber(number);
                order.setDeliveryStatus(false);
                order.setSubcategory(subcategory1);
                order.setUser(user);
                orderRepository.save(order);
            });

        });

        return  map;
    }

    @PostMapping("/addMenu/{restaurant_id}/{type_id}")
    public Map<String, String> addSubcategory(@RequestBody Subcategory subcategory, @PathVariable("restaurant_id") Long id, @PathVariable("type_id") Long typeId)
    {
        Map<String, String> response = new HashMap<>();
        response.put("Response","Success");

        restaurantRepository.findById(id).ifPresent(restaurant -> {
            boolean wasFound = false;
            Set<Category> categories = restaurant.getCategories();
            for(Category category : categories)
                if(category.getType().id == typeId)
                {
                    wasFound = true;
                    category.addSubcategories(subcategory);
                    categoryRepository.save(category);
                }

            if(!wasFound)
            {
                typesRepository.findById(typeId).ifPresent(type -> {
                    Category category = new Category();
                    category.setType(type);
                    Subcategory subcategory1 = subcategoryRepository.save(subcategory);
                    category.addSubcategories(subcategory1);
                    categoryRepository.save(category);
                    restaurant.addCategory(category);
                    restaurantRepository.save(restaurant);
                });

            }
        });


        return response;

    }

    @GetMapping("/getOrders/{id}")
    public List<Order> getOrders(@PathVariable("id") int id)
    {

        List <Order> result = new ArrayList<>();

        long val = System.currentTimeMillis() - cache.getTime();
        System.out.println(val);

        if(val < 30000 && cache.getList()!= null){

            result.addAll(cache.getList());
            System.out.println("From cache");
            cache.setTime(System.currentTimeMillis());
        }
        else{

            List<Order> list = orderRepository.findAll();

            for(Order order: list)
                if(order.getRestaurantId() == id && !order.getDeliveryStatus())
                    result.add(order);

            System.out.println("From db");
            cache.setList(result);
            cache.setTime(System.currentTimeMillis() + val);
        }

        return result;
    }

    @PutMapping("/deliverOrder/{id}")
    public Map<String, String> deliverOrder(@PathVariable("id") Long id)
    {
        Map<String, String> map = new HashMap<>();

        map.put("Response", "Success");

        orderRepository.findById(id).ifPresent(order -> {
            order.setDeliveryStatus(true);
            orderRepository.save(order);
        });

        return map;
    }



}
