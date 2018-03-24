package ES;

import ES.Components.Component;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class MappedEntitySystem extends EntitySystem {

    private HashMap<Class, HashMap<UUID, Component>> components = new HashMap();

    @Override
    public <T extends Component> T getComponent(UUID uuid, Class<T> c) {
        HashMap<UUID, Component> store = components.get(c);
        if(store != null){
            T component = (T) store.get(uuid);
            if(component != null){
                return component;
            }else{
                throw new IllegalArgumentException("Entity " + uuid + " does not have the component: " + c.getName());
            }
        }else{
            throw new IllegalArgumentException("Component does not exist: " + c.getName() + " does not is not available in this system");
        }
    }

    @Override
    public void removeComponent(UUID uuid, Class c) {
        HashMap<UUID, ? extends Component> store = components.get(uuid);
        if(store != null){
            store.remove(uuid);
        }else{
            throw new IllegalArgumentException("Component does not exist: " + c.getName() + " does not is not available in this system");
        }
    }

    @Override
    public LinkedList<UUID> getEntitiesWith(Class... componentList) {
        LinkedList entities = new LinkedList();
        if(componentList.length > 0){
            HashMap<UUID, ? extends Component> store = components.get(componentList[0]);
            if(store != null){
                for (UUID uuid : store.keySet()) {
                    if(entityHas(uuid, componentList)){
                        entities.add(uuid);
                    }
                }
            }
        }
        return entities;
    }

    @Override
    public LinkedList<UUID> getEntitiesWith(Class component) {
        LinkedList entities = new LinkedList();
        HashMap<UUID, ? extends Component> store = components.get(component);
        if(store != null){
            for (UUID uuid : store.keySet()) {
                entities.add(uuid);
            }
        }
        return entities;
    }

    @Override
    public boolean entityHas(UUID uuid, Class... comps) {
        HashMap<UUID, ? extends Component> store;
        for (Class component : comps) {
            store = components.get(component);
            if(store != null){
                if(!store.containsKey(uuid)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void addComponent(UUID uuid, Component c) {
        if(!components.containsKey(c.getClass())){
            components.put(c.getClass(), new HashMap());
        }
        HashMap<UUID, Component> store = components.get(c.getClass());
        store.put(uuid, c);
    }

    @Override
    public <T extends Component>Collection<T> getComponents(Class<T> component){
        LinkedList<T> list = new LinkedList();
        HashMap<UUID, Component> store = components.get(component);
        if(store != null){
            for (Component c : store.values()) {
                list.add((T)c);
            }
        }
        return list;
    }
}
