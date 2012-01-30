
package models;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;
import controllers.CRUD.Exclude;
import java.io.FileInputStream;
import java.io.IOException;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
@Table(name = "File")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.INTEGER
)
abstract public class File extends Model {

	@Required
	@MaxSize(100)
	@Basic(optional = false)
    @Column(name = "title")
	public String title;
	
	@Exclude
	@Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
	public Date created;
	
	@Exclude
	@Required
	@Basic(optional = false)
    @Column(name = "name")
	public String name;
	
	@Exclude
	@Column(name = "pathName")
	public String path;
	
	@Exclude
	@Required
	@Basic(optional = false)
    @Column(name = "fileSize")
	public long size;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "file")
//	public List<Task> taskList;
//	
//	@OneToMany(mappedBy = "file1")
//	public List<Task> taskList1;
//	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "file2")
//	public List<Task> taskList2;
	
	@Exclude
	@Required
	@JoinColumn(name = "fileData", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public FileData data;
	
//	@JoinColumn(name = "type", referencedColumnName = "id")
//	@ManyToOne
//	public FileType type;

	public File() {
	}
	
	public File(java.io.File file) throws IOException {
		size = file.length();
		name = file.getName();
		created = new Date();
		final byte[] fileData = new byte[(int)size];
		if(new FileInputStream(file).read(fileData) != size)
			throw new IOException("File creation failed");
		data = new FileData(fileData);	
	}


	@Override
	public void _save() {
		if(created == null)
			created = new Date();
		super._save();
	}
	
	@Override
	public String toString() {
		return title;
	}
	
}
