package models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * User role in system
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Entity
@Table(name = "Role")
public class Role extends Model {

	@Required
	@MaxSize(100)
	@Basic(optional = false)
	@Column(name = "name")
	public String name;
	@Required
	@MaxSize(20)
	@Basic(optional = false)
	@Column(name = "keyName")
	public String key;

	/**
	 * Is this user in (any) given role
	 * @param key comma separated roles
	 * @return 
	 */
	public boolean is(String key) {
		String[] keys = key.split(",");
		for (String k : keys) {
			if (this.key.equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Roles that could be checked
	 */
	public static class Check {

		public static final String ADMIN = "admin";
		public static final String CONTESTANT = "contestant";
		public static final String ADMIN_OR_CONTESTANT = "admin,contestant";
	}
}
