package fr.fpage.kera.persistence.integration.fixture.entity;

import fr.fpage.kera.persistence.entity.IdentifiableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "test_composition_child")
public class CompositionChildEntity implements IdentifiableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int childOrder;

    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aggregate_id", nullable = false)
    private AggregateRootEntity aggregate;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(final UUID id) {
        this.id = id;
    }

    public int getChildOrder() {
        return childOrder;
    }

    public void setChildOrder(final int childOrder) {
        this.childOrder = childOrder;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public AggregateRootEntity getAggregate() {
        return aggregate;
    }

    public void setAggregate(final AggregateRootEntity aggregate) {
        this.aggregate = aggregate;
    }
}
