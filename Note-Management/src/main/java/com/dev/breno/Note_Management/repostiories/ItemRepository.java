package com.dev.breno.Note_Management.repostiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.breno.Note_Management.models.Item;
import com.dev.breno.Note_Management.models.Nota;

public interface ItemRepository extends JpaRepository<Item, Long>{

	List<Item> findByNota(Nota nota);

}
