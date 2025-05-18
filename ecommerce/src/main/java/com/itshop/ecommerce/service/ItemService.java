package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Item;
import com.itshop.ecommerce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> getById(int id) {
        return itemRepository.findById(id);
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item update(int id, Item updatedItem) {
        return itemRepository.findById(id).map(existing -> {
            existing.setName(updatedItem.getName());
            existing.setCcBuilder(updatedItem.getCcBuilder());
            return itemRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
    }

    public void delete(int id) {
        itemRepository.deleteById(id);
    }
}
