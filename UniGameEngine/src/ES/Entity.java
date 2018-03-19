
package ES;

import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class Entity {
    private UUID id;

    public Entity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
