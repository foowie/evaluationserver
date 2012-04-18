package models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Type of file
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Entity
@Table(name = "FileType")
public class FileType extends Model {

	@Required
	@MaxSize(100)
	@Basic(optional = false)
	@Column(name = "name")
	public String name;

	@Override
	public String toString() {
		return name;
	}
}
