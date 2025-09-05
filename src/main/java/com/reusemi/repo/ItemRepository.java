package com.reusemi.repo;

import com.reusemi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select distinct i.categoria from Item i where i.categoria is not null")
    List<String> findDistinctCategorias();
}
