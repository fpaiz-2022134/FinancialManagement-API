package com.francopaiz.financialManagementAPI.model.mongo;

import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/*@AllArgsConstructor
@NoArgsConstructor*/


@Data

@Document(collection = "category")
public class CategoryMongo {

    @Id
    private String id;
    private String name;

    public CategoryMongo() {
    }

    public CategoryMongo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

