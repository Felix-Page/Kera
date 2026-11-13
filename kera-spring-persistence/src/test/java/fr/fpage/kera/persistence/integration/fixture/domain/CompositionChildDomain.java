package fr.fpage.kera.persistence.integration.fixture.domain;

import fr.fpage.kera.domain.IdentifiableDomain;

import java.util.UUID;

public class CompositionChildDomain extends IdentifiableDomain<UUID> {

    private final int childOrder;
    private final String label;

    public CompositionChildDomain(final UUID id, final int childOrder, final String label) {
        super(id);
        this.childOrder = childOrder;
        this.label = label;
    }

    public static CompositionChildDomain create(final int childOrder, final String label) {
        return new CompositionChildDomain(null, childOrder, label);
    }

    public int getChildOrder() {
        return childOrder;
    }

    public String getLabel() {
        return label;
    }
}
