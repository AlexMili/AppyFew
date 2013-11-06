package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fxkun_000
 * Date: 19/10/13
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
/* @Entity */
public class User { //} extends Model{

    /* @Id */
    public String _id;

    public String name;
    public List<String> adids;
}
