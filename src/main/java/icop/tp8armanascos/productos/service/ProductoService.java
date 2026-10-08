package icop.tp8armanascos.productos.service;

import icop.tp8armanascos.productos.dto.ProductoRequestDTO;
import icop.tp8armanascos.productos.dto.ProductoResponseDTO;
import icop.tp8armanascos.productos.entity.Producto;
import icop.tp8armanascos.productos.exception.ProductoNotFoundException;
import icop.tp8armanascos.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        validar(dto);
        Producto producto = repository.save(toEntity(dto));
        return toResponseDTO(producto);
    }

    public List<ProductoResponseDTO> listar() {
        return toResponseList(repository.findAll());
    }

    public ProductoResponseDTO obtenerPorId(Long id) throws ProductoNotFoundException {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return toResponseDTO(producto);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto)
            throws ProductoNotFoundException {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        validar(dto);
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return toResponseDTO(repository.save(producto));
    }

    public void eliminar(Long id) throws ProductoNotFoundException {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        repository.delete(producto);
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return toResponseList(repository.findByNombre(nombre));
    }

    private void validar(ProductoRequestDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (dto.getPrecio() == null || !Double.isFinite(dto.getPrecio()) || dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock debe ser cero o mayor");
        }
    }

    private Producto toEntity(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return producto;
    }

    private ProductoResponseDTO toResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setDisponible(producto.getStock() > 0);
        return dto;
    }

    private List<ProductoResponseDTO> toResponseList(List<Producto> productos) {
        List<ProductoResponseDTO> respuesta = new ArrayList<>();
        for (Producto producto : productos) {
            respuesta.add(toResponseDTO(producto));
        }
        return respuesta;
    }
}
