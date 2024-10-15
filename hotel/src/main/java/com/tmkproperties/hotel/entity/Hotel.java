package com.tmkproperties.hotel.entity;

import com.tmkproperties.hotel.constants.HotelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Hotel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long hotelId;

    private Long ownerId;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private HotelType hotelType;

    private String name;
    private String location;
    private String description;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    @Column(nullable = false, unique = true)
    private String slug;

    public void setName(String name) {
        this.name = name;
        this.slug = generateSlug(name);
    }

    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }

}
