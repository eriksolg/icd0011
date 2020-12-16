package com.example.ex08;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("update item i set i.done = true where i.id =:id")
    @Modifying
    public void markAsDone(@Param("id") Long id);

    Iterable<Item> findByTextContainingIgnoreCase(String searchString);

}
