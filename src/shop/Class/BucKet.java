package shop.Class;

public class BucKet {
    private mainCart[] carts;

    public BucKet() {
        carts = new mainCart[20];
    }

    public boolean addItem(Product newProDuct) {
        for (int i = 0; i < 20; i++) {
            if (carts[i] == null) {
                carts[i].addItem(newProDuct);

            }
        }
        return true;
    }

    public void removeItem(String name) {
        for (int i = 0; i < 20; i++) {
            if (carts[i] == null) {
                carts[i].removeItem(name);

            }
        }
    }
}
