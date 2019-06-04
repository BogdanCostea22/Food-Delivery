package com.sd.assignment2;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToOne(cascade = CascadeType.ALL)
    Type type;



    @OneToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    Set<Subcategory> subcategories;

    public Category() {
        subcategories = new HashSet<>();
    }

    public Category(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<Subcategory> getSubcategories() {
        return subcategories;
    }


    public void setSubcategories(Set<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public void addSubcategories(Subcategory subcategory)
    {
        subcategories.add(subcategory);
    }
}
