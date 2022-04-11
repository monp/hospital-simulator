package ch.pmo.model;

import java.util.Objects;

public final class Drug {

    private final String name;

    public Drug(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Drug name cannot be null or empty.");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return name.equals(drug.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
