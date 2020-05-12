package com.example.androidlab7;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

class ItemService {
    int maxId = 0;
    boolean isLocked = false;
    Random random = new Random();
    private List<Item> items;
    private List<DataChangedListener> listeners;
    private static final ItemService ourInstance = new ItemService();

    static ItemService getInstance() {
        return ourInstance;
    }

    public List<Item> getAvailableItems() {
        List<Item> availableItems = new LinkedList<>();
        for(Item item : items) {
            if(item.getCount() > 0)
                availableItems.add(item);
        }
        return availableItems;
    }

    public void addDataChangedListener(DataChangedListener listener) {
        listeners.add(listener);
    }

    public void addItem(final Item newItem) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Item, Void, Void> task = new AsyncTask<Item, Void, Void>() {
            @Override
            protected Void doInBackground(Item... itemList) {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt() % 2 + 3);
                    while (isLocked) {
                        TimeUnit.MILLISECONDS.sleep(300);
                    }
                    synchronized (items) {
                        isLocked = true;
                        Item newItem = itemList[0];
                        newItem.setId(maxId + 1);
                        maxId +=1;
                        items.add(newItem);
                    }
                    isLocked = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listeners.forEach(new Consumer<DataChangedListener>() {
                    @Override
                    public void accept(DataChangedListener e) {
                        e.notifyDataChanged();
                    }
                });
            }
        };
        task.execute(newItem);
    }

    public void deleteItem(int id) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                try {
                    while (isLocked) {
                        TimeUnit.MILLISECONDS.sleep(200);
                    }
                    int id = integers[0];
                    for(Item item : items) {
                        if(item.getId() == id)
                            items.remove(item);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listeners.forEach(new Consumer<DataChangedListener>() {
                    @Override
                    public void accept(DataChangedListener e) {
                        e.notifyDataChanged();
                    }
                });
            }
        };
        task.execute(id);
    }

    public void updateItem(final Item updatedItem) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Item, Void, Void> task = new AsyncTask<Item, Void, Void>() {
            @Override
            protected Void doInBackground(Item... itemList) {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt() % 2 + 3);
                    while (isLocked) {
                        TimeUnit.MILLISECONDS.sleep(300);
                    }
                    synchronized (items) {
                        isLocked = true;
                        Item updatedItem = itemList[0];
                        for(Item item :items) {
                            if(item.getId() == updatedItem.getId()) {
                                items.set(items.indexOf(item), updatedItem);
                            }
                        }
                        Cart.getInstance().updateItem(updatedItem);
                    }
                    isLocked = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listeners.forEach(new Consumer<DataChangedListener>() {
                    @Override
                    public void accept(DataChangedListener e) {
                        e.notifyDataChanged();
                    }
                });
            }
        };
        task.execute(updatedItem);
    }

    public void removeListener(DataChangedListener listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public List<Item> getItems() {
        return items;
    }

    public void performPurchase(final Context context) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt() % 2 + 3);
                    while(isLocked) {
                        TimeUnit.MILLISECONDS.sleep(200);
                    }
                    synchronized (items) {
                        isLocked = true;
                        Cart cart = Cart.getInstance();
                        for(Item item : cart.getItemsArray()) {
                            if(item.getCount() < cart.getCount(item) || items.indexOf(item) < 0) {
                                Cart.getInstance().clearCart();
                                return false;
                            }
                        }
                        for(Item item : cart.getItemsArray()) {
                            item.setCount(item.getCount() - cart.getCount(item));
                        }
                        Cart.getInstance().clearCart();
                        isLocked = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isCompleted) {
                if(isCompleted) {
                    Toast.makeText(context, "Покупка совершена", Toast.LENGTH_SHORT).show();
                    listeners.forEach(new Consumer<DataChangedListener>() {
                        @Override
                        public void accept(DataChangedListener e) {
                            e.notifyDataChanged();
                        }
                    });
                } else {
                    Toast.makeText(context, "Покупка не удалась(", Toast.LENGTH_SHORT).show();
                }

            }
        };
        task.execute();
    }
    private ItemService() {
        items = new LinkedList<>();
        listeners = new LinkedList<>();
        addItem(new Item(1,"pen", 100, 2, "office supplies"));
        addItem(new Item(2,"pencil", 120, 3, "office supplies"));
        addItem(new Item(3,"sharpener", 99, 1, "office supplies"));
        addItem(new Item(4,"napkins", 50, 5, "kitchen supplies"));
    }

}
