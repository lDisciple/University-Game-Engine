
package ES;

import ES.Components.Component;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class MetaEntity {
    private UUID id;
    private EntitySystem parent;

    public MetaEntity(UUID id, EntitySystem parent) {
        this.id = id;
        this.parent = parent;
    }

    public MetaEntity(EntitySystem parent) {
        this.id = EntitySystem.generateEntity();
        this.parent = parent;
    }

    public UUID getId() {
        return id;
    }
    
    /**
     * Get the component {@code c}
     * @param <T> Class of the component to retrieve
     * @param c Class of the component to retrieve
     * @return The component.
     */
    public <T extends Component> T getAs(Class<T> c) {
        return parent.getComponent(id, c);
    }
    
    /**
     * Remove the component {@code c}
     * @param c Class of the component to remove
     */
    public void remove(Class c) {
        parent.removeComponent(id, c);
    }
    
    /**
     * Add the component {@code c}
     * @param c The component to remove
     */
    public void add(Component c) {
        parent.addComponent(id, c);
    }
}
