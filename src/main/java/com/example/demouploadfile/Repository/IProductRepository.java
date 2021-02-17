package com.example.demouploadfile.Repository;


import com.example.demouploadfile.Model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends CrudRepository<Product, Long> {
}
