package shop.Class;

import java.util.ArrayList;

public class mainCart {
    private ArrayList<Product> item;

    public mainCart() {
        item = new ArrayList<>();
    }

    public boolean addItem(Product newProDuct){
        if(item.size() == 0) {
            item.add(newProDuct);
            return true;
        }
        else {
            item.add(newProDuct);
            return true;
        }

    }

    public void removeItem(String name) {
        for (int i =0 ; i < item.size();i++){
            if (item.get(i).getName().equals(name)){
                item.remove(i);
                break;
            }
        }

    }
}
