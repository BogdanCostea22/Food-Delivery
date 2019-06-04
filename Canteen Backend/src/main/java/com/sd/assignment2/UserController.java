package com.sd.assignment2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public List<User> all()
    {
        return userRepository.findAll();
    }

    @PostMapping("/registerUser")
    public Map<String, String> saveUser(@RequestBody User user){
        Map<String, String> map = new HashMap<>();
        map.put("Response", "Success");

        userRepository.save(user);
        System.out.println("Employee Saved Successfully");

        return map;
    }


    @GetMapping("/login/{username}/{password}")
    public Map<String, String> loginUser(@PathVariable String username, @PathVariable String password){
        User user1  = userRepository.findByUsername(username);
        Map<String, String> map = new HashMap<>();
        map.put("id", "-1");

        if(user1 == null)
            return map;

        map.put("id", user1.getId().toString());
        map.put("role", Boolean.toString(user1.isRole()));

        user1.setActivate(true);
        userRepository.save(user1);

        return map;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteUser(@PathVariable Long id)
    {
        Map<String, String> map = new HashMap<>();

        map.put("response", "error");

        if(userRepository.findById(id).isPresent()) {
            map.put("response", "success");
            userRepository.findById(id).ifPresent(user1 -> {
                userRepository.delete(user1);
            });

        }
        else
            map.put("response", "error");

        return map;

    }

    @PutMapping("/logout/{id}")
    public Map<String, String> logoutUser(@PathVariable Long id)
    {
        Map<String, String> map = new HashMap<>();

        if(userRepository.findById(id).isPresent()) {
            map.put("response", "success");
            userRepository.findById(id).ifPresent(user1 -> {
                user1.setActivate(false);
                userRepository.save(user1);
            });

        }
        else
            map.put("response", "error");

        return map;
    }
}
