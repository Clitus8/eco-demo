package org.example.ecobe.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecobe.dto.CategoryDto;

import java.util.Set;

@Entity
@AllArgsConstructor
@Builder
@Table(name="category")
@Data
@NoArgsConstructor
public class  Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Product> products; // Use Set to store multiple product

    public CategoryDto getDto(){
        return CategoryDto.builder()
                .id(id)
                .description(description)
                .name(name)
                .build();
    }

}

