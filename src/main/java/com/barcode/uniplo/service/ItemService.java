package com.barcode.uniplo.service;

import com.barcode.uniplo.dao.ItemRepository;
import com.barcode.uniplo.domain.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void addItem(ItemDto itemDto) {
        itemRepository.insertItem(itemDto);
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.selectAllItems();
    }

    public ItemDto getItemById(Integer id) {
        return itemRepository.selectItemById(id);
    }

    public List<ItemDto> findByGender(String genderCode) {
        return itemRepository.findByGenderCode(genderCode);
    }
}
