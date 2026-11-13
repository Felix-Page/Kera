package fr.fpage.kera.persistence.integration.fixture.entity;

import fr.fpage.kera.persistence.entity.ReferenceableEntity;
import fr.fpage.kera.persistence.entity.VersionedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "test_aggregate_root")
public class AggregateRootEntity implements VersionedEntity<UUID>, ReferenceableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "external_reference_id")
    private ExternalReferenceEntity externalReference;

    @OneToMany(mappedBy = "aggregate", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("childOrder ASC")
    private List<CompositionChildEntity> children = new ArrayList<>();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(final UUID id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public void setVersion(final Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ExternalReferenceEntity getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(final ExternalReferenceEntity externalReference) {
        this.externalReference = externalReference;
    }

    public List<CompositionChildEntity> getChildren() {
        return children;
    }

    public void setChildren(final List<CompositionChildEntity> children) {
        this.children.clear();
        if (children != null) {
            children.forEach(child -> {
                child.setAggregate(this);
                this.children.add(child);
            });
        }
    }
}
