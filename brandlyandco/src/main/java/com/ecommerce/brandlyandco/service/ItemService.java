package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.Item;
import com.ecommerce.brandlyandco.entity.SubCategory;
import com.ecommerce.brandlyandco.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }


    public List<Item> getAllSubCategories() {
        return itemRepository.findAll();
    }




    public Optional<Item> getSubCategoryById(int id) {
        return itemRepository.findById(id);
    }


    public Item updateItem(int id, Item updatedItem) {
        return itemRepository.findById(id).map(existing -> {
            existing.setItemname(updatedItem.getItemname());
            existing.setCatagory(updatedItem.getCatagory());
            existing.setSubCategory(updatedItem.getSubCategory());
            return itemRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Item not found with ID " + id));
    }


}
