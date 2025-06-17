package com.rating_system.rating_system;

public class ProductSummaryDTO {
    private Long id;
    private String name;
    private String description;
    private double averageRating;

    public ProductSummaryDTO(Long id, String name, String description, double averageRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.averageRating = averageRating;
    }

    // Getters only (DTOs usually don't need setters)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getAverageRating() { return averageRating; }
}
