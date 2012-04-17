package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name = "FileData")
public class FileData extends Model {

	@Lob
	@Column(name = "fileData")
	@Required
	public byte[] data;

	@Override
	public String toString() {
		return getId().toString();
	}

	public FileData() {
	}

	public FileData(byte[] data) {
		this.data = data;
	}
}
