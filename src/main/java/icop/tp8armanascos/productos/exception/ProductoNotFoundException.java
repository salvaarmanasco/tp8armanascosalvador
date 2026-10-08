package icop.tp8armanascos.productos.exception;

public class ProductoNotFoundException extends Exception {
    public ProductoNotFoundException(Long id) {
        super("No existe un producto con el id " + id);
    }
}
