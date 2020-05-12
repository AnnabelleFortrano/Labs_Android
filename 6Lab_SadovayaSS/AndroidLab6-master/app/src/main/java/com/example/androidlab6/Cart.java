package com.example.androidlab6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Cart {
    private Map<Item, Integer> items;
    private static final Cart ourInstance = new Cart();

    static Cart getInstance() {
        return ourInstance;
    }
    public void addItem(Item item) {
        if(items.get(item) == null) {
            items.put(item, 1);
        } else {
            items.put(item, items.get(item) + 1);
        }
    }

    public Item[] getItemsArray() {
        return items.keySet().toArray(new Item[items.size()]);
    }

    public int getCount(Item item) {
        if(items.get(item) == null)
            return 0;
        return items.get(item);
    }

    public void  deleteItem(Item item) {
        if(items.get(item) != null) {
            if(items.get(item) == 1) {
                items.remove(item);
                return;
            }
            items.put(item, items.get(item) - 1);
        }
    }

    public void updateItem(Item updatedItem) {
        for(Item item : items.keySet()) {
            if(item.getId() == updatedItem.getId()) {
                if(items.get(item) > updatedItem.getCount()) {
                    items.remove(item);
                } else {
                    Integer count = items.get(item);
                    items.remove(item);
                    items.put(updatedItem, count);
                }
            }
        }
    }

    public void clearCart() {
        items.clear();
    }


    public Map<Item, Integer> getItems() {
        return items;
    }

    private Cart() {
        items = new HashMap<>();
    }
}
