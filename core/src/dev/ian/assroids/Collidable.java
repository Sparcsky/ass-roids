package dev.ian.assroids;

/**
 * Created by: Ian Parcon
 * Date created: Sep 08, 2018
 * Time created: 9:57 AM
 */
public interface Collidable {

    void accept(CollidableVisitor visitor);
}
