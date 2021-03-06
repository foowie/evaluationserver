package models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Programming language
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Entity
@Table(name = "Language")
public class Language extends Model {

	@Required
	@MaxSize(100)
	@Basic(optional = false)
	@Column(name = "name")
	public String name;
	@Required
	@MaxSize(10)
	@Basic(optional = false)
	@Column(name = "extension")
	public String extension;
	@Required
	@MaxSize(20)
	@Basic(optional = false)
	@Column(name = "keyName")
	public String key;

	@Override
	public String toString() {
		return name;
	}
}
