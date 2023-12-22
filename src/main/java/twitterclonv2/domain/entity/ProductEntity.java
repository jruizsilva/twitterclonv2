package twitterclonv2.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    @NotBlank(message = "el nombre es requerido")
    private String name;

    @DecimalMin(value = "0.01",
                message = "el precio debe ser mayor a 0")
    @NotNull(message = "el precio es requerido")
    private BigDecimal price;

}