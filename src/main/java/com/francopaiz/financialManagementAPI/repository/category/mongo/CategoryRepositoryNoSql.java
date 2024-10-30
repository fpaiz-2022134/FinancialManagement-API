package com.francopaiz.financialManagementAPI.repository.category.mongo;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.mongo.CategoryMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryNoSql extends MongoRepository<CategoryMongo, String> {

}
