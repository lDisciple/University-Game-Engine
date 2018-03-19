
package ES;

import ES.Components.Component;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public abstract class EntitySystem {
    
    /**
     * Get the component {@code c} from the entity with the UUID {@code uuid}.
     * @param <T> The class of component
     * @param uuid The entity that contains the component.
     * @param c The component class.
     * @return The component of type {@code T}.
     */
    public abstract <T extends Component>T getComponent(UUID uuid, Class<T> c);
    /**
     * Get the component {@code c} from the entity with the UUID {@code uuid}.
     * @param <T> The class of component
     * @param entity The entity that contains the component.
     * @param c The component class.
     * @return The component of type {@code T}.
     */
    public <T extends Component>T getComponent(Entity entity, Class<T> c){
        return getComponent(entity.getId(), c);
    };
    
    /**
     * Removes a component associated with the entity with UUID of {@code uuid}.
     * @param uuid The UUID of the entity.
     * @param c The component to delete.
     */
    public abstract void removeComponent(UUID uuid, Class c);
    /**
     * Removes a component associated with the entity.
     * @param entity The entity.
     * @param c The component to delete.
     */
    public void removeComponent(Entity entity, Class c){
        removeComponent(entity.getId(), c);
    }
    
    /**
     * Adds a component to the entity with UUID of {@code uuid}.
     * @param uuid The UUID of the entity.
     * @param c The component to add.
     */
    public abstract void addComponent(UUID uuid, Component c);
    /**
     * Removes a component to the entity.
     * @param entity The entity.
     * @param c The component to add.
     */
    public void addComponent(Entity entity, Component c){
        addComponent(entity.getId(), c);
    }
    
    /**
     * Returns entities with the specified components
     * @param components Component classes
     * @return UUIDs of matching entities.
     */
    public abstract LinkedList<UUID> getEntitiesWith(Class... components);
    
    /**
     * Returns entities with the specified component
     * @param component Component class
     * @return UUIDs of matching entities.
     */
    public abstract LinkedList<UUID> getEntitiesWith(Class component);
    
    /**
     * Returns all components of type {@code component}
     * @param <T> Component class
     * @param component Component class
     * @return UUIDs of matching entities.
     */
    public abstract <T extends Component>Collection<T> getComponents(Class<T> component);
    
    /**
     * Checks if the entity has the specified components
     * @param uuid UUID of the entity.
     * @param components Component classes
     * @return True if the entity has the specified components.
     */
    public abstract boolean entityHas(UUID uuid, Class... components);
    
    /**
     * Checks if the entity has the specified components
     * @param entity The entity.
     * @param components Component classes
     * @return True if the entity has the specified components.
     */
    public boolean entityHas(Entity entity, Class... components){
        return entityHas(entity.getId(), components);
    }
    
    public static UUID generateEntity(){
        return UUID.randomUUID();
    }
}
